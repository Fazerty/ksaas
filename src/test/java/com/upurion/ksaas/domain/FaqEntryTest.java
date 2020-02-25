package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class FaqEntryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaqEntry.class);
        FaqEntry faqEntry1 = new FaqEntry();
        faqEntry1.setId(1L);
        FaqEntry faqEntry2 = new FaqEntry();
        faqEntry2.setId(faqEntry1.getId());
        assertThat(faqEntry1).isEqualTo(faqEntry2);
        faqEntry2.setId(2L);
        assertThat(faqEntry1).isNotEqualTo(faqEntry2);
        faqEntry1.setId(null);
        assertThat(faqEntry1).isNotEqualTo(faqEntry2);
    }
}
