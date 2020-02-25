package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class BlogSectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogSection.class);
        BlogSection blogSection1 = new BlogSection();
        blogSection1.setId(1L);
        BlogSection blogSection2 = new BlogSection();
        blogSection2.setId(blogSection1.getId());
        assertThat(blogSection1).isEqualTo(blogSection2);
        blogSection2.setId(2L);
        assertThat(blogSection1).isNotEqualTo(blogSection2);
        blogSection1.setId(null);
        assertThat(blogSection1).isNotEqualTo(blogSection2);
    }
}
