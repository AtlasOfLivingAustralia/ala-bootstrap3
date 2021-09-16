package au.org.ala.bootstrap3

import grails.testing.services.ServiceUnitTest
import org.springframework.http.HttpMethod
import org.springframework.mock.web.MockServletContext
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class TagLinkServiceSpec extends Specification implements ServiceUnitTest<TagLinkService> {

    String logoutRequestUri
    HttpServletRequest logoutRequest
    def setup() {
        def servletContext = new MockServletContext()
        logoutRequestUri = service.grailServerURL+'/some/path/with/params?test&foo=bar'
        logoutRequest = MockMvcRequestBuilders.request(HttpMethod.GET, logoutRequestUri).buildRequest(servletContext)
    }

    def cleanup() {
    }

    void "test clearing cache"() {
        when:
        service.clearCache()

        then:
        service.hfCache.every { it.value.content == '' }
    }

    void "test build default logout URL with no additional parameters"() {
        setup:

        expect:
        service.buildLogoutUrl(logoutRequest) == "${service.grailServerURL}/"
    }

    void "test build logout URL using a defined logout URL"() {
        setup:
        def request = new MockHttpServletRequestBuilder(HttpMethod.GET, 'http://example.com').buildRequest(new MockServletContext())
        def logoutUrlBase = service.grailServerURL + '/logout'

        expect:
        service.buildLogoutUrl(request, null, logoutUrlBase, null) == "${service.grailServerURL}/logout"
    }

    void "test build logout URL excludes CAS logout URL by default"() {
        setup:
        def logoutUrlBase = service.grailServerURL + '/logout'
        def casLogoutUrl = service.casLogoutUrl

        expect:
        service.buildLogoutUrl(logoutRequest, casLogoutUrl, logoutUrlBase, null) == "${service.grailServerURL}/logout"
    }

    void "test build logout URL includes CAS logout URL included if requested"() {
        setup:
        def logoutUrlBase = service.grailServerURL + '/logout'
        def casLogoutUrl = service.casLogoutUrl
        service.includeLogoutCasUrlParam = true

        expect:
        service.buildLogoutUrl(logoutRequest, casLogoutUrl, logoutUrlBase, null) == "${service.grailServerURL}/logout?casUrl=${casLogoutUrl}"

        cleanup:
        service.includeLogoutCasUrlParam = false
    }

    void "test build logout URL excludes Default App return URL by default"() {
        setup:
        def logoutUrlBase = service.grailServerURL + '/logout'
        def casLogoutUrl = service.casLogoutUrl

        expect:
        service.buildLogoutUrl(logoutRequest, casLogoutUrl, logoutUrlBase, null) == "${service.grailServerURL}/logout"
    }

    void "test build logout URL includes Default App return URL if requested"() {
        setup:
        def logoutUrlBase = service.grailServerURL + '/logout'
        def casLogoutUrl = service.casLogoutUrl
        service.includeLogoutDefaultAppUrlParam = true

        def encodedLogoutRequestUri = 'https://bie.ala.org.au/some/path/with/params?test%26foo%3Dbar'

        expect:
        service.buildLogoutUrl(logoutRequest, casLogoutUrl, logoutUrlBase, null) == "${service.grailServerURL}/logout?appUrl=$encodedLogoutRequestUri"

        cleanup:
        service.includeLogoutDefaultAppUrlParam = false
    }

    void "test build logout URL includes Custom App return URL"() {
        setup:
        def logoutUrlBase = service.grailServerURL + '/logout'
        def casLogoutUrl = service.casLogoutUrl
        def returnUrl = "http://example.com/bye"

        expect:
        service.buildLogoutUrl(logoutRequest, casLogoutUrl, logoutUrlBase, returnUrl) == "${service.grailServerURL}/logout?appUrl=$returnUrl"

    }
}
