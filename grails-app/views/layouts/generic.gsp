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
    <g:render template="head" model="[assetPrefix: 'core', requireModule: 'bootstrap']" />
    <g:layoutHead />
    <hf:head/>
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

<g:render template="tail" model="[assetPrefix: 'core']" />
</body>
</html>