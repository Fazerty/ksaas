<i18n>
{
  "en": {
  },
  "fr": {
  }
}
</i18n>
<template>
  <b-container>
    <b-row cols="6" v-for="(employee, index) in employees" :key="index">
      <b-card
        no-body
        style="max-width: 60rem;"
        :img-src="'../../content/images/' + employee.picture"
        img-alt="Image"
        img-left
        img-width="150rem"
        img-height="200rem"
      >
        <template v-slot:header>
          <h4 class="mb-0">{{employee.firstName}} {{employee.lastName}}</h4>
        </template>

        <b-card-body>
          <b-card-title>{{getEmployeeTitle(employee)}}</b-card-title>
          <b-card-text>{{getEmployeeAbout(employee)}}</b-card-text>
        </b-card-body>

        <b-card-footer></b-card-footer>
      </b-card>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./aboutUs.component.ts">
</script>
<style lang="scss" scoped>
</style>
