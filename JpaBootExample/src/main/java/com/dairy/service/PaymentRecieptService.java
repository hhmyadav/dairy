package com.dairy.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.dairy.model.EntryForm;
import com.dairy.model.Ledger;

@Service
public class PaymentRecieptService {
     
	DateTimeFormatter ddMMyyyyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	
	
	 public void setPaymentForm(List<EntryForm> entryForms ,Model model)
	 {   
		    
		    double cowMilkQuantity = 0.0 ;
			double cowMilkAmount = 0.0 ;
			double buffaloMilkQuantity = 0.0 ;
			double buffaloMilkAmount = 0.0 ;
			double milkTotalQuantity = 0.0 ;
			double finalAmount = 0.0 ;
		 
		 Iterator<EntryForm> entryForm = entryForms.iterator();
		  
		  while (entryForm.hasNext()) {
		
			EntryForm entryform = entryForm.next();
			
			if(entryform.getMilkType().toUpperCase().equals("COW"))
			{
				cowMilkQuantity = cowMilkQuantity + entryform.getMilkQuantity();
				cowMilkAmount = cowMilkAmount + entryform.getTotalAmount();
			}
			
			if(entryform.getMilkType().toUpperCase().equals("BUFFALO"))
			{
				buffaloMilkQuantity = buffaloMilkQuantity + entryform.getMilkQuantity();
				buffaloMilkAmount = buffaloMilkAmount + entryform.getTotalAmount();
			}
				
			milkTotalQuantity = milkTotalQuantity + entryform.getMilkQuantity();
			finalAmount = finalAmount + entryform.getTotalAmount();
			
		  }
        
		     
		   model.addAttribute("cowMilkQuantity",format2Decimal(cowMilkQuantity));	
		   model.addAttribute("cowMilkAmount",format2Decimal(cowMilkAmount));
		   model.addAttribute("buffaloMilkQuantity" , format2Decimal(buffaloMilkQuantity));
		   model.addAttribute("buffaloMilkAmount" , format2Decimal(buffaloMilkAmount));
		   model.addAttribute("milkTotalQuantity" , format2Decimal(milkTotalQuantity));
		   model.addAttribute("finalAmount" , format2Decimal(finalAmount));
		 
	 }
	  
	 
	 
	
	 public void setPaymentReciept(Ledger ledger ,Model model,LocalDateTime fromDate,LocalDateTime toDate,Long numberOfDays,Double cowMilkQuantity,Double cowMilkAmount,Double buffaloMilkQuantity,Double buffaloMilkAmount,Double milkTotalQuantity,Double finalAmount)
	 {    
		   model.addAttribute("receiptNumber",ledger.getId());
	       model.addAttribute("fromDate", fromDate);
		   model.addAttribute("toDate", toDate);
		   model.addAttribute("numberOfDays",numberOfDays);
		   model.addAttribute("cowMilkQuantity",format2Decimal(cowMilkQuantity));	
		   model.addAttribute("cowMilkAmount",format2Decimal(cowMilkAmount));
		   model.addAttribute("buffaloMilkQuantity" , format2Decimal(buffaloMilkQuantity));
		   model.addAttribute("buffaloMilkAmount" , format2Decimal(buffaloMilkAmount));
		   model.addAttribute("milkTotalQuantity" , format2Decimal(milkTotalQuantity));
		   model.addAttribute("finalAmount" , format2Decimal(finalAmount));
		   model.addAttribute("paidAmount",format2Decimal(ledger.getAmount()));
		   model.addAttribute("remaningAmount",format2Decimal(ledger.getUser().getAmountBalance()));
		   model.addAttribute("date", LocalDateTime.now());
	 }
	 
	 public Double format2Decimal(double value)
	 {    
	        DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");  
	    	return Double.valueOf(twoDecimalFormat.format(value));
	 }
	
}
