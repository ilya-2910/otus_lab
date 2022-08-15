package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Pet} entity.
 */
public class PetDTO implements Serializable {

    private Long id;

    private String name;

    private PetTypeDTO type;

    private OwnerDTO owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetTypeDTO getType() {
        return type;
    }

    public void setType(PetTypeDTO type) {
        this.type = type;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetDTO)) {
            return false;
        }

        return id != null && id.equals(((PetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
//            ", typeId=" + getTypeId() +
//            ", ownerId=" + getOwnerId() +
            "}";
    }
}
