package com.upurion.ksaas.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.upurion.ksaas.domain.enumeration.Language;

/**
 * A TranslatedName.
 */
@Entity
@Table(name = "translated_name")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TranslatedName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 3)
    @Column(name = "slug", nullable = false, unique=true)
    private String slug;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private Tag tag;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private BlogSection blogSection;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private FaqSection faqSection;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private DocumentationChapter documentationChapter;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private DocumentationSection documentationSection;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public TranslatedName language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public TranslatedName name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public TranslatedName slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Tag getTag() {
        return tag;
    }

    public TranslatedName tag(Tag tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public BlogSection getBlogSection() {
        return blogSection;
    }

    public TranslatedName blogSection(BlogSection blogSection) {
        this.blogSection = blogSection;
        return this;
    }

    public void setBlogSection(BlogSection blogSection) {
        this.blogSection = blogSection;
    }

    public FaqSection getFaqSection() {
        return faqSection;
    }

    public TranslatedName faqSection(FaqSection faqSection) {
        this.faqSection = faqSection;
        return this;
    }

    public void setFaqSection(FaqSection faqSection) {
        this.faqSection = faqSection;
    }

    public DocumentationChapter getDocumentationChapter() {
        return documentationChapter;
    }

    public TranslatedName documentationChapter(DocumentationChapter documentationChapter) {
        this.documentationChapter = documentationChapter;
        return this;
    }

    public void setDocumentationChapter(DocumentationChapter documentationChapter) {
        this.documentationChapter = documentationChapter;
    }

    public DocumentationSection getDocumentationSection() {
        return documentationSection;
    }

    public TranslatedName documentationSection(DocumentationSection documentationSection) {
        this.documentationSection = documentationSection;
        return this;
    }

    public void setDocumentationSection(DocumentationSection documentationSection) {
        this.documentationSection = documentationSection;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranslatedName)) {
            return false;
        }
        return id != null && id.equals(((TranslatedName) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TranslatedName{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", name='" + getName() + "'" +
            ", slug='" + getSlug() + "'" +
            "}";
    }
}
