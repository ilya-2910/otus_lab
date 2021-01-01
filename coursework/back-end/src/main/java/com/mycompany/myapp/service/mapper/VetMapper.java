package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.VetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vet} and its DTO {@link VetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VetMapper extends EntityMapper<VetDTO, Vet> {


    @Mapping(target = "visits", ignore = true)
    @Mapping(target = "removeVisits", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "removeSchedules", ignore = true)
    Vet toEntity(VetDTO vetDTO);

    default Vet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vet vet = new Vet();
        vet.setId(id);
        return vet;
    }
}
