<%@ page import="org.wso2.carbon.registry.common.utils.RegistryUtil" %>
<%@ page import="org.wso2.carbon.registry.samples.ui.custom.topics.utils.GetEndpointUtil" %>
<%@ page import="org.wso2.carbon.registry.ui.common.UIException" %>
<%@ page import="org.wso2.carbon.registry.samples.ui.custom.topics.beans.EndpointBean" %>
<%
    String cPath = request.getParameter("path");
    EndpointBean bean;
    try {
        bean = GetEndpointUtil.getEndpointBean(cPath, config, session);
    } catch (UIException e) {
        %>Error occured while retrieving endpoint details<%
        return;
    }
%>

<br/>

<h3>Edit Endpoint</h3>

<form id="eprEditForm">
<input type="hidden" name="path" value="<%=cPath%>"/>
<table width="50%">
    <tr>
        <td>Name</td>
        <td><input type="text" name="name" value="<%=bean.getName()%>"/></td>
    </tr>
    <tr>
        <td>URI</td>
        <td><input type="text" name="uri" value="<%=bean.getUri()%>"/></td>
    </tr>
    <tr>
        <td>Format</td>
        <td><input type="text" name="format" value="<%=bean.getFormat()%>"/></td>
    </tr>
    <tr>
        <td>Optimization method</td>
        <td><input type="text" name="optimize" value="<%=bean.getOptimize()%>"/></td>
    </tr>
    <tr>
        <td>Duration to suspend this endpoint on failure</td>
        <td><input type="text" name="sd" value="<%=bean.getSuspendDurationOnFailure()%>"/></td>
    </tr>
    <tr>
        <td><input type="button" value="Save" onclick="submitCustomViewUIForm('eprEditForm', '../registry/custom/endpoint/epr_edit_handler_ajaxprocessor.jsp')"/></td>
        <td></td>
    </tr>
</table>
</form>

<br/>