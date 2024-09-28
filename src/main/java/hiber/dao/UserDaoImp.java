package hiber.dao;

import hiber.model.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
  @SuppressWarnings("unchecked")
  public Optional<User> getCarByModelAndSeries(
      String model,
      int series
  ) {
    final byte MAX_RESULTS_ALLOWED = 1;
    String hql = "from User where "
        + "car.model = :model "
        + "and "
        + "car.series = :series";
    try {
      TypedQuery<User> query = sessionFactory
          .getCurrentSession()
          .createQuery(hql);

      query
          .setParameter("model", model)
          .setParameter("series", series)
          .setMaxResults(MAX_RESULTS_ALLOWED);

      return Optional.ofNullable(
          query.getSingleResult()
      );
    } catch (NoResultException | HibernateException | ClassCastException e) {
      LoggingUtil.printExceptionInfo("getCarByModelAndSeries", e);
      return Optional.empty();
    }
  }
}
