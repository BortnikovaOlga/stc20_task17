package ru.stc20.bortnikova.DAO.User;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManager;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManagerJdbcImpl;
import ru.stc20.bortnikova.Entety.User;


/**
 * работа с таблицей user  информация о пользователях
 *
 * @author Bortnikova Olga
 */
public class UserDaoJdbcImpl implements UserDao {

    private Connection connect;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbcImpl.class.getName());

    protected static final String SQLstrInsert =
            "INSERT INTO users (id, name, birthday, login_id, city, email, desctiption) VALUES (?,?,?,?,?,?,?);";
    protected static final String SQLstrCreate = "DROP TABLE IF EXISTS users CASCADE ;"
            + "CREATE TABLE users ("
            + "id          integer primary key, "
            + "name        varchar(20) NOT NULL, "
            + "birthday    date, "
            + "login_id    integer, "
            + "city        varchar(20),"
            + "email       varchar(30), "
            + "desctiption varchar(50));";

    public UserDaoJdbcImpl() {
        this.connect = ConnectionManagerJdbcImpl.getInstance().getConnection();
    }

    public UserDaoJdbcImpl(ConnectionManager connectionManager) {
        this.connect = connectionManager.getConnection();
    }

    /**
     * создание таблицы users
     *
     * @return true в случае успешной операции, false если таблица не создана
     */
    @Override
    public boolean createDB() {
        /*try {
            Connection connect = ConnectionManagerJdbcImpl.getInstance().getConnection();*/
        try (Statement statement = connect.createStatement()) {
            statement.execute(SQLstrCreate);
            logger.info("создана таблица user");
        } catch (SQLException e) {
            logger.error("Ошибка создания таблицы user", e);
            return false;
        }
        return true;
    }

    /**
     * метод добавляет новую запись в таблицу users в соответствии с состоянием объекта user
     *
     * @param user объект User
     * @return true при успешной операции, false если запись не добавлена
     */
    @Override
    public boolean add(User user) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQLstrInsert);) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setDate(3, Date.valueOf(user.getBithday()));
            preparedStatement.setInt(4, user.getLogin_id());
            preparedStatement.setString(5, user.getCity());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getDescription());
            preparedStatement.execute();
            logger.info("добавлена запись в таблицу user");
        } catch (SQLException e) {
            logger.error("Ошибка записи в таблицу user", e);
            return false;
        }
        return true;
    }

    /**
     * метод добавляет записи в таблицу users
     *
     * @param userArr массив объектов User
     * @return true при успешной операции, false если записи не добавлены
     */
    @Override
    public boolean add(User[] userArr) {

        try (PreparedStatement preparedStatement = connect.prepareStatement(SQLstrInsert)) {
            for (User u : userArr) {
                preparedStatement.setInt(1, u.getId());
                preparedStatement.setString(2, u.getName());
                preparedStatement.setDate(3, Date.valueOf(u.getBithday()));
                preparedStatement.setInt(4, u.getLogin_id());
                preparedStatement.setString(5, u.getCity());
                preparedStatement.setString(6, u.getEmail());
                preparedStatement.setString(7, u.getDescription());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            logger.info("добавлены записи в таблицу user");
        } catch (SQLException e) {
            logger.error("Ошибка записи в таблицу user", e);
            return false;
        }
        return true;
    }
}
