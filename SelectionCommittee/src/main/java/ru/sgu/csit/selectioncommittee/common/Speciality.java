package ru.sgu.csit.selectioncommittee.common;

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: Apr 19, 2010
 * Time: 3:45:56 PM
 * @author xx & hd
 */
@Entity
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ReceiptExamine> exams;

    public Speciality() {
    }

    public Speciality(String name) {
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

    public List<ReceiptExamine> getExams() {
        return exams;
    }

    public void setExams(List<ReceiptExamine> exams) {
        this.exams = exams;
    }

    public void addExam(ReceiptExamine exam) {
        if (exams == null) {
            exams = new ArrayList<ReceiptExamine>();
        }
        exams.add(exam);
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
