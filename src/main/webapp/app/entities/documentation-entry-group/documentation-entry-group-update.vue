<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.documentationEntryGroup.home.createOrEditLabel" v-text="$t('ksaasApp.documentationEntryGroup.home.createOrEditLabel')">Create or edit a DocumentationEntryGroup</h2>
                <div>
                    <div class="form-group" v-if="documentationEntryGroup.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documentationEntryGroup.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ksaasApp.documentationEntryGroup.blog')" for="documentation-entry-group-blog">Blog</label>
                        <select class="form-control" id="documentation-entry-group-blog" name="blog" v-model="documentationEntryGroup.blog">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="documentationEntryGroup.blog && documentationSectionOption.id === documentationEntryGroup.blog.id ? documentationEntryGroup.blog : documentationSectionOption" v-for="documentationSectionOption in documentationSections" :key="documentationSectionOption.id">{{documentationSectionOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ksaasApp.documentationEntryGroup.entry')" for="documentation-entry-group-entry">Entry</label>
                        <select class="form-control" id="documentation-entry-group-entry" name="entry" v-model="documentationEntryGroup.entry">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="documentationEntryGroup.entry && faqEntryOption.id === documentationEntryGroup.entry.id ? documentationEntryGroup.entry : faqEntryOption" v-for="faqEntryOption in faqEntries" :key="faqEntryOption.id">{{faqEntryOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documentationEntryGroup.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./documentation-entry-group-update.component.ts">
</script>
