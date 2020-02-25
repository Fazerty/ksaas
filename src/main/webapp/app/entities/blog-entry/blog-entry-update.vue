<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ksaasApp.blogEntry.home.createOrEditLabel" v-text="$t('ksaasApp.blogEntry.home.createOrEditLabel')">Create or edit a BlogEntry</h2>
                <div>
                    <div class="form-group" v-if="blogEntry.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="blogEntry.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.blogEntry.language')" for="blog-entry-language">Language</label>
                        <select class="form-control" disabled name="language" :class="{'valid': !$v.blogEntry.language.$invalid, 'invalid': $v.blogEntry.language.$invalid }" v-model="$v.blogEntry.language.$model" id="blog-entry-language" >
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
                        <label class="form-control-label" v-text="$t('ksaasApp.blogEntry.title')" for="blog-entry-title">Title</label>
                        <input type="text" class="form-control" name="title" id="blog-entry-title"
                            :class="{'valid': !$v.blogEntry.title.$invalid, 'invalid': $v.blogEntry.title.$invalid }" v-model="$v.blogEntry.title.$model"  required/>
                        <div v-if="$v.blogEntry.title.$anyDirty && $v.blogEntry.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.blogEntry.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.blogEntry.content')" for="blog-entry-content">Content</label>
                        <textarea class="form-control" name="content" id="blog-entry-content"
                            :class="{'valid': !$v.blogEntry.content.$invalid, 'invalid': $v.blogEntry.content.$invalid }" v-model="$v.blogEntry.content.$model"  required></textarea>
                        <div v-if="$v.blogEntry.content.$anyDirty && $v.blogEntry.content.$invalid">
                            <small class="form-text text-danger" v-if="!$v.blogEntry.content.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
      <div v-html="compiledMarkdown"></div>


                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ksaasApp.blogEntry.date')" for="blog-entry-date">Date</label>
                        <div class="d-flex">
                            <input id="blog-entry-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.blogEntry.date.$invalid, 'invalid': $v.blogEntry.date.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.blogEntry.date.$model)"
                            @change="updateInstantField('date', $event)"/>
                        </div>
                        <div v-if="$v.blogEntry.date.$anyDirty && $v.blogEntry.date.$invalid">
                            <small class="form-text text-danger" v-if="!$v.blogEntry.date.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.blogEntry.date.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('ksaasApp.blogEntry.tag')" for="blog-entry-tag">Tag</label>
                        <select class="form-control" id="blog-entry-tag" multiple name="tag" v-model="blogEntry.tags">
                            <option v-bind:value="getSelected(blogEntry.tags, tagOption)" v-for="tagOption in tags" :key="tagOption.id">{{tagOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.blogEntry.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./blog-entry-update.component.ts">
</script>
