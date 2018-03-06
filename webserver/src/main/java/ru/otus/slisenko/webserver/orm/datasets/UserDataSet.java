package ru.otus.slisenko.webserver.orm.datasets;

import java.util.Objects;

public class UserDataSet extends DataSet {
    private String name;
    private int age;

    public UserDataSet() {
    }

    public UserDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataSet dataSet = (UserDataSet) o;
        return age == dataSet.age &&
                Objects.equals(name, dataSet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
