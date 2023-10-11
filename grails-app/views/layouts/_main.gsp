<!DOCTYPE html>
<html lang="en-AU">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="app.version" content="${g.meta(name:'info.app.version')}"/>
    <meta name="app.build" content="${g.meta(name:'info.app.build')}"/>
    <meta name="description" content="${grailsApplication.config.getProperty('skin.orgNameLong')?:'Atlas of Living Australia'}"/>
    <meta name="author" content="${grailsApplication.config.getProperty('skin.orgNameLong')?:'Atlas of Living Australia'}">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Favicon -->
    <link href="${grailsApplication.config.getProperty('skin.favicon')}" rel="shortcut icon"  type="image/x-icon"/>

    <title><g:layoutTitle /></title>
    <g:if test="${!grailsApplication.config.getProperty('headerAndFooter.excludeBootstrapCss')}">
        <link href="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/css/bootstrap.min.css" rel="stylesheet" media="screen,print"/>
        <link href="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/css/bootstrap-theme.min.css" rel="stylesheet" media="screen,print"/>
    </g:if>
    <g:if test="${!grailsApplication.config.getProperty('headerAndFooter.excludeAlaStylesCss')}">
        <link href="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/css/ala-styles.css" rel="stylesheet"
              media="screen,print"/>
    </g:if>

    <g:set var="hfVersion" value="${grailsApplication.config.getProperty('headerAndFooter.version', Integer, 2)}" />

    <g:if test="${hfVersion == 1}">
        <asset:stylesheet src="${pageProperty(name: 'meta.head-css') ?: "core"}"/>
        <asset:stylesheet src="${pageProperty(name: 'meta.head-screen-print-css') ?: "core-screen-print"}"
                          media="screen,print"/>
    </g:if>
    <g:elseif test="${hfVersion == 2}">
        <link href="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/css/autocomplete.min.css" rel="stylesheet" media="screen,print"/>
        <link href="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/css/autocomplete-extra.min.css" rel="stylesheet" media="screen,print"/>
        <link href="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/css/font-awesome.min.css" rel="stylesheet" media="screen,print"/>
    </g:elseif>


    <plugin:isAvailable name="alaAdminPlugin"><asset:stylesheet src="ala-admin-asset.css" /></plugin:isAvailable>

    <g:if test="${hfVersion == 1}">
        <asset:javascript src="${pageProperty(name: 'meta.head-js') ?: 'head'}"/>
        <asset:javascript src="${pageProperty(name: 'meta.deferred-js') ?: 'jquery-extensions'}" />
    </g:if>
    <g:elseif test="${hfVersion == 2}">
        <script type="text/javascript"
                src="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/js/jquery.min.js"></script>
        <script type="text/javascript"
                src="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/js/jquery-migration.min.js"></script>
        <script type="text/javascript"
                src="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/js/autocomplete.min.js"></script>
    </g:elseif>

    <g:if test="${!grailsApplication.config.getProperty('headerAndFooter.excludeApplicationJs')}">
        <script type="text/javascript" src="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/js/application.js"
                defer></script>
    </g:if>
    <g:if test="${!grailsApplication.config.getProperty('headerAndFooter.excludeBootstrapJs')}">
        <script type="text/javascript"
                src="${grailsApplication.config.getProperty('headerAndFooter.baseURL')}/js/bootstrap.min.js"></script>
    </g:if>
    <g:layoutHead />
    <hf:head/>

</head>
<body class="${pageProperty(name:'body.class')}" id="${pageProperty(name:'body.id')}" onload="${pageProperty(name:'body.onload')}">
<g:set var="fluidLayout" value="${pageProperty(name:'meta.fluidLayout')?:grailsApplication.config.getProperty('skin.fluidLayout', Boolean, false)}"/>
<%-- Nag Banner --%>
<hf:nagger>
<div id="overlay">
    <div id="nag-modal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title"><g:message message="survey.title"/></h4>
                </div>
                <div class="modal-body">
                    <p><g:message code="survey.p" args="[u.createLink(baseProperty:'userdetails.web.url', pathsProperty: 'userdetails.profilePath')]"/></p>
                    <p><g:message message="survey.p2" /></p>
                </div>
                <div class="modal-footer">
                    <a role="button" class="btn btn-primary" href="${u.createLink(baseProperty:'userdetails.web.url', pathsProperty: 'userdetails.profilePath')}">OK</a>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <asset:script type="text/javascript">
    $(document).ready(function(){
        $("#nag-modal").modal('show');
    });
    </asset:script>
</div>
</hf:nagger>
<!-- Header -->
<hf:banner logoutUrl="${g.createLink(controller: "logout", action: "logout", absolute: true)}"
           ignoreCookie="${grailsApplication.config.getProperty('ignoreCookie', Boolean, false)}" fluidLayout="${grailsApplication.config.getProperty('skin.fluidLayout', Boolean, false)}"/>
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
                        <li><a href="${grailsApplication.config.getProperty('skin.homeUrl')?:'https://www.ala.org.au'}"><g:message code="breadcrumb.home" default="Home"/></a></li>
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

<hf:footer logoutUrl="${g.createLink(controller: "logout", action: "logout", absolute: true)}"
           ignoreCookie="${grailsApplication.config.getProperty('ignoreCookie', Boolean, false)}"/>

<asset:deferredScripts/>

</body>
</html>
