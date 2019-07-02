<plugin:isNotAvailable name="resources">
    <g:if test="${assetLinks}">
        <g:each var="link" in="${assetLinks}">
            <link href="${link.href}" rel="${link.rel ?: 'stylesheet' }" media="${link.media}" />
        </g:each>
    </g:if>
    <asset:javascript src="${grailsApplication.config.headerAndFooter.baseURL}/js/jquery.min.js" />
    <asset:javascript src="${grailsApplication.config.headerAndFooter.baseURL}/js/autocomplete.min.js" />
</plugin:isNotAvailable>
<plugin:isAvailable name="resources">
    <r:require modules="${requireModule}"/>
    <r:layoutResources disposition="head"/>
</plugin:isAvailable>