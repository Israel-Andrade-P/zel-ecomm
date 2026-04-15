package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.email=?1 ")
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.username=?1 ")
    Optional<UserEntity> findByUsername(String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM UserEntity u WHERE u.username=?1")
    int deleteByUsername(@Param("username") String username);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.email=?1")
    Optional<UserEntity> findByIdWithRoles(String email);
}
