package bupt.intt.wsmonitor.servicemonitor.genericDao;

import java.util.List;

public interface GenericDao<T> {
	List<T> getAll();
	T getByDn(String dn);
	void create(T t);
	void update(T t);
	void delete(T t);
}
