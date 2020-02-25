package com.upurion.ksaas.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FaqEntryGroup.
 */
@Entity
@Table(name = "faq_entry_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FaqEntryGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("faqEntryGroups")
    private FaqSection blog;

    @ManyToOne
    @JsonIgnoreProperties("faqEntryGroups")
    private FaqEntry entry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FaqSection getBlog() {
        return blog;
    }

    public FaqEntryGroup blog(FaqSection faqSection) {
        this.blog = faqSection;
        return this;
    }

    public void setBlog(FaqSection faqSection) {
        this.blog = faqSection;
    }

    public FaqEntry getEntry() {
        return entry;
    }

    public FaqEntryGroup entry(FaqEntry faqEntry) {
        this.entry = faqEntry;
        return this;
    }

    public void setEntry(FaqEntry faqEntry) {
        this.entry = faqEntry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FaqEntryGroup)) {
            return false;
        }
        return id != null && id.equals(((FaqEntryGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FaqEntryGroup{" +
            "id=" + getId() +
            "}";
    }
}
