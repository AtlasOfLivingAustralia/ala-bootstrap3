package au.org.ala.bootstrap3

import au.org.ala.web.AuthService
import org.grails.plugins.codecs.DefaultCodecLookup
import spock.lang.Specification
import grails.testing.web.taglib.TagLibUnitTest

class HeaderFooterTagLibSpec extends Specification implements TagLibUnitTest<HeaderFooterTagLib> {

    def setup() {
        tagLib.tagLinkService = new TagLinkService()
        tagLib.tagLinkService.alaBaseURL = 'https://example.com/base-url'
        tagLib.tagLinkService.casLoginUrl = 'https://example.com/cas/login'
        tagLib.tagLinkService.casLogoutUrl = 'https://example.com/cas/logout'
        tagLib.tagLinkService.grailServerURL = 'https://example.com/grailsserverurl'

        tagLib.tagLinkService.codecLookup = new DefaultCodecLookup()
        tagLib.tagLinkService.codecLookup.setGrailsApplication(grailsApplication)
        tagLib.tagLinkService.codecLookup.reInitialize()

        tagLib.tagLinkService.authService = Spy(AuthService) {
            userDetails() >> null
            loginUrl(_) >> "/login"
        }
    }

    def cleanup() {
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
        def expected = "<a href='/login' class='test-css-class'>Log in</a>"
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
