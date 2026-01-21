package com.poly.lab8.DAO;

import com.poly.lab8.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, String> {}
