<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.faqEntry.home.createOrEditLabel" v-text="$t('ksaasApp.faqEntry.home.createOrEditLabel')">Create or edit a FaqEntry</h2>
                <div>
                    <div class="form-group" v-if="faqEntry.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="faqEntry.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.faqEntry.language')" for="faq-entry-language">Language</label>
                        <select class="form-control" name="language" :class="{'valid': !$v.faqEntry.language.$invalid, 'invalid': $v.faqEntry.language.$invalid }" v-model="$v.faqEntry.language.$model" id="faq-entry-language" >
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
                        <label class="form-control-label" v-text="$t('ksaasApp.faqEntry.title')" for="faq-entry-title">Title</label>
                        <input type="text" class="form-control" name="title" id="faq-entry-title"
                            :class="{'valid': !$v.faqEntry.title.$invalid, 'invalid': $v.faqEntry.title.$invalid }" v-model="$v.faqEntry.title.$model"  required/>
                        <div v-if="$v.faqEntry.title.$anyDirty && $v.faqEntry.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.faqEntry.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.faqEntry.content')" for="faq-entry-content">Content</label>
                        <textarea class="form-control" name="content" id="faq-entry-content"
                            :class="{'valid': !$v.faqEntry.content.$invalid, 'invalid': $v.faqEntry.content.$invalid }" v-model="$v.faqEntry.content.$model"  required></textarea>
                        <div v-if="$v.faqEntry.content.$anyDirty && $v.faqEntry.content.$invalid">
                            <small class="form-text text-danger" v-if="!$v.faqEntry.content.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.faqEntry.date')" for="faq-entry-date">Date</label>
                        <div class="d-flex">
                            <input id="faq-entry-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.faqEntry.date.$invalid, 'invalid': $v.faqEntry.date.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.faqEntry.date.$model)"
                            @change="updateInstantField('date', $event)"/>
                        </div>
                        <div v-if="$v.faqEntry.date.$anyDirty && $v.faqEntry.date.$invalid">
                            <small class="form-text text-danger" v-if="!$v.faqEntry.date.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.faqEntry.date.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.faqEntry.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./faq-entry-update.component.ts">
</script>
