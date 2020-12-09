package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Visit;

public interface TimeVisitService {

    /**
     *
     *
     * @return is vet allow for time
     */
    boolean isVisitTimeOverlap(Visit visit);

}
