package au.org.ala.bootstrap3

import grails.util.TypeConvertingMap
import groovy.transform.CompileStatic
import org.grails.buffer.StreamCharBuffer
import org.grails.plugins.web.taglib.UrlMappingTagLib

/*
* This g:paginate tag fix is copied from:
* https://github.com/groovydev/twitter-bootstrap-grails-plugin/blob/master/grails-app/taglib/org/groovydev/TwitterBootstrapTagLib.groovy
*/
@CompileStatic
class TwitterBootstrapTagLib {

    /*
     * This g:paginate tag fix is based on:
     * https://github.com/grails/grails-core/blob/master/grails-plugin-gsp/src/main/groovy/org/codehaus/groovy/grails/plugins/web/taglib/RenderTagLib.groovy
     */

    /**
     * NOTE This may not work in your application - try hf:paginate instead!
     *
     * Creates next/previous links to support pagination for the current controller.<br/>
     *
     * &lt;g:paginate total="${Account.count()}" /&gt;<br/>
     *
     * @emptyTag
     *
     * @attr total REQUIRED The total number of results to paginate
     * @attr action the name of the action to use in the link, if not specified the default action will be linked
     * @attr controller the name of the controller to use in the link, if not specified the current controller will be linked
     * @attr id The id to use in the link
     * @attr params A map containing request parameters
     * @attr prev The text to display for the previous link (defaults to "Previous" as defined by default.paginate.prev property in I18n messages.properties)
     * @attr next The text to display for the next link (defaults to "Next" as defined by default.paginate.next property in I18n messages.properties)
     * @attr max The number of records displayed per page (defaults to 10). Used ONLY if params.max is empty
     * @attr maxsteps The number of steps displayed for pagination (defaults to 10). Used ONLY if params.maxsteps is empty
     * @attr offset Used only if params.offset is empty
     * @attr fragment The link fragment (often called anchor tag) to use
     */
    Closure paginate = { Map attrsMap ->
        TypeConvertingMap attrs = (TypeConvertingMap)attrsMap
        def configTabLib = grailsApplication.config.navigate('grails','plugins','twitterbootstrap','fixtaglib')
        if (!configTabLib) {
            def renderTagLib = (UrlMappingTagLib) grailsApplication.mainContext.getBean('org.grails.plugins.web.taglib.UrlMappingTagLib')
            renderTagLib.paginate.call(attrs)
        } else {
            def renderTagLib = (HeaderFooterTagLib) grailsApplication.mainContext.getBean(HeaderFooterTagLib.class.name)
            renderTagLib.paginate.call(attrs)
        }
    }

    /**
     *
     * @param body REQUIRED The body must be breadcrumbLink
     */
    Closure breadcrumb = { Map attrs, Closure<StreamCharBuffer> body ->
        out << '<ol class="breadcrumb">'
        out << body()
        out << '</ol>'
    }

    /**
     * @emptyTag
     *
     * @attr value REQUIRED The value is the value of the link
     * @attr active True if the link is active
     */
    Closure breadcrumbLink = { Map attrsMap ->
        TypeConvertingMap attrs = (TypeConvertingMap)attrsMap
        def value = attrs.value
        def active = attrs.boolean('active')
        def link = attrs.link ?: '#'
        if (active) {
            out << "<li class=\"active\">${value}</li>".toString()
        } else {
            out << "<li><a href=\"${link}\">${value}</a></li>".toString()
        }
    }
}