package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class BlogEntryGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogEntryGroup.class);
        BlogEntryGroup blogEntryGroup1 = new BlogEntryGroup();
        blogEntryGroup1.setId(1L);
        BlogEntryGroup blogEntryGroup2 = new BlogEntryGroup();
        blogEntryGroup2.setId(blogEntryGroup1.getId());
        assertThat(blogEntryGroup1).isEqualTo(blogEntryGroup2);
        blogEntryGroup2.setId(2L);
        assertThat(blogEntryGroup1).isNotEqualTo(blogEntryGroup2);
        blogEntryGroup1.setId(null);
        assertThat(blogEntryGroup1).isNotEqualTo(blogEntryGroup2);
    }
}
