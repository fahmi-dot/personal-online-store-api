package com.fahmi.personalonlinestore.repository;

import com.fahmi.personalonlinestore.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findByUserIdOrderByIsPrimaryDescCreatedAtDesc(String userId);

    Optional<Address> findByIdAndUserId(String id, String userId);

    Optional<Address> findByUserIdAndIsPrimaryTrue(String userId);

    @Modifying
    @Query("UPDATE Address a SET a.isPrimary = false WHERE a.user.id = :userId")
    void resetPrimaryByUserId(@Param("userId") String userId);
}
