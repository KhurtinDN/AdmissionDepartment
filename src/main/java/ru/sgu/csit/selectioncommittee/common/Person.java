package ru.sgu.csit.selectioncommittee.common;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * Date: Apr 19, 2010
 * Time: 1:51:34 PM
 * @author xx & hd
 */
@MappedSuperclass
@Embeddable
public class Person {
    protected String name;
    protected String phoneNumbers;

    public Person() {
    }

    public Person(String name, String phoneNumbers) {
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phoneNumbers='" + phoneNumbers + '\'' +
                '}';
    }
}
