modules = {
    bootstrap {
        dependsOn 'core'
        resource url:grailsApplication.config.headerAndFooter.baseURL + '/js/bootstrap.min.js'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/bootstrap.min.css', attrs:[media:'screen, print']
    }

    ala {
        dependsOn 'bootstrap', 'font-awesome'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/ala-styles.css', attrs:[media:'screen, print']
    }

    core {
        dependsOn 'jquery', 'autocomplete'
        resource url: [ dir:'js', file: 'html5.js', plugin:'ala-bootstrap3'], wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/js/application.js'
    }

    autocomplete {
        // This is a legacy component that do not work with latest versions of jQuery (1.11+, maybe others)
        dependsOn 'jquery-migration'
        // Important note!!: To use this component along side jQuery UI, you need to download a custom jQuery UI compilation that
        // do not include the autocomplete widget
        resource url: [ dir:'css', file: 'jquery.autocomplete.css', plugin:'ala-bootstrap3'], attrs:[media:'screen, print']
        resource url: [ dir:'js', file: 'jquery.autocomplete.js', plugin:'ala-bootstrap3'], attrs:[media:'screen, print']
    }

    'jquery-migration' {
        // Needed to support legacy js components that do not work with latest versions of jQuery
        dependsOn 'jquery'
        resource url: [ dir:'js', file: 'jquery-migrate-1.2.1.min.js', plugin:'ala-bootstrap3']
    }

    'font-awesome' {
        resource url:[plugin: 'ala-bootstrap3', dir: 'css', file: 'font-awesome-4.3.0.css'], attrs:[media:'all']
    }


}
