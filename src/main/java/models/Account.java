package models;

import java.util.Objects;

/**
 * @author ArvikV
 * @version 1.0
 * @since 02.01.2022
 * определим модель данных, конструктор, геттеры/сеттеры и переопределим иклз и хэшкод
 */
public class Account {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Account(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Account(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Account(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Account(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id
                && Objects.equals(name, account.name)
                && Objects.equals(email, account.email)
                && Objects.equals(phone, account.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone);
    }
}
