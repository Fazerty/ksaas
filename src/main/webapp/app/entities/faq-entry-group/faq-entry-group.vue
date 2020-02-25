<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.faqEntryGroup.home.title')" id="faq-entry-group-heading">Faq Entry Groups</span>
            <router-link :to="{name: 'FaqEntryGroupCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-faq-entry-group">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.faqEntryGroup.home.createLabel')">
                    Create a new Faq Entry Group
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
        <div class="alert alert-warning" v-if="!isFetching && faqEntryGroups && faqEntryGroups.length === 0">
            <span v-text="$t('ksaasApp.faqEntryGroup.home.notFound')">No faqEntryGroups found</span>
        </div>
        <div class="table-responsive" v-if="faqEntryGroups && faqEntryGroups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ksaasApp.faqEntryGroup.blog')">Blog</span></th>
                    <th><span v-text="$t('ksaasApp.faqEntryGroup.entry')">Entry</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="faqEntryGroup in faqEntryGroups"
                    :key="faqEntryGroup.id">
                    <td>
                        <router-link :to="{name: 'FaqEntryGroupView', params: {faqEntryGroupId: faqEntryGroup.id}}">{{faqEntryGroup.id}}</router-link>
                    </td>
                    <td>
                        <div v-if="faqEntryGroup.blog">
                            <router-link :to="{name: 'FaqSectionView', params: {faqSectionId: faqEntryGroup.blog.id}}">{{faqEntryGroup.blog.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="faqEntryGroup.entry">
                            <router-link :to="{name: 'FaqEntryView', params: {faqEntryId: faqEntryGroup.entry.id}}">{{faqEntryGroup.entry.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'FaqEntryGroupView', params: {faqEntryGroupId: faqEntryGroup.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'FaqEntryGroupEdit', params: {faqEntryGroupId: faqEntryGroup.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(faqEntryGroup)"
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
            <span slot="modal-title"><span id="ksaasApp.faqEntryGroup.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-faqEntryGroup-heading" v-bind:title="$t('ksaasApp.faqEntryGroup.delete.question')">Are you sure you want to delete this Faq Entry Group?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-faqEntryGroup" v-text="$t('entity.action.delete')" v-on:click="removeFaqEntryGroup()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./faq-entry-group.component.ts">
</script>
