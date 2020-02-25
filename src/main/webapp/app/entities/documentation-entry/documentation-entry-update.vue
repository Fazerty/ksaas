<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.documentationEntry.home.createOrEditLabel" v-text="$t('ksaasApp.documentationEntry.home.createOrEditLabel')">Create or edit a DocumentationEntry</h2>
                <div>
                    <div class="form-group" v-if="documentationEntry.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documentationEntry.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.documentationEntry.language')" for="documentation-entry-language">Language</label>
                        <select class="form-control" name="language" :class="{'valid': !$v.documentationEntry.language.$invalid, 'invalid': $v.documentationEntry.language.$invalid }" v-model="$v.documentationEntry.language.$model" id="documentation-entry-language" >
                            <option value="FR" v-bind:label="$t('ksaasApp.Language.FR')">FR</option>
                            <option value="EN" v-bind:label="$t('ksaasApp.Language.EN')">EN</option>
                            <option value="ES" v-bind:label="$t('ksaasApp.Language.ES')">ES</option>
                            <option value="IT" v-bind:label="$t('ksaasApp.Language.IT')">IT</option>
                            <option value="RO" v-bind:label="$t('ksaasApp.Language.RO')">RO</option>
                            <option value="DU" v-bind:label="$t('ksaasApp.Language.DU')">DU</option>
                            <option value="NL" v-bind:label="$t('ksaasApp.Language.NL')">NL</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.documentationEntry.title')" for="documentation-entry-title">Title</label>
                        <input type="text" class="form-control" name="title" id="documentation-entry-title"
                            :class="{'valid': !$v.documentationEntry.title.$invalid, 'invalid': $v.documentationEntry.title.$invalid }" v-model="$v.documentationEntry.title.$model"  required/>
                        <div v-if="$v.documentationEntry.title.$anyDirty && $v.documentationEntry.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documentationEntry.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.documentationEntry.content')" for="documentation-entry-content">Content</label>
                        <textarea class="form-control" name="content" id="documentation-entry-content"
                            :class="{'valid': !$v.documentationEntry.content.$invalid, 'invalid': $v.documentationEntry.content.$invalid }" v-model="$v.documentationEntry.content.$model"  required></textarea>
                        <div v-if="$v.documentationEntry.content.$anyDirty && $v.documentationEntry.content.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documentationEntry.content.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.documentationEntry.date')" for="documentation-entry-date">Date</label>
                        <div class="d-flex">
                            <input id="documentation-entry-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.documentationEntry.date.$invalid, 'invalid': $v.documentationEntry.date.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.documentationEntry.date.$model)"
                            @change="updateInstantField('date', $event)"/>
                        </div>
                        <div v-if="$v.documentationEntry.date.$anyDirty && $v.documentationEntry.date.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documentationEntry.date.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.documentationEntry.date.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documentationEntry.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./documentation-entry-update.component.ts">
</script>
