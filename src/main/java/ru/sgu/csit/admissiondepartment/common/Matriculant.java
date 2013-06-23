package ru.sgu.csit.admissiondepartment.common;

import com.google.common.base.Objects;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;

import javax.persistence.*;
import java.util.*;

/**
 * Date: Apr 19, 2010
 * Time: 1:41:16 PM
 *
 * @author xx & hd
 */
@Entity
public class Matriculant extends Person {

    @Column(nullable = false)
    private String receiptNumber;

    private Date filingDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "fatherName")),
            @AttributeOverride(name = "phoneNumbers", column = @Column(name = "fatherPhoneNumbers"))
    })
    private Person father;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "motherName")),
            @AttributeOverride(name = "phoneNumbers", column = @Column(name = "motherPhoneNumbers"))
    })
    private Person mother;

    @Column(nullable = false)
    private EntranceCategory entranceCategory;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Integer, String> speciality = new TreeMap<Integer, String>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Integer> balls = new HashMap<String, Integer>();

    @Embedded
    private Documents documents;

    private String entranceSpecialityName;
    private String schoolName;
    private String info;

    public Boolean completeAllDocuments() {
        return documents != null && documents.completeAllDocuments();
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public boolean isNoExamine() {
        return !(entranceCategory == null || entranceCategory == EntranceCategory.EXAMINE);
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public EntranceCategory getEntranceCategory() {
        return entranceCategory;
    }

    public void setEntranceCategory(EntranceCategory entranceCategory) {
        this.entranceCategory = entranceCategory;
    }

    public Map<Integer, String> getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Map<Integer, String> speciality) {
        this.speciality = speciality;
    }

    public Map<String, Integer> getBalls() {
        return balls;
    }

    public void setBalls(Map<String, Integer> balls) {
        this.balls = balls;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public String getEntranceSpecialityName() {
        return entranceSpecialityName;
    }

    public void setEntranceSpecialityName(String entranceSpecialityName) {
        this.entranceSpecialityName = entranceSpecialityName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean onSpeciality(int specialityIndex) {
        List<Speciality> specialities = DataAccessFactory.getSpecialities();

        if (specialities != null) {
            Speciality speciality = specialities.get(specialityIndex);
            List<ReceiptExamine> exams = speciality.getExams();

            if (entranceCategory != EntranceCategory.EXAMINE) {
                return speciality.getName().equals(this.speciality.get(1));
            }
            if (exams != null) {
                for (ReceiptExamine examine : exams) {
                    Integer currentBall = balls.get(examine.getName());

                    if (currentBall == null || currentBall == 0) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Integer calculateTotalBallsForSpeciality(String specialityName) {
        Integer result = null;

        if (specialityName != null && !specialityName.isEmpty()) {
            List<Speciality> specialities = DataAccessFactory.getSpecialities();

            if (specialities != null) {
                for (Speciality speciality : specialities) {
                    if (speciality.getName().equals(specialityName)) {
                        List<ReceiptExamine> exams = speciality.getExams();

                        if (exams != null) {
                            result = 0;

                            for (ReceiptExamine examine : exams) {
                                Integer currentBall = balls.get(examine.getName());

                                if (currentBall != null) {
                                    result += currentBall;
                                }
                            }
                            return result;
                        }
                    }
                }
            }
        }
        return result;
    }

    public String printToString() {
        String str = "Рег. № " + receiptNumber + " от " + filingDate + "\n" +
                name + "\n\n";

        if (entranceCategory != null) {
            str += "Поступает ";
            switch (entranceCategory) {
                case EXAMINE:
                    str += "по результатам экзаменов\n";
                    if (balls != null && !balls.isEmpty()) {
                        for (Map.Entry<String, Integer> entry : balls.entrySet()) {
                            str += "\t" + entry.getKey() + " - " + entry.getValue() + "\n";
                        }
                    }
                    break;
                case NO_EXAMINE:
                    str += "без экзаменов\n";
                    break;
                case OUT_EXAMINE_OTHER:
                    str += "вне конкурса\n";
                    break;
                case OUT_EXAMINE_ORPHAN:
                    str += "вне конкурса - сирота\n";
                    break;
                case OUT_EXAMINE_INVALID:
                    str += "вне конкурса - инвалид\n";
                    break;
            }
            if (speciality != null && !speciality.isEmpty()) {
                str += "На специальности:";
                for (int i = 0; i < speciality.size(); ++i) {
                    str += "\n\t" + String.valueOf(i + 1) + ". " + speciality.get(i + 1);
                }
                str += "\n";
            }
        }
        if (entranceSpecialityName != null && !"".equals(entranceSpecialityName)) {
            str += "Результат зачисления: " + entranceSpecialityName + "\n";
        }
        str += "\n";
        if (schoolName != null && !schoolName.isEmpty()) {
            str += "Предыдущее место учёбы:\n\t" + schoolName + "\n";
        }
        if (documents != null && !documents.isTookDocuments()) {
            str += "Предоставленные документы:\n" + documents.printToString();
        } else {
            str += "Документы не предоставлены или были забраны\n";
        }
        if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
            str += "Тел. " + phoneNumbers + "\n\n";
        }
        if (father != null &&
                ((father.getName() != null && !father.getName().isEmpty())
                        || (father.getPhoneNumbers() != null && !father.getPhoneNumbers().isEmpty()))) {
            str += "Отец: " + father.getName() + ", тел. " + father.getPhoneNumbers() + "\n";
        }
        if (mother != null &&
                ((mother.getName() != null && !mother.getName().isEmpty())
                        || (mother.getPhoneNumbers() != null && !mother.getPhoneNumbers().isEmpty()))) {
            str += "Мать: " + mother.getName() + ", тел. " + mother.getPhoneNumbers() + "\n";
        }
        if (info != null && !info.isEmpty()) {
            str += "\nПримечание:\n" + info;
        }
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Matriculant that = (Matriculant) obj;

        return super.equals(that) &&
                Objects.equal(this.receiptNumber, that.receiptNumber) &&
                Objects.equal(this.filingDate, that.filingDate) &&
                Objects.equal(this.mother, that.mother) &&
                Objects.equal(this.father, that.father) &&
                Objects.equal(this.entranceCategory, that.entranceCategory) &&
                Objects.equal(this.speciality, that.speciality) &&
                Objects.equal(this.balls, that.balls) &&
                Objects.equal(this.documents, that.documents) &&
                Objects.equal(this.schoolName, that.schoolName) &&
                Objects.equal(this.info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                super.hashCode(),
                receiptNumber,
                filingDate,
                mother,
                father,
                entranceCategory,
                speciality,
                balls,
                documents,
                schoolName,
                info
        );
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(super.toString())
                .add("receiptNumber", receiptNumber)
                .add("filingDate", filingDate)
                .add("mother", mother)
                .add("father", father)
                .add("entranceCategory", entranceCategory)
                .add("speciality", speciality)
                .add("balls", balls)
                .add("documents", documents)
                .add("schoolName", schoolName)
                .add("info", info)
                .toString();
    }
}
