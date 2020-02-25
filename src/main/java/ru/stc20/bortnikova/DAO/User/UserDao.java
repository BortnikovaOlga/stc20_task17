package ru.stc20.bortnikova.DAO.User;


import ru.stc20.bortnikova.Entety.User;

/**
 * интерфейс для объекта User
 * @author Bortnikova Olga
 */
public interface UserDao {
    boolean createDB();

    boolean add(User user) ;
    boolean add(User[] userArr);
}
