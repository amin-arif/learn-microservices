package com.dev.accounts.controller;

import com.dev.accounts.constant.ApplicationConstant;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.dto.ErrorResponseDto;
import com.dev.accounts.dto.ResponseDto;
import com.dev.accounts.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
		name = "Account Controller",
		description = "CRUD operations for account"
)
@RestController
@Validated
@RequestMapping(path = "/api/account", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@Operation(summary = "Create account", description = "Create account for customer")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Account created successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		accountService.createAccount(customerDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(
						ApplicationConstant.STATUS_201,
						ApplicationConstant.MESSAGE_201
				));
	}

	@GetMapping("/fetch")
	@Operation(summary = "Fetch account information", description = "Fetch account by mobile number")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Account information fetched successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	public ResponseEntity<CustomerDto> fetchAccount(
			@RequestParam
			@Pattern(regexp = "(^$|[0-9]{11})", message = "Invalid mobile number")
			String mobileNumber) {
		CustomerDto customerDto = accountService.fetchAccountByMobileNumber(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	@Operation(summary = "Update account", description = "Update account for customer")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Account updated successfully"),
			@ApiResponse(responseCode = "417", description = "Update operation failed. Please try again or contact Dev team",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
		boolean isUpdated = accountService.updateAccount(customerDto);
		if (!isUpdated) {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(
							ApplicationConstant.STATUS_417,
							ApplicationConstant.MESSAGE_417_UPDATE
					));
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto(
						ApplicationConstant.STATUS_200,
						ApplicationConstant.MESSAGE_200
				));
	}

	@Operation(summary = "Delete account", description = "Delete account for customer")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Account deleted successfully"),
			@ApiResponse(responseCode = "417", description = "Delete operation failed. Please try again or contact Dev team",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccount(
			@RequestParam
			@Pattern(regexp = "(^$|[0-9]{11})", message = "Invalid mobile number")
			String mobileNumber) {
		boolean deleted = accountService.deleteAccount(mobileNumber);
		if (deleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(
							ApplicationConstant.STATUS_200,
							ApplicationConstant.MESSAGE_200
					));
		}
		return ResponseEntity
				.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ResponseDto(
						ApplicationConstant.STATUS_417,
						ApplicationConstant.MESSAGE_417_DELETE
				));
	}
}
