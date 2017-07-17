 <!DOCTYPE html>
<html lang="en-AU">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="app.version" content="${g.meta(name:'app.version')}"/>
    <meta name="app.build" content="${g.meta(name:'app.build')}"/>
    <meta name="description" content="${grailsApplication.config.skin.orgNameLong}"/>
    <meta name="author" content="${grailsApplication.config.skin.orgNameLong}">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><g:layoutTitle /></title>

    <g:if test="${!grailsApplication.config.headerAndFooter.excludeBootstrapCss}">
        <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/bootstrap.min.css" rel="stylesheet"
              media="screen,print"/>
    </g:if>
    <g:if test="${!grailsApplication.config.headerAndFooter.excludeAlaStylesCss}">
        <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/ala-styles.css" rel="stylesheet"
              media="screen,print"/>
    </g:if>

    <asset:stylesheet src="core" media="screen,print"/>

    <asset:stylesheet src="${pageProperty(name: 'meta.head-screen-print-css') ?: "core-screen-print-css"}"
                      media="screen,print"/>
    <asset:stylesheet src="${pageProperty(name: 'meta.head-css') ?: "core-head-css"}"/>

    <asset:javascript src="jquery"/>
    <asset:javascript src="jquery-extensions"/>

    <g:if test="${!grailsApplication.config.headerAndFooter.excludeApplicationJs}">
        <script type="text/javascript" src="${grailsApplication.config.headerAndFooter.baseURL}/js/application.js"
                defer></script>
    </g:if>
    <g:if test="${!grailsApplication.config.headerAndFooter.excludeBootstrapJs}">
        <script type="text/javascript"
                src="${grailsApplication.config.headerAndFooter.baseURL}/js/bootstrap.min.js"></script>
    </g:if>

    <asset:javascript src="${pageProperty(name: 'meta.head-js') ?: 'core-head-js'}"/>
    <asset:javascript src="${pageProperty(name: 'meta.deferred-js') ?: 'core-deferred-js'}" asset-defer="true"/>

    <g:layoutHead />
</head>
<body class="${pageProperty(name:'body.class')}" id="${pageProperty(name:'body.id')}" onload="${pageProperty(name:'body.onload')}">

<!-- Header -->
<hf:banner logoutUrl="${g.createLink(controller:"logout", action:"logout", absolute: true)}" />
<!-- End header -->
<g:set var="fluidLayout" value="${pageProperty(name:'meta.fluidLayout')?:grailsApplication.config.skin?.fluidLayout.toBoolean()}"/>
<!-- Container -->
<div class="${fluidLayout ? 'container-fluid' : 'container'}" id="main">
    <g:layoutBody />
</div><!-- End container #main col -->

<hf:footer/>

<asset:deferredScripts/>
</body>
</html>