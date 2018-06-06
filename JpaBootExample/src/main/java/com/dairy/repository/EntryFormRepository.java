package com.dairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dairy.model.EntryForm;

@Repository
public interface EntryFormRepository extends JpaRepository<EntryForm, Long> {
    
}
