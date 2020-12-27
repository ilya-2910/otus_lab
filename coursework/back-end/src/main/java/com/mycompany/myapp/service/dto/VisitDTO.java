package com.mycompany.myapp.service.dto;

import java.time.Instant;
import java.io.Serializable;
import javax.persistence.Lob;
import com.mycompany.myapp.domain.enumeration.VisitStatus;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Visit} entity.
 */
public class VisitDTO implements Serializable {
    
    private Long id;

    private Instant startDate;

    private Instant endDate;

    @Lob
    private String description;

    private VisitStatus status;


    private Long petId;

    private Long vetId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getVetId() {
        return vetId;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisitDTO)) {
            return false;
        }

        return id != null && id.equals(((VisitDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisitDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", petId=" + getPetId() +
            ", vetId=" + getVetId() +
            "}";
    }
}
