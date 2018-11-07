package bupt.intt.wsmonitor.servicemonitor.dao;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;;

class WebServiceAttributeMapper implements AttributesMapper{
	public Object mapFromAttributes(Attributes attrs)
    throws NamingException {
		WebServiceEntity service = new WebServiceEntity();
		
		service.setReturnType((String)attrs.get("wsrtype").get());
		service.setServiceName((String)attrs.get("wn").get());
		service.setServiceParameters((String)attrs.get("wsop").get());
		service.setServiceUrl((String)attrs.get("wsurl").get());
		
		return service;
	}
}
