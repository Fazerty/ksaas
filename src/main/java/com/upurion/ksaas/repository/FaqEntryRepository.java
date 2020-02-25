package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.FaqEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FaqEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaqEntryRepository extends JpaRepository<FaqEntry, Long> {

}
