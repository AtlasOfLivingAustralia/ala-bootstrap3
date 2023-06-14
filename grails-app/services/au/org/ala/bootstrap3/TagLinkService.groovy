package au.org.ala.bootstrap3

import au.org.ala.cas.util.AuthenticationCookieUtils
import grails.web.mapping.LinkGenerator
import org.grails.encoder.CodecLookup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import java.util.concurrent.ConcurrentHashMap


class TagLinkService {

    CodecLookup codecLookup

    static LOGGED_IN_CLASS = 'logged-in'
    static LOGGED_OUT_CLASS = 'not-logged-in'

    /**
     * All the following defaults can be overridden by the specified config declarations.
     *
     * The banner include assumes that ala-cas-client exists in the app library.
     */

    @Value('${ala.baseURL:https://www.ala.org.au}')
    String alaBaseURL ="https://www.ala.org.au"
    @Value('${bie.baseURL:https://bie.ala.org.au}')
    String bieBaseURL ="https://bie.ala.org.au"
    @Value('${grails.serverURL:https://bie.ala.org.au}')
    String grailServerURL = "http://bie.ala.org.au"
    @Value('${bie.searchPath:https://bie.ala.org.au}')
    String bieSearchPath = "/search"
    @Value('${headerAndFooter.baseURL:https://www.ala.org.au/commonui-bs3}')
    String headerAndFooterBaseURL = "https://www.ala.org.au/commonui-bs3"
    @Value('${userDetails.url:https://auth.ala.org.au/userdetails}')
    String userDetailsServerUrl = "https://auth.ala.org.au/userdetails"
    @Value('${security.cas.loginUrl:https://auth.ala.org.au/cas/login}')
    String casLoginUrl = "https://auth.ala.org.au/cas/login"
    @Value('${security.cas.logoutUrl:https://auth.ala.org.au/cas/login}')
    String casLogoutUrl = "https://auth.ala.org.au/cas/logout"
    @Value('${headerAndFooter.cacheTimeout:1800000}')
    Long cacheTimeout = 1800000
    @Value('${headerAndFooter.version:2}')
    String headerAndFooterVersion = "2"
    @Value('${security.oidc.enabled:false}')
    Boolean isOidc = false
    @Value('${fathom.site-id}')
    String fathomSiteId

    @Autowired
    LinkGenerator grailsLinkGenerator

    /**
     * Cache for includes. Expires after 30 mins or when clearCache is called.
     */
    def hfCache = new ConcurrentHashMap<>([
            banner: [timestamp: new Date().time, content: ""],
            menu  : [timestamp: new Date().time, content: ""],
            footer: [timestamp: new Date().time, content: ""],
            head  : [timestamp: new Date().time, content: ""]
    ])

    /**
     * Clear the in-memory cache of content fragments.
     * @return
     */
    def clearCache() {
        hfCache.each { key, obj -> hfCache[key].content = "" }
        log.info "cache cleared"
    }

    /**
     * Build a uri from a base uri, additional optional path segments, additional optional query parameters,
     * optional fragment and optional uri variables to expand placeholders.
     * Ensures the resulting uri correctly encodes the parts of the uri.
     * @param baseUri The base uri.
     * @param pathSegments Path segments to be added to the base uri.
     * @param queryParameters Query parameters to be added to the base uri.
     *      Existing query parameters that match new query parameters will be replaced.
     * @param fragment The new fragment, will replace any existing fragment
     * @return Built uri string.
     */
    String buildUri(String baseUri, List pathSegments = null, Map<String, Object> queryParameters = null,
                    String fragment = null, Map<String, ?> uriVariables = null) {
        def builder = UriComponentsBuilder.fromHttpUrl(baseUri)

        // add the path segments
        if (pathSegments) {
            def hasFinalSlash = false
            for (def pathSegment in pathSegments) {
                if (pathSegment == null) {
                    log.warn("Skipping null path segment.")
                    continue
                }
                def splitPathSegment = pathSegment.toString().split('/')
                hasFinalSlash = pathSegment.toString().endsWith('/')
                for (def splitItem in splitPathSegment) {
                    builder.pathSegment(splitItem)
                }
            }

            if (hasFinalSlash) {
                builder.path('/')
            }
        }

        // add the query parameters
        if (queryParameters != null) {
            for (def queryParameter in queryParameters) {
                if (queryParameter.key == null || queryParameter.key.toString().trim() == '') {
                    log.warn("Cannot add a query parameter with an invalid key '${queryParameter.key}=${queryParameter.value}'.")
                    continue
                }

                // flatten the value to make all values available to be added
                [queryParameter.value].flatten().each { builder.queryParam(queryParameter.key.toString(), it) }
            }
        }

        // add the fragment
        if (fragment) {
            builder.fragment(fragment)
        }

        // build UriComponents that is not encoded, and expand variables if provided
        def uriComponentsRaw = builder.build(false)
        if (uriVariables) {
            uriComponentsRaw = uriComponentsRaw.expand(uriVariables)
        }

        // normalise the path segments, encode the components, then get the uri as a string
        def result = uriComponentsRaw.normalize().encode().toUriString()
        log.debug("From '${baseUri}' '${pathSegments}' '${queryParameters}' '${fragment}' built url '${result}'.")

        return result
    }

