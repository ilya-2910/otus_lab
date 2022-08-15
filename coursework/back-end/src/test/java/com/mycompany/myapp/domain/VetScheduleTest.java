package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class VetScheduleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VetSchedule.class);
        VetSchedule vetSchedule1 = new VetSchedule();
        vetSchedule1.setId(1L);
        VetSchedule vetSchedule2 = new VetSchedule();
        vetSchedule2.setId(vetSchedule1.getId());
        assertThat(vetSchedule1).isEqualTo(vetSchedule2);
        vetSchedule2.setId(2L);
        assertThat(vetSchedule1).isNotEqualTo(vetSchedule2);
        vetSchedule1.setId(null);
        assertThat(vetSchedule1).isNotEqualTo(vetSchedule2);
    }
}
