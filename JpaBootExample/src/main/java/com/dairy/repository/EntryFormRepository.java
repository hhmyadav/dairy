package com.dairy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dairy.model.EntryForm;

@Repository
public interface EntryFormRepository extends JpaRepository<EntryForm, Long> {
	
	public List<EntryForm> findByUserUserId(Long userId);
	public List<EntryForm> findByUserUserIdAndEntryDateTimeAfter(Long userId , LocalDateTime fromDate);
    public List<EntryForm> findByUserUserIdAndEntryDateTimeBetween(Long userId , LocalDateTime fromDate ,LocalDateTime toDate);
    public List<EntryForm> findByType(String type);
	
}
