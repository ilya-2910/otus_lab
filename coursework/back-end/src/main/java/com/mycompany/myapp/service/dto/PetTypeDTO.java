package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PetType} entity.
 */
public class PetTypeDTO implements Serializable {
    
    private Long id;

    private String type;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((PetTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetTypeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
