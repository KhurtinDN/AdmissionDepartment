package ru.sgu.csit.admissiondepartment.common;

import com.google.common.base.Objects;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * Date: Apr 19, 2010
 * Time: 1:51:34 PM
 * @author xx & hd
 */
@MappedSuperclass
@Embeddable
public class Person extends PersistentItem {

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Person that = (Person) obj;

        return super.equals(that) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.phoneNumbers, that.phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name, phoneNumbers);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(super.toString())
                .add("name", name)
                .add("phoneNumbers", phoneNumbers)
                .toString();
    }
}
