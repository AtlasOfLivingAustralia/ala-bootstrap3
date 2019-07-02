modules = {
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
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/font-awesome.min.css', attrs:[media:'screen, print'], disposition: 'head'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/autocomplete.min.css', attrs:[media:'screen, print'], disposition: 'head'

        resource url: grailsApplication.config.headerAndFooter.baseURL + '/js/jquery.min.js', disposition: 'head'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/js/autocomplete.min.js', disposition: 'head'
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/js/application.js'
    }
}
