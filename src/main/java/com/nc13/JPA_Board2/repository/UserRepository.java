package com.nc13.JPA_Board2.repository;

import com.nc13.JPA_Board2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 이메일로 회원 정보 조회 (select * from user_table where username=?)
    Optional<UserEntity> findByUsername(String username);

}
