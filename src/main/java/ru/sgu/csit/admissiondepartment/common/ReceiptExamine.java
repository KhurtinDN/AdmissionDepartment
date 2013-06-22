package ru.sgu.csit.admissiondepartment.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Date: Apr 19, 2010
 * Time: 3:48:23 PM
 * @author xx & hd
 */
@Entity
public class ReceiptExamine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public ReceiptExamine() {
    }

    public ReceiptExamine(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReceiptExamine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
