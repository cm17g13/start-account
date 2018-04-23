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
	JSONUtil jsonUtil;

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public String getAllAccounts() {
		
		Query query = manager.createQuery("SELECT a FROM ACCOUNTS a");
		Collection<Account> accounts = (Collection<Account>) query.getResultList();
		return jsonUtil.getJSONForObject(accounts);
	}
	
	
	@Transactional(SUPPORTS)
	private Account findAnAccount(String accountNumber) {
		
		Query query = manager.createQuery("SELECT a FROM ACCOUNTS a WHERE a.accountNumber == :accountNumber", Account.class);
		return  (Account) query.getSingleResult();
		//return manager.find(Account.class, accountNumber); how you'd do it if you want to use find in the EntityManager
	}
	
	
	@Transactional(REQUIRED)
	public String createAccount(String accout) {
		
		Account newAccount = jsonUtil.getObjectForJSON(accout, Account.class);
		if(findAnAccount(newAccount.getAccountNumber()) != null) {
			manager.persist(newAccount);
			return "{\"message\": \"the account has been added\"}";
		} else {
			return "{\"message\": \"the account exists, and so was not added\"}";
		}
		
	}
	
	
	@Transactional(REQUIRED)
	public String updateAccount(String id, String accountToUpdate) {
		
		Account existingAccount = jsonUtil.getObjectForJSON(accountToUpdate, Account.class);
		if (findAnAccount(existingAccount.getAccountNumber()) != null) {
			manager.merge(existingAccount);
			return "{\"message\": \"the account has been updated\"}";
		} else {
			return "{\"message\": \"the account did not exist, and so was not updated\"}";
		}
		
	}
	
	@Transactional(REQUIRED)
	public String deleteAccount(String id) {
		
		Account exists = findAnAccount(id);
		if (exists != null) {
			manager.remove(exists);
			return "{\"message\": \"the account has been deleted\"}";
		} else {
			return "{\"message\": \"the account did not exist, and so was not deleted\"}";
		}	
	}
}

