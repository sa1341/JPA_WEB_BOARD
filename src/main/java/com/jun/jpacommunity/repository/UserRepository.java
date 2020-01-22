package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// 현재 내가봤을땐 로그인 후속작업에서 무언가 문제가있는거같다..
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
