<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.documentationSection.home.title')" id="documentation-section-heading">Documentation Sections</span>
            <router-link :to="{name: 'DocumentationSectionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-documentation-section">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.documentationSection.home.createLabel')">
                    Create a new Documentation Section
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
        <div class="alert alert-warning" v-if="!isFetching && documentationSections && documentationSections.length === 0">
            <span v-text="$t('ksaasApp.documentationSection.home.notFound')">No documentationSections found</span>
        </div>
        <div class="table-responsive" v-if="documentationSections && documentationSections.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ksaasApp.documentationSection.info')">Info</span></th>
                    <th><span v-text="$t('ksaasApp.documentationSection.blog')">Blog</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="documentationSection in documentationSections"
                    :key="documentationSection.id">
                    <td>
                        <router-link :to="{name: 'DocumentationSectionView', params: {documentationSectionId: documentationSection.id}}">{{documentationSection.id}}</router-link>
                    </td>
                    <td>{{documentationSection.info}}</td>
                    <td>
                        <div v-if="documentationSection.blog">
                            <router-link :to="{name: 'DocumentationChapterView', params: {documentationChapterId: documentationSection.blog.id}}">{{documentationSection.blog.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DocumentationSectionView', params: {documentationSectionId: documentationSection.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DocumentationSectionEdit', params: {documentationSectionId: documentationSection.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(documentationSection)"
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
            <span slot="modal-title"><span id="ksaasApp.documentationSection.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-documentationSection-heading" v-bind:title="$t('ksaasApp.documentationSection.delete.question')">Are you sure you want to delete this Documentation Section?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-documentationSection" v-text="$t('entity.action.delete')" v-on:click="removeDocumentationSection()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./documentation-section.component.ts">
</script>
