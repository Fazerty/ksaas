package com.upurion.ksaas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.upurion.ksaas.web.rest.TestUtil;

public class TranslatedNameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranslatedName.class);
        TranslatedName translatedName1 = new TranslatedName();
        translatedName1.setId(1L);
        TranslatedName translatedName2 = new TranslatedName();
        translatedName2.setId(translatedName1.getId());
        assertThat(translatedName1).isEqualTo(translatedName2);
        translatedName2.setId(2L);
        assertThat(translatedName1).isNotEqualTo(translatedName2);
        translatedName1.setId(null);
        assertThat(translatedName1).isNotEqualTo(translatedName2);
    }
}
