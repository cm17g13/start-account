package com.qa.service;

import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import static javax.transaction.Transactional.TxType.*;
import java.util.Collection;
import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
public class AccountManagerDBImpl implements AccountManagerInterface {

	@Inject
	private JSONUtil jsonConverter;

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	@Override
	public String getAllAccounts() {
		
		TypedQuery<Account> query = manager.createQuery("SELECT a FROM ACCOUNTS a", Account.class);
		Collection<Account> accounts = query.getResultList();
		return jsonConverter.getJSONForObject(accounts);
	}
	

	@Override
	public String findAnAccount(String accountNumber) {

		TypedQuery<Account> query = manager.createQuery("SELECT a FROM ACCOUNTS a WHERE a.accountNumber = :accountNumber", Account.class);
		Account anAccount = query.getSingleResult();
		return   jsonConverter.getJSONForObject(anAccount);
		//return manager.find(Account.class, accountNumber); how you'd do it if you want to use find in the EntityManager
	}
	
	
	@Override
	@Transactional(REQUIRED)
	public String createAccount(String account) {
		
		Account newAccount = jsonConverter.getObjectForJSON(account, Account.class);
		if(!findAnAccount(newAccount.getAccountNumber()).equals("null")) {
			return "{\"message\": \"the account exists, and so was not added\"}";
		} else {
			manager.persist(newAccount);
			return "{\"message\": \"the account has been added\"}";
		}
	}
	
	
	@Override
	@Transactional(REQUIRED)
	public String updateAccount(String account) {
		
		Account existingAccount = jsonConverter.getObjectForJSON(account, Account.class);
		if (!findAnAccount(existingAccount.getAccountNumber()).equals("null")) {
			manager.merge(existingAccount);
			return "{\"message\": \"the account has been updated\"}";
		} else {
			return "{\"message\": \"the account did not exist, and so was not updated\"}";
		}
	}
	
	@Override
	@Transactional(REQUIRED)
	public String deleteAccount(String accountNumber) {
		
		String exists = findAnAccount(accountNumber);
		if (!exists.equals("null")) {
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

