package web.model;


import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;


    @NotNull(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов ")
    @Column
    private String name;

    @NotNull(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов ")
    @Column
    private String surname;

    @Min(value = 5, message = "Возраст не может быть менее 5 лет")
    @Column
    private int age;

    public User() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;

        // Если объект является прокси-объектом
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer ().getPersistentClass ()
                : o.getClass ();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer ().getPersistentClass ()
                : this.getClass ();

        // Если классы разные, считаем объекты разными
        if (thisEffectiveClass != oEffectiveClass) return false;

        User user = (User) o;

        // Проверяем ID объекта
        return id == user.id;
    }

    @Override
    public final int hashCode() {
        // Используем getClass().hashCode() для обычных объектов, для прокси - берем класс через LazyInitializer
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer ().getPersistentClass ().hashCode ()
                : getClass ().hashCode ();
    }
}

