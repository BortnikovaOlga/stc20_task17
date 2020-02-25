package ru.stc20.bortnikova.DAO.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManager;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManagerJdbcImpl;
import ru.stc20.bortnikova.DAO.Role.RoleDao;
import ru.stc20.bortnikova.DAO.Role.RoleDaoJdbcImpl;
import ru.stc20.bortnikova.Entety.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserRoleDaoJdbcImplTest {

    private UserRoleDao userRoleDao;
    private static ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    @BeforeEach
    void setUp() {
        initMocks(this);
        connectionManager = spy(ConnectionManagerJdbcImpl.getInstance());
        when(connectionManager.getConnection()).thenReturn(connection);
        userRoleDao = new UserRoleDaoJdbcImpl(connectionManager);
    }

    @Test
    void createDB() throws SQLException {
        Statement statement = mock(Statement.class);
        doReturn(statement).when(connection).createStatement();
        boolean result = userRoleDao.createDB();
        verify(statement, times(1)).execute(UserRoleDaoJdbcImpl.SQLstrCreate);
        assertTrue(result);

    }

    @Test
    void add() throws SQLException {
        doReturn(preparedStatement).when(connection).prepareStatement(UserRoleDaoJdbcImpl.SQLstrInsert);
        int user_id = 6;
        int role_id = 6;
        boolean result = userRoleDao.add(new UserRole(user_id, role_id));
        verify(preparedStatement, times(1)).setInt(1, user_id);
        verify(preparedStatement, times(1)).setInt(2, role_id);
        verify(preparedStatement, times(1)).execute();
        assertTrue(result);
    }
}