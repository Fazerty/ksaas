<template>
  <div>
    <br />
    <div
      class="alert alert-warning"
      v-if="!isFetching && blogSections && blogSections.length === 0"
    >
      <span v-text="$t('ksaasApp.blogSection.home.notFound')">No blogSections found</span>
    </div>
    <div class="table-responsive" v-if="blogSections && blogSections.length > 0">
      <table class="table table-striped">
        <thead></thead>
        <tbody>
          <tr v-for="blogSection in blogSections" :key="blogSection.id">
            <td>
              <router-link
                :to="{name: 'BlogSectionView', params: {blogSectionId: blogSection.id}}"
              >{{getBlocSectionName(blogSection) ? getBlocSectionName(blogSection).name : '??' }}</router-link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts" src="./blog-section.component.ts">
</script>
