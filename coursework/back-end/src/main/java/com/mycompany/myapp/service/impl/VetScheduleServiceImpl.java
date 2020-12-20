package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VetScheduleService;
import com.mycompany.myapp.domain.VetSchedule;
import com.mycompany.myapp.repository.VetScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VetSchedule}.
 */
@Service
@Transactional
public class VetScheduleServiceImpl implements VetScheduleService {

    private final Logger log = LoggerFactory.getLogger(VetScheduleServiceImpl.class);

    private final VetScheduleRepository vetScheduleRepository;

    public VetScheduleServiceImpl(VetScheduleRepository vetScheduleRepository) {
        this.vetScheduleRepository = vetScheduleRepository;
    }

    /**
     * Save a vetSchedule.
     *
     * @param vetSchedule the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VetSchedule save(VetSchedule vetSchedule) {
        log.debug("Request to save VetSchedule : {}", vetSchedule);
        return vetScheduleRepository.save(vetSchedule);
    }

    /**
     * Get all the vetSchedules.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VetSchedule> findAll() {
        log.debug("Request to get all VetSchedules");
        return vetScheduleRepository.findAll();
    }


    /**
     * Get one vetSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VetSchedule> findOne(Long id) {
        log.debug("Request to get VetSchedule : {}", id);
        return vetScheduleRepository.findById(id);
    }

    /**
     * Delete the vetSchedule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VetSchedule : {}", id);

        vetScheduleRepository.deleteById(id);
    }
}
