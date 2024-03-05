package com.system.churchflow.repository;


import com.system.churchflow.model.Institution;
import com.system.churchflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution,Integer>{

    @Query("SELECT i FROM Institution i WHERE i.cnpj = :cnpj")
    Institution findByCnpj(String cnpj);

    @Query("SELECT i FROM Institution i WHERE i.cnpj = :cnpj")
    Optional<Institution> findByCnpjOptional(String cnpj);
}
