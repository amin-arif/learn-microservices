package com.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Account", description = "Account information")
public class AccountDto {

	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{12})", message = "Invalid account number")
	@Schema(name = "accountNumber", description = "Account number", example = "123456789012")
	private Long accountNumber;

	@NotEmpty
	@Schema(description = "Account type", example = "SAVINGS")
	private String accountType;

	@NotEmpty
	@Schema(description = "Branch address", example = "Dhaka")
	private String branchAddress;

}
