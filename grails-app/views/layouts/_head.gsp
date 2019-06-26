<plugin:isNotAvailable name="resources">
    <asset:stylesheet src="${pageProperty(name:'meta.head-screen-print-css') ?: "${assetPrefix}-screen-print.css"}" media="screen,print" />
    <asset:stylesheet src="${pageProperty(name:'meta.head-css') ?: "${assetPrefix}.css"}" />
    <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/ala.min.css" rel="stylesheet" media="screen,print" />
    <script type="text/javascript" src="${grailsApplication.config.headerAndFooter.baseURL}/js/ala.min.js"></script>
</plugin:isNotAvailable>
<plugin:isAvailable name="resources">
    <r:require modules="${requireModule}"/>
    <r:layoutResources disposition="head"/>
</plugin:isAvailable>