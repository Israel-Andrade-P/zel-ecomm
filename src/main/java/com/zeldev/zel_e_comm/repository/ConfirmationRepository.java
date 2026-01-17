package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.ConfirmationEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {
    @Query(
            """
                    SELECT c FROM ConfirmationEntity c WHERE c.confKey=:key
                    """
    )
    Optional<ConfirmationEntity> findByKey(String key);

    ConfirmationEntity findByUser(UserEntity user);
}
