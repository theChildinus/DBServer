<%@ page import="org.wso2.carbon.registry.samples.ui.custom.topics.utils.SaveEndpointUtil" %>
<%@ page import="org.wso2.carbon.registry.common.utils.RegistryUtil" %>
<%@ page import="org.wso2.carbon.registry.ui.common.UIException" %>
<%
    String esPath = request.getParameter("path");
    try {
        SaveEndpointUtil.saveEndpointBean(esPath, request, config, session);
    } catch (UIException e) {
        %>Could not save the endpoint details<%
        return;
    }

    String eprMainPath = "epr_main_ajaxprocessor.jsp?path=" + esPath;
%>

<jsp:forward page="<%=eprMainPath%>"/>