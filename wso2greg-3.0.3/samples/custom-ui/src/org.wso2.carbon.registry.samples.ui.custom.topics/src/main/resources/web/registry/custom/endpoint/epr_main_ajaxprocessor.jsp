<%@ page import="org.wso2.carbon.registry.samples.ui.custom.topics.beans.EndpointBean" %>
<%@ page import="org.wso2.carbon.registry.samples.ui.custom.topics.utils.GetEndpointUtil" %>
<%@ page import="org.wso2.carbon.registry.common.utils.RegistryUtil" %>
<%@ page import="org.wso2.carbon.registry.ui.common.UIException" %>
<%
    String cPath = RegistryUtil.getPath(request);
    EndpointBean bean;
    try {
        bean = GetEndpointUtil.getEndpointBean(cPath, config, session);
    } catch (UIException e) {
        %>Error occured while retrieving endpoint details<%
        return;
    }
%>

<br/>

<h3>Endpoint</h3>

<table width="50%">
    <tr>
        <td>Name</td>
        <td><%=bean.getName()%></td>
    </tr>
    <tr>
        <td>URI</td>
        <td><%=bean.getUri()%></td>
    </tr>
    <tr>
        <td>Format</td>
        <td><%=bean.getFormat()%></td>
    </tr>
    <tr>
        <td>Optimization method</td>
        <td><%=bean.getOptimize()%></td>
    </tr>
    <tr>
        <td>Duration to suspend this endpoint on failure</td>
        <td><%=bean.getSuspendDurationOnFailure()%></td>
    </tr>
</table>

<a onclick="loadViewUI('../registry/custom/endpoint/epr_edit_ajaxprocessor.jsp', '<%=cPath%>')">Edit end point</a>

<br/>