package com.waste_manager.team_roadmap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends Repository<Seller, Long> {

    Seller save(Seller seller);

    // Find All
    List<Seller> findAll();

    // Find By
    Optional<Seller> findById(long id);
    List<Seller> findByDName(String dName);

    // Update
    @Transactional
    @Modifying
    @Query("update Seller u set u.dName = :DName where u.ID = :ID")
    void updateDNameById(@Param("DName") String newDName, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Seller u set u.fName = :FName where u.ID = :ID")
    void updateFNameById(@Param("FName") String newFName, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Seller u set u.sName = :SName where u.ID = :ID")
    void updateSNameById(@Param("SName") String newSName, @Param("ID") long id);

    @Transactional
    @Modifying
    @Query("update Seller u set u.address = :address where u.ID = :ID")
    void updateAddressById(@Param("address") String newAddress, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Seller u set u.postcode = :postcode where u.ID = :ID")
    void updatePostcodeById(@Param("postcode") String newPostcode, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Seller u set u.county = :county where u.ID = :ID")
    void updateCountyById(@Param("county") String newCounty, @Param("ID") long id);

    @Transactional
    @Modifying
    @Query("update Seller u set u.email = :email where u.ID = :ID")
    void updateEmailById(@Param("email") String newEmail, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Seller u set u.phone = :phone where u.ID = :ID")
    void updatePhoneById(@Param("phone") String newPhone, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Seller u set u.password = :password where u.ID = :ID")
    void updatePasswordById(@Param("password") String newPassword, @Param("ID") long id);
}