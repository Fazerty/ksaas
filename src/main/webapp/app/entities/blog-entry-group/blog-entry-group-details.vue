<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="blogEntryGroup">
                <h2 class="jh-entity-heading"><span v-text="$t('ksaasApp.blogEntryGroup.detail.title')">BlogEntryGroup</span> {{blogEntryGroup.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ksaasApp.blogEntryGroup.user')">User</span>
                    </dt>
                    <dd>
                        {{blogEntryGroup.user ? blogEntryGroup.user.login : ''}}
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.blogEntryGroup.blog')">Blog</span>
                    </dt>
                    <dd>
                        <div v-if="blogEntryGroup.blog">
                            <router-link :to="{name: 'BlogSectionView', params: {blogSectionId: blogEntryGroup.blog.id}}">{{blogEntryGroup.blog.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ksaasApp.blogEntryGroup.entry')">Entry</span>
                    </dt>
                    <dd>
                        <div v-if="blogEntryGroup.entry">
                            <router-link :to="{name: 'BlogEntryView', params: {blogEntryId: blogEntryGroup.entry.id}}">{{blogEntryGroup.entry.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="blogEntryGroup.id" :to="{name: 'BlogEntryGroupEdit', params: {blogEntryGroupId: blogEntryGroup.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./blog-entry-group-details.component.ts">
</script>
