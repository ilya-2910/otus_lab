package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.service.TimeVisitService;
import com.mycompany.myapp.service.VisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class TimeVisitServiceImpl implements TimeVisitService {

    private final VisitService visitService;

    public TimeVisitServiceImpl(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public boolean isVisitTimeOverlap(Visit visit) {
        if (visit.getVet() == null) return true;

        return visitService.findAll().stream()
            .filter(visit1 -> visit1.getVet() != null)
            .filter(visit1 -> !visit1.getId().equals(visit.getId()))
            .filter(visit1 -> overlap(visit1.getStartDate(), visit1.getEndDate(), visit.getStartDate(), visit.getEndDate()))
            .findFirst().isPresent();
    }

    /**
     * overlap 2 dates
     *
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    boolean overlap(Instant start1, Instant end1, Instant start2, Instant end2){
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

}
