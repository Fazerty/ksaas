package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class DocumentationEntryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentationEntry.class);
        DocumentationEntry documentationEntry1 = new DocumentationEntry();
        documentationEntry1.setId(1L);
        DocumentationEntry documentationEntry2 = new DocumentationEntry();
        documentationEntry2.setId(documentationEntry1.getId());
        assertThat(documentationEntry1).isEqualTo(documentationEntry2);
        documentationEntry2.setId(2L);
        assertThat(documentationEntry1).isNotEqualTo(documentationEntry2);
        documentationEntry1.setId(null);
        assertThat(documentationEntry1).isNotEqualTo(documentationEntry2);
    }
}
