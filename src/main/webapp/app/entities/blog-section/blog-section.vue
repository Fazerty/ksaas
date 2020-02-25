<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.blogSection.home.title')" id="blog-section-heading">Blog Sections</span>
            <router-link :to="{name: 'BlogSectionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-blog-section">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.blogSection.home.createLabel')">
                    Create a new Blog Section
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
        <div class="alert alert-warning" v-if="!isFetching && blogSections && blogSections.length === 0">
            <span v-text="$t('ksaasApp.blogSection.home.notFound')">No blogSections found</span>
        </div>
        <div class="table-responsive" v-if="blogSections && blogSections.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ksaasApp.blogSection.info')">Info</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="blogSection in blogSections"
                    :key="blogSection.id">
                    <td>
                        <router-link :to="{name: 'BlogSectionView', params: {blogSectionId: blogSection.id}}">{{blogSection.id}}</router-link>
                    </td>
                    <td>{{blogSection.info}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'BlogSectionView', params: {blogSectionId: blogSection.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'BlogSectionEdit', params: {blogSectionId: blogSection.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(blogSection)"
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
            <span slot="modal-title"><span id="ksaasApp.blogSection.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-blogSection-heading" v-bind:title="$t('ksaasApp.blogSection.delete.question')">Are you sure you want to delete this Blog Section?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-blogSection" v-text="$t('entity.action.delete')" v-on:click="removeBlogSection()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./blog-section.component.ts">
</script>
