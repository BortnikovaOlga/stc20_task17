package ru.stc20.bortnikova.Entety;

/**
 * объект Role соответствует записи в таблице role
 * @author Bortnikova Olga
 */
public class Role {
    private int id;
    private String name;
    private String description;

    public Role(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
