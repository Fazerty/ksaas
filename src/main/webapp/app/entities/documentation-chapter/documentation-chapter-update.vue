<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.documentationChapter.home.createOrEditLabel" v-text="$t('ksaasApp.documentationChapter.home.createOrEditLabel')">Create or edit a DocumentationChapter</h2>
                <div>
                    <div class="form-group" v-if="documentationChapter.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documentationChapter.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.documentationChapter.info')" for="documentation-chapter-info">Info</label>
                        <input type="text" class="form-control" name="info" id="documentation-chapter-info"
                            :class="{'valid': !$v.documentationChapter.info.$invalid, 'invalid': $v.documentationChapter.info.$invalid }" v-model="$v.documentationChapter.info.$model"  required/>
                        <div v-if="$v.documentationChapter.info.$anyDirty && $v.documentationChapter.info.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documentationChapter.info.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.documentationChapter.info.minLength" v-bind:value="$t('entity.validation.minlength')">
                                This field is required to be at least 2 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documentationChapter.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./documentation-chapter-update.component.ts">
</script>
