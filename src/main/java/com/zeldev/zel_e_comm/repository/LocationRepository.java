package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.LocationEntity;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    @Query(
            """
                    SELECT l FROM LocationEntity l WHERE l.user.email=?1
                    """
    )
    List<LocationEntity> findLocationsByUserEmail(String email);

    @Query(
            """
                    SELECT l FROM LocationEntity l WHERE l.zipCode=?1 AND l.user.email=?2
                    """
    )
    Optional<LocationEntity> findLocationByZipCodeAndUserEmail(String zipCode, String email);

    @Query("SELECT l FROM LocationEntity l WHERE l.publicId=?1")
    Optional<LocationEntity> findByPublicId(UUID publicId);

    @Query(
            """
                    SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END\s
                    FROM LocationEntity l WHERE l.publicId = ?1 AND l.user.email = ?2
                   \s"""
    )
    boolean existsByIdAndUserEmail(UUID publicId, String userEmail);
}
