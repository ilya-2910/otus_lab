package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vet.
 */
@Entity
@Table(name = "vet")
public class Vet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "vet")
    private Set<Visit> visits = new HashSet<>();

    @OneToMany(mappedBy = "vet")
    private Set<VetSchedule> schedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Vet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Vet phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public Vet visits(Set<Visit> visits) {
        this.visits = visits;
        return this;
    }

    public Vet addVisits(Visit visit) {
        this.visits.add(visit);
        visit.setVet(this);
        return this;
    }

    public Vet removeVisits(Visit visit) {
        this.visits.remove(visit);
        visit.setVet(null);
        return this;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public Set<VetSchedule> getSchedules() {
        return schedules;
    }

    public Vet schedules(Set<VetSchedule> vetSchedules) {
        this.schedules = vetSchedules;
        return this;
    }

    public Vet addSchedules(VetSchedule vetSchedule) {
        this.schedules.add(vetSchedule);
        vetSchedule.setVet(this);
        return this;
    }

    public Vet removeSchedules(VetSchedule vetSchedule) {
        this.schedules.remove(vetSchedule);
        vetSchedule.setVet(null);
        return this;
    }

    public void setSchedules(Set<VetSchedule> vetSchedules) {
        this.schedules = vetSchedules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vet)) {
            return false;
        }
        return id != null && id.equals(((Vet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
