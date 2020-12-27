package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VetScheduleMapperTest {

    private VetScheduleMapper vetScheduleMapper;

    @BeforeEach
    public void setUp() {
        vetScheduleMapper = new VetScheduleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(vetScheduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vetScheduleMapper.fromId(null)).isNull();
    }
}
