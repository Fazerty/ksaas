<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.faqSection.home.createOrEditLabel" v-text="$t('ksaasApp.faqSection.home.createOrEditLabel')">Create or edit a FaqSection</h2>
                <div>
                    <div class="form-group" v-if="faqSection.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="faqSection.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.faqSection.info')" for="faq-section-info">Info</label>
                        <input type="text" class="form-control" name="info" id="faq-section-info"
                            :class="{'valid': !$v.faqSection.info.$invalid, 'invalid': $v.faqSection.info.$invalid }" v-model="$v.faqSection.info.$model"  required/>
                        <div v-if="$v.faqSection.info.$anyDirty && $v.faqSection.info.$invalid">
                            <small class="form-text text-danger" v-if="!$v.faqSection.info.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.faqSection.info.minLength" v-bind:value="$t('entity.validation.minlength')">
                                This field is required to be at least 2 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.faqSection.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./faq-section-update.component.ts">
</script>
