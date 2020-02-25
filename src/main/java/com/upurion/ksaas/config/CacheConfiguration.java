package com.upurion.ksaas.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.upurion.ksaas.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.upurion.ksaas.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.upurion.ksaas.domain.User.class.getName());
            createCache(cm, com.upurion.ksaas.domain.Authority.class.getName());
            createCache(cm, com.upurion.ksaas.domain.User.class.getName() + ".authorities");
            createCache(cm, com.upurion.ksaas.domain.TranslatedName.class.getName());
            createCache(cm, com.upurion.ksaas.domain.BlogSection.class.getName());
            createCache(cm, com.upurion.ksaas.domain.BlogSection.class.getName() + ".names");
            createCache(cm, com.upurion.ksaas.domain.BlogEntryGroup.class.getName());
            createCache(cm, com.upurion.ksaas.domain.BlogEntry.class.getName());
            createCache(cm, com.upurion.ksaas.domain.BlogEntry.class.getName() + ".tags");
            createCache(cm, com.upurion.ksaas.domain.Tag.class.getName());
            createCache(cm, com.upurion.ksaas.domain.Tag.class.getName() + ".names");
            createCache(cm, com.upurion.ksaas.domain.Tag.class.getName() + ".entries");
            createCache(cm, com.upurion.ksaas.domain.FaqSection.class.getName());
            createCache(cm, com.upurion.ksaas.domain.FaqSection.class.getName() + ".names");
            createCache(cm, com.upurion.ksaas.domain.FaqEntryGroup.class.getName());
            createCache(cm, com.upurion.ksaas.domain.FaqEntry.class.getName());
            createCache(cm, com.upurion.ksaas.domain.DocumentationChapter.class.getName());
            createCache(cm, com.upurion.ksaas.domain.DocumentationChapter.class.getName() + ".names");
            createCache(cm, com.upurion.ksaas.domain.DocumentationSection.class.getName());
            createCache(cm, com.upurion.ksaas.domain.DocumentationSection.class.getName() + ".names");
            createCache(cm, com.upurion.ksaas.domain.DocumentationEntryGroup.class.getName());
            createCache(cm, com.upurion.ksaas.domain.DocumentationEntry.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
