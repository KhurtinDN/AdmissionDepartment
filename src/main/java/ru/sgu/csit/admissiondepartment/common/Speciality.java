package ru.sgu.csit.admissiondepartment.common;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.List;

/**
 * Date: Apr 19, 2010
 * Time: 3:45:56 PM
 * @author xx & hd
 */
@Entity
public class Speciality extends PersistentItem {

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ReceiptExamine> exams;

    public Speciality() {
    }

    public Speciality(String name) {
        this.name = name;
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
            exams = Lists.newArrayList();
        }
        exams.add(exam);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Speciality that = (Speciality) obj;

        return super.equals(that) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.exams, that.exams);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name, exams);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(super.toString())
                .add("name", name)
                .add("exams", exams)
                .toString();
    }
}
