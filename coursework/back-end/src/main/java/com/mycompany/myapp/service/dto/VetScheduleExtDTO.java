package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.VetSchedule} entity.
 */
public class VetScheduleExtDTO implements Serializable {

    private Long id;

    private Instant startDate;

    private Instant endDate;

    private VetDTO vet;

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

    public VetDTO getVet() {
        return vet;
    }

    public void setVet(VetDTO vet) {
        this.vet = vet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VetScheduleExtDTO)) {
            return false;
        }

        return id != null && id.equals(((VetScheduleExtDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
//    @Override
//    public String toString() {
//        return "VetScheduleDTO{" +
//            "id=" + getId() +
//            ", startDate='" + getStartDate() + "'" +
//            ", endDate='" + getEndDate() + "'" +
//            ", vetId=" + getVetId() +
//            "}";
//    }
}
