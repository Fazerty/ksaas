<i18n>
{
  "en": {
    "pricing": {"month": "month",
                "free": "Free",
                "choose": "choose",
                "plans": "Plans",
                "planDetails": "Plan Details"}
  },
  "fr": {
    "pricing": {"month": "mois",
                "free": "Gratuit",
                "choose": "Choisir",
                "plans": "Offres",
                "planDetails": "Détails"}
  }
}
</i18n>
<template>
  <b-container>
    <!-- Display plan prices -->
    <div class="text-success mb-2">
      <h3>{{$t('pricing.plans')}}</h3>
    </div>
    <b-card-group deck>
      <b-card
        header-tag="header"
        footer-tag="footer"
        header-bg-variant="info"
        header-text-variant="white"
        v-for="(plan, index) in plans"
        :key="index"
        :plan="plan"
        :data-index="index"
      >
        <template v-slot:header>
          <h3 class="mb-0 text-center">{{plan.name}}</h3>
        </template>
        <b-card-text v-if="plan.price">
          <h5>
            {{plan.price}}
            <span v-if="plan.monthly">/{{$t('month')}}</span>
          </h5>
        </b-card-text>
        <b-card-text v-else>
          <h5>{{$t('pricing.free')}}</h5>
        </b-card-text>
        <b-card-text>
          <ul>
            <li class="plan__feature" v-for="feature in plan.features" v-bind:key="feature">
              <span></span>
              {{ feature }}
            </li>
          </ul>
        </b-card-text>

        <template v-slot:footer>
          <div style="text-align:center;">
            <b-button href="#" variant="primary" :disabled="disabled">{{$t('choose')}}</b-button>
          </div>
        </template>
      </b-card>
    </b-card-group>

    <!-- Plan Details -->
    <div class="mt-5 mb-5">
      <hr />
    </div>
    <div class="text-success mt-5 mb-2">
      <h3>{{$t('pricing.planDetails')}}</h3>
    </div>
    <b-table
      :striped="striped"
      :bordered="bordered"
      :borderless="borderless"
      :outlined="outlined"
      :small="small"
      :hover="hover"
      :dark="dark"
      :fixed="fixed"
      :foot-clone="footClone"
      :no-border-collapse="noCollapse"
      :items="items"
      :fields="fields"
      :head-variant="headVariant"
      :table-variant="tableVariant"
    >
      <template v-slot:cell(desktop)="data">
        <span v-if="data.value  === true">
          <font-awesome-icon icon="check" :style="{ color: 'green' }"></font-awesome-icon>
        </span>
        <span v-else>
          <span v-if="data.value  === false">
            <font-awesome-icon icon="times" :style="{ color: 'gray' }"></font-awesome-icon>
          </span>
          <span v-else>{{data.value}}</span>
        </span>
      </template>

      <template v-slot:cell(standard)="data">
        <span v-if="data.value  === true">
          <font-awesome-icon icon="check" :style="{ color: 'green' }"></font-awesome-icon>
        </span>
        <span v-else>
          <span v-if="data.value  === false">
            <font-awesome-icon icon="times" :style="{ color: 'gray' }"></font-awesome-icon>
          </span>
          <span v-else>{{data.value}}</span>
        </span>
      </template>

      <template v-slot:cell(enterprise)="data">
        <span v-if="data.value  === true">
          <font-awesome-icon icon="check" :style="{ color: 'green' }"></font-awesome-icon>
        </span>
        <span v-else>
          <span v-if="data.value  === false">
            <font-awesome-icon icon="times" :style="{ color: 'gray' }"></font-awesome-icon>
          </span>
          <span v-else>{{data.value}}</span>
        </span>
      </template>
    </b-table>
  </b-container>
</template>

<script lang="ts" src="./pricing.component.ts">
</script>
<style lang="scss" scoped>
ul {
  list-style: none;
  margin-right: -5px;
}
li {
  height: 30px;
  margin: 0 0 5px 0;
  margin-right: -5px;
}
li span {
  height: 10px;
  width: 10px;
  background-color: rgb(134, 133, 133);
  border-radius: 50%;
  display: inline-block;
  margin-right: 5px;
}
</style>
