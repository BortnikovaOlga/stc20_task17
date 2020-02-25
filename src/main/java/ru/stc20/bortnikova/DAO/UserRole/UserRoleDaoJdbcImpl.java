package ru.stc20.bortnikova.DAO.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManager;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManagerJdbcImpl;
import ru.stc20.bortnikova.Entety.UserRole;


/**
 * работа в таблице userrole
 *
 * @author Bortnikova Olga
 */

public class UserRoleDaoJdbcImpl implements UserRoleDao {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleDaoJdbcImpl.class.getName());

    private Connection connect;
    protected static final String SQLstrInsert = "INSERT INTO userrole (user_id, role_id) VALUES (?,?);";
    protected static final String SQLstrCreate = "DROP TABLE IF EXISTS userrole CASCADE ;"
            + "CREATE TABLE userrole ("
            + "role_id  integer references role on delete cascade , "
            + "user_id  integer references users on delete cascade ); ";

    public UserRoleDaoJdbcImpl() {
        this.connect = ConnectionManagerJdbcImpl.getInstance().getConnection();
    }

    public UserRoleDaoJdbcImpl(ConnectionManager connectionManager) {
        this.connect=connectionManager.getConnection();
    }

    /**
     * метод создает таблицу userrole
     * @return true в случае успешной операции, false если таблица не создана
     */
    @Override
    public boolean createDB() {
        try (Statement statement = connect.createStatement()){
            statement.execute(SQLstrCreate);
            logger.info("Создана таблица userrole");
        } catch (SQLException e) {
            logger.error("Ошибка создания таблицы userrole", e);
            return false;
        }
        return true;
    }

    /**
     * метод добавляет новую запись в таблицу userrole
     *
     * @param userRole объект UserRole
     * @return true при успешной операции, false если запись не добавлена
     */
    @Override
    public boolean add(UserRole userRole) {

        try (PreparedStatement preparedStatement = connect.prepareStatement(SQLstrInsert)) {
            preparedStatement.setInt(1, userRole.getUser_id());
            preparedStatement.setInt(2, userRole.getRole_id());
            preparedStatement.execute();
            logger.info("добавлена запись в таблицу userrole");
        } catch (SQLException e) {
            logger.error("Ошибка добавления в таблицу userrole", e);
            return false;
        }
        return true;

    }

}
