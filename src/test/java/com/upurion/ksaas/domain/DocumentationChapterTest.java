package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class DocumentationChapterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentationChapter.class);
        DocumentationChapter documentationChapter1 = new DocumentationChapter();
        documentationChapter1.setId(1L);
        DocumentationChapter documentationChapter2 = new DocumentationChapter();
        documentationChapter2.setId(documentationChapter1.getId());
        assertThat(documentationChapter1).isEqualTo(documentationChapter2);
        documentationChapter2.setId(2L);
        assertThat(documentationChapter1).isNotEqualTo(documentationChapter2);
        documentationChapter1.setId(null);
        assertThat(documentationChapter1).isNotEqualTo(documentationChapter2);
    }
}
