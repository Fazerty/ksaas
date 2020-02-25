package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.BlogSection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * Spring Data repository for the BlogSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogSectionRepository extends JpaRepository<BlogSection, Long> {

}
