package com.dairy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dairy.model.Ledger;
import com.dairy.model.User;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long>{
	
	
	
	public List<Ledger> findByUserAndDayTypeAndPaymentTypeAndPaymentByAndTransactionDateBetween(User user ,String dayType ,String paymentType , String paymentBy , LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByUserAndPaymentTypeAndPaymentByAndTransactionDateBetween(User user ,String paymentType , String paymentBy , LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByUserAndPaymentTypeAndTransactionDateBetween(User user ,String paymentType ,LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByUserAndPaymentByAndTransactionDateBetween(User user , String paymentBy , LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByUserAndDayTypeAndTransactionDateBetween(User user , String dayType , LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByUserAndTransactionDateBetween(User user,LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByUserAndPaymentTypeAndPaymentByAndDayType(User user , String paymentType,String paymentBy,String dayType);
	public List<Ledger> findByUserAndPaymentByAndDayType(User user ,String paymentBy,String dayType);
	public List<Ledger> findByUserAndPaymentTypeAndDayType(User user , String paymentType,String dayType);
	public List<Ledger> findByUserAndPaymentTypeAndPaymentBy(User user , String paymentType,String paymentBy);
	public List<Ledger> findByUserAndPaymentType(User user , String paymentType);
	public List<Ledger> findByUserAndPaymentBy(User user , String paymentBy);
	public List<Ledger> findByUserAndDayType(User user ,String dayType);	
	public List<Ledger> findByUser(User user);
	  
	
	public List<Ledger> findByUserUserId(Long userId);
	public List<Ledger> findByPaymentTypeAndUserUserId(String paymentType ,Long userId);
	public List<Ledger> findByDayTypeAndUserUserId(String dayType ,Long userId );
	public List<Ledger> findByPaymentByAndUserUserId(String paymentBy , Long userId );
	public List<Ledger> findByPaymentTypeAndDayTypeAndUserUserId(String paymentType ,String dayType ,Long userId );
	public List<Ledger> findByPaymentTypeAndDayTypeAndPaymentByAndUserUserId(String paymentType ,String dayType ,String paymentBy ,Long userId );
	public List<Ledger> findByPaymentTypeAndDayTypeAndPaymentByAndTransactionDateBetweenAndUserUserId(String paymentType , String dayType , String paymentBy ,LocalDateTime transactionStartDate ,LocalDateTime transactionEndDate,Long userId );
	public List<Ledger> findByPaymentTypeAndDayTypeAndTransactionDateBetweenAndUserUserId(String paymentType ,String dayType ,LocalDateTime transactionStartDate ,LocalDateTime transactionEndDate,Long userId );
	public List<Ledger> findByPaymentTypeAndTransactionDateBetweenAndUserUserId(String paymentType , LocalDateTime transactionStartDate ,LocalDateTime transactionEndDate,Long userId );
	public List<Ledger> findByDayTypeAndTransactionDateBetweenAndUserUserId(String dayType , LocalDateTime transactionStartDate ,LocalDateTime transactionEndDate,Long userId );
	public List<Ledger> findByTransactionDateBetweenAndUserUserId(LocalDateTime transactionStartDate ,LocalDateTime transactionEndDate,Long userId );

	
	
	public List<Ledger> findByDayTypeAndPaymentTypeAndPaymentByAndTransactionDateBetween(String dayType ,String paymentType , String paymentBy , LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByPaymentTypeAndPaymentByAndTransactionDateBetween(String paymentType , String paymentBy , LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByPaymentTypeAndTransactionDateBetween(String paymentType, LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByPaymentByAndTransactionDateBetween(String paymentBy , LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByDayTypeAndTransactionDateBetween(String dayType , LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByTransactionDateBetween(LocalDateTime startdateTime , LocalDateTime endDateTime );
	public List<Ledger> findByPaymentTypeAndPaymentByAndDayType(String paymentType,String paymentBy,String dayType);
	public List<Ledger> findByPaymentByAndDayType(String paymentBy,String dayType);
	public List<Ledger> findByPaymentTypeAndDayType(String paymentType,String dayType);
	public List<Ledger> findByPaymentTypeAndPaymentBy(String paymentType,String paymentBy);
	public List<Ledger> findByPaymentType(String paymentType);
	public List<Ledger> findByPaymentBy(String paymentBy);    
	public List<Ledger> findByDayType(String dayType);
	
    public Ledger findFirstByUserUserIdAndPaymentTypeOrderByTransactionDateDesc(Long userId,String paymentType);
	


}
