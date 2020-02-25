<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ksaasApp.faqEntry.home.title')" id="faq-entry-heading">Faq Entries</span>
            <router-link :to="{name: 'FaqEntryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-faq-entry">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ksaasApp.faqEntry.home.createLabel')">
                    Create a new Faq Entry
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
        <div class="alert alert-warning" v-if="!isFetching && faqEntries && faqEntries.length === 0">
            <span v-text="$t('ksaasApp.faqEntry.home.notFound')">No faqEntries found</span>
        </div>
        <div class="table-responsive" v-if="faqEntries && faqEntries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('ksaasApp.faqEntry.language')">Language</span></th>
                    <th><span v-text="$t('ksaasApp.faqEntry.title')">Title</span></th>
                    <th><span v-text="$t('ksaasApp.faqEntry.content')">Content</span></th>
                    <th><span v-text="$t('ksaasApp.faqEntry.date')">Date</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="faqEntry in faqEntries"
                    :key="faqEntry.id">
                    <td>
                        <router-link :to="{name: 'FaqEntryView', params: {faqEntryId: faqEntry.id}}">{{faqEntry.id}}</router-link>
                    </td>
                    <td v-text="$t('ksaasApp.Language.' + faqEntry.language)">{{faqEntry.language}}</td>
                    <td>{{faqEntry.title}}</td>
                    <td>{{faqEntry.content}}</td>
                    <td v-if="faqEntry.date"> {{$d(Date.parse(faqEntry.date), 'short') }}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'FaqEntryView', params: {faqEntryId: faqEntry.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'FaqEntryEdit', params: {faqEntryId: faqEntry.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(faqEntry)"
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
            <span slot="modal-title"><span id="ksaasApp.faqEntry.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-faqEntry-heading" v-bind:title="$t('ksaasApp.faqEntry.delete.question')">Are you sure you want to delete this Faq Entry?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-faqEntry" v-text="$t('entity.action.delete')" v-on:click="removeFaqEntry()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./faq-entry.component.ts">
</script>
