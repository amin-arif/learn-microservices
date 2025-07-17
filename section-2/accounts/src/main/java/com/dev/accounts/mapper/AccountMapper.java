package com.dev.accounts.mapper;

import com.dev.accounts.dto.AccountDto;
import com.dev.accounts.entity.Account;

public class AccountMapper {

	public static void mapToAccount(AccountDto accountDto, Account account) {
		account.setAccountNumber(accountDto.getAccountNumber());
		account.setAccountType(accountDto.getAccountType());
		account.setBranchAddress(accountDto.getBranchAddress());
	}

	public static AccountDto mapToAccountDto(Account account) {
		AccountDto accountsDto = new AccountDto();
		accountsDto.setAccountNumber(account.getAccountNumber());
		accountsDto.setAccountType(account.getAccountType());
		accountsDto.setBranchAddress(account.getBranchAddress());
		return accountsDto;
	}

}
