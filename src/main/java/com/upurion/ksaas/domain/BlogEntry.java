package com.upurion.ksaas.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.upurion.ksaas.domain.enumeration.Language;

/**
 * A BlogEntry.
 */
@Entity
@Table(name = "blog_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "blog_entry_tag",
               joinColumns = @JoinColumn(name = "blog_entry_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

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

    public BlogEntry language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public BlogEntry title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public BlogEntry content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getDate() {
        return date;
    }

    public BlogEntry date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public BlogEntry tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public BlogEntry addTag(Tag tag) {
        this.tags.add(tag);
        tag.getEntries().add(this);
        return this;
    }

    public BlogEntry removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getEntries().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogEntry)) {
            return false;
        }
        return id != null && id.equals(((BlogEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BlogEntry{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
