package ru.stc20.bortnikova.Entety;
/**
 * объект User соответствует записи в таблице user
 *
 * @author Bortnikova Olga
 */
public class User {
    private int id;
    private String name;
    private String bithday;
    private int login_id;
    private String city;
    private String email;
    private String description;

    public User(int id, String name, String bithday, int login_id, String city, String email, String description) {
        this.id = id;
        this.name = name;
        this.bithday = bithday;
        this.login_id = login_id;
        this.city = city;
        this.email = email;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBithday() {
        return bithday;
    }

    public int getLogin_id() {
        return login_id;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

}
