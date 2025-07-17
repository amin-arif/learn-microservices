package com.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Customer", description = "Customer information with account details")
public class CustomerDto {

	@NotEmpty
	@Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
	@Schema(description = "Name of the customer", example = "John Doe")
	private String name;

	@NotEmpty
	@Email(message = "Invalid email address")
	@Schema(description = "Email address of the customer", example = "john@example.com")
	private String email;

	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{11})", message = "Invalid mobile number")
	@Schema(description = "Mobile number of the customer", example = "01234567890")
	private String mobileNumber;

	@Schema(description = "Account information of the customer")
	private AccountDto accountDto;
}
