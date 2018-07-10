<!DOCTYPE html>
<html lang="en-AU">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="app.version" content="${g.meta(name:'info.app.version')}"/>
    <meta name="app.build" content="${g.meta(name:'info.app.build')}"/>
    <meta name="description" content="${grailsApplication.config.skin?.orgNameLong?:'Atlas of Living Australia'}"/>
    <meta name="author" content="${grailsApplication.config.skin?.orgNameLong?:'Atlas of Living Australia'}">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Favicon -->
    <link href="${grailsApplication.config.skin.favicon}" rel="shortcut icon"  type="image/x-icon"/>

    <title><g:layoutTitle /></title>

    <g:if test="${!grailsApplication.config.headerAndFooter.excludeBootstrapCss}">
        <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/bootstrap.min.css" rel="stylesheet" media="screen,print"/>
        <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/bootstrap-theme.min.css" rel="stylesheet" media="screen,print"/>
    </g:if>
    <g:if test="${!grailsApplication.config.headerAndFooter.excludeAlaStylesCss}">
        <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/ala-styles.css" rel="stylesheet"
              media="screen,print"/>
    </g:if>
    
    <asset:stylesheet src="${pageProperty(name: 'meta.head-screen-print-css') ?: "core-screen-print"}"
                      media="screen,print"/>
    <asset:stylesheet src="${pageProperty(name: 'meta.head-css') ?: "core"}"/>
    
    <plugin:isAvailable name="alaAdminPlugin"><asset:stylesheet src="ala-admin-asset.css" /></plugin:isAvailable>

    <asset:javascript src="${pageProperty(name: 'meta.head-js') ?: 'head'}"/>

    <g:if test="${!grailsApplication.config.headerAndFooter.excludeApplicationJs}">
        <script type="text/javascript" src="${grailsApplication.config.headerAndFooter.baseURL}/js/application.js"
                defer></script>
    </g:if>
    <g:if test="${!grailsApplication.config.headerAndFooter.excludeBootstrapJs}">
        <script type="text/javascript"
                src="${grailsApplication.config.headerAndFooter.baseURL}/js/bootstrap.min.js"></script>
    </g:if>

    <g:layoutHead />

    <hf:head/>

</head>
<body class="${pageProperty(name:'body.class')}" id="${pageProperty(name:'body.id')}" onload="${pageProperty(name:'body.onload')}">
<g:set var="fluidLayout" value="${pageProperty(name:'meta.fluidLayout')?:grailsApplication.config.getProperty('skin.fluidLayout', Boolean, false)}"/>
<!-- Header -->
<hf:banner logoutUrl="${g.createLink(controller: "logout", action: "logout", absolute: true)}"
           ignoreCookie="${grailsApplication.config.ignoreCookie}"/>
<!-- End header -->
<g:if test="${!pageProperty(name:'meta.hideBreadcrumb')}">
    <!-- Breadcrumb -->
    <g:set var="breadcrumbParent" value="${pageProperty(name:'meta.breadcrumbParent')}"/>
    <g:set var="breadcrumbs" value="${pageProperty(name:'meta.breadcrumbs')}"/>
    <g:set var="breadcrumb" value="${pageProperty(name:'meta.breadcrumb')?:pageProperty(name:'title')}"/>
    <section id="breadcrumb">
        <div class="${fluidLayout ? 'container-fluid' : 'container'}">
            <div class="row">
                <nav aria-label="Breadcrumb" role="navigation">
                    <ol class="breadcrumb-list">
                        <li><a href="https://www.ala.org.au/">Home</a></li>
                        <g:if test="${breadcrumbParent}">
                            <g:set value="${breadcrumbParent.tokenize(',')}" var="parentArray"/>
                            <li><a href="${parentArray[0]}">${parentArray[1]}</a></li>
                        </g:if>
                        <g:if test="${breadcrumbs}">
                            <g:each in="${breadcrumbs.tokenize('\\')}" var="item">
                                <li><a href="${item.split(',', 2)[0]}">${item.split(',', 2)[1]}</a></li>
                            </g:each>
                        </g:if>
                        <li class="active">${breadcrumb}</li>
                    </ol>
                </nav>
            </div>
        </div>
    </section>
    <!-- End Breadcrumb -->
</g:if>
<!-- Optional banner message (requires ala-admin-plugin) -->
<plugin:isAvailable name="alaAdminPlugin">
	<div class="ala-admin-message">
        <ala:systemMessage/>
	</div>
</plugin:isAvailable>
<!-- end banner message -->
<!-- Container -->
<div class="${fluidLayout ? 'container-fluid' : 'container'}" id="main">
    <g:layoutBody />
</div><!-- End container #main col -->

<hf:footer/>

<asset:javascript src="${pageProperty(name: 'meta.deferred-js') ?: 'jquery-extensions'}" />
<asset:deferredScripts/>

</body>
</html>
