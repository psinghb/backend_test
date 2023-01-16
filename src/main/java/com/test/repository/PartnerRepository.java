package com.test.repository;

import com.test.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    @Query("select p.id from Partner p where p.parent.id = ?1")
    Set<Long> findSubPartnerIds(Long partnerId);

    @Query("select p.id from Partner p where p.parent.id in ?1")
    Set<Long> findSubPartnerIds(Set<Long> partnerIds);
}
