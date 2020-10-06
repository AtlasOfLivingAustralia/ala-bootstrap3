package au.org.ala.bootstrap3

class UrlBuilderTagLib {

    TagLinkService tagLinkService

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
    def link = { attrs, body ->

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
    def createLink = { attrs ->
        def attrsMap = attrs instanceof Map ? attrs : Collections.emptyMap()
        final baseProperty = attrsMap.remove('baseProperty') as String
        final base = baseProperty ? grailsApplication.config.getProperty(baseProperty) : attrsMap.remove('base') as String
        final pathsProperty = attrsMap.remove('pathsProperty')
        final paths = pathsProperty ? grailsApplication.config.get(pathsProperty) : attrsMap.remove('paths')
        final params = attrsMap.remove('params') as Map<String, ?>
        final vars = attrsMap.remove('vars') as Map<String, ?>
        final fragment = attrsMap.remove('fragment') as String

        def recognisedPaths = paths instanceof Collection ? (paths?.toList() ?: []) : [paths]
        return tagLinkService.buildUri(base, recognisedPaths, params, fragment, vars)
    }
}
