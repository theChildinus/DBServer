package org.wso2.carbon.registry.samples.ui.custom.topics.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMAbstractFactory;
import org.wso2.carbon.registry.ui.common.UIException;
import org.wso2.carbon.registry.ui.resource.clients.CustomUIServiceClient;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class SaveEndpointUtil {

    private static final Log log = LogFactory.getLog(SaveEndpointUtil.class);

    public static void saveEndpointBean(
            String path, HttpServletRequest request, ServletConfig config, HttpSession session) throws UIException {

        try {
            CustomUIServiceClient customUIServiceClient =
                    new CustomUIServiceClient(config, session);

            String name = request.getParameter("name");
            String uri = request.getParameter("uri");
            String format = request.getParameter("format");
            String optimize = request.getParameter("optimize");
            String sd = request.getParameter("sd");

            OMFactory fac = OMAbstractFactory.getOMFactory();
            OMElement endpointElement = fac.createOMElement("endpoint", null);
            endpointElement.addAttribute("name", name, null);

            OMElement addressElement = fac.createOMElement("address", null);
            addressElement.addAttribute("uri", uri, null);
            addressElement.addAttribute("format", format, null);
            addressElement.addAttribute("optimize", optimize, null);
            endpointElement.addChild(addressElement);

            OMElement sdElement = fac.createOMElement("suspendDurationOnFailure", null);
            sdElement.setText(sd);
            addressElement.addChild(sdElement);

            String content = endpointElement.toString();

            customUIServiceClient.updateTextContent(path, content);

        } catch (Exception e) {
            String msg = "Failed to get end point details. " + e.getMessage();
            log.error(msg, e);
            throw new UIException(msg, e);
        }
    }
}
