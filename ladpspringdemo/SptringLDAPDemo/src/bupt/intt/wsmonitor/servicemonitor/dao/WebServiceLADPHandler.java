package bupt.intt.wsmonitor.servicemonitor.dao;

import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;

import org.springframework.ldap.core.DistinguishedName;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

class WebServiceLADPHandler {
	public static Name getDName(WebServiceEntity wService) {
		DistinguishedName name = new DistinguishedName("");
		name.add("ou","services");
		name.add("wn",wService.getServiceName());	
		
		return name;
	}
	
	public static Attributes BuildAttributes(WebServiceEntity wService) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add("ws");
		attrs.put(ocattr);
		
		attrs.put("wn",wService.getServiceName());
		attrs.put("wsop",wService.getServiceParameters());		
		attrs.put("wsrtype",wService.getReturnType());
		attrs.put("wsurl",wService.getServiceUrl());
		
		return attrs;
	}
}
