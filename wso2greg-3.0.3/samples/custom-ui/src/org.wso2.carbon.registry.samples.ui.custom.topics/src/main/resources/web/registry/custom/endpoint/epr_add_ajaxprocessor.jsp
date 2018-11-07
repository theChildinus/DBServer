
<%
    String parentPath = request.getParameter("parentPath");
%>

<br/>


<form id="eprAddForm" action="../registry/custom/endpoint/epr_add_handler_ajaxprocessor.jsp" method="post">
<input type="hidden" name="parentPath" value="<%=parentPath%>"/>
<table style="width:100%" class="styledLeft">
	<thead>
		<tr>
			<th colspan="2">
<strong>Add Resource</strong>
</th>
		</tr>
	</thead>
	<tbody>
    <tr>
        <td class="leftCol-med">Resource name <span class="required">*</span></td>
        <td><input type="text" name="resourceName"/></td>
    </tr>
    <tr>
        <td>Endpoint name  <span class="required">*</span></td>
        <td><input type="text" name="eprName"/></td>
    </tr>
    <tr>
        <td>URI <span class="required">*</span></td>
        <td><input type="text" name="uri"/></td>
    </tr>
    <tr>
        <td>Format</td>
        <td><input type="text" name="format"/></td>
    </tr>
    <tr>
        <td>Optimization method</td>
        <td><input type="text" name="optimize"/></td>
    </tr>
    <tr>
        <td>Duration to suspend this endpoint on failure</td>
        <td><input type="text" name="sd"/></td>
    </tr>
    <tr>
        <%--<td><input type="button" value="Save" onclick="submitCustomAddUIForm('eprAddForm', '../registry/custom/topics/epr_add_handler_ajaxprocessor.jsp')"/></td>--%>
        <td colspan="2" class="buttonRow"><input class="button" type="submit" value="Save"/></td>
    </tr>
    </tbody>
</table>
</form>

<br/>