<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="faqEntryGroup">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.faqEntryGroup.detail.title')">FaqEntryGroup</span> {{faqEntryGroup.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.faqEntryGroup.blog')">Blog</span>
                    </dt>
                    <dd>
                        <div v-if="faqEntryGroup.blog">
                            <router-link :to="{name: 'FaqSectionView', params: {faqSectionId: faqEntryGroup.blog.id}}">{{faqEntryGroup.blog.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.faqEntryGroup.entry')">Entry</span>
                    </dt>
                    <dd>
                        <div v-if="faqEntryGroup.entry">
                            <router-link :to="{name: 'FaqEntryView', params: {faqEntryId: faqEntryGroup.entry.id}}">{{faqEntryGroup.entry.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="faqEntryGroup.id" :to="{name: 'FaqEntryGroupEdit', params: {faqEntryGroupId: faqEntryGroup.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./faq-entry-group-details.component.ts">
</script>
