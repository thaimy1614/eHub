package com.datn.user_service.repository;

import com.datn.user_service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

    Page<User> findByAddressContainingIgnoreCase(String username, Pageable pageable);

    User findByEmail(String email);
}
