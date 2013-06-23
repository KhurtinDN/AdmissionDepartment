package ru.sgu.csit.admissiondepartment.common;

import com.google.common.base.Objects;

import javax.persistence.Embeddable;

/**
 * Date: 22.06.13
 * Time: 17:24
 *
 * @author xx & hd
 */
@Embeddable
public class Documents {

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
        StringBuilder stringBuilder = new StringBuilder();

        if (originalAttestat != null) {
            stringBuilder.append('\t').append(originalAttestat ? "Оригинал аттестата" : "Копия аттестата").append('\n');
        }
        if (attestatInsert != null && attestatInsert) {
            stringBuilder.append('\t').append("Вкладыш аттестата").append('\n');
        }
        if (originalEge != null && originalEge) {
            stringBuilder.append('\t').append("Оригиналы ЕГЭ").append('\n');
        }
        if (countPhotos != 0) {
            stringBuilder.append('\t').append("Фотографии: ").append(countPhotos).append(" шт.").append('\n');
        }
        if (countPassportCopy != 0) {
            stringBuilder.append('\t').append("Копии паспорта: ").append(countPassportCopy).append(" шт.").append('\n');
        }
        if (originalMedicalCertificate != null && originalMedicalCertificate) {
            stringBuilder.append('\t').append("Оригинал медицинской справки").append('\n');
        }
        if (copyMedicalPolicy != null && copyMedicalPolicy) {
            stringBuilder.append('\t').append("Копия медицинского полиса").append('\n');
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Documents that = (Documents) obj;

        return super.equals(that) &&
                Objects.equal(this.originalAttestat, that.originalAttestat) &&
                Objects.equal(this.attestatInsert, that.attestatInsert) &&
                Objects.equal(this.originalEge, that.originalEge) &&
                Objects.equal(this.countPhotos, that.countPhotos) &&
                Objects.equal(this.countPassportCopy, that.countPassportCopy) &&
                Objects.equal(this.originalMedicalCertificate, that.originalMedicalCertificate) &&
                Objects.equal(this.copyMedicalPolicy, that.copyMedicalPolicy) &&
                Objects.equal(this.tookDocuments, that.tookDocuments);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                originalAttestat,
                attestatInsert,
                originalEge,
                countPhotos,
                countPassportCopy,
                originalMedicalCertificate,
                copyMedicalPolicy,
                tookDocuments
        );
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("originalAttestat", originalAttestat)
                .add("attestatInsert", attestatInsert)
                .add("originalEge", originalEge)
                .add("countPhotos", countPhotos)
                .add("countPassportCopy", countPassportCopy)
                .add("originalMedicalCertificate", originalMedicalCertificate)
                .add("copyMedicalPolicy", copyMedicalPolicy)
                .add("tookDocuments", tookDocuments)
                .toString();
    }
}
