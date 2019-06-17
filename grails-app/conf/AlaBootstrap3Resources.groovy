modules = {
    bootstrap {
        dependsOn 'core'
    }

    ala {
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/css/ala.min.css', attrs:[media:'screen, print']
    }

    core {
        resource url: grailsApplication.config.headerAndFooter.baseURL + '/js/ala.min.js'
    }
}
