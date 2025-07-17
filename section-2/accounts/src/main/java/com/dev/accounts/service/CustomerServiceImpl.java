package com.dev.accounts.service;

import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	@Override
	public ResponseEntity<ResponseDto> createCustomer(CustomerDto customerDto) {
		return null;
	}
}
