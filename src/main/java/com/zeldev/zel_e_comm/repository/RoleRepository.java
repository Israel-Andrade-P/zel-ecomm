package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query(
            """
                    SELECT r FROM RoleEntity r WHERE r.name=:name
                    """
    )
    Optional<RoleEntity> findByRoleName(String name);
}
