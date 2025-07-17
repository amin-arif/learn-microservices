package com.dev.accounts.service;

import com.dev.accounts.dto.CustomerDto;

public interface AccountService {

	void createAccount(CustomerDto customerDto);

	CustomerDto fetchAccountByMobileNumber(String mobileNumber);

	boolean updateAccount(CustomerDto customerDto);

	boolean deleteAccount(String mobileNumber);
}
