package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.FaqSection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FaqSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaqSectionRepository extends JpaRepository<FaqSection, Long> {

}
