<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.blogEntryGroup.home.title')" id="blog-entry-group-heading">Blog Entry Groups</span>
            <router-link :to="{name: 'BlogEntryGroupCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-blog-entry-group">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.blogEntryGroup.home.createLabel')">
                    Create a new Blog Entry Group
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
        <div class="alert alert-warning" v-if="!isFetching && blogEntryGroups && blogEntryGroups.length === 0">
            <span v-text="$t('ksaasApp.blogEntryGroup.home.notFound')">No blogEntryGroups found</span>
        </div>
        <div class="table-responsive" v-if="blogEntryGroups && blogEntryGroups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ksaasApp.blogEntryGroup.user')">User</span></th>
                    <th><span v-text="$t('ksaasApp.blogEntryGroup.blog')">Blog</span></th>
                    <th><span v-text="$t('ksaasApp.blogEntryGroup.entry')">Entry</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="blogEntryGroup in blogEntryGroups"
                    :key="blogEntryGroup.id">
                    <td>
                        <router-link :to="{name: 'BlogEntryGroupView', params: {blogEntryGroupId: blogEntryGroup.id}}">{{blogEntryGroup.id}}</router-link>
                    </td>
                    <td>
                        {{blogEntryGroup.user ? blogEntryGroup.user.login : ''}}
                    </td>
                    <td>
                        <div v-if="blogEntryGroup.blog">
                            <router-link :to="{name: 'BlogSectionView', params: {blogSectionId: blogEntryGroup.blog.id}}">{{blogEntryGroup.blog.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="blogEntryGroup.entry">
                            <router-link :to="{name: 'BlogEntryView', params: {blogEntryId: blogEntryGroup.entry.id}}">{{blogEntryGroup.entry.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'BlogEntryGroupView', params: {blogEntryGroupId: blogEntryGroup.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'BlogEntryGroupEdit', params: {blogEntryGroupId: blogEntryGroup.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(blogEntryGroup)"
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
            <span slot="modal-title"><span id="ksaasApp.blogEntryGroup.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-blogEntryGroup-heading" v-bind:title="$t('ksaasApp.blogEntryGroup.delete.question')">Are you sure you want to delete this Blog Entry Group?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-blogEntryGroup" v-text="$t('entity.action.delete')" v-on:click="removeBlogEntryGroup()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./blog-entry-group.component.ts">
</script>
