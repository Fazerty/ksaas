package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.DocumentationSection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentationSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentationSectionRepository extends JpaRepository<DocumentationSection, Long> {

}
