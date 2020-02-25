package ru.stc20.bortnikova;

import ru.stc20.bortnikova.ConnectionManager.ConnectionManagerJdbcImpl;
import ru.stc20.bortnikova.DAO.Role.RoleDao;
import ru.stc20.bortnikova.DAO.Role.RoleDaoJdbcImpl;
import ru.stc20.bortnikova.DAO.User.UserDao;
import ru.stc20.bortnikova.DAO.User.UserDaoJdbcImpl;
import ru.stc20.bortnikova.DAO.UserRole.UserRoleDao;
import ru.stc20.bortnikova.DAO.UserRole.UserRoleDaoJdbcImpl;
import ru.stc20.bortnikova.Entety.Role;
import ru.stc20.bortnikova.Entety.User;
import ru.stc20.bortnikova.Entety.UserRole;
import ru.stc20.bortnikova.Logs.Logs;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * демо - работа с БД
 *
 * @author Bortnikova Olga
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        // создать таблицу для записи логов
        Logs.createDB();

        // создаем объекты управления таблицами
        RoleDao roleDao = new RoleDaoJdbcImpl();
        UserDao userDao = new UserDaoJdbcImpl();
        UserRoleDao userroleDao = new UserRoleDaoJdbcImpl();

       //создаем таблицы role(справочник ролей) , user(информация о пользователях) , userrole(сводная - роли пользователей)
        if (!(userDao.createDB() & roleDao.createDB() & userroleDao.createDB())) return;

        User[] userArr = new User[]{
                new User(1, "Anton", "1990-12-11", 123, "Innopolis", "AntonK@mail.ru", ""),
                new User(2, "Marya", "1996-07-15", 124, "Kazan", "Marya@mail.ru", ""),
                new User(3, "Olga", "1979-09-28", 125, "Rubtsovsk", "OlgaB@mail.ru", "")};

        // вставить записи в таблицу users из массива userArr
        userDao.add(userArr);

        // вставить записи справочник ролей
        roleDao.add(new Role(1, "Administration", ""));
        roleDao.add(new Role(2, "Clients", ""));
        roleDao.add(new Role(3, "Billing", ""));

        // заполнить таблицу соответствия ролей для пользователей
        userroleDao.add(new UserRole(1, 1));
        userroleDao.add(new UserRole(2, 3));
        userroleDao.add(new UserRole(3, 2));


        // переход на ручное управление коммитами
        Connection connect = ConnectionManagerJdbcImpl.getInstance().getConnection();
        connect.setAutoCommit(false);

        // попробуем добавить неверные данные, для этого объявим нового пользователя
        User user = new User(4, "Nyusha", "2019-04-01", 126, "Smeshariki", "", "");
        UserRole userRole = new UserRole(4, 4); // роль 4 не существует в справочнике ролей

        // точка для отката транзакции
        Savepoint savepoint = connect.setSavepoint("A");

        if (userDao.add(user) & userroleDao.add(userRole))
            connect.commit(); // если записи добавлены, делаем коммит
        else
            connect.rollback(savepoint); // иначе откат

        connect.close();
    }
}



