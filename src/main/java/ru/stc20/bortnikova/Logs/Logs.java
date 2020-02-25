package ru.stc20.bortnikova.Logs;


import ru.stc20.bortnikova.ConnectionManager.ConnectionManagerJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  класс для работы с таблицей логов
 *  @author Bortnikova Olga
 */
public class Logs {

    private static final String SQLstrCreate = "DROP TABLE IF EXISTS logs ;" +
            "CREATE TABLE logs (" +
            "id        varchar(40), " +
            "date      varchar(40), " +
            "log_level varchar(10), " +
            "message   varchar(100), " +
            "exception varchar(256)); ";

    public static void createDB() throws SQLException {

        Connection connect = ConnectionManagerJdbcImpl.getInstance().getConnection();
        Statement statement = connect.createStatement();
        statement.execute(SQLstrCreate);

    }
}
