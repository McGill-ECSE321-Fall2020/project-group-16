package ca.mcgill.ecse321.artgalleryapplication.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.artgalleryapplication.dto.PaymentDto;

import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;
import ca.mcgill.ecse321.artgalleryapplication.service.PaymentService;

@CrossOrigin(origins = "*")
@RestController
public class PaymentRestController {

	@Autowired
	private PaymentService paymentService;

	/**
	 *
	 * @param paymentForm
	 * @param paymentDate
	 * @param cardNumber
	 * @param expirationDate
	 * @param cvv
	 * @param paymentTime
	 * @return
	 * @throws ApiRequestException
	 */
	@PostMapping(value = {"/payments", "/payments/"}) 
	public PaymentDto createPayment (@RequestParam("paymentForm") PaymentForm paymentForm,
									 @RequestParam("paymentDate") Date paymentDate,
									 @RequestParam("cardNumber") String cardNumber,
									 @RequestParam("expirationDate") Date expirationDate,
									 @RequestParam("cvv") int cvv,
									 @RequestParam("paymentTime") Time paymentTime)
									 throws ApiRequestException {
		
		Payment payment = paymentService.createPayment(paymentForm, paymentDate, cardNumber, expirationDate, cvv, paymentTime);
		
		return ConvertToDto.convertToDto(payment);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = {"/payments/{id}", "/payments/{id}/"})	
	public PaymentDto getPaymentById(@PathVariable("id") int id) {
		Payment p = paymentService.getPayment(id);
		return ConvertToDto.convertToDto(p);
	}

	/**
	 *
	 * @return
	 */
	@GetMapping(value = {"/payments", "/payments/"})
	public List<PaymentDto> getAllPayments() {
		List<PaymentDto> payments = new ArrayList<>();
	
		for(Payment p : paymentService.getAllPayments()) {
			payments.add(ConvertToDto.convertToDto(p));
		}
		return payments;
	}

	/**
	 *
	 * @param cardNumber
	 * @return
	 */
	@GetMapping(value = {"/payments/byCardNumber", "/payments/byCardNumber/"})
	public List<PaymentDto> getAllPaymentsByCardNumber(@RequestParam("cardNumber") String cardNumber) {
		List<PaymentDto> payments = new ArrayList<>();
		for(Payment p : paymentService.getAllPaymentsByCardNumber(cardNumber)) {
			payments.add(ConvertToDto.convertToDto(p));
		}
		return payments;
	}

	/**
	 *
	 * @param paymentDate
	 * @return
	 */
	@GetMapping(value = {"/payments/byPaymentDate", "/payments/byPaymentDate/"})
	public List<PaymentDto> getAllPaymentsByPaymentDate(@RequestParam("paymentDate") Date paymentDate) {
		List<PaymentDto> payments = new ArrayList<>();
		for(Payment p : paymentService.getAllPaymentsByPaymentDate(paymentDate)) {
			payments.add(ConvertToDto.convertToDto(p));
		}
		return payments;
	}

	/**
	 *
	 * @param paymentTime
	 * @return
	 */
	@GetMapping(value = {"/payments/byPaymentTime", "/payments/byPaymentTime/"})
	public List<PaymentDto> getAllPaymentsByPaymentTime(@RequestParam("paymentTime") Time paymentTime) {
		List<PaymentDto> payments = new ArrayList<>();
		for(Payment p : paymentService.getAllPaymentsByPaymentTime(paymentTime)) {
			payments.add(ConvertToDto.convertToDto(p));
		}
		return payments;
	}
	
}
