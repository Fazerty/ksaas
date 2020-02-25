<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="documentationEntry">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.documentationEntry.detail.title')">DocumentationEntry</span> {{documentationEntry.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.documentationEntry.language')">Language</span>
                    </dt>
                    <dd>
                        <span v-text="$t('ksaasApp.Language.' + documentationEntry.language)">{{documentationEntry.language}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.documentationEntry.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{documentationEntry.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.documentationEntry.content')">Content</span>
                    </dt>
                    <dd>
                        <span>{{documentationEntry.content}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.documentationEntry.date')">Date</span>
                    </dt>
                    <dd>
                        <span v-if="documentationEntry.date">{{$d(Date.parse(documentationEntry.date), 'long') }}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="documentationEntry.id" :to="{name: 'DocumentationEntryEdit', params: {documentationEntryId: documentationEntry.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./documentation-entry-details.component.ts">
</script>
