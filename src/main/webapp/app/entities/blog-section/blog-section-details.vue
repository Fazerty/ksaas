<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="blogSection">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.blogSection.detail.title')">BlogSection</span> {{blogSection.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.blogSection.info')">Info</span>
                    </dt>
                    <dd>
                        <span>{{blogSection.info}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="blogSection.id" :to="{name: 'BlogSectionEdit', params: {blogSectionId: blogSection.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./blog-section-details.component.ts">
</script>
