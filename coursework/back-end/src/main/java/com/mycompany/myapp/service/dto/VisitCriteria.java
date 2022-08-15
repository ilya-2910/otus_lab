package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mycompany.myapp.domain.enumeration.VisitStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Visit} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.VisitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /visits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VisitCriteria implements Serializable, Criteria {
    /**
     * Class for filtering VisitStatus
     */
    public static class VisitStatusFilter extends Filter<VisitStatus> {

        public VisitStatusFilter() {
        }

        public VisitStatusFilter(VisitStatusFilter filter) {
            super(filter);
        }

        @Override
        public VisitStatusFilter copy() {
            return new VisitStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private VisitStatusFilter status;

    private LongFilter petId;

    private LongFilter vetId;

    public VisitCriteria() {
    }

    public VisitCriteria(VisitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.petId = other.petId == null ? null : other.petId.copy();
        this.vetId = other.vetId == null ? null : other.vetId.copy();
    }

    @Override
    public VisitCriteria copy() {
        return new VisitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public VisitStatusFilter getStatus() {
        return status;
    }

    public void setStatus(VisitStatusFilter status) {
        this.status = status;
    }

    public LongFilter getPetId() {
        return petId;
    }

    public void setPetId(LongFilter petId) {
        this.petId = petId;
    }

    public LongFilter getVetId() {
        return vetId;
    }

    public void setVetId(LongFilter vetId) {
        this.vetId = vetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VisitCriteria that = (VisitCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(petId, that.petId) &&
            Objects.equals(vetId, that.vetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startDate,
        endDate,
        status,
        petId,
        vetId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (petId != null ? "petId=" + petId + ", " : "") +
                (vetId != null ? "vetId=" + vetId + ", " : "") +
            "}";
    }

}