    /**
     * Build a url that forwards to request.forwardURI.
     * @param request
     * @return The built url.
     */
    String buildRequestForwardUrl(HttpServletRequest request) {
        def requestSchemeAuthority = removeContext(grailServerURL)
        def requestPath = request.forwardURI ? ((request.forwardURI.startsWith('/') ? '' : '/') + request.forwardURI) : ''
        def requestQuery = request.queryString ? (request.queryString.startsWith('?') ? '' : '?') + request.queryString : ''
        def result = buildUri([requestSchemeAuthority, requestPath, requestQuery].join(''))
        return result
    }

    /**
     * Build a login url.
     * @param request The current request.
     * @param customCasLoginUrl A custom CAS login url. Leave null to use the default.
     * @param customLoginReturnToUrl A custom login return to url. Leave null to use the current request forward uri.
     * @return A login url with the service url properly encoded.
     */
    String buildLoginUrl(HttpServletRequest request, String customCasLoginUrl = null, String customLoginReturnToUrl = null) {
        def loginReturnToUrl = customLoginReturnToUrl ? buildUri(customLoginReturnToUrl) : buildRequestForwardUrl(request)
        def loginUrl
        if (isOidc) {
            loginUrl = grailsLinkGenerator.link(mapping:'login', params: [path: loginReturnToUrl])
        } else {
            def casLoginUrl = customCasLoginUrl ?: this.casLoginUrl
            loginUrl = buildUri(casLoginUrl, [], ['service': loginReturnToUrl])
        }

        return loginUrl
    }

    /**
     * Build a logout url.
     * @param request The current request.
     * @param customCasLogoutUrl A custom CAS login url. Leave null to use the default.
     * @param customLogoutUrl A custom logout url. Leave blank to use the default.
     * @param customLogoutReturnToUrl A custom logout return to url. Leave null to use the current request forward uri.
     * @return A logout url.
     */
    String buildLogoutUrl(HttpServletRequest request, String customCasLogoutUrl = null, String customLogoutUrl = null,
                          String customLogoutReturnToUrl = null) {
//        def logoutUrl = customLogoutUrl ?: (grailServerURL + (grailServerURL.endsWith('/') ? '' : '/'))
        def logoutUrl = customLogoutUrl ?: grailsLinkGenerator.link(absolute: true, uri: '/')
        def logoutReturnToUrl = customLogoutReturnToUrl ? buildUri(customLogoutReturnToUrl) : ''

        def result
        if (isOidc) {
            // hits the pac4j logout filter instead of the logout controller
            def params = [:]
            if (logoutReturnToUrl) {
                params.url = logoutReturnToUrl
            }
            result = grailsLinkGenerator.link(uri: '/logout', params: params)
        } else {
            def logoutParams = [:]
            if (logoutReturnToUrl) {
                logoutParams.appUrl = logoutReturnToUrl
            }

            result = buildUri(logoutUrl, [], logoutParams)
        }

        return result
    }

    /**
     * Get the content from cache of the web.
     * @param which specifies the include
     * @param attrs any specified params
     * @return
     */
    String load(String which, def request, Map attrs) {
        String newContent
        String content = hfCache[which].content
        if (content == "" || (new Date().time > hfCache[which].timestamp + cacheTimeout)) {
            newContent = getContent(which)
            if (newContent) {
                hfCache[which].content = newContent
                hfCache[which].timestamp = new Date().time
                content = newContent
            }
        }
        return transform(request, content, attrs)
    }

    /**
     * Loads the content from the web.
     * @param which specifies the include
     * @return
     */
    String getContent(String which) {
        // Build url based on Bootstrap versions
        def baseUri = headerAndFooterBaseURL
        def pathSegments = ["${which}.html".toString()]
        def url = buildUri(baseUri, pathSegments)
        def conn = new URL(url).openConnection()
        try {
            conn.setConnectTimeout(10000)
            conn.setReadTimeout(50000)
            return conn.content.text
        } catch (SocketTimeoutException e) {
            log.error("Timed out getting ${which} template. URL= ${url}.", e)
        } catch (Exception e) {
            log.error("Failed to get ${which} template. ${e.getClass()} ${e.getMessage()} URL= ${url}.", e)
        }
        return ""
    }

