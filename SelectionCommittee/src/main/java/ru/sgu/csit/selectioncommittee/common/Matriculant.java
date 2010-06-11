package ru.sgu.csit.selectioncommittee.common;

import org.hibernate.annotations.CollectionOfElements;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;

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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

    private EntranceCategory entranceCategory;

    @CollectionOfElements(/*targetElement = Speciality.class,*/ fetch = FetchType.EAGER)
    private Map<Integer, String> speciality = new TreeMap<Integer, String>();

    @CollectionOfElements(fetch = FetchType.EAGER)
    //@MapKey(name = "id")
    private Map<String, Integer> balls = new HashMap<String, Integer>();

    @Embedded
    private Documents documents;

    private String schoolName;
    private String info;

    public Boolean completeAllDocuments() {
        return documents != null && documents.completeAllDocuments();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @CollectionOfElements
    public Map<Integer, String> getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Map<Integer, String> speciality) {
        this.speciality = speciality;
    }

    @CollectionOfElements
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

    @Embeddable
    public static class Documents {
        private Boolean originalAttestat;
        private Boolean attestatInsert;
        private Boolean originalEge;
        private Integer countPhotos = 0;
        private Integer countPassportCopy = 0;
        private Boolean originalMedicalCertificate;
        private Boolean copyMedicalPolicy;
        private Boolean tookDocuments;

        public Boolean isOriginalAttestat() {
            return originalAttestat;
        }

        public void setOriginalAttestat(Boolean originalAttestat) {
            this.originalAttestat = originalAttestat;
        }

        public Boolean isAttestatInsert() {
            return attestatInsert;
        }

        public void setAttestatInsert(Boolean attestatInsert) {
            this.attestatInsert = attestatInsert;
        }

        public Boolean isOriginalEge() {
            return originalEge;
        }

        public void setOriginalEge(Boolean originalEge) {
            this.originalEge = originalEge;
        }

        public Integer getCountPhotos() {
            return countPhotos;
        }

        public void setCountPhotos(Integer countPhotos) {
            this.countPhotos = countPhotos;
        }

        public Integer getCountPassportCopy() {
            return countPassportCopy;
        }

        public void setCountPassportCopy(Integer countPassportCopy) {
            this.countPassportCopy = countPassportCopy;
        }

        public Boolean isOriginalMedicalCertificate() {
            return originalMedicalCertificate;
        }

        public void setOriginalMedicalCertificate(Boolean originalMedicalCertificate) {
            this.originalMedicalCertificate = originalMedicalCertificate;
        }

        public Boolean isCopyMedicalPolicy() {
            return copyMedicalPolicy;
        }

        public void setCopyMedicalPolicy(Boolean copyMedicalPolicy) {
            this.copyMedicalPolicy = copyMedicalPolicy;
        }

        public Boolean isTookDocuments() {
            if (tookDocuments == null) {
                return false;
            }
            return tookDocuments;
        }

        public void setTookDocuments(Boolean tookDocuments) {
            this.tookDocuments = tookDocuments;
        }

        public Boolean completeAllDocuments() {
            return (tookDocuments != null && tookDocuments) ||
                    ((originalAttestat != null && originalAttestat)
                    && (attestatInsert != null && attestatInsert)
                    && (originalEge != null && originalEge)
                    && (countPhotos != 0)
                    && (countPassportCopy != 0)
                    && (originalMedicalCertificate != null && originalMedicalCertificate)
                    && (copyMedicalPolicy != null && copyMedicalPolicy));
        }

        public String printToString() {
            String str = "\t";

            if (originalAttestat != null) {
                if (originalAttestat) {
                    str += "Оригинал аттестата\n";
                } else {
                    str += "Копия аттестата\n";
                }
            }
            if (attestatInsert != null && attestatInsert) {
                str += "\tВкладыш аттестата\n";
            }
            if (originalEge != null && originalEge) {
                str += "\tОригиналы ЕГЭ\n";
            }
            if (countPhotos != 0) {
                str += "\tФотографии: " + countPhotos + " шт.\n";
            }
            if (countPassportCopy != 0) {
                str += "\tКопии паспорта: " + countPassportCopy + " шт.\n";
            }
            if (originalMedicalCertificate != null && originalMedicalCertificate) {
                str += "\tОригинал медицинской справки\n";
            }
            if (copyMedicalPolicy != null && copyMedicalPolicy) {
                str += "\tКопия медицинского полиса\n";
            }

            return str;
        }

        @Override
        public String toString() {
            return "Documents{" +
                    "originalAttestat=" + originalAttestat +
                    ", attestatInsert=" + attestatInsert +
                    ", originalEge=" + originalEge +
                    ", countPhotos=" + countPhotos +
                    ", countPassportCopy=" + countPassportCopy +
                    ", originalMedicalCertificate=" + originalMedicalCertificate +
                    ", copyMedicalPolicy=" + copyMedicalPolicy +
                    ", tookDocuments=" + tookDocuments +
                    '}';
        }
    }

    public String printToString() {
        String str = "Рег. № " + receiptNumber + " от " + filingDate + "\n" +
                name + "\n";

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
        if (schoolName != null && !schoolName.isEmpty()) {
            str += "Предыдущее учебное заведение:\n" + schoolName + "\n";
        }
        if (documents != null && !documents.isTookDocuments()) {
            str += "Предоставленные документы:\n" + documents.printToString();
        } else {
            str += "Документы не предоставлены или были забраны\n";
        }
        if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
            str += "Тел. " + phoneNumbers + "\n";
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
            str += "Примечание:\n" + info;
        }
        return str;
    }

    @Override
    public String toString() {
        return "Matriculant{" +
                "id=" + id +
                ", receiptNumber='" + receiptNumber + '\'' +
                ", filingDate=" + filingDate +
                ", father=" + father +
                ", mother=" + mother +
                ", entranceCategory=" + entranceCategory +
                ", speciality=" + speciality +
                ", balls=" + balls +
                ", documents=" + documents +
                ", schoolName='" + schoolName + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
