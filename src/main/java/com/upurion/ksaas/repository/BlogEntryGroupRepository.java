package com.upurion.ksaas.repository;
import com.upurion.ksaas.domain.BlogEntryGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the BlogEntryGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogEntryGroupRepository extends JpaRepository<BlogEntryGroup, Long> {

    @Query("select blogEntryGroup from BlogEntryGroup blogEntryGroup where blogEntryGroup.user.login = ?#{principal.username}")
    List<BlogEntryGroup> findByUserIsCurrentUser();

}
