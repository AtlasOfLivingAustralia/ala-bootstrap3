package au.org.ala.bootstrap3

import grails.core.GrailsApplication
import grails.plugins.*
import grails.util.Holders
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.MutablePropertySources

class AlaBootstrap3GrailsPlugin extends Plugin {

    def version = "3.0.0-SNAPSHOT"

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.11 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Ala Bootstrap3 Plugin" // Headline display name of the plugin
    def author = ""
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/ala-bootstrap3"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() {
        addDefaultConfig()
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        addDefaultConfig()
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }

    // add default value for config.headerAndFooter.baseURL that can be overridden by client app
    private void addDefaultConfig() {
        def value = "https://www.ala.org.au/commonui-bs3"
        def buildProps = new Properties()
        buildProps.setProperty("headerAndFooter.baseURL", value)
        def configSlurper = new ConfigSlurper().parse(buildProps)
        applicationContext.environment.propertySources.addLast(new MapPropertySource('ala-bootstrap3-default', configSlurper))

        //if config is already initialised, add default value if it is missing
        if (Holders?.config && !Holders.config.headerAndFooter.baseURL) {
            Holders.config.headerAndFooter.baseURL = value
        }
    }
}