    /**
     * Does the appropriate substitutions on the included content.
     * @param content
     * @param attrs any specified params to override defaults
     * @return
     */
    String transform(def request, String content, Map attrs) {
        switch (headerAndFooterVersion) {
            case "2":
                return transformV2(request, content, attrs)
            case "1":
                return transformV1(request, content, attrs)
            default:
                return transformV1(request, content, attrs)
        }
    }

    /**
     * @deprecated
     * Does the appropriate substitutions on the included content.
     * @param content
     * @param attrs any specified params to override defaults
     * @return
     */
    String transformV1(def request, content, attrs) {
        content = content.replace('::headerFooterServer::', encodeOutput(buildUri(headerAndFooterBaseURL)))
        content = content.replace('::centralServer::', encodeOutput(buildUri(alaBaseURL)))
        content = content.replace('::userdetailsServer::', encodeOutput(buildUri(userDetailsServerUrl)))
        content = content.replace('::searchServer::', encodeOutput(buildUri(bieBaseURL)))
        // change for BIE to grailServerURL
        content = content.replace('::searchPath::', encodeOutput(bieSearchPath))
        content = content.replace('::authStatusClass::', isLoggedIn(request, attrs) ? LOGGED_IN_CLASS : LOGGED_OUT_CLASS)

        if (attrs.fluidLayout) {
            content = content.replace('class="container"', 'class="container-fluid"')
        }

        if (content =~ "::loginLogoutListItem::") {
            // only do the work if it is needed
            content = content.replace('::loginLogoutListItem::', buildLoginoutLink(request, attrs))
        }

        content = this.contentBasedOnCurrentUser(request, content, attrs)
        return content
    }

    /**
     * Does the appropriate substitutions on the included content.
     * @param content
     * @param attrs any specified params to override defaults
     * @return
     */
    String transformV2(def request, content, attrs) {
        content = content.replace('::headerFooterServer::', encodeOutput(buildUri(headerAndFooterBaseURL)))
        content = content.replace('::centralServer::', encodeOutput(buildUri(alaBaseURL)))
        content = content.replace('::searchServer::', encodeOutput(buildUri(bieBaseURL)))
        // change for BIE to grailServerURL
        content = content.replace('::searchPath::', encodeOutput(bieSearchPath))

        if ((attrs.fluidLayout ?: "true").toBoolean()) {
            content = content.replace('::containerClass::', "container-fluid")
        } else {
            content = content.replace('::containerClass::', "container")
        }

        def signedInOutClass = isLoggedIn(request, attrs) ? 'signedIn' : 'signedOut'
        content = content.replace('::loginURL::', encodeOutput(buildLoginLink(request, attrs)))
        content = content.replace('::logoutURL::', encodeOutput(buildLogoutLink(request, attrs)))
        content = content.replace('::myProfileURL::', encodeOutput(buildMyProfileLink(request, attrs)))
        content = content.replace('::editAccountLink::', encodeOutput(buildEditAccountLink(request, attrs)))
        content = content.replace('::loginStatus::', signedInOutClass)
        content = content.replace('::fathomID::', fathomSiteId)

        return content
    }

    /**
     * Determine whether the current request has a logged in user or not.
     * @param request The current request.
     * @param attrs The tag attributes.
     * @return True if there is a logged in user, otherwise false.
     */
    boolean isLoggedIn(request, attrs) {

        // is logged in if there is a user session in the request
        def userSession = request.userPrincipal
        if (userSession) {
            return true
        }

        // is logged in if the custom cookie is not ignored and the custom cookie is present
        def ignoreCookie = attrs.ignoreCookie?.toString() != 'false'
        def customCookieExists = AuthenticationCookieUtils.cookieExists(request, AuthenticationCookieUtils.ALA_AUTH_COOKIE)
        if (!ignoreCookie && customCookieExists) {
            return true
        }

        return false
    }

    /**
     * Builds the login or logout link based on current login status.
     * @param attrs any specified params to override defaults
     * @return
     */
    String buildLoginoutLink(def request, Map attrs) {
        switch (headerAndFooterVersion) {
            case "2":
                return buildLoginoutLinkV2(request, attrs)
            case "1":
                return buildLoginoutLinkV1(request, attrs)
            default:
                return buildLoginoutLinkV2(request, attrs)
        }
    }

