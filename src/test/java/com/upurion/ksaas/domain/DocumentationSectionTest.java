package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class DocumentationSectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentationSection.class);
        DocumentationSection documentationSection1 = new DocumentationSection();
        documentationSection1.setId(1L);
        DocumentationSection documentationSection2 = new DocumentationSection();
        documentationSection2.setId(documentationSection1.getId());
        assertThat(documentationSection1).isEqualTo(documentationSection2);
        documentationSection2.setId(2L);
        assertThat(documentationSection1).isNotEqualTo(documentationSection2);
        documentationSection1.setId(null);
        assertThat(documentationSection1).isNotEqualTo(documentationSection2);
    }
}
