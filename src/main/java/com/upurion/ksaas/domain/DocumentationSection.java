package com.upurion.ksaas.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DocumentationSection.
 */
@Entity
@Table(name = "documentation_section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentationSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "info", nullable = false)
    private String info;

    @OneToMany(mappedBy = "documentationSection")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TranslatedName> names = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("documentationSections")
    private DocumentationChapter blog;

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

    public DocumentationSection info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<TranslatedName> getNames() {
        return names;
    }

    public DocumentationSection names(Set<TranslatedName> translatedNames) {
        this.names = translatedNames;
        return this;
    }

    public DocumentationSection addNames(TranslatedName translatedName) {
        this.names.add(translatedName);
        translatedName.setDocumentationSection(this);
        return this;
    }

    public DocumentationSection removeNames(TranslatedName translatedName) {
        this.names.remove(translatedName);
        translatedName.setDocumentationSection(null);
        return this;
    }

    public void setNames(Set<TranslatedName> translatedNames) {
        this.names = translatedNames;
    }

    public DocumentationChapter getBlog() {
        return blog;
    }

    public DocumentationSection blog(DocumentationChapter documentationChapter) {
        this.blog = documentationChapter;
        return this;
    }

    public void setBlog(DocumentationChapter documentationChapter) {
        this.blog = documentationChapter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentationSection)) {
            return false;
        }
        return id != null && id.equals(((DocumentationSection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentationSection{" +
            "id=" + getId() +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
