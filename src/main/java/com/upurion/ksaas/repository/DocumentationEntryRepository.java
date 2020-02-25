package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.DocumentationEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentationEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentationEntryRepository extends JpaRepository<DocumentationEntry, Long> {

}
