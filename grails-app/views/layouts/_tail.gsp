<!-- Footer -->
<hf:footer logoutUrl="${g.createLink(controller: "logout", action: "logout", absolute: true)}"
           ignoreCookie="${grailsApplication.config.ignoreCookie}"/>
<!-- End footer -->

<!-- JS resources-->
<plugin:isNotAvailable name="resources">
    <g:if test="${!grailsApplication.config.headerAndFooter.excludeApplicationJs}">
        <script type="text/javascript" src="${grailsApplication.config.headerAndFooter.baseURL}/js/application.js"></script>
    </g:if>
    <g:if test="${!grailsApplication.config.headerAndFooter.excludeBootstrapJs}">
        <script type="text/javascript" src="${grailsApplication.config.headerAndFooter.baseURL}/js/bootstrap.min.js"></script>
    </g:if>
    <asset:javascript src="${pageProperty(name:'meta.deferred-js') ?: "${assetPrefix}.js"}" />
    <asset:deferredScripts />
</plugin:isNotAvailable>
<plugin:isAvailable name="resources">
    <r:layoutResources disposition="defer"/>
</plugin:isAvailable>