package ru.stc20.bortnikova.DAO.Role;

import ru.stc20.bortnikova.Entety.Role;

/**
 * интерфейс для работы с объектом Role
 * @Bortnikova Olga
 */
public interface RoleDao {

    boolean add(Role role) ;
    boolean createDB();
}
