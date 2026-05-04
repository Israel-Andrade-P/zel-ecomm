package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
    @Query(
            """
                    SELECT c FROM CredentialEntity c WHERE c.user.id=:userId
                    """
    )
    Optional<CredentialEntity> findByUserId(Long userId);
}
