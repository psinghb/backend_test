package com.test.repository;

import com.test.entity.Contract;
import com.test.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("select count(c) from Contract c where extract(YEAR from c.date) = ?2 and c.partner.id in ?1 and extract(QUARTER from c.date) = ?3")
    Long countOfContractByPartnerIdInAndYearAndQuarter(Set<Long> partnerIds, int year, int quarter);


    @Query("select c from Contract c where extract(YEAR from c.date) = ?2 and c.partner.id in ?1 and extract(QUARTER from c.date) = ?3")
    List<Contract> findByPartnerIdInAndYearAndQuarter(Set<Long> partnerIds, int year, int quarter);
}
