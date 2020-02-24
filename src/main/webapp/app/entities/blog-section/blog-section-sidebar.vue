<template>
    <div>
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
                </thead>
                <tbody>
                <tr v-for="blogSection in blogSections"
                    :key="blogSection.id">
                    <td><router-link :to="{name: 'BlogSectionView', params: {blogSectionId: blogSection.id}}">{{blogSection.info}}</router-link></td>
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
