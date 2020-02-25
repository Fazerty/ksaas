<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="faqEntry">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.faqEntry.detail.title')">FaqEntry</span> {{faqEntry.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.faqEntry.language')">Language</span>
                    </dt>
                    <dd>
                        <span v-text="$t('ksaasApp.Language.' + faqEntry.language)">{{faqEntry.language}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.faqEntry.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{faqEntry.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.faqEntry.content')">Content</span>
                    </dt>
                    <dd>
                        <span>{{faqEntry.content}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.faqEntry.date')">Date</span>
                    </dt>
                    <dd>
                        <span v-if="faqEntry.date">{{$d(Date.parse(faqEntry.date), 'long') }}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="faqEntry.id" :to="{name: 'FaqEntryEdit', params: {faqEntryId: faqEntry.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./faq-entry-details.component.ts">
</script>
