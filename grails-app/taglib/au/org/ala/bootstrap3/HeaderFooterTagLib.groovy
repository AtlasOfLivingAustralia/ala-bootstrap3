package au.org.ala.bootstrap3

import grails.util.Holders
import au.org.ala.cas.util.AuthenticationCookieUtils

class HeaderFooterTagLib {

    static namespace = 'hf'     // namespace for headers and footers
    static returnObjectForTags = ['createLoginUrl']

    /**
     * All the following statics can be overridden by the specified config declarations.
     *
     * The banner include assumes that ala-cas-client exists in the app library.
     */

    def alaBaseURL = Holders.config.ala.baseURL ?: "http://www.ala.org.au"
    def bieBaseURL = Holders.config.bie.baseURL ?: "http://bie.ala.org.au"
    def grailServerURL = Holders.config.grails.serverURL ?: "http://bie.ala.org.au"
    def bieSearchPath = Holders.config.bie.searchPath ?: "/search"
    def headerAndFooterBaseURL = Holders.config.headerAndFooter.baseURL ?: "http://www2.ala.org.au/commonui-bs3"
    // the next two can also be overridden by tag attributes
    def casLoginUrl = Holders.config.security.cas.loginUrl ?: "https://auth.ala.org.au/cas/login"
    def casLogoutUrl = Holders.config.security.cas.logoutUrl ?: "https://auth.ala.org.au/cas/logout"
    def cacheTimeout = Holders.config.headerAndFooter.cacheTimeout ?: 1800000

    /**
     * Display the page banner. Includes login/logout link and search box.
     *
     * Usage: <hf:banner [param=""]../>
     *
     * @attr logoutUrl the local url that should invalidate the session and redirect to the auth
     *  logout url - defaults to {CH.config.grails.serverURL}/session/logout
     * @attr loginReturnToUrl where to go after logging in - defaults to current page
     * @attr logoutReturnToUrl where to go after logging out - defaults to current page
     * @attr loginReturnUrl where to go after login - defaults to current page
     * @attr casLoginUrl - defaults to {CH.config.security.cas.loginUrl}
     * @attr casLogoutUrl - defaults to {CH.config.security.cas.logoutUrl}
     * @attr ignoreCookie - if true the helper cookie will not be used to determine login - defaults to false
     * @attr fluidLayout - if true the BS CSS class of "container" is changed to "container-fluid"
     */
    def banner = { attrs ->
        out << load('banner', attrs)
    }

    /**
     * Display the main menu.
     *
     * Note that highlighting of the current menu item is done by including the appropriate class in the
     * body tag, eg class="collections".
     *
     * Usage: <hf:menu/>
     *
     * @attr fluidLayout - if true the BS CSS class of "container" is changed to "container-fluid"
     */
    def menu = { attrs ->
        out << load('menu', attrs)
    }

    /**
     * Displays the page footer.
     *
     * Usage: <hf:footer/>
     */
    def footer = {
        out << load('footer', [:])
    }

    /**
     * Cache for includes. Expires after 30mins or when clearCache is called.
     */
    def hfCache = [
            banner: [timestamp: new Date().time, content: ""],
            menu: [timestamp: new Date().time, content: ""],
            footer: [timestamp: new Date().time, content: ""]]

    /**
     * Call this tag from a controller to clear the cache.
     */
    def clearCache = {
        hfCache.each { key, obj -> hfCache[key].content = ""}
        println "cache cleared"
    }

    /**
     * Generate the login/logout link
     *
     * @attr cssClass - CSS class to add to a tag
     *
     * plus
     * @attr logoutUrl the local url that should invalidate the session and redirect to the auth
     *  logout url - defaults to {CH.config.grails.serverURL}/session/logout
     * @attr loginReturnToUrl where to go after logging in - defaults to current page
     * @attr logoutReturnToUrl where to go after logging out - defaults to current page
     * @attr loginReturnUrl where to go after login - defaults to current page
     * @attr casLoginUrl - defaults to {CH.config.security.cas.loginUrl}
     * @attr casLogoutUrl - defaults to {CH.config.security.cas.logoutUrl}
     * @attr ignoreCookie - if true the helper cookie will not be used to determine login - defaults to false
     */
    def loginLogout = { attrs ->
        out << buildLoginoutLink(attrs)
    }

