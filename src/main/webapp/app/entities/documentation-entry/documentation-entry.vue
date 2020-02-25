<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.documentationEntry.home.title')" id="documentation-entry-heading">Documentation Entries</span>
            <router-link :to="{name: 'DocumentationEntryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-documentation-entry">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.documentationEntry.home.createLabel')">
                    Create a new Documentation Entry
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
        <div class="alert alert-warning" v-if="!isFetching && documentationEntries && documentationEntries.length === 0">
            <span v-text="$t('ksaasApp.documentationEntry.home.notFound')">No documentationEntries found</span>
        </div>
        <div class="table-responsive" v-if="documentationEntries && documentationEntries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ksaasApp.documentationEntry.language')">Language</span></th>
                    <th><span v-text="$t('ksaasApp.documentationEntry.title')">Title</span></th>
                    <th><span v-text="$t('ksaasApp.documentationEntry.content')">Content</span></th>
                    <th><span v-text="$t('ksaasApp.documentationEntry.date')">Date</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="documentationEntry in documentationEntries"
                    :key="documentationEntry.id">
                    <td>
                        <router-link :to="{name: 'DocumentationEntryView', params: {documentationEntryId: documentationEntry.id}}">{{documentationEntry.id}}</router-link>
                    </td>
                    <td v-text="$t('ksaasApp.Language.' + documentationEntry.language)">{{documentationEntry.language}}</td>
                    <td>{{documentationEntry.title}}</td>
                    <td>{{documentationEntry.content}}</td>
                    <td v-if="documentationEntry.date"> {{$d(Date.parse(documentationEntry.date), 'short') }}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DocumentationEntryView', params: {documentationEntryId: documentationEntry.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DocumentationEntryEdit', params: {documentationEntryId: documentationEntry.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(documentationEntry)"
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
            <span slot="modal-title"><span id="ksaasApp.documentationEntry.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-documentationEntry-heading" v-bind:title="$t('ksaasApp.documentationEntry.delete.question')">Are you sure you want to delete this Documentation Entry?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-documentationEntry" v-text="$t('entity.action.delete')" v-on:click="removeDocumentationEntry()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./documentation-entry.component.ts">
</script>
