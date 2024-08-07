 <!DOCTYPE html>
<html lang="en-AU">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="app.version" content="${g.meta(name:'app.version')}"/>
    <meta name="app.build" content="${g.meta(name:'app.build')}"/>
    <meta name="description" content="${grailsApplication.config.getProperty('skin.orgNameLong')}"/>
    <meta name="author" content="${grailsApplication.config.getProperty('skin.orgNameLong')}">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><g:layoutTitle /></title>

    <g:if test="${!grailsApplication.config.getProperty('headerAndFooter.excludeBootstrapCss')}">
        <link href="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/css/bootstrap.min.css" rel="stylesheet"
              media="screen,print"/>
    </g:if>

    <asset:stylesheet src="${pageProperty(name: 'meta.head-screen-print-css') ?: "core-screen-print"}"
                      media="screen,print"/>
    <asset:stylesheet src="${pageProperty(name: 'meta.head-css') ?: "core"}"/>

    <asset:javascript src="${pageProperty(name: 'meta.head-js') ?: 'head'}"/>

    <g:if test="${!grailsApplication.config.getProperty('headerAndFooter.excludeApplicationJs')}">
        <script type="text/javascript" src="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/js/application.js"
                defer></script>
    </g:if>
    <g:if test="${!grailsApplication.config.getProperty('headerAndFooter.excludeBootstrapJs')}">
        <script type="text/javascript"
                src="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/js/bootstrap.min.js"></script>
    </g:if>

    <g:if test="${grailsApplication.config.getProperty('headerAndFooter.challengeJsUrl')}">
        <script type="text/javascript"
                src="${grailsApplication.config.getProperty('headerAndFooter.challengeJsUrl')}" defer></script>
    </g:if>

    <g:layoutHead />
    <hf:head/>
</head>
<body class="${pageProperty(name:'body.class')}" id="${pageProperty(name:'body.id')}" onload="${pageProperty(name:'body.onload')}">

<!-- Header -->
<hf:banner logoutUrl="${g.createLink(controller:"logout", action:"logout", absolute: true)}" />
<!-- End header -->
<g:set var="fluidLayout" value="${pageProperty(name:'meta.fluidLayout')?:grailsApplication.config.getProperty('skin.fluidLayout', Boolean, false)}"/>
<!-- Container -->
<div class="${fluidLayout ? 'container-fluid' : 'container'}" id="main">
    <g:layoutBody />
</div><!-- End container #main col -->

<hf:footer logoutUrl="${g.createLink(controller: "logout", action: "logout", absolute: true)}"
           ignoreCookie="${grailsApplication.config.getProperty('ignoreCookie', Boolean, false)}"/>

<asset:javascript src="${pageProperty(name: 'meta.deferred-js') ?: 'jquery-extensions'}" />
<asset:deferredScripts/>
</body>
</html>
