package com.dev.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseClass {
	@Id
	private Long accountNumber;

	private Long customerId;

	private String accountType;

	private String branchAddress;
}
