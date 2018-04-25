package com.qa.service;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.HashMap;


@Alternative
public class AccountManagerMapImpl implements AccountManagerInterface {

    private HashMap<String, Account> accounts;

    private JSONUtil jsonConverter;

    public AccountManagerMapImpl() {
        this.accounts = new HashMap<String, Account>();
        this.jsonConverter = new JSONUtil();
    }

    public String getAllAccounts() {
        return jsonConverter.getJSONForObject(accounts.values());
    }

    public String findAnAccount(String accountNumber) {
        return jsonConverter.getJSONForObject(accounts.get(accountNumber));
    }

    public String createAccount(String account) {

        Account newAccount = jsonConverter.getObjectForJSON(account, Account.class);
        if(!findAnAccount(newAccount.getAccountNumber()).equals("null")) {
            return "{\"message\": \"the account exists, and so was not added\"}";
        } else {
            accounts.put(newAccount.getAccountNumber(), newAccount);
            return "{\"message\": \"the account has been added\"}";
        }
    }

    public String updateAccount(String account) {
        Account existingAccount = jsonConverter.getObjectForJSON(account, Account.class);
        if (!findAnAccount(existingAccount.getAccountNumber()).equals("null")) {
            accounts.put(existingAccount.getAccountNumber() ,existingAccount);
            return "{\"message\": \"the account has been updated\"}";
        } else {
            return "{\"message\": \"the account did not exist, and so was not updated\"}";
        }
    }


    public String deleteAccount(String accountNumber) {

        String exists = findAnAccount(accountNumber);
        if (!exists.equals("null")) {
           accounts.remove(accountNumber);
            return "{\"message\": \"the account has been deleted\"}";
        } else {
            return "{\"message\": \"the account did not exist, and so was not deleted\"}";
        }
    }
}