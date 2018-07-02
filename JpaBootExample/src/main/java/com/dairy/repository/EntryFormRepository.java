package com.dairy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dairy.model.EntryForm;

@Repository
public interface EntryFormRepository extends PagingAndSortingRepository<EntryForm,Long> {
	
	public List<EntryForm> findByUserUserId(Long userId);
	public List<EntryForm> findByUserUserIdAndEntryDateTimeAfter(Long userId , LocalDateTime fromDate);
    public List<EntryForm> findByUserUserIdAndEntryDateTimeBetween(Long userId , LocalDateTime fromDate ,LocalDateTime toDate);
    public Page<EntryForm> findByTypeOrderByEntryDateTime(String type,Pageable pageable);
    
    Page<EntryForm> findAll(Pageable pageable);
	
}
