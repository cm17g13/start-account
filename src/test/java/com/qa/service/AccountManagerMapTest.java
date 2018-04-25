package com.qa.service;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

public class AccountManagerMapTest {

    private AccountManagerMapImpl accountManager;

    private JSONUtil jsonConverter;

    String person1 = "{\"firstName\":\"Tib\",\"secondName\":\"Coder\",\"accountNumber\":\"1111\"}";

    @Before
    public void before() {
        this.accountManager = new AccountManagerMapImpl();
        this.jsonConverter = new JSONUtil();
        accountManager.createAccount(person1);
    }

    @Test
    public void getAllAccountsTest() {
        String personArray = "[" + person1 + "]";
        Assert.assertEquals(personArray, accountManager.getAllAccounts());
    }


    @Test
    public void findAnAccountTest() {
        Assert.assertEquals(person1,accountManager.findAnAccount("1111"));
    }

    @Test
    public void createAccountTest() {
        accountManager.deleteAccount("1111");
        Assert.assertEquals("{\"message\": \"the account has been added\"}", accountManager.createAccount(person1));
    }

    @Test
    public void updateAccountTest() {
        Assert.assertEquals("{\"message\": \"the account has been updated\"}", accountManager.updateAccount(person1));

    }

    @Test
    public void deleteAccountTest() {
        Assert.assertEquals("{\"message\": \"the account has been deleted\"}", accountManager.deleteAccount("1111"));

    }

}
