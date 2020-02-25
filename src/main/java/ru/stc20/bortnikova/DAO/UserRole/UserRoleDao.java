package ru.stc20.bortnikova.DAO.UserRole;

import ru.stc20.bortnikova.Entety.UserRole;

/**
 * интерфес для объекта UserRole
 *
 * @author Bortnikova Olga
 */
public interface UserRoleDao {

    boolean createDB();

    boolean add(UserRole userRole) ;

}
