package ru.otus.slisenko.orm.datasets;

@TableName(name = "users")
public class UserDataSet extends DataSet {
    private String name;
    private int age;

    public UserDataSet() {
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
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
