package com.system.churchflow.services;

import com.system.churchflow.repository.CellsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellsService {

    @Autowired
    private CellsRepository repository;



}
