package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pet} and its DTO {@link PetDTO}.
 */
@Mapper(componentModel = "spring", uses = {PetTypeMapper.class, OwnerMapper.class})
public interface PetMapper extends EntityMapper<PetDTO, Pet> {

//    @Mapping(source = "type.id", target = "typeId")
//    @Mapping(source = "owner.id", target = "ownerId")
    PetDTO toDto(Pet pet);

    @Mapping(target = "visits", ignore = true)
    @Mapping(target = "removeVisits", ignore = true)
//    @Mapping(source = "typeId", target = "type")
//    @Mapping(source = "ownerId", target = "owner")
    Pet toEntity(PetDTO petDTO);

    default Pet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pet pet = new Pet();
        pet.setId(id);
        return pet;
    }
}
