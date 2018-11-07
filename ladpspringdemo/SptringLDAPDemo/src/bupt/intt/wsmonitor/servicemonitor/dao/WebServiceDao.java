package bupt.intt.wsmonitor.servicemonitor.dao;

import java.util.List;
import javax.naming.Name;

import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;
import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;

import org.springframework.ldap.core.LdapTemplate;

public class WebServiceDao implements GenericDao<WebServiceEntity> {
	private LdapTemplate ldapTemplate;
	
	public List<WebServiceEntity> getAll() {
		String _filter = "(objectclass=ws)";
		return ldapTemplate.search(WebServiceEntity.CATEGORYNAME, _filter, new WebServiceAttributeMapper());
	}
	
	public WebServiceEntity getByDn(String dn) {
		return (WebServiceEntity)ldapTemplate.lookup(dn, new WebServiceAttributeMapper());
	}
	
	public void create(WebServiceEntity service) {
		Name serviceDn = WebServiceLADPHandler.getDName(service);
		ldapTemplate.bind(serviceDn, null, WebServiceLADPHandler.BuildAttributes(service));
	}
	
	public void update(WebServiceEntity t) {
		
	}
	
	public void delete(WebServiceEntity service) {
		Name serviceDn = WebServiceLADPHandler.getDName(service);
		ldapTemplate.unbind(serviceDn);
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}
}
