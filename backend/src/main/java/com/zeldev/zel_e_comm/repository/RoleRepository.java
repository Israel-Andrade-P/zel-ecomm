package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.enumeration.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query(
            """
                    SELECT r FROM RoleEntity r WHERE r.role=:role
                    """
    )
    Optional<RoleEntity> findByRoleName(RoleType role);
}
