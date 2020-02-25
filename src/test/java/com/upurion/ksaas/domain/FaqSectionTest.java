package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class FaqSectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaqSection.class);
        FaqSection faqSection1 = new FaqSection();
        faqSection1.setId(1L);
        FaqSection faqSection2 = new FaqSection();
        faqSection2.setId(faqSection1.getId());
        assertThat(faqSection1).isEqualTo(faqSection2);
        faqSection2.setId(2L);
        assertThat(faqSection1).isNotEqualTo(faqSection2);
        faqSection1.setId(null);
        assertThat(faqSection1).isNotEqualTo(faqSection2);
    }
}
