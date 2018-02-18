package ru.otus.slisenko.orm.datasets;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
