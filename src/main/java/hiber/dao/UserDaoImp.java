package hiber.dao;

import hiber.model.User;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.LoggingUtil;

@Repository
public class UserDaoImp implements UserDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public void add(User user) {
    sessionFactory
        .getCurrentSession()
        .save(user);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<User> listUsers() {
    TypedQuery<User> query = sessionFactory
        .getCurrentSession()
        .createQuery("from User");
    return query.getResultList();
  }

  @Override
  public User getCarOwner(
      String model,
      int series
  ) {
    final byte MAX_RESULTS_ALLOWED = 1;
    try {
      return (User) sessionFactory
          .getCurrentSession()
          .createQuery(
              "from User where "
                  + "car.model = :model "
                  + "and "
                  + "car.series = :series"
          )
          .setParameter("model", model)
          .setParameter("series", series)
          .setMaxResults(MAX_RESULTS_ALLOWED)
          .getSingleResult();
    } catch (NoResultException | HibernateException | ClassCastException e) {
      LoggingUtil.printExceptionInfo("getCarOwner", e);
      return null;
    }
  }
}
