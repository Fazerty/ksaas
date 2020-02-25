<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="documentationEntryGroup">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.documentationEntryGroup.detail.title')">DocumentationEntryGroup</span> {{documentationEntryGroup.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.documentationEntryGroup.blog')">Blog</span>
                    </dt>
                    <dd>
                        <div v-if="documentationEntryGroup.blog">
                            <router-link :to="{name: 'DocumentationSectionView', params: {documentationSectionId: documentationEntryGroup.blog.id}}">{{documentationEntryGroup.blog.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.documentationEntryGroup.entry')">Entry</span>
                    </dt>
                    <dd>
                        <div v-if="documentationEntryGroup.entry">
                            <router-link :to="{name: 'FaqEntryView', params: {faqEntryId: documentationEntryGroup.entry.id}}">{{documentationEntryGroup.entry.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="documentationEntryGroup.id" :to="{name: 'DocumentationEntryGroupEdit', params: {documentationEntryGroupId: documentationEntryGroup.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./documentation-entry-group-details.component.ts">
</script>
