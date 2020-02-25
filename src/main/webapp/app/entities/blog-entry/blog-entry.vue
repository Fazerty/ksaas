<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.blogEntry.home.title')" id="blog-entry-heading">Blog Entries</span>
            <router-link :to="{name: 'BlogEntryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-blog-entry">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.blogEntry.home.createLabel')">
                    Create a new Blog Entry
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
        <div class="alert alert-warning" v-if="!isFetching && blogEntries && blogEntries.length === 0">
            <span v-text="$t('ksaasApp.blogEntry.home.notFound')">No blogEntries found</span>
        </div>
        <div class="table-responsive" v-if="blogEntries && blogEntries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('language')"><span v-text="$t('ksaasApp.blogEntry.language')">Language</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('ksaasApp.blogEntry.title')">Title</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('content')"><span v-text="$t('ksaasApp.blogEntry.content')">Content</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('date')"><span v-text="$t('ksaasApp.blogEntry.date')">Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="blogEntry in blogEntries"
                    :key="blogEntry.id">
                    <td>
                        <router-link :to="{name: 'BlogEntryView', params: {blogEntryId: blogEntry.id}}">{{blogEntry.id}}</router-link>
                    </td>
                    <td v-text="$t('ksaasApp.Language.' + blogEntry.language)">{{blogEntry.language}}</td>
                    <td>{{blogEntry.title}}</td>
                    <td>{{blogEntry.content}}</td>
                    <td v-if="blogEntry.date"> {{$d(Date.parse(blogEntry.date), 'short') }}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'BlogEntryView', params: {blogEntryId: blogEntry.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'BlogEntryEdit', params: {blogEntryId: blogEntry.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(blogEntry)"
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
            <span slot="modal-title"><span id="ksaasApp.blogEntry.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-blogEntry-heading" v-bind:title="$t('ksaasApp.blogEntry.delete.question')">Are you sure you want to delete this Blog Entry?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-blogEntry" v-text="$t('entity.action.delete')" v-on:click="removeBlogEntry()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./blog-entry.component.ts">
</script>