    /**
     * @deprecated
     * Builds the login or logout link based on current login status.
     * @param attrs any specified params to override defaults
     * @return
     */
    String buildLoginoutLinkV1(def request, attrs) {
        def cssClass = attrs.cssClass ?: ''
        if (isLoggedIn(request, attrs)) {
            def url = encodeOutput(buildLogoutLink(request, attrs))
            return "<a href='${url}' class='${cssClass}'>Logout</a>"
        } else {
            def url = encodeOutput(buildLoginLink(request, attrs))
            return "<a href='${url}' class='${cssClass}'>Log in</a>"
        }
    }

    /**
     * Builds the login or logout link based on current login status.
     * @param attrs any specified params to override defaults
     * @return
     */
    String buildLoginoutLinkV2(def request, attrs) {
        def extraCssClass = attrs.cssClass ?: ''
        if (isLoggedIn(request, attrs)) {
            def cssClass = ['btn btn-outline-white btn-sm']
            if (extraCssClass) {
                cssClass.add(extraCssClass)
            }
            return "<a href='${encodeOutput(buildLogoutLink(request, attrs))}' class='${cssClass.join(' ')}'>Logout</a>"
        } else {
            def cssClass = ['btn btn-primary btn-sm']
            if (extraCssClass) {
                cssClass.add(extraCssClass)
            }
            return "<a href='${encodeOutput(buildLoginLink(request, attrs))}' class='${cssClass.join(' ')}'>Login</a>"
        }
    }

    /**
     * Build the login link
     * @param request The current web request.
     * @param attrs any specified params to override defaults
     * @return The login url
     */
    String buildLoginLink(def request, Map attrs = [:]) {
        String customCasLoginUrl = attrs.casLoginUrl
        String customLoginReturnToUrl = attrs.loginReturnToUrl ?: attrs.loginReturnUrl
        return buildLoginUrl(request, customCasLoginUrl, customLoginReturnToUrl)
    }

    /**
     * Build the logout link
     * @param attrs any specified params to override defaults
     * @return The logout url
     */
    String buildLogoutLink(request, Map attrs) {
        String customCasLogoutUrl = null
        String customLogoutUrl = attrs.logoutUrl
        String customLogoutReturnToUrl = attrs.logoutReturnToUrl ?: attrs.logoutReturnUrl
        return buildLogoutUrl(request, customCasLogoutUrl, customLogoutUrl, customLogoutReturnToUrl)
    }

    String buildMyProfileLink(request, Map attrs) {
        attrs.myProfileLink ?: buildUri(userDetailsServerUrl, ['my-profile'])
    }

    String buildEditAccountLink(request, Map attrs) {
        attrs.editAccountLink ?: buildUri(userDetailsServerUrl, ['registration','editAccount'])
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

    /**
     * Add content based on whether the current user is logged in or not.
     * @param request The current request.
     * @param content The content to add.
     * @param attrs The tag attributes.
     * @return The modified content.
     */
    String contentBasedOnCurrentUser(def request, String content, Map attrs) {
        def replaceMarkerBegin = '::beginLoggedInOnly::'
        def replaceMarkerEnd = '::endLoggedInOnly::'
        def isLoggedIn = isLoggedIn(request, attrs)

        log.warn("Starting replacements for logged ${isLoggedIn ? 'in' : 'out'} user...")

        if (isLoggedIn) {
            // if the user is logged in, just remove the markers without other modifications
            content = content.replaceAll(/::beginLoggedInOnly::/, '')
            content = content.replaceAll(/::endLoggedInOnly::/, '')

            log.warn("Replacing ${replaceMarkerBegin} and ${replaceMarkerEnd} for logged in user.")
        } else {
            // if the user is not logged in, remove everything between the markers, including the markers
            log.warn("Removing content for logged out user.")

            def indexStart, indexEnd
            while (true) {
                indexStart = content.indexOf(replaceMarkerBegin)
                indexEnd = content.indexOf(replaceMarkerEnd)
                if (indexStart < 0 || indexEnd < 0) {
                    break
                }

                def indexEndLength = indexEnd + replaceMarkerEnd.length()
                content = content.substring(0, indexStart) + content.substring(indexEndLength)

                log.warn("Removed content between index ${indexStart} and ${indexEndLength} for logged out user.")
            }
        }

        return content
    }

    private String encodeOutput(String value) {
        if (value) {
            def encoder = codecLookup.lookupEncoder('HTML')
            def encodedValue = encoder.encode(value)
            encodedValue
        } else {
            ""
        }
    }
}
