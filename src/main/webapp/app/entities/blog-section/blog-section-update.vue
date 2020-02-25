<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ksaasApp.blogSection.home.createOrEditLabel"
          v-text="$t('ksaasApp.blogSection.home.createOrEditLabel')"
        >Create or edit a BlogSection</h2>
        <div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('ksaasApp.blogSection.info')"
              for="blog-section-info"
            >Info</label>
            <input
              type="text"
              class="form-control"
              name="info"
              id="blog-section-info"
              :class="{'valid': !$v.blogSection.info.$invalid, 'invalid': $v.blogSection.info.$invalid }"
              v-model="$v.blogSection.info.$model"
              required
            />
            <div v-if="$v.blogSection.info.$anyDirty && $v.blogSection.info.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.blogSection.info.required"
                v-text="$t('entity.validation.required')"
              >This field is required.</small>
              <small
                class="form-text text-danger"
                v-if="!$v.blogSection.info.minLength"
                v-bind:value="$t('entity.validation.minlength')"
              >This field is required to be at least 2 characters.</small>
            </div>
          </div>

          <h4 v-text="$t('ksaasApp.blogSection.names')">Names</h4>
          <b-container>
            <b-row v-for="(translatedName, index) in $v.blogSection.names.$each.$iter" :key="index">
              <b-col>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('ksaasApp.translatedName.language')"
                    for="translated-name-language"
                  >Language</label>
                  <select
                    class="form-control"
                    name="language"
                    :class="{'valid': !translatedName.language.$invalid, 'invalid': translatedName.language.$invalid }"
                    v-model="translatedName.language.$model"
                    id="translated-name-language"
                  >
                    <option value="FR" v-bind:label="$t('ksaasApp.Language.FR')">FR</option>
                    <option value="EN" v-bind:label="$t('ksaasApp.Language.EN')">EN</option>
                    <option value="ES" v-bind:label="$t('ksaasApp.Language.ES')">ES</option>
                    <option value="IT" v-bind:label="$t('ksaasApp.Language.IT')">IT</option>
                    <option value="RO" v-bind:label="$t('ksaasApp.Language.RO')">RO</option>
                    <option value="DU" v-bind:label="$t('ksaasApp.Language.DU')">DU</option>
                    <option value="NL" v-bind:label="$t('ksaasApp.Language.NL')">NL</option>
                  </select>
                </div>
              </b-col>

              <b-col>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('ksaasApp.translatedName.name')"
                    for="translated-name-name"
                  >Name</label>
                  <input
                    type="text"
                    class="form-control"
                    name="name"
                    id="translated-name-name"
                    :class="{'valid': !translatedName.name.$invalid, 'invalid': translatedName.name.$invalid }"
                    v-model="translatedName.name.$model"
                    required
                  />
                  <div v-if="translatedName.name.$anyDirty && translatedName.name.$invalid">
                    <small
                      class="form-text text-danger"
                      v-if="!translatedName.name.required"
                      v-text="$t('entity.validation.required')"
                    >This field is required.</small>
                    <small
                      class="form-text text-danger"
                      v-if="!translatedName.name.minLength"
                      v-bind:value="$t('entity.validation.minlength')"
                    >This field is required to be at least 3 characters.</small>
                  </div>
                </div>
              </b-col>

              <b-col>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('ksaasApp.translatedName.slug')"
                    for="translated-name-slug"
                  >Slug</label>
                  <input
                    type="text"
                    class="form-control"
                    name="slug"
                    id="translated-name-slug"
                    :class="{'valid': !translatedName.slug.$invalid, 'invalid': translatedName.slug.$invalid }"
                    v-model="translatedName.slug.$model"
                    required
                  />
                  <div v-if="translatedName.slug.$anyDirty && translatedName.slug.$invalid">
                    <small
                      class="form-text text-danger"
                      v-if="!translatedName.slug.required"
                      v-text="$t('entity.validation.required')"
                    >This field is required.</small>
                    <small
                      class="form-text text-danger"
                      v-if="!translatedName.slug.minLength"
                      v-bind:value="$t('entity.validation.minlength')"
                    >This field is required to be at least 3 characters.</small>
                  </div>
                </div>
              </b-col>
            </b-row>
          </b-container>
        </div>
        <div>
          <button
            type="button"
            id="cancel-save"
            class="btn btn-secondary"
            v-on:click="previousState()"
          >
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;
            <span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            :disabled="$v.blogSection.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;
            <span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./blog-section-update.component.ts">
</script>
