<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.faqEntryGroup.home.createOrEditLabel" v-text="$t('ksaasApp.faqEntryGroup.home.createOrEditLabel')">Create or edit a FaqEntryGroup</h2>
                <div>
                    <div class="form-group" v-if="faqEntryGroup.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="faqEntryGroup.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ksaasApp.faqEntryGroup.blog')" for="faq-entry-group-blog">Blog</label>
                        <select class="form-control" id="faq-entry-group-blog" name="blog" v-model="faqEntryGroup.blog">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="faqEntryGroup.blog && faqSectionOption.id === faqEntryGroup.blog.id ? faqEntryGroup.blog : faqSectionOption" v-for="faqSectionOption in faqSections" :key="faqSectionOption.id">{{faqSectionOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ksaasApp.faqEntryGroup.entry')" for="faq-entry-group-entry">Entry</label>
                        <select class="form-control" id="faq-entry-group-entry" name="entry" v-model="faqEntryGroup.entry">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="faqEntryGroup.entry && faqEntryOption.id === faqEntryGroup.entry.id ? faqEntryGroup.entry : faqEntryOption" v-for="faqEntryOption in faqEntries" :key="faqEntryOption.id">{{faqEntryOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.faqEntryGroup.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./faq-entry-group-update.component.ts">
</script>
