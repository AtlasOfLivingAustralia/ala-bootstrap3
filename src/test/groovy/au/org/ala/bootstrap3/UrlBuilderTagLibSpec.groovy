package au.org.ala.bootstrap3

import grails.test.mixin.TestFor
import org.grails.plugins.codecs.DefaultCodecLookup
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(UrlBuilderTagLib)
class UrlBuilderTagLibSpec extends Specification {

    def setup() {
        grailsApplication.config.someUrl.baseUrl = 'https://example.org/'
        grailsApplication.config.someUrl.noTrailing = 'https://example.org'
        grailsApplication.config.someUrl.paths = 'a/{id}'
        grailsApplication.config.someUrl.pathsList = ['a', '{id}']

        tagLib.tagLinkService = new TagLinkService()
        tagLib.tagLinkService.codecLookup = new DefaultCodecLookup()
        tagLib.tagLinkService.codecLookup.setGrailsApplication(grailsApplication)
        tagLib.tagLinkService.codecLookup.reInitialize()
    }

    def cleanup() {
    }

    void "test custom createLink 1"() {
        given:
        def base = 'https://example.org:99'
        def paths = ['a', 'b', 'c', '{id}']
        def params = ['a': '{id}', 'b': '&="#']
        def vars = [id: 'd']
        def fragment = 'name'
        def expected = 'https://example.org:99/a/b/c/d?a=d&b=%26%3D%22%23#name'

        expect:
        tagLib.createLink(base: base, paths: paths, params: params, vars: vars, fragment: fragment) == expected
    }

    void "test custom createLink 2"() {
        given:
        def params = ['a': '{id}', 'b': '&="#']
        def vars = [id: 'd']
        def fragment = 'name'
        def base = 'https://example.org:99/?c=3'
        def path = '/a/b/c/{id}/'
        def expected = 'https://example.org:99/a/b/c/d/?c=3&a=d&b=%26%3D%22%23#name'

        expect:
        tagLib.createLink(base: base, paths: path, params: params, vars: vars, fragment: fragment) == expected
    }

    void "test custom createLink 3"() {
        given:
        def base = 'someUrl.baseUrl'
        def paths = ['a', 'b', 'c', '{id}']
        def params = ['a': '{id}', 'b': '&="#']
        def vars = [id: 'd']
        def expected = 'https://example.org/a/b/c/d?a=d&b=%26%3D%22%23'

        expect:
        tagLib.createLink(baseProperty: base, paths: paths, params: params, vars: vars) == expected
    }

    void "test custom createLink 4"() {
        given:
        def base = 'someUrl.noTrailing'
        def paths = 'a/b/c/{id}'
        def params = ['a': '{id}', 'b': '&="#']
        def vars = [id: 'd']
        def fragment = 'name'
        def expected = 'https://example.org/a/b/c/d?a=d&b=%26%3D%22%23#name'

        expect:
        tagLib.createLink(baseProperty: base, paths: paths, params: params, vars: vars, fragment: fragment) == expected
    }

    void "test custom createLink 5"() {
        given:
        def base = 'someUrl.noTrailing'
        def pathsProp = 'someUrl.pathsList'
        def vars = [id: 'd']
        def expected = 'https://example.org/a/d'

        expect:
        tagLib.createLink(baseProperty: base, pathsProperty: pathsProp, vars: vars) == expected
    }

    void "test custom createLink 6"() {
        given:
        def base = 'https://example.org'
        def paths = ['a', 'b']
        def params = ['a': null, 'b': null]
        def expected = 'https://example.org/a/b?a&b'
        expect:
        tagLib.createLink(base: base, paths: paths, params: params) == expected
    }

    def "test link template 1"() {
        given:
        def contents = '<u:link base="https://example.org" paths="a/{id}" params="[a: \'{id}\']" vars="[id: userId]" fragment="dave">${userId}</u:link>'
        def model = [userId: 1]
        def expected = '<a href="https://example.org/a/1?a=1#dave">1</a>'

        expect:
        applyTemplate(contents, model) == expected
    }

    def "test link template 2"() {
        given:
        def contents = '<u:link baseProperty="someUrl.baseUrl" elementId="a" class="btn btn-default" target="_blank">Click me</u:link>'
        def expected = '<a href="https://example.org/" id="a" class="btn btn-default" target="_blank">Click me</a>'

        expect:
        applyTemplate(contents) == expected
    }

    def "test link template 3"() {
        given:
        def contents = '<u:link baseProperty="someUrl.noTrailing" pathsProperty="someUrl.paths" vars="[id: userId]">${userId}</u:link>'
        def model = [userId: 1]
        def expected = '<a href="https://example.org/a/1">1</a>'

        expect:
        applyTemplate(contents, model) == expected
    }
}
