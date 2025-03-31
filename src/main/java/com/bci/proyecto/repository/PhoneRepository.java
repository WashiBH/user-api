package com.bci.proyecto.repository;

import com.bci.proyecto.model.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneModel, Long> {
}
