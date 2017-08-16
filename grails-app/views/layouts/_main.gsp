<!DOCTYPE html>
<html lang="en-AU">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="app.version" content="${g.meta(name:'info.app.version')}"/>
    <meta name="app.build" content="${g.meta(name:'info.app.build')}"/>
    <meta name="description" content="Atlas of Living Australia"/>
    <meta name="author" content="Atlas of Living Australia">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Favicon -->
    <g:if test="${grailsApplication.config.skin?.favicon && grailsApplication.config.skin.favicon.contains(".ico")}">
      <link href="${grailsApplication.config.skin.favicon}" rel="shortcut icon"  type="image/x-icon"/>
    </g:if>
    <g:else>
      <link rel="apple-touch-icon" sizes="57x57" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-57x57.png">
      <link rel="apple-touch-icon" sizes="60x60" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-60x60.png">
      <link rel="apple-touch-icon" sizes="72x72" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-72x72.png">
      <link rel="apple-touch-icon" sizes="76x76" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-76x76.png">
      <link rel="apple-touch-icon" sizes="114x114" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-114x114.png">
      <link rel="apple-touch-icon" sizes="120x120" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-120x120.png">
      <link rel="apple-touch-icon" sizes="144x144" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-144x144.png">
      <link rel="apple-touch-icon" sizes="152x152" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-152x152.png">
      <link rel="apple-touch-icon" sizes="180x180" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-icon-180x180.png">
      <link rel="icon" type="image/png" sizes="192x192" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/android-icon-192x192.png">
      <link rel="icon" type="image/png" sizes="32x32" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/favicon-32x32.png">
      <link rel="icon" type="image/png" sizes="96x96" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/favicon-96x96.png">
      <link rel="icon" type="image/png" sizes="16x16" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/favicon-16x16.png">
      <link rel="manifest" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/manifest.json?v2">
      <meta name="msapplication-TileColor" content="#ffffff">
      <meta name="msapplication-TileImage" content="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/ms-icon-144x144.png">
      <meta name="theme-color" content="#ffffff">
    </g:else>
    
    <link rel="apple-touch-icon" sizes="180x180" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/favicon-16x16.png">
    <link rel="manifest" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/manifest.json">
    <link rel="mask-icon" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/safari-pinned-tab.svg" color="#5bbad5">
    <link rel="shortcut icon" href="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/favicon.ico">
    <meta name="msapplication-config" content="https://www.ala.org.au/wp-content/themes/ala-wordpress-theme/img/favicon/browserconfig.xml">
    <meta name="theme-color" content="#ffffff">
    

    <title><g:layoutTitle /></title>

    <g:if test="${!grailsApplication.config.headerAndFooter.excludeBootstrapCss}">
        <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/bootstrap.min.css" rel="stylesheet"
              media="screen,print"/>
    </g:if>
    <g:if test="${!grailsApplication.config.headerAndFooter.excludeAlaStylesCss}">
        <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/ala-styles.css" rel="stylesheet"
              media="screen,print"/>
    </g:if>

    <asset:stylesheet src="${pageProperty(name: 'meta.head-screen-print-css') ?: "core-screen-print"}"
                      media="screen,print"/>
    <asset:stylesheet src="${pageProperty(name: 'meta.head-css') ?: "core"}"/>

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

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="${pageProperty(name:'body.class')}" id="${pageProperty(name:'body.id')}" onload="${pageProperty(name:'body.onload')}">
<g:set var="fluidLayout" value="${pageProperty(name:'meta.fluidLayout')?:grailsApplication.config.skin?.fluidLayout}"/>
<!-- Header -->
<hf:banner logoutUrl="${g.createLink(controller: "logout", action: "logout", absolute: true)}"
           ignoreCookie="${grailsApplication.config.ignoreCookie}"/>
<!-- End header -->
<!-- Breadcrumb -->
<section id="breadcrumb">
    <div class="container">
        <div class="row">
            <nav aria-label="Breadcrumb" role="navigation">
                <ol class="breadcrumb-list">
                    <li><a href="https://www.ala.org.au/">Home</a></li>
                    <g:if test="${pageProperty(name:'meta.breadcrumbParent')}">
                        <g:set value="${pageProperty(name:'meta.breadcrumbParent').tokenize(',')}" var="parentArray"/>
                        <li><a href="${parentArray[0]}">${parentArray[1]}</a></li>
                    </g:if>
                    <g:if test="${pageProperty(name: 'meta.breadcrumbs')}">
                        <g:each in="${pageProperty(name: 'meta.breadcrumbs').tokenize('\\')}" var="item">
                            <li><a href="${item.split(',', 2)[0]}">${item.split(',', 2)[1]}</a></li>
                        </g:each>
                    </g:if>
                    <li class="active"><g:if test="${pageProperty(name:'meta.breadcrumb')}">${pageProperty(name:'meta.breadcrumb')}</g:if><g:else>${pageProperty(name:'title')}</g:else></li>
                </ol>
            </nav>
        </div>
    </div>
</section>
<!-- End Breadcrumb -->
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