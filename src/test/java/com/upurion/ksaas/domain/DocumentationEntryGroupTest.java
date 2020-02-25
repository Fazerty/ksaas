package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class DocumentationEntryGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentationEntryGroup.class);
        DocumentationEntryGroup documentationEntryGroup1 = new DocumentationEntryGroup();
        documentationEntryGroup1.setId(1L);
        DocumentationEntryGroup documentationEntryGroup2 = new DocumentationEntryGroup();
        documentationEntryGroup2.setId(documentationEntryGroup1.getId());
        assertThat(documentationEntryGroup1).isEqualTo(documentationEntryGroup2);
        documentationEntryGroup2.setId(2L);
        assertThat(documentationEntryGroup1).isNotEqualTo(documentationEntryGroup2);
        documentationEntryGroup1.setId(null);
        assertThat(documentationEntryGroup1).isNotEqualTo(documentationEntryGroup2);
    }
}
