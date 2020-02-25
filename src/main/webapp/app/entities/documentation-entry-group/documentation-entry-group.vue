<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.documentationEntryGroup.home.title')" id="documentation-entry-group-heading">Documentation Entry Groups</span>
            <router-link :to="{name: 'DocumentationEntryGroupCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-documentation-entry-group">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.documentationEntryGroup.home.createLabel')">
                    Create a new Documentation Entry Group
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
        <div class="alert alert-warning" v-if="!isFetching && documentationEntryGroups && documentationEntryGroups.length === 0">
            <span v-text="$t('ksaasApp.documentationEntryGroup.home.notFound')">No documentationEntryGroups found</span>
        </div>
        <div class="table-responsive" v-if="documentationEntryGroups && documentationEntryGroups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ksaasApp.documentationEntryGroup.blog')">Blog</span></th>
                    <th><span v-text="$t('ksaasApp.documentationEntryGroup.entry')">Entry</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="documentationEntryGroup in documentationEntryGroups"
                    :key="documentationEntryGroup.id">
                    <td>
                        <router-link :to="{name: 'DocumentationEntryGroupView', params: {documentationEntryGroupId: documentationEntryGroup.id}}">{{documentationEntryGroup.id}}</router-link>
                    </td>
                    <td>
                        <div v-if="documentationEntryGroup.blog">
                            <router-link :to="{name: 'DocumentationSectionView', params: {documentationSectionId: documentationEntryGroup.blog.id}}">{{documentationEntryGroup.blog.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="documentationEntryGroup.entry">
                            <router-link :to="{name: 'FaqEntryView', params: {faqEntryId: documentationEntryGroup.entry.id}}">{{documentationEntryGroup.entry.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DocumentationEntryGroupView', params: {documentationEntryGroupId: documentationEntryGroup.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DocumentationEntryGroupEdit', params: {documentationEntryGroupId: documentationEntryGroup.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(documentationEntryGroup)"
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
            <span slot="modal-title"><span id="ksaasApp.documentationEntryGroup.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-documentationEntryGroup-heading" v-bind:title="$t('ksaasApp.documentationEntryGroup.delete.question')">Are you sure you want to delete this Documentation Entry Group?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-documentationEntryGroup" v-text="$t('entity.action.delete')" v-on:click="removeDocumentationEntryGroup()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./documentation-entry-group.component.ts">
</script>
