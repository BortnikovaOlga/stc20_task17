package ru.stc20.bortnikova.DAO.Role;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManager;
import ru.stc20.bortnikova.ConnectionManager.ConnectionManagerJdbcImpl;
import ru.stc20.bortnikova.Entety.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class RoleDaoJdbcImplTest {

    private RoleDao roleDao;
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
        roleDao = new RoleDaoJdbcImpl(connectionManager);
    }

    @Test
    void add() throws SQLException {
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDaoJdbcImpl.SQLstrInsert);
        int id = 6;
        String testuser = "testuser";
        boolean result = roleDao.add(new Role(id, testuser, testuser));
        verify(connection, times(1)).prepareStatement(RoleDaoJdbcImpl.SQLstrInsert);
        verify(preparedStatement, times(1)).setInt(1, id);
        verify(preparedStatement, times(1)).setString(2, testuser);
        verify(preparedStatement, times(1)).setString(3, testuser);
        verify(preparedStatement, times(1)).execute();
        assertTrue(result);
    }

    @Test
    void addWithSqlException() throws SQLException {
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDaoJdbcImpl.SQLstrInsert);
        doThrow(SQLException.class).when(preparedStatement).execute();
        int id = 6;
        String testuser = "testuser";
        boolean result = roleDao.add(new Role(id, testuser, testuser));
        verify(connection, times(1)).prepareStatement(RoleDaoJdbcImpl.SQLstrInsert);
        verify(preparedStatement, times(1)).setInt(1, id);
        verify(preparedStatement, times(2)).setString(anyInt(), anyString());
        verify(preparedStatement, never()).executeQuery();
        verify(preparedStatement, times(1)).execute();
        assertFalse(result);
    }

    @Test
    void createDB() throws SQLException {
        Statement statement = mock(Statement.class);
        doReturn(statement).when(connection).createStatement();
        boolean result = roleDao.createDB();
        verify(statement, times(1)).execute(RoleDaoJdbcImpl.SQLstrCreate);
        assertTrue(result);
    }
}