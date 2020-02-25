package ru.stc20.bortnikova.DAO.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManager;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManagerJdbcImpl;
import ru.stc20.bortnikova.DAO.Role.RoleDaoJdbcImpl;
import ru.stc20.bortnikova.Entety.User;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserDaoJdbcImplTest {
    private UserDao userDao;
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    @BeforeEach
    void setUp() {
        initMocks(this);
        connectionManager = spy(ConnectionManagerJdbcImpl.getInstance());
        when(connectionManager.getConnection()).thenReturn(connection);
        userDao = new UserDaoJdbcImpl(connectionManager);
    }

    @Test
    void add() throws SQLException {
        doReturn(preparedStatement).when(connection).prepareStatement(UserDaoJdbcImpl.SQLstrInsert);
        int id = 6;
        String name = "usertest";
        String bithday = "2020-01-01";
        int login_id = 6;
        String city = "citytest";
        String email = "test@mail.ru";
        String description = "";
        boolean result = userDao.add(new User(id, name, bithday, login_id, city, email, description));
        verify(connection, times(1)).prepareStatement(UserDaoJdbcImpl.SQLstrInsert);
        verify(preparedStatement, times(1)).setInt(1, id);
        verify(preparedStatement, times(1)).setString(2, name);
        verify(preparedStatement, times(1)).setDate(3, Date.valueOf(bithday));
        verify(preparedStatement, times(1)).setInt(4, login_id);
        verify(preparedStatement, times(1)).setString(5, city);
        verify(preparedStatement, times(1)).setString(6, email);
        verify(preparedStatement, times(1)).setString(7, description);
        verify(preparedStatement, times(1)).execute();
        assertTrue(result);
    }

    @Test
    void addFromArray() throws SQLException {
        doReturn(preparedStatement).when(connection).prepareStatement(UserDaoJdbcImpl.SQLstrInsert);
        int id = 6;
        String name = "usertest";
        String bithday = "2020-01-01";
        int login_id = 6;
        String city = "citytest";
        String email = "test@mail.ru";
        String description = "";
        boolean result = userDao.add(new User[]{
                new User(id, name, bithday, login_id, city, email, description),
                new User(id, name, bithday, login_id, city, email, description)});
        verify(connection, times(1)).prepareStatement(UserDaoJdbcImpl.SQLstrInsert);
        verify(preparedStatement, times(2)).setInt(1, id);
        verify(preparedStatement, times(2)).setString(2, name);
        verify(preparedStatement, times(2)).setDate(3, Date.valueOf(bithday));
        verify(preparedStatement, times(2)).setInt(4, login_id);
        verify(preparedStatement, times(2)).setString(5, city);
        verify(preparedStatement, times(2)).setString(6, email);
        verify(preparedStatement, times(2)).setString(7, description);
        verify(preparedStatement, times(1)).executeBatch();
        assertTrue(result);
    }
    @Test
    void createDB() throws SQLException {
        Statement statement = mock(Statement.class);
        doReturn(statement).when(connection).createStatement();
        boolean result = userDao.createDB();
        verify(statement, times(1)).execute(UserDaoJdbcImpl.SQLstrCreate);
        assertTrue(result);

    }

}