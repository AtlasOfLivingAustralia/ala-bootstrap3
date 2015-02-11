modules = {
    bootstrap {
        dependsOn 'core'
        resource url:'/js/bootstrap.min.js', plugin:'ala-bootstrap3'
        resource url:'/css/bootstrap.min.css', plugin:'ala-bootstrap3', attrs:[media:'screen, projection, print']
    }

    ala {
        dependsOn 'bootstrap'
        resource url:'/css/my-styles.css', plugin:'ala-bootstrap3', attrs:[media:'screen, projection, print']
        resource url:'http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css'
    }

    core {
        dependsOn 'jquery', 'autocomplete'
        resource url: '/js/html5.js', plugin:'ala-bootstrap3', wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
        resource url: '/js/application.js', plugin:'ala-bootstrap3', attrs:[media:'screen, projection, print']
    }

    autocomplete {
        // This is a legacy component that do not work with latest versions of jQuery (1.11+, maybe others)
        dependsOn 'jquery-migration'
        // Important note!!: To use this component along side jQuery UI, you need to download a custom jQuery UI compilation that
        // do not include the autocommplete widget
        resource url: '/css/jquery.autocomplete.css', plugin:'ala-bootstrap3', attrs:[media:'screen, projection, print']
        resource url: '/js/jquery.autocomplete.js', plugin:'ala-bootstrap3', attrs:[media:'screen, projection, print']
    }

    'jquery-migration' {
        // Needed to support legacy js components that do not work with latest versions of jQuery
        dependsOn 'jquery'
        resource url: '/js/jquery-migrate-1.2.1.min.js', plugin:'ala-bootstrap3'
    }
}