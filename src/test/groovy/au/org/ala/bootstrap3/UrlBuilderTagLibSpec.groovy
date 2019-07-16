package au.org.ala.bootstrap3

import grails.test.mixin.TestFor
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
        grailsApplication.config.someUrl.pathsList = ['a','{id}']
    }

    def cleanup() {
    }

    void "test createLink"() {
        given:
        def base = 'https://example.org:99'
        def paths = ['a','b','c', '{id}']
        def params = ['a':1,'b':2]
        def vars = [id: 'd']
        def fragment = 'name'

        def base2 = 'https://example.org:99/?c=3'
        def path = '/a/b/c/{id}/'

        expect:
        tagLib.createLink(base: base, paths: paths, params: params, vars: vars, fragment: fragment) == 'https://example.org:99/a/b/c/d?a=1&b=2#name'
        tagLib.createLink(base: base2, paths: path, params: params, vars: vars, fragment: fragment) == 'https://example.org:99/a/b/c/d/?c=3&a=1&b=2#name'
        tagLib.createLink(baseProperty: 'someUrl.baseUrl', paths: paths, params: params, vars: vars) == 'https://example.org/a/b/c/d?a=1&b=2'
        tagLib.createLink(baseProperty: 'someUrl.noTrailing', paths: 'a/b/c/{id}', params: params, vars: vars, fragment: fragment) == 'https://example.org/a/b/c/d?a=1&b=2#name'
        tagLib.createLink(baseProperty: 'someUrl.noTrailing', pathsProperty: 'someUrl.pathsList', vars: vars) == 'https://example.org/a/d'
    }

    def "test link"() {
        expect:
        applyTemplate('<u:link base="https://example.org" paths="a/{id}" params="[a: \'{id}\']" vars="[id: userId]" fragment="dave">${userId}</u:link>', [userId: 1]) == '<a href="https://example.org/a/1?a=1#dave">1</a>'
        applyTemplate('<u:link baseProperty="someUrl.baseUrl" elementId="a" class="btn btn-default" target="_blank">Click me</u:link>') == '<a href="https://example.org/" id="a" class="btn btn-default" target="_blank">Click me</a>'
        applyTemplate('<u:link baseProperty="someUrl.noTrailing" pathsProperty="someUrl.paths" vars="[id: userId]">${userId}</u:link>', [userId: 1]) == '<a href="https://example.org/a/1">1</a>'
    }
}
