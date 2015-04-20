modules = {
    bootstrap {
        dependsOn 'core'
        resource url: [ dir:'js', file: 'bootstrap.min.js', plugin:'ala-bootstrap3']
        resource url: [ dir:'css', file: 'bootstrap.min.css', plugin:'ala-bootstrap3'], attrs:[media:'screen, projection, print']
    }

    ala {
        dependsOn 'bootstrap', 'font-awesome'
        resource url: [ dir:'css', file: 'ala-styles.css', plugin:'ala-bootstrap3'], attrs:[media:'screen, projection, print']
    }

    core {
        dependsOn 'jquery', 'autocomplete'
        resource url: [ dir:'js', file: 'html5.js', plugin:'ala-bootstrap3'], wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
        resource url: [ dir:'js', file: 'application.js', plugin:'ala-bootstrap3'], attrs:[media:'screen, projection, print']
    }

    autocomplete {
        // This is a legacy component that do not work with latest versions of jQuery (1.11+, maybe others)
        dependsOn 'jquery-migration'
        // Important note!!: To use this component along side jQuery UI, you need to download a custom jQuery UI compilation that
        // do not include the autocommplete widget
        resource url: [ dir:'css', file: 'jquery.autocomplete.css', plugin:'ala-bootstrap3'], attrs:[media:'screen, projection, print']
        resource url: [ dir:'js', file: 'jquery.autocomplete.js', plugin:'ala-bootstrap3'], attrs:[media:'screen, projection, print']
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