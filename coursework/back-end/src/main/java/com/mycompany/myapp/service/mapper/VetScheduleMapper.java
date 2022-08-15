package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.VetScheduleDTO;

import com.mycompany.myapp.service.dto.VetScheduleExtDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VetSchedule} and its DTO {@link VetScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {VetMapper.class})
public interface VetScheduleMapper extends EntityMapper<VetScheduleDTO, VetSchedule> {

//    @Mapping(source = "vet.id", target = "vetId")
    VetScheduleDTO toDto(VetSchedule vetSchedule);

//    @Mapping(source = "vetId", target = "vet")
    VetSchedule toEntity(VetScheduleDTO vetScheduleDTO);

    default VetSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        VetSchedule vetSchedule = new VetSchedule();
        vetSchedule.setId(id);
        return vetSchedule;
    }
}
