<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.blogEntryGroup.home.createOrEditLabel" v-text="$t('ksaasApp.blogEntryGroup.home.createOrEditLabel')">Create or edit a BlogEntryGroup</h2>
                <div>
                    <div class="form-group" v-if="blogEntryGroup.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="blogEntryGroup.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ksaasApp.blogEntryGroup.user')" for="blog-entry-group-user">User</label>
                        <select class="form-control" id="blog-entry-group-user" name="user" v-model="blogEntryGroup.user">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="blogEntryGroup.user && userOption.id === blogEntryGroup.user.id ? blogEntryGroup.user : userOption" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ksaasApp.blogEntryGroup.blog')" for="blog-entry-group-blog">Blog</label>
                        <select class="form-control" id="blog-entry-group-blog" name="blog" v-model="blogEntryGroup.blog">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="blogEntryGroup.blog && blogSectionOption.id === blogEntryGroup.blog.id ? blogEntryGroup.blog : blogSectionOption" v-for="blogSectionOption in blogSections" :key="blogSectionOption.id">{{blogSectionOption.info}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ksaasApp.blogEntryGroup.entry')" for="blog-entry-group-entry">Entry</label>
                        <select class="form-control" id="blog-entry-group-entry" name="entry" v-model="blogEntryGroup.entry">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="blogEntryGroup.entry && blogEntryOption.id === blogEntryGroup.entry.id ? blogEntryGroup.entry : blogEntryOption" v-for="blogEntryOption in blogEntries" :key="blogEntryOption.id">{{blogEntryOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.blogEntryGroup.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./blog-entry-group-update.component.ts">
</script>
