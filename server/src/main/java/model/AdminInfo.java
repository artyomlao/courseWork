package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AdminInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminInfo adminInfo = (AdminInfo) o;
        return id == adminInfo.id && Objects.equals(login, adminInfo.login) && Objects.equals(password, adminInfo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }
}
