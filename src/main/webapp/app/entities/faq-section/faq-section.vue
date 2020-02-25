<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.faqSection.home.title')" id="faq-section-heading">Faq Sections</span>
            <router-link :to="{name: 'FaqSectionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-faq-section">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.faqSection.home.createLabel')">
                    Create a new Faq Section
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && faqSections && faqSections.length === 0">
            <span v-text="$t('ksaasApp.faqSection.home.notFound')">No faqSections found</span>
        </div>
        <div class="table-responsive" v-if="faqSections && faqSections.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ksaasApp.faqSection.info')">Info</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="faqSection in faqSections"
                    :key="faqSection.id">
                    <td>
                        <router-link :to="{name: 'FaqSectionView', params: {faqSectionId: faqSection.id}}">{{faqSection.id}}</router-link>
                    </td>
                    <td>{{faqSection.info}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'FaqSectionView', params: {faqSectionId: faqSection.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'FaqSectionEdit', params: {faqSectionId: faqSection.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(faqSection)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="ksaasApp.faqSection.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-faqSection-heading" v-bind:title="$t('ksaasApp.faqSection.delete.question')">Are you sure you want to delete this Faq Section?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-faqSection" v-text="$t('entity.action.delete')" v-on:click="removeFaqSection()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./faq-section.component.ts">
</script>