    /**
     * Generate a URL for logging in the user\. Generally used as a method call
     * rather than a tag eg.<br/>
     *
     * &lt;a href="${createLoginUrl()}"&gt;log in&lt;/a&gt;
     *
     * @emptyTag
     *
     * @attr casLoginUrl - defaults to {CH.config.security.cas.loginUrl}
     * @attr loginReturnToUrl where to go after logging in - defaults to current page
     */
    Closure createLoginUrl = { attrs ->
        String loginUrl = buildLoginLink(attrs)
        return loginUrl
    }
    /**
     * Get the content from cache of the web.
     * @param which specifies the include
     * @param attrs any specified params
     * @return
     */
    String load(which, attrs) {
        def content
        if (hfCache[which].content == "" || (new Date().time > hfCache[which].timestamp + cacheTimeout)) {
            content = getContent(which)
            hfCache[which].content = content
            hfCache[which].timestamp = new Date().time
        }
        else {
            content = hfCache[which].content
            //println "using cache"
        }
        return transform(content, attrs)
    }

    /**
     * Loads the content from the web.
     * @param which specifies the include
     * @return
     */
    String getContent(which) {
        def url = headerAndFooterBaseURL + '/' + which + ".html" // Bootstrap versions
        def conn = new URL(url).openConnection()
        try {
            conn.setConnectTimeout(10000)
            conn.setReadTimeout(50000)
            return conn.content.text
        } catch (SocketTimeoutException e) {
            log.warn "Timed out getting ${which} template. URL= ${url}."
            println "Timed out getting ${which} template. URL= ${url}."
        } catch (Exception e) {
            log.warn "Failed to get ${which} template. ${e.getClass()} ${e.getMessage()} URL= ${url}."
            println "Failed to get ${which} template. ${e.getClass()} ${e.getMessage()} URL= ${url}."
        }
        return ""
    }

    /**
     * Does the appropriate substitutions on the included content.
     * @param content
     * @param attrs any specified params to override defaults
     * @return
     */
    String transform(content, attrs) {
        content = content.replaceAll(/::headerFooterServer::/, headerAndFooterBaseURL)
        content = content.replaceAll(/::centralServer::/, alaBaseURL)
        content = content.replaceAll(/::searchServer::/, bieBaseURL) // change for BIE to grailServerURL
        content = content.replaceAll(/::searchPath::/, bieSearchPath)
        if (attrs.fluidLayout) {
            content = content.replaceAll('class="container"', 'class="container-fluid"')
        }
        if (content =~ "::loginLogoutListItem::") {
            // only do the work if it is needed
            content = content.replaceAll(/::loginLogoutListItem::/, buildLoginoutLink(attrs))
        }
        return content
    }

    /**
     * Builds the login or logout link based on current login status.
     * @param attrs any specified params to override defaults
     * @return
     */
    String buildLoginoutLink(attrs) {
        def requestUri = removeContext(grailServerURL) + request.forwardURI
        def logoutUrl = attrs.logoutUrl ?: grailServerURL + "/session/logout"
        def logoutReturnToUrl = attrs.logoutReturnToUrl ?: requestUri
        def casLogoutUrl = attrs.casLogoutUrl ?: casLogoutUrl

        // TODO should this be attrs.logoutReturnToUrl?
        if (!attrs.loginReturnToUrl && request.queryString) {
            logoutReturnToUrl += "?" + URLEncoder.encode(request.queryString, "UTF-8")
        }

        if ((attrs.ignoreCookie != "true" &&
                AuthenticationCookieUtils.cookieExists(request, AuthenticationCookieUtils.ALA_AUTH_COOKIE)) ||
                request.userPrincipal) {
            return "<a href='${logoutUrl}" +
                    "?casUrl=${casLogoutUrl}" +
                    "&appUrl=${logoutReturnToUrl}' " +
                    "class='${attrs.cssClass}'>Logout</a>"
        } else {
            // currently logged out
            return "<a href='${buildLoginLink(attrs)}' class='${attrs.cssClass}'><span>Log in</span></a>"
        }
    }

    /**
     * Build the login link
     * @param attrs any specified params to override defaults
     * @return The login url
     */
    String buildLoginLink(attrs) {
        def casLoginUrl = attrs.casLoginUrl ?: casLoginUrl
        def loginReturnToUrl = attrs.loginReturnToUrl ?: (removeContext(grailServerURL) + request.forwardURI + (request.queryString ? "?" + URLEncoder.encode(request.queryString, "UTF-8") : ""))
        String loginUrl = "${casLoginUrl}?service=${loginReturnToUrl}"
        return loginUrl
    }

    /**
     * Remove the context path and params from the url.
     * @param urlString
     * @return
     */
    private String removeContext(urlString) {
        def url = urlString.toURL()
        def protocol = url.protocol != -1 ? url.protocol + "://" : ""
        def port = url.port != -1 ? ":" + url.port : ""
        return protocol + url.host + port
    }
}