package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.DocumentationChapter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentationChapter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentationChapterRepository extends JpaRepository<DocumentationChapter, Long> {

}
