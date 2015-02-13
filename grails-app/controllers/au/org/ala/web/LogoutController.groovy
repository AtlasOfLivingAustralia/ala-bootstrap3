package au.org.ala.web

class LogoutController {
    /**
     * Do logouts through this app so we can invalidate the session.
     *
     * @param casUrl the url for logging out of cas
     * @param appUrl the url to redirect back to after the logout
     */
    def logout = {
        session.invalidate()
        redirect(url: "${params.casUrl}?url=${params.appUrl}")
    }

    /**
     * Clear the headers and footers cache
     *
     */
    def clearCache = {
        render hf.clearCache()
    }
}
