<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="documentationSection">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.documentationSection.detail.title')">DocumentationSection</span> {{documentationSection.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.documentationSection.info')">Info</span>
                    </dt>
                    <dd>
                        <span>{{documentationSection.info}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.documentationSection.blog')">Blog</span>
                    </dt>
                    <dd>
                        <div v-if="documentationSection.blog">
                            <router-link :to="{name: 'DocumentationChapterView', params: {documentationChapterId: documentationSection.blog.id}}">{{documentationSection.blog.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="documentationSection.id" :to="{name: 'DocumentationSectionEdit', params: {documentationSectionId: documentationSection.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./documentation-section-details.component.ts">
</script>
