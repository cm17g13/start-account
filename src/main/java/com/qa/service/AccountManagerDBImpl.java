package com.qa.service;

import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import static javax.transaction.Transactional.TxType.*;
import java.util.Collection;
import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
public class AccountManagerDBImpl {

	@Inject
	private JSONUtil jsonConverter;

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	public String getAllAccounts() {
		
		TypedQuery<Account> query = manager.createQuery("SELECT a FROM ACCOUNTS a", Account.class);
		Collection<Account> accounts = query.getResultList();
		return jsonConverter.getJSONForObject(accounts);
	}
	

	public Account findAnAccount(String accountNumber) {

		TypedQuery<Account> query = manager.createQuery("SELECT a FROM ACCOUNTS a WHERE a.accountNumber = :accountNumber", Account.class);
		return   query.getSingleResult();
		//return manager.find(Account.class, accountNumber); how you'd do it if you want to use find in the EntityManager
	}
	
	
	@Transactional(REQUIRED)
	public String createAccount(String account) {
		
		Account newAccount = jsonConverter.getObjectForJSON(account, Account.class);
		if(findAnAccount(newAccount.getAccountNumber()) != null) {
			return "{\"message\": \"the account exists, and so was not added\"}";
		} else {
			manager.persist(newAccount);
			return "{\"message\": \"the account has been added\"}";
		}
	}
	
	
	@Transactional(REQUIRED)
	public String updateAccount(String account) {
		
		Account existingAccount = jsonConverter.getObjectForJSON(account, Account.class);
		if (findAnAccount(existingAccount.getAccountNumber()) != null) {
			manager.merge(existingAccount);
			return "{\"message\": \"the account has been updated\"}";
		} else {
			return "{\"message\": \"the account did not exist, and so was not updated\"}";
		}
	}
	
	@Transactional(REQUIRED)
	public String deleteAccount(String accountNumber) {
		
		Account exists = findAnAccount(accountNumber);
		if (exists != null) {
			manager.remove(exists);
			return "{\"message\": \"the account has been deleted\"}";
		} else {
			return "{\"message\": \"the account did not exist, and so was not deleted\"}";
		}	
	}
	
	@Transactional(REQUIRED)
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}
	
	@Transactional(REQUIRED)
	public void setJsonConverter(JSONUtil jsonConverter) {
		this.jsonConverter = jsonConverter;
	}
}

