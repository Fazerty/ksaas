package com.upurion.ksaas.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FaqSection.
 */
@Entity
@Table(name = "faq_section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FaqSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "info", nullable = false)
    private String info;

    @OneToMany(mappedBy = "faqSection")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TranslatedName> names = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public FaqSection info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<TranslatedName> getNames() {
        return names;
    }

    public FaqSection names(Set<TranslatedName> translatedNames) {
        this.names = translatedNames;
        return this;
    }

    public FaqSection addNames(TranslatedName translatedName) {
        this.names.add(translatedName);
        translatedName.setFaqSection(this);
        return this;
    }

    public FaqSection removeNames(TranslatedName translatedName) {
        this.names.remove(translatedName);
        translatedName.setFaqSection(null);
        return this;
    }

    public void setNames(Set<TranslatedName> translatedNames) {
        this.names = translatedNames;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FaqSection)) {
            return false;
        }
        return id != null && id.equals(((FaqSection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FaqSection{" +
            "id=" + getId() +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
