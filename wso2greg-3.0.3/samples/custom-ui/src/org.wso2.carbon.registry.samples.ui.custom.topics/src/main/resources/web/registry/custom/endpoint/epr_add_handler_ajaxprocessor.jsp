<%@ page import="org.wso2.carbon.registry.samples.ui.custom.topics.utils.AddEndpointUtil" %>
<%@ page import="org.wso2.carbon.registry.ui.common.UIException" %>

<%
    String parentPath = request.getParameter("parentPath");
    try {
        AddEndpointUtil.addEndpointBean(request, config, session);
    } catch (UIException e) {
        %>
Could not add endpoint
<%
        return;
    }

    String resourcePagePath = "../../../resources/resource.jsp?path=" + parentPath;
    
    response.sendRedirect(resourcePagePath);
%>



