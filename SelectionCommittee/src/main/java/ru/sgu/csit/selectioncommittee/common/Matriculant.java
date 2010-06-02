package ru.sgu.csit.selectioncommittee.common;

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

    @Embeddable
    public static class Documents {
        Boolean originalAttestat;
        Boolean attestatInsert;
        Boolean originalEge;
        Boolean allPhotos;
        Boolean allPassportCopy;
        Boolean originalMedicalCertificate;
        Boolean copyMedicalPolicy;

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

        public Boolean isAllPhotos() {
            return allPhotos;
        }

        public void setAllPhotos(Boolean allPhotos) {
            this.allPhotos = allPhotos;
        }

        public Boolean isAllPassportCopy() {
            return allPassportCopy;
        }

        public void setAllPassportCopy(Boolean allPassportCopy) {
            this.allPassportCopy = allPassportCopy;
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

        public Boolean completeAllDocuments() {
            return originalAttestat
                    && attestatInsert
                    && originalEge
                    && allPhotos
                    && allPassportCopy
                    && originalMedicalCertificate
                    && copyMedicalPolicy;
        }

        @Override
        public String toString() {
            return "Documents{" +
                    "originalAttestat=" + originalAttestat +
                    ", attestatInsert=" + attestatInsert +
                    ", originalEge=" + originalEge +
                    ", allPhotos=" + allPhotos +
                    ", allPassportCopy=" + allPassportCopy +
                    ", originalMedicalCertificate=" + originalMedicalCertificate +
                    ", copyMedicalPolicy=" + copyMedicalPolicy +
                    '}';
        }
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
