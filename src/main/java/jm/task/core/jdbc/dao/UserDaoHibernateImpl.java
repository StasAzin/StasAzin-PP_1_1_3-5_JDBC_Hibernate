package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS user (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NULL, lastName VARCHAR(45) NULL, age TINYINT NULL, UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE, PRIMARY KEY (id))";
    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS user";

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try (session) {
            session.createSQLQuery(CREATE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            switch (transaction.getStatus()) {
                case ACTIVE, MARKED_ROLLBACK -> transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try (session) {
            session.createSQLQuery(DROP_USER_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            switch (transaction.getStatus()) {
                case ACTIVE, MARKED_ROLLBACK -> transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try (session) {
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            switch (transaction.getStatus()) {
                case ACTIVE, MARKED_ROLLBACK -> transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try (session) {
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            switch (transaction.getStatus()) {
                case ACTIVE, MARKED_ROLLBACK -> transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try (session) {
            userList = session.createQuery("SELECT user FROM User user", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            switch (transaction.getStatus()) {
                case ACTIVE, MARKED_ROLLBACK -> transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try (session) {
            session.createSQLQuery("TRUNCATE user").executeUpdate();
        } catch (Exception e) {
            switch (transaction.getStatus()) {
                case ACTIVE, MARKED_ROLLBACK -> transaction.rollback();
            }
        }
    }
}
