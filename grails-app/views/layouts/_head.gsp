<plugin:isNotAvailable name="resources">
    <link href="${grailsApplication.config.headerAndFooter.baseURL}/css/ala.min.css" rel="stylesheet" media="screen,print" />
    <g:if test="${assetLinks}">
        <g:each var="link" in="${assetLinks}">
            <link href="${link.href}" rel="${link.rel ?: 'stylesheet' }" media="${link.media}" />
        </g:each>
    </g:if>
    <asset:javascript src="${pageProperty(name:'meta.head-js') ?: "${assetPrefix}-head.js"}" />
</plugin:isNotAvailable>
<plugin:isAvailable name="resources">
    <r:require modules="${requireModule}"/>
    <r:layoutResources/>
</plugin:isAvailable>