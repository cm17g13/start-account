package com.qa.service;

import com.qa.domain.Account;

import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;

public abstract interface AccountManagerInterface {

    String getAllAccounts();

    String findAnAccount(String accountNumber);

    @Transactional(REQUIRED)
    String createAccount(String account);

    @Transactional(REQUIRED)
    String updateAccount(String account);

    @Transactional(REQUIRED)
    String deleteAccount(String accountNumber);
}
