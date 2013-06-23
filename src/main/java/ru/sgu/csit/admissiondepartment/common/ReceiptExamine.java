package ru.sgu.csit.admissiondepartment.common;

import com.google.common.base.Objects;

import javax.persistence.Entity;

/**
 * Date: Apr 19, 2010
 * Time: 3:48:23 PM
 * @author xx & hd
 */
@Entity
public class ReceiptExamine extends PersistentItem {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                Objects.equal(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(super.toString())
                .add("name", name)
                .toString();
    }
}
