package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class FaqEntryGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaqEntryGroup.class);
        FaqEntryGroup faqEntryGroup1 = new FaqEntryGroup();
        faqEntryGroup1.setId(1L);
        FaqEntryGroup faqEntryGroup2 = new FaqEntryGroup();
        faqEntryGroup2.setId(faqEntryGroup1.getId());
        assertThat(faqEntryGroup1).isEqualTo(faqEntryGroup2);
        faqEntryGroup2.setId(2L);
        assertThat(faqEntryGroup1).isNotEqualTo(faqEntryGroup2);
        faqEntryGroup1.setId(null);
        assertThat(faqEntryGroup1).isNotEqualTo(faqEntryGroup2);
    }
}
