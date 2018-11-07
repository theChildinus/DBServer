function getBackendServerUrl() {

    var xmlHttpReq = createXmlHttpRequest();

                //Make sure the XMLHttpRequest object was instantiated
    if (xmlHttpReq)
    {
        // This is a synchronous POST, hence UI blocking.
        xmlHttpReq.open("GET", "carbon/gauges/gadgets/flash/flashdata-ajaxprocessor.jsp?funcName=getBackendServerUrl" +
                               "&ms=" + new Date().getTime(), false);
        xmlHttpReq.send(null);

        if (xmlHttpReq.status == 200) {
            return removeCarriageReturns(xmlHttpReq.responseText);
        }

        return false;
    }

    return false;
}

function createXmlHttpRequest() {
    var request;

                // Lets try using ActiveX to instantiate the XMLHttpRequest object
    try {
        request = new ActiveXObject("Microsoft.XMLHTTP");
    } catch(ex1) {
        try {
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch(ex2) {
            request = null;
        }
    }

                // If the previous didn't work, lets check if the browser natively support XMLHttpRequest
    if (!request && typeof XMLHttpRequest != "undefined") {
        //The browser does, so lets instantiate the object
        request = new XMLHttpRequest();
    }

    return request;
}

function removeCarriageReturns(string) {
    return string.replace(/\n/g, "");
}

function init() {
    var serverListArray = getServerLisit().split(",");

    // Cleaning up the existing select box
    var selectBoxEl = document.getElementById("server_select_box");
    selectBoxEl.innerHTML = "";

    var newServerSelectHTML = '<select id="serverUrls" onchange="loadServices();"><option value="">--Server--</option>';
    for (var x = 0; x < serverListArray.length; x++) {
        newServerSelectHTML +=
        '<option value="' + serverListArray[x] + '">' + serverListArray[x] + '</option>';
    }
    newServerSelectHTML += '</select>';

    // Adding the new select to div
    selectBoxEl.innerHTML = newServerSelectHTML;

    if (!isServerExists(serverListArray, serverUrl)) {
        serverUrl = "";
        prefs.set("serverUrl", serverUrl);
    }

    drawDiagram();
}

function getServiceList() {
    var selectedServerURL = document.getElementById('serverUrls')[document.getElementById('serverUrls').selectedIndex].value;

    if (!(selectedServerURL == "No Servers Configured")) {
        var xmlHttpReq = createXmlHttpRequest();
        
        //Make sure the XMLHttpRequest object was instantiated
        if (xmlHttpReq)
        {
            // This is a synchronous POST, hence UI blocking.
            xmlHttpReq.open("GET", "carbon/gauges/gadgets/flash/flashdata-ajaxprocessor.jsp?funcName=getServicesList&serverUrl=" +
                                   encodeHex(selectedServerURL) + "&ms=" +
                                   new Date().getTime(), false);
            xmlHttpReq.send(null);

            if (xmlHttpReq.status == 200) {
                return removeCarriageReturns(xmlHttpReq.responseText);
            }

            return false;
        }
    }
    return false;
}

function getServerLisit() {
    var xmlHttpReq = createXmlHttpRequest();
    var serverList = [];
    //Make sure the XMLHttpRequest object was instantiated
    if (xmlHttpReq)
    {
        // This is a synchronous POST, hence UI blocking.
        xmlHttpReq.open("GET", "carbon/gauges/gadgets/flash/flashdata-ajaxprocessor.jsp?funcName=getServerList" +
                               "&ms=" + new Date().getTime(), false);
        xmlHttpReq.send(null);

        if (xmlHttpReq.status == 200) {
            return removeCarriageReturns(xmlHttpReq.responseText);
        }

        return false;
    }
    return false;
}

function getlastminuterequestcount(serverUrl, serviceName) {
    var xmlHttpReq = createXmlHttpRequest();

    //Make sure the XMLHttpRequest object was instantiated
    if (xmlHttpReq)
    {
        // This is a synchronous POST, hence UI blocking.
        xmlHttpReq.open("GET", "carbon/gauges/gadgets/flash/flashdata-ajaxprocessor.jsp?funcName=lastminuterequestcount&serverUrl=" +
                               encodeHex(serverUrl) + "&serviceName=" + serviceName + "&ms=" +
                               new Date().getTime(), false);
        xmlHttpReq.send(null);

        if (xmlHttpReq.status == 200) {
            return removeCarriageReturns(xmlHttpReq.responseText);
        }

        return false;
    }
    return false;
}

function getminmaxaverageresptimessystem(serverUrl) {
    var xmlHttpReq = createXmlHttpRequest();

    //Make sure the XMLHttpRequest object was instantiated
    if (xmlHttpReq)
    {
        // This is a synchronous POST, hence UI blocking.
        xmlHttpReq.open("GET", "carbon/gauges/gadgets/flash/flashdata-ajaxprocessor.jsp?funcName=getminmaxaverageresptimessystem&serverUrl=" +
                               encodeHex(serverUrl) + "&ms=" + new Date().getTime(), false);
        xmlHttpReq.send(null);

        if (xmlHttpReq.status == 200) {
            return removeCarriageReturns(xmlHttpReq.responseText);
        }

        return false;
    }
    return false;
}

function lastminuterequestcountsystem() {
    var xmlHttpReq = createXmlHttpRequest();

    //Make sure the XMLHttpRequest object was instantiated
    if (xmlHttpReq)
    {
        // This is a synchronous POST, hence UI blocking.
        xmlHttpReq.open("GET", "carbon/gauges/gadgets/flash/flashdata-ajaxprocessor.jsp?funcName=lastminuterequestcountsystem&serverUrl=" +
                               encodeHex(serverUrl) + "&ms=" + new Date().getTime(), false);
        xmlHttpReq.send(null);

        if (xmlHttpReq.status == 200) {
            return removeCarriageReturns(xmlHttpReq.responseText);
        }

        return false;
    }
    return false;
}

function loadServices() {
    var serviceListArray = getServiceList().split(",");

    // Cleaning up the existing select box
    var selectBoxEl = document.getElementById("service_select_box");
    selectBoxEl.innerHTML = "";

    var newServerSelectHTML = '<select id="services" onchange="refreshData();"><option value="">--Service--</option>';
    for (var x = 0; x < serviceListArray.length; x++) {
        newServerSelectHTML +=
        '<option value="' + serviceListArray[x] + '">' + serviceListArray[x] + '</option>';
    }
    newServerSelectHTML += '</select>';

    // Adding the new select to div
    selectBoxEl.innerHTML = newServerSelectHTML;
}

function refreshData() {
    serverUrl =
    document.getElementById('serverUrls')[document.getElementById('serverUrls').selectedIndex].value;
    serviceName =
    document.getElementById('services')[document.getElementById('services').selectedIndex].value;

    if ((serverUrl != "") && (serverUrl != "No Servers Configured") &&
        (serviceName != "")) {
        prefs.set("serverUrl", serverUrl);
        prefs.set("serviceName", serviceName);
    }

    drawDiagram();
}

function isServerExists(serverListArray, monitoredServer) {
    for (var x = 0; x < serverListArray.length; x++) {
        if (serverListArray[x] == monitoredServer) {
            return true
        }
    }

    return false;
}


