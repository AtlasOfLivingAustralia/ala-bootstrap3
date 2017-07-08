<!DOCTYPE html>
<html lang="en-AU">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="app.version" content="${g.meta(name:'app.version')}"/>
    <meta name="app.build" content="${g.meta(name:'app.build')}"/>
    <meta name="description" content="Atlas of Living Australia"/>
    <meta name="author" content="Atlas of Living Australia">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${grailsApplication.config.skin?.favicon?:'http://www.ala.org.au/wp-content/themes/ala2011/images/favicon.ico'}" rel="shortcut icon"  type="image/x-icon"/>

    <title><g:layoutTitle /></title>

    <r:require modules="bootstrap, ala"/>

    <r:layoutResources/>
    <g:layoutHead />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="${pageProperty(name:'body.class')}" id="${pageProperty(name:'body.id')}" onload="${pageProperty(name:'body.onload')}">
<g:set var="fluidLayout" value="${pageProperty(name:'meta.fluidLayout')?:grailsApplication.config.skin?.fluidLayout}"/>
<!-- Header -->
<hf:banner logoutUrl="${g.createLink(controller:"logout", action:"logout", absolute: true)}" />
<!-- End header -->
<!-- Breadcrumb -->
<section id="breadcrumb">
    <div class="container">
        <div class="row">
            <ul class="breadcrumb-list">
                <li><a href="https://www.ala.org.au/">Home</a></li>
                <g:if test="${pageProperty(name:'meta.breadcrumbParent')}">
                    <g:set value="${pageProperty(name:'meta.breadcrumbParent').tokenize(',')}" var="parentArray"/>
                    <li><span class="glyphicon glyphicon-menu-right"></span><a href="${parentArray[0]}">${parentArray[1]}</a></li>
                </g:if>
                <g:if test="${pageProperty(name:'meta.breadcrumb')}">
                    <li class="active"><span class="glyphicon glyphicon-menu-right"></span>${pageProperty(name:'meta.breadcrumb')}</li>
                </g:if>
                <g:else>
                    <li class="active"><span class="glyphicon glyphicon-menu-right"></span>${pageProperty(name:'title')}</li>
                </g:else>
            </ul>
        </div>
    </div>
</section>
<!-- End Breadcrumb -->
<!-- Container -->
<div class="${fluidLayout ? 'container-fluid' : 'container'}" id="main">
    <g:layoutBody />
</div><!-- End container #main col -->

<!-- Footer -->
<hf:footer/>
<!-- End footer -->

<!-- JS resources-->
<r:layoutResources disposition="defer"/>
</body>
</html>