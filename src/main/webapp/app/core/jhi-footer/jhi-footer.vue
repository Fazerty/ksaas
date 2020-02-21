<i18n>
{
    "en": {
        "footer": {
            "about": {
                "blog": "Blog",
                "contactUs": "Contact us",
                "ourStory": "Our story",
                "privacyPolicy": "Privacy policy",
                "termsOfService": "Terms of Service",
                "title": "About"
            },
            "description": "The easy way to manage your data.",
            "legalNotice": "Legal Notice",
            "links": {
                "documentation": "Documentation",
                "home": "Home",
                "pricing": "Pricing",
                "title": "Links"
            },
            "newsletter": {
                "emailPlaceHolder": "Enter your email.",
                "subscribe": "Subscribe",
                "title": "Subscribe to our newsletter."
            }
        }
    },
    "fr": {
        "footer": {
            "about": {
                "blog": "Blog",
                "contactUs": "Contactez-nous",
                "ourStory": "Notre histoire",
                "privacyPolicy": "Politique de confidentialité",
                "termsOfService": "Conditions générales d'utilisation",
                "title": "A propos"
            },
            "description": "Le moyen facile de gérer vos données.",
            "legalNotice": "Mentions Légales",
            "links": {
                "documentation": "Documentation",
                "home": "Acceuil",
                "pricing": "Tarifs",
                "title": "Liens"
            },
            "newsletter": {
                "emailPlaceHolder": "Entrez votre adresse email.",
                "subscribe": "S'inscrire",
                "title": "Inscrivez-vous à notre newsletter."
            }
        }
    }
}
</i18n>

<template>
  <b-container fluid id="footer" class="footer light font-small">
    <b-container class="text-left">
      <b-row>
        <!-- Upurion Section -->
        <b-col sm="3">
          <h3 class="text-primary">Upurion</h3>
          <span
            v-text="$t('footer.description')"
            class="text-gray"
          >The easy way to manage your data.</span>
        </b-col>

        <!-- About section -->
        <b-col sm="3">
          <h5 class="title text-primary"  v-text="$t('footer.about.title')">About</h5>
          <ul class="sm">
            <li class="list-unstyled">
              <a href="#!" v-text="$t('footer.about.ourStory')">Our story</a>
            </li>
            <li class="list-unstyled">
              <a href="#!" v-text="$t('footer.about.blog')">Blog</a>
            </li>
            <li class="list-unstyled">
              <a href="#!" v-text="$t('footer.about.contactUs')">Contact us</a>
            </li>
            <li class="list-unstyled">
              <a href="#!" v-text="$t('footer.about.termsOfService')">Terms of Service</a>
            </li>
            <li class="list-unstyled">
              <a href="#!" v-text="$t('footer.about.privacyPolicy')">Privacy policy</a>
            </li>
          </ul>
        </b-col>

        <!-- Links section -->
        <b-col sm="3">
          <h5 class="title text-primary" v-text="$t('footer.links.title')">Links</h5>
          <ul>
            <li class="list-unstyled">
              <a href="#!" v-text="$t('footer.links.home')">Home</a>
            </li>
            <li class="list-unstyled">
              <a href="#!" v-text="$t('footer.links.pricing')">Pricing</a>
            </li>
            <li class="list-unstyled">
              <a href="#!" v-text="$t('footer.links.documentation')">Documentation</a>
            </li>
          </ul>
        </b-col>
        <!-- Newsletter section -->
        <b-col sm="3">
          <b-form novalidate>
            <b-form-group
              id="input-group-1"
              label-for="input-1"
              :label="$t('footer.newsletter.title')"
              label-class="text-primary font-weight-bold pt-0"
            >
              <b-form-input
                id="input-1"
                v-model="email"
                size="sm"
                type="email"
                required
                :placeholder="$t('footer.newsletter.emailPlaceHolder')"
              ></b-form-input>
            </b-form-group>

            <b-btn
              @click="onSubscribe"
              size="sm"
              variant="info"
              v-text="$t('footer.newsletter.subscribe')"
            >Subscribe</b-btn>
          </b-form>
        </b-col>
      </b-row>
    </b-container>
    <div class="text-center">
      <b-container fluid class="text-center text-gray bg-info">
        &copy; 2020 Copyright:
        <a href="https://www.upurion.com">Upurion.com</a>
      </b-container>
    </div>
  </b-container>
</template>

<script lang="ts" src="./jhi-footer.component.ts"></script>

<style lang="scss" scoped>
.footer {
  text-align: left;
  font-size: 85%;
  background-color: var(--secondary);
}
</style>
