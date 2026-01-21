package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.entity.Account;

public interface AccountDAO extends JpaRepository<Account,Integer> {
}
