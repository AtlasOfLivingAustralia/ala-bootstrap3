modules = {

    overrides {
        'jquery' {
            if ( grailsApplication.config.headerAndFooter.version == "2") {
                resource id: 'js', url: grailsApplication.config.headerAndFooter.baseURL + '/js/jquery.min.js', disposition: 'head'

            } else {
                resource id: 'js', url:[dir:'js', file:'jquery.min.js', plugin: "ala-bootstrap3"], disposition: 'head'
            }
        }
    }

    bootstrap {
        dependsOn 'core'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/bootstrap.min.css', attrs:[media:'screen, print'], disposition: 'head'
        resource url:grailsApplication.config.headerAndFooter.baseURL + '/js/bootstrap.min.js'
    }

    ala {
        dependsOn('bootstrap', 'core')
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/bootstrap-theme.min.css', attrs:[media:'screen, print'], disposition: 'head'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/autocomplete-extra.min.css', attrs:[media:'screen, print'], disposition: 'head'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/ala-styles.css', attrs:[media:'screen, print'], disposition: 'head'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/ala-theme.css', attrs:[media:'screen, print'], disposition: 'head'
    }

    core {
        dependsOn('jquery')
        if ( grailsApplication.config.headerAndFooter.version == "2") {
            resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/autocomplete.min.css', attrs:[media:'screen, print'], disposition: 'head'
            resource url: grailsApplication.config.headerAndFooter.baseURL + '/js/jquery-migration.js', disposition: 'head'
            resource url: grailsApplication.config.headerAndFooter.baseURL + '/js/autocomplete.min.js', disposition: 'head'
        } else {
            resource url:[dir:'css', file:'autocomplete.min.css'], disposition: 'head'
            resource url:[dir:'js', file:'jquery-migration.js', plugin: "ala-bootstrap3"], disposition: 'head'
            resource url:[dir:'js', file:'autocomplete.min.js'], disposition: 'head'
        }

        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/font-awesome.min.css', attrs:[media:'screen, print'], disposition: 'head'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/js/application.js'
    }
}
