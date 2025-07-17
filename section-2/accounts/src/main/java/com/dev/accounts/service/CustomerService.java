package com.dev.accounts.service;

import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

	ResponseEntity<ResponseDto> createCustomer(CustomerDto customerDto);

}
