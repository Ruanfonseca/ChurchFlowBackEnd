package com.system.churchflow.services;


import com.system.churchflow.exceptions.ObjectNotFoundException;
import com.system.churchflow.model.Institution;
import com.system.churchflow.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstituitionService {

    @Autowired
    private InstitutionRepository repository;

    public Institution findById(Integer id){
        Institution institution = repository.findById(id).orElseThrow(
                ()->new ObjectNotFoundException("Instuição não encontrada ! Id: "+id)
        );
        return institution;
    }

    public Institution findInstuitionWithCnpj(String cnpj){
        Institution institution = repository.findByCnpj(cnpj);
        return institution;

    }

    public void delete(Institution institution){
        repository.delete(institution);
    }

    public void deleteWithCnpj(String cnpj){
        Institution institution = findInstuitionWithCnpj(cnpj);
        repository.delete(institution);
    }

    public Institution create(Institution institution) {

        boolean institutionExists = repository.findByCnpjOptional(institution.getCnpj()).isPresent();

        // Se a instituição já existe
        if (institutionExists) {
            // Pode lançar uma exceção, retornar null
            throw new RuntimeException("Instituição com CNPJ " + institution.getCnpj() + " já existe.");

        }

        return repository.save(institution);
    }

}
