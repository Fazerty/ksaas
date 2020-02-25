package com.upurion.ksaas.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DocumentationEntryGroup.
 */
@Entity
@Table(name = "documentation_entry_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentationEntryGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("documentationEntryGroups")
    private DocumentationSection blog;

    @ManyToOne
    @JsonIgnoreProperties("documentationEntryGroups")
    private FaqEntry entry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentationSection getBlog() {
        return blog;
    }

    public DocumentationEntryGroup blog(DocumentationSection documentationSection) {
        this.blog = documentationSection;
        return this;
    }

    public void setBlog(DocumentationSection documentationSection) {
        this.blog = documentationSection;
    }

    public FaqEntry getEntry() {
        return entry;
    }

    public DocumentationEntryGroup entry(FaqEntry faqEntry) {
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
        if (!(o instanceof DocumentationEntryGroup)) {
            return false;
        }
        return id != null && id.equals(((DocumentationEntryGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentationEntryGroup{" +
            "id=" + getId() +
            "}";
    }
}
