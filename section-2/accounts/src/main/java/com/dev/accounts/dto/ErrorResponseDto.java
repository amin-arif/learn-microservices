package com.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Error Response", description = "Error response DTO")
public class ErrorResponseDto {
	@Schema(description = "API path")
	private String apiPath;

	@Schema(description = "Error code")
	private HttpStatus errorCode;

	@Schema(description = "Error message")
	private String errorMessage;

	@Schema(description = "Error time")
	private LocalDateTime errorTime;
}
