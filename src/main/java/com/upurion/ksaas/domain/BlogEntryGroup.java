package com.upurion.ksaas.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BlogEntryGroup.
 */
@Entity
@Table(name = "blog_entry_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogEntryGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("blogEntryGroups")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("blogEntryGroups")
    private BlogSection blog;

    @ManyToOne
    @JsonIgnoreProperties("blogEntryGroups")
    private BlogEntry entry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public BlogEntryGroup user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BlogSection getBlog() {
        return blog;
    }

    public BlogEntryGroup blog(BlogSection blogSection) {
        this.blog = blogSection;
        return this;
    }

    public void setBlog(BlogSection blogSection) {
        this.blog = blogSection;
    }

    public BlogEntry getEntry() {
        return entry;
    }

    public BlogEntryGroup entry(BlogEntry blogEntry) {
        this.entry = blogEntry;
        return this;
    }

    public void setEntry(BlogEntry blogEntry) {
        this.entry = blogEntry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogEntryGroup)) {
            return false;
        }
        return id != null && id.equals(((BlogEntryGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BlogEntryGroup{" +
            "id=" + getId() +
            "}";
    }
}
