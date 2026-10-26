package com.zeldev.zel_e_comm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.RoleType;
import com.zeldev.zel_e_comm.enumeration.UserStatus;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  @Query("SELECT u FROM UserEntity u WHERE u.email=?1 ")
  Optional<UserEntity> findByEmail(String email);

  @Query("SELECT u FROM UserEntity u WHERE u.username=?1 ")
  Optional<UserEntity> findByUsername(String username);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("DELETE FROM UserEntity u WHERE u.username=?1")
  int deleteByUsername(@Param("username") String username);

  @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.email=?1")
  Optional<UserEntity> findByEmailWithRoles(String email);

  @Query("SELECT COUNT(u) FROM UserEntity u JOIN u.roles r WHERE r.role = :role")
  long countByRole(@Param("role") RoleType role);

  @Query(
    value = "SELECT u FROM UserEntity u WHERE u.status = :status AND EXISTS (SELECT r FROM u.roles r WHERE r.role = :role)",
    countQuery = "SELECT COUNT(u) FROM UserEntity u WHERE u.status = :status AND EXISTS (SELECT r FROM u.roles r WHERE r.role = :role)"
  )
  Page<UserEntity> findByRole(@Param("role") RoleType role, @Param("status") UserStatus status, Pageable pageable);
}
