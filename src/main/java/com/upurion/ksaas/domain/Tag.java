package com.upurion.ksaas.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "tag")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TranslatedName> names = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<BlogEntry> entries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TranslatedName> getNames() {
        return names;
    }

    public Tag names(Set<TranslatedName> translatedNames) {
        this.names = translatedNames;
        return this;
    }

    public Tag addNames(TranslatedName translatedName) {
        this.names.add(translatedName);
        translatedName.setTag(this);
        return this;
    }

    public Tag removeNames(TranslatedName translatedName) {
        this.names.remove(translatedName);
        translatedName.setTag(null);
        return this;
    }

    public void setNames(Set<TranslatedName> translatedNames) {
        this.names = translatedNames;
    }

    public Set<BlogEntry> getEntries() {
        return entries;
    }

    public Tag entries(Set<BlogEntry> blogEntries) {
        this.entries = blogEntries;
        return this;
    }

    public Tag addEntry(BlogEntry blogEntry) {
        this.entries.add(blogEntry);
        blogEntry.getTags().add(this);
        return this;
    }

    public Tag removeEntry(BlogEntry blogEntry) {
        this.entries.remove(blogEntry);
        blogEntry.getTags().remove(this);
        return this;
    }

    public void setEntries(Set<BlogEntry> blogEntries) {
        this.entries = blogEntries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return id != null && id.equals(((Tag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            "}";
    }
}
