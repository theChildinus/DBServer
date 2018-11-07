package bupt.intt.wsmonitor.servicemonitor.genericDao;

import java.util.List;

public abstract interface GenericDao<T>
{
  public abstract List<T> getAll();

  public abstract T getByDn(String paramString);

  public abstract void create(T paramT);

  public abstract void update(T paramT);

  public abstract void delete(T paramT);
}

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao
 * JD-Core Version:    0.6.0
 */