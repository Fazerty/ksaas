<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="blogEntry">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.blogEntry.detail.title')">BlogEntry</span> {{blogEntry.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.blogEntry.language')">Language</span>
                    </dt>
                    <dd>
                        <span v-text="$t('ksaasApp.Language.' + blogEntry.language)">{{blogEntry.language}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.blogEntry.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{blogEntry.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.blogEntry.content')">Content</span>
                    </dt>
                    <dd>
                        <span>{{blogEntry.content}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.blogEntry.date')">Date</span>
                    </dt>
                    <dd>
                        <span v-if="blogEntry.date">{{$d(Date.parse(blogEntry.date), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.blogEntry.tag')">Tag</span>
                    </dt>
                    <dd>
                        <span v-for="(tag, i) in blogEntry.tags" :key="tag.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'TagView', params: {tagId: tag.id}}">{{tag.id}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="blogEntry.id" :to="{name: 'BlogEntryEdit', params: {blogEntryId: blogEntry.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./blog-entry-details.component.ts">
</script>
