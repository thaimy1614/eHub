package com.datn.user_service.repository;

import com.datn.user_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Boolean existsByEmail(String email);
}
