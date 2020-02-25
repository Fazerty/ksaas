package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.FaqEntryGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FaqEntryGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaqEntryGroupRepository extends JpaRepository<FaqEntryGroup, Long> {

}
