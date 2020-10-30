package ca.mcgill.ecse321.artgalleryapplication.controller;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.artgalleryapplication.dto.PaymentDto;

import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;
import ca.mcgill.ecse321.artgalleryapplication.service.PaymentService;

public class PaymentRestController {

	@Autowired
	private PaymentService paymentService;
	
	
	@PostMapping(value = {"/payments", "/payments/"}) 
	public PaymentDto createPayment (@RequestParam("paymentForm") PaymentForm paymentForm,
									 @RequestParam("paymentDate") Date paymentDate,
									 @RequestParam("cardNumber") String cardNumber,
									 @RequestParam("expirationDate") Date expirationDate,
									 @RequestParam("cvv") int cvv,
									 @RequestParam("paymentId") int paymentId,
									 @RequestParam("paymentTime") Time paymentTime)
									 throws IllegalArgumentException{
		
		Payment payment = paymentService.createPayment(paymentForm, paymentDate, cardNumber, expirationDate, cvv, paymentId, paymentTime);
		
		return ConvertToDto.convertToDto(payment);
	}
	
	@GetMapping(value = {"/payments/{id}", "/payments/{id}/"})	
	public PaymentDto getPaymentById(@PathVariable("id") int id) {
		Payment p = paymentService.getPayment(id);
		return ConvertToDto.convertToDto(p);
	}
	
	@GetMapping(value = {"/payments", "/payments/"})
	public List<PaymentDto> getAllPayments() {
		List<PaymentDto> payments = new ArrayList<>();
	
		for(Payment p : paymentService.getAllPayments()) {
			payments.add(ConvertToDto.convertToDto(p));
		}
		return payments;
	}
	
	@GetMapping(value = {"/payments/byCardNumber", "/payments/byCardNumber/"})
	public List<PaymentDto> getAllPaymentsByCardNumber(@RequestParam("cardNumber") String cardNumber) {
		List<PaymentDto> payments = new ArrayList<>();
		for(Payment p : paymentService.getAllPaymentsByCardNumber(cardNumber)) {
			payments.add(ConvertToDto.convertToDto(p));
		}
		return payments;
	}
	
	@GetMapping(value = {"/payments/byPaymentDate", "/payments/byPaymentDate/"})
	public List<PaymentDto> getAllPaymentsByPaymentDate(@RequestParam("paymentDate") Date paymentDate) {
		List<PaymentDto> payments = new ArrayList<>();
		for(Payment p : paymentService.getAllPaymentsByPaymentDate(paymentDate)) {
			payments.add(ConvertToDto.convertToDto(p));
		}
		return payments;
	}
	
	@GetMapping(value = {"/payments/byPaymentTime", "/payments/byPaymentTime/"})
	public List<PaymentDto> getAllPaymentsByPaymentTime(@RequestParam("paymentTime") Time paymentTime) {
		List<PaymentDto> payments = new ArrayList<>();
		for(Payment p : paymentService.getAllPaymentsByPaymentTime(paymentTime)) {
			payments.add(ConvertToDto.convertToDto(p));
		}
		return payments;
	}
	
}
