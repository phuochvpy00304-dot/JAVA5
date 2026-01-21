package com.poly.lab8.Service;

import com.poly.lab8.Entity.Account;

import java.util.List;

public interface AccountService {
    Account findById(String username);
    List<Account> findAll();
}
