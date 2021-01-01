package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class VetScheduleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VetScheduleDTO.class);
        VetScheduleDTO vetScheduleDTO1 = new VetScheduleDTO();
        vetScheduleDTO1.setId(1L);
        VetScheduleDTO vetScheduleDTO2 = new VetScheduleDTO();
        assertThat(vetScheduleDTO1).isNotEqualTo(vetScheduleDTO2);
        vetScheduleDTO2.setId(vetScheduleDTO1.getId());
        assertThat(vetScheduleDTO1).isEqualTo(vetScheduleDTO2);
        vetScheduleDTO2.setId(2L);
        assertThat(vetScheduleDTO1).isNotEqualTo(vetScheduleDTO2);
        vetScheduleDTO1.setId(null);
        assertThat(vetScheduleDTO1).isNotEqualTo(vetScheduleDTO2);
    }
}
