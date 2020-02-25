package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.DocumentationEntryGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentationEntryGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentationEntryGroupRepository extends JpaRepository<DocumentationEntryGroup, Long> {

}
