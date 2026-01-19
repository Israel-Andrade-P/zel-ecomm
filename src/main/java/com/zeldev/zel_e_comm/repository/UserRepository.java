package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(
            """
                    SELECT u FROM UserEntity u WHERE u.email=:email      \s
                            """
    )
    Optional<UserEntity> findByEmail(String email);
}
