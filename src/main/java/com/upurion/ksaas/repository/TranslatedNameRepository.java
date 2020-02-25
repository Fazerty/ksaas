package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.TranslatedName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TranslatedName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranslatedNameRepository extends JpaRepository<TranslatedName, Long> {

}
