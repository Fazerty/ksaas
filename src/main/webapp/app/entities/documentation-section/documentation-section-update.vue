<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.documentationSection.home.createOrEditLabel" v-text="$t('ksaasApp.documentationSection.home.createOrEditLabel')">Create or edit a DocumentationSection</h2>
                <div>
                    <div class="form-group" v-if="documentationSection.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documentationSection.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.documentationSection.info')" for="documentation-section-info">Info</label>
                        <input type="text" class="form-control" name="info" id="documentation-section-info"
                            :class="{'valid': !$v.documentationSection.info.$invalid, 'invalid': $v.documentationSection.info.$invalid }" v-model="$v.documentationSection.info.$model"  required/>
                        <div v-if="$v.documentationSection.info.$anyDirty && $v.documentationSection.info.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documentationSection.info.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.documentationSection.info.minLength" v-bind:value="$t('entity.validation.minlength')">
                                This field is required to be at least 2 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ksaasApp.documentationSection.blog')" for="documentation-section-blog">Blog</label>
                        <select class="form-control" id="documentation-section-blog" name="blog" v-model="documentationSection.blog">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="documentationSection.blog && documentationChapterOption.id === documentationSection.blog.id ? documentationSection.blog : documentationChapterOption" v-for="documentationChapterOption in documentationChapters" :key="documentationChapterOption.id">{{documentationChapterOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documentationSection.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./documentation-section-update.component.ts">
</script>
