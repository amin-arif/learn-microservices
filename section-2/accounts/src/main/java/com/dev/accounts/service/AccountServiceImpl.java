package com.dev.accounts.service;

import com.dev.accounts.constant.ApplicationConstant;
import com.dev.accounts.dto.AccountDto;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.entity.Account;
import com.dev.accounts.entity.Customer;
import com.dev.accounts.exception.CustomerAlreadyExistsException;
import com.dev.accounts.exception.ResourceNotFoundException;
import com.dev.accounts.mapper.AccountMapper;
import com.dev.accounts.mapper.CustomerMapper;
import com.dev.accounts.repository.AccountRepository;
import com.dev.accounts.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final CustomerRepository customerRepository;
	private final AccountRepository accountsRepository;

	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = new Customer();
				CustomerMapper.mapToCustomer(customerDto, customer);
		Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customer.getMobileNumber());
		if (customerOptional.isPresent()) {
			throw new CustomerAlreadyExistsException(ApplicationConstant.ERROR_MESSAGE_DUPLICATE_PHONE_NUMBER);
		}
		Customer savedCustomer = customerRepository.save(customer);
		accountsRepository.save(createAccount(savedCustomer));
	}

	private Account createAccount(Customer customer) {
		Account account = new Account();
		Long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
		account.setAccountNumber(randomAccountNumber);
		account.setAccountType(ApplicationConstant.SAVINGS);
		account.setBranchAddress(ApplicationConstant.ADDRESS);
		account.setCustomerId(customer.getCustomerId());
		return account;
	}

	@Override
	public CustomerDto fetchAccountByMobileNumber(String mobileNumber) {
		Optional<Customer> customerOptional =
				Optional.ofNullable(
						customerRepository.findByMobileNumber(mobileNumber)
								.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber))
				);

		Optional<Account> account =
				Optional.ofNullable(
						accountsRepository.findByCustomerId(customerOptional.get().getCustomerId())
								.orElseThrow(() -> new ResourceNotFoundException(
										"Account",
										"customerId",
										customerOptional.get().getCustomerId().toString()))
				);

		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customerOptional.get());
		customerDto.setAccountDto(AccountMapper.mapToAccountDto(account.get()));
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated = false;
		AccountDto accountDto = customerDto.getAccountDto();
		Optional<Account> account = Optional.ofNullable(accountsRepository.findById(accountDto.getAccountNumber())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Account",
						"accountNumber", accountDto.getAccountNumber().toString())));
		if (account.isPresent()) {
			AccountMapper.mapToAccount(accountDto, account.get());
			accountsRepository.save(account.get());

			Customer customer = customerRepository.findById(account.get().getCustomerId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Customer",
							"customerId", account.get().getCustomerId().toString()));
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);

			isUpdated = true;
		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		boolean isDeleted = false;
		Optional<Customer> customer = Optional.ofNullable(customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Customer", "mobileNumber", mobileNumber)));
		if (customer.isPresent()) {
			accountsRepository.deleteByCustomerId(customer.get().getCustomerId());
			customerRepository.delete(customer.get());
			isDeleted = true;
		}
		return isDeleted;
	}

}
