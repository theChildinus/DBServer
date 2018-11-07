<%@ page import="org.wso2.carbon.registry.samples.ui.custom.topics.clients.TopicServiceClient" %>
<%@ page import="org.wso2.carbon.registry.samples.mgt.ui.custom.topics.beans.xsd.TopicBean" %>
<%@ page import="org.wso2.carbon.registry.common.utils.RegistryUtil" %>
<%@ page import="org.wso2.carbon.registry.samples.mgt.ui.custom.topics.beans.xsd.MapEntry" %>
<%
    MapEntry[] endpoints = new MapEntry[0];
    MapEntry[] topics = new MapEntry[0];
    try {
        String path = RegistryUtil.getPath(request);
        TopicServiceClient client = new TopicServiceClient(config, session);
        TopicBean bean = client.getTopicBean(path);

        endpoints = bean.getEndpoints();
        if (endpoints == null) {
            endpoints = new MapEntry[0];
        }

        topics = bean.getSubtopics();
        if (topics == null) {
            topics = new MapEntry[0];
        }
        
    } catch (Exception e) {
        %>Error occured while processing the topic details<br/>Error: <%=e.getMessage()%><%
        return;
    }
%>

<h3>Subscribed endpoints</h3>
<br/>

<% if (endpoints.length == 0) { %>
No endpoints are subscribed for this topic
<%
    } else {
        %><ul><%
        for (MapEntry endpoint: endpoints) {

            %><li><a href="./resource.jsp?path=<%=endpoint.getValue()%>"><%=endpoint.getName()%></a></li><%
        }
        %></ul><%
    }
%>

<br/>

<h3>Subtopics</h3>
<br/>

<% if (topics.length == 0) { %>
No subtopics are defined under this topic
<%
    } else {
        %><ul><%
        for (MapEntry topic: topics) {

            %><li><a href="./resources.jsp?path=<%=topic.getValue()%>"><%=topic.getName()%></a></li><%
        }
        %></ul><%
    }
%>

