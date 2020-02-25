<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="faqSection">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.faqSection.detail.title')">FaqSection</span> {{faqSection.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.faqSection.info')">Info</span>
                    </dt>
                    <dd>
                        <span>{{faqSection.info}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="faqSection.id" :to="{name: 'FaqSectionEdit', params: {faqSectionId: faqSection.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./faq-section-details.component.ts">
</script>
