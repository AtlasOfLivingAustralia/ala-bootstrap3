package au.org.ala.bootstrap3

import au.org.ala.cas.util.AuthenticationCookieUtils
import grails.test.mixin.TestFor
import org.grails.plugins.codecs.DefaultCodecLookup
import spock.lang.Specification

import javax.servlet.http.Cookie
import java.security.Principal

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(HeaderFooterTagLib)
class HeaderFooterTagLibSpec extends Specification {

    def originalAlaBaseURL
    def originalSecurityCasLoginUrl
    def originalSecurityCasLogoutUrl
    def originalGrailsServerURL

    def setup() {
        originalAlaBaseURL = grailsApplication.config.ala.baseURL
        grailsApplication.config.ala.baseURL = 'https://example.com/base-url'

        originalSecurityCasLoginUrl = grailsApplication.config.security.cas.loginUrl
        grailsApplication.config.security.cas.loginUrl = 'https://example.com/cas/login'

        originalSecurityCasLogoutUrl = grailsApplication.config.security.cas.logoutUrl
        grailsApplication.config.security.cas.logoutUrl = 'https://example.com/cas/logout'

        originalGrailsServerURL = grailsApplication.config.grails.serverURL
        grailsApplication.config.grails.serverURL = 'https://example.com/grailsserverurl'

        tagLib.tagLinkService = new TagLinkService()
        tagLib.tagLinkService.codecLookup = new DefaultCodecLookup()
        tagLib.tagLinkService.codecLookup.setGrailsApplication(grailsApplication)
        tagLib.tagLinkService.codecLookup.reInitialize()
    }

    def cleanup() {
        grailsApplication.config.ala.baseURL = originalAlaBaseURL
        grailsApplication.config.security.cas.loginUrl = originalSecurityCasLoginUrl
        grailsApplication.config.security.cas.logoutUrl = originalSecurityCasLogoutUrl
        grailsApplication.config.grails.serverURL = originalGrailsServerURL
    }

    void "test banner content already retrieved"() {
        when:
        // start test with a clear cache
        tagLib.tagLinkService.clearCache()

        then:
        tagLib.tagLinkService.hfCache['banner'].content == ''

        when:
        // ensure the first call populates the cache
        def firstResult = tagLib.banner()
        def firstContent = tagLib.tagLinkService.hfCache['banner'].content
        def firstTimestamp = tagLib.tagLinkService.hfCache['banner'].timestamp

        then:
        // the result and the cache content won't match - the cached content is transformed for each user
        firstResult
        firstContent != ''
        firstResult != firstContent
        firstTimestamp < new Date().time

        when:
        // try loading the same tag again
        def secondResult = tagLib.banner()
        def secondContent = tagLib.tagLinkService.hfCache['banner'].content
        def secondTimestamp = tagLib.tagLinkService.hfCache['banner'].timestamp

        then:
        // the cache should match the first call
        firstResult == secondResult
        firstContent == secondContent
        firstTimestamp == secondTimestamp
    }

    void "test clearCache"() {
        expect:
        tagLib.clearCache() == ''
    }

    void "test footer"() {
        given:
        def expected = '<footer'

        when:
        def result = tagLib.footer()

        then:
        result
        result.startsWith(expected) || result.startsWith(' ' + expected)
    }

    void "test head"() {
        given:
        def expected = '<!-- head -->'

        when:
        def result = tagLib.head()

        then:
        result
        result?.startsWith(expected)
        tagLib.tagLinkService.hfCache['head']['content']?.startsWith(expected)
    }

    void "test loginLogout with no login session or cookie"() {
        given:
        def expected = "<a href='https://example.com/cas/login?service=https://example.com/a-test-page' class='test-css-class'>Log in</a>"
        def forwardUri = '/a-test-page'

        when:
        request.forwardURI = forwardUri
        def result = tagLib.loginLogout([cssClass: 'test-css-class'])

        then:
        result
        result == expected
    }

    void "test menu - there is no menu.html file"() {
        when:
        def result = tagLib.menu()

        then:
        !result
        !tagLib.tagLinkService.hfCache['menu']['content']
    }

    void "test paginate"() {
        expect:
        tagLib.paginate(total: 1).startsWith('<nav aria-label="Page navigation">')
    }
}
