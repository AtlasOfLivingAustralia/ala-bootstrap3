package au.org.ala.bootstrap3

import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

class UrlBuilderTagLib {

    static namespace = 'u'
    static defaultEncodeAs = [taglib:'html']
    static returnObjectForTags = ['createLink']
    static encodeAsForTags = [link: [taglib:'text'], createLink: [taglib: 'none']]

    static final Set<String> LINK_ATTRIBUTES = [
            'base'
            ,'baseProperty'
            ,'pathsProperty'
            ,'paths'
            ,'params'
            ,'vars'
            ,'fragment'
    ]

    /**
     * General linking to URLs based on a base URL etc. Examples:<br/>
     *
     * &lt;u:link baseProperty="userdetails.url" paths="['myprofile']"&gt;link 1&lt;/u:link&gt;<br/>
     * &lt;u:link base="https://example.org/{id}/{page}" vars="[id: 'username', page: 'contact']"&gt;link 2&lt;/u:link&gt;<br/>
     *
     * @attr baseProperty the Grails Config property to get the base url from
     * @attr base The base url
     * @attr pathsProperty the Grails Config property to get the paths from
     * @attr paths A list of paths and/or path segments (or single String path)
     * @attr params A map of query params
     * @attr fragment The URL fragment
     * @attr vars Spring URI template variable substitutions
     * @attr elementId DOM element id
     * @return The build URL as a String
     */
    Closure link = { attrs, body ->

        def writer = getOut()
        def elementId = attrs.remove('elementId')

        writer <<  '<a href="'
        writer << createLink(attrs).encodeAsHTML()
        writer << '"'
        if (elementId) {
            writer << " id=\"${elementId}\""
        }
        def remainingKeys = attrs.keySet() - LINK_ATTRIBUTES
        for (key in remainingKeys) {
            writer << " " << key << "=\"" << attrs[key]?.encodeAsHTML() << "\""
        }
        writer << '>'
        writer << body()
        writer << '</a>'
    }

    /**
     * Creates a link from a set of attributes. This
     * link can then be included in links, ajax calls etc. Generally used as a method call
     * rather than a tag eg.<br/>
     *
     * &lt;a href="${u.createLink(baseProperty:'userdetails.url')}"&gt;UserDetails&lt;/a&gt;
     *
     * @emptyTag
     *
     * @attr baseProperty the Grails Config property to get the base url from
     * @attr base The base url
     * @attr pathsProperty the Grails Config property to get the paths from
     * @attr paths A list of paths and/or path segments (or single String path)
     * @attr params A map of query params
     * @attr fragment The URL fragment
     * @attr vars Spring URI template variable substitutions
     * @return The build URL as a String
     */
    Closure createLink = { attrs ->
        return doCreateLink(attrs instanceof Map ? attrs: Collections.emptyMap()).toString()
    }

    UriComponents doCreateLink(Map attrs) {
        final baseProperty = attrs.remove('baseProperty')
        final base = baseProperty ? grailsApplication.config.getProperty(baseProperty) : attrs.remove('base')
        final pathsProperty = attrs.remove('pathsProperty')
        final paths = pathsProperty ? grailsApplication.config.get(pathsProperty) : attrs.remove('paths')
        final params = attrs.remove('params')
        final vars = attrs.remove('vars')
        final fragment = attrs.remove('fragment')

        def builder = UriComponentsBuilder.fromHttpUrl(base)
        if (paths) {
            if (paths instanceof CharSequence) {
                builder = builder.path(paths)
            } else if (paths instanceof Collection) {
                builder = builder.pathSegment(*paths)
            } else {
                builder = builder.path(paths)
            }
        }
        if (params) {
            builder = params.inject(builder) { UriComponentsBuilder b, param -> b.queryParam(param.key, param.value) }
        }
        if (fragment) {
            builder = builder.fragment(fragment)
        }

        return vars ? builder.buildAndExpand(vars) : builder.build()
    }
}
