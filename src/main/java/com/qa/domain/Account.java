package com.qa.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity 
@Table(name="Accounts")
public class Account {
	
	@Size(min = 2, max = 20)
	private String firstName;
	@Size(min = 2, max = 20)
	private String secondName;
	@Id @Size(min = 4, max = 4)
	private String accountNumber;

	public Account(String firstName, String secondName, String accountNumber) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.accountNumber = accountNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
