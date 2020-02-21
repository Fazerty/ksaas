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
  <b-container fluid id="footer" class="footer bg-primary font-small">
    <b-container class="text-left">
      <b-row>
        <!-- Upurion Section -->
        <b-col sm="3">
          <div class="logo" b-link to="/">
                <span class="logo-img"></span>
                <span v-text="$t('global.title')" class="logo-title">Upurion</span>
                <!-- <span class="navbar-version">{{version}}</span> -->
          </div>
          <div
            v-text="$t('footer.description')"
            class="text-white"
          >The easy way to manage your data.</div>
        </b-col>

        <!-- About section -->
        <b-col sm="3">
          <h5 class="title text-white" v-text="$t('footer.about.title')">About</h5>
          <ul class="sm">
            <li class="list-unstyled">
              <b-link class="text-white" v-text="$t('footer.about.ourStory')" to="/help/documentation">Our story</b-link>
            </li>
            <li class="list-unstyled">
              <b-link class="text-white" v-text="$t('footer.about.blog')" to="/help/documentation">Blog</b-link>
            </li>
            <li class="list-unstyled">
              <b-link class="text-white" v-text="$t('footer.about.contactUs')" to="/help/documentation">Contact us</b-link>
            </li>
            <li class="list-unstyled">
              <b-link
                class="text-white"
                href="#!"
                v-text="$t('footer.about.termsOfService')"
                to="/help/documentation"
              >Terms of Service</b-link>
            </li>
            <li class="list-unstyled">
              <b-link
                class="text-white"
                href="#!"
                v-text="$t('footer.about.privacyPolicy')"
                to="/help/documentation"
              >Privacy policy</b-link>
            </li>
          </ul>
        </b-col>

        <!-- Links section -->
        <b-col sm="3">
          <h5 class="title text-white" v-text="$t('footer.links.title')" >Links</h5>
          <ul>
            <li class="list-unstyled">
              <b-link class="text-white" v-text="$t('footer.links.home')" to="/">Home</b-link>
            </li>
            <li class="list-unstyled">
              <b-link class="text-white" v-text="$t('footer.links.pricing')" to="/pricing">Pricing</b-link>
            </li>
            <li class="list-unstyled">
              <b-link
                class="text-white"
                href="#!"
                v-text="$t('footer.links.documentation')"
                to="/help/documentation"
              >Documentation</b-link>
            </li>
          </ul>
        </b-col>
        <!-- Newsletter section -->
        <b-col sm="3">
          <br/>
          <b-form novalidate>
            <b-form-group
              id="input-group-1"
              label-for="input-1"
              :label="$t('footer.newsletter.title')"
              label-class="text-white font-weight-bold pt-0"
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
    <hr class="bg-white mb-1" />
    <b-container fluid class="text-center text-white bg-gray">
      &copy; 2020 Copyright:
      <b-link class="text-white" href="https://www.upurion.com">Upurion.com</b-link>
    </b-container>
    <br />
  </b-container>
</template>

<script lang="ts" src="./jhi-footer.component.ts"></script>

<style lang="scss" scoped>

.logo .logo-img {
  height: 30px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 90%;
  background: url("../../../content/images/logo.png") no-repeat center
    center;
  background-size: contain;
  width: 90%;
}

.logo-title {
  display: inline-block;
  color: white;
  font-size: 200%;
  font-family: Times, "Times New Roman", serif;
  font-weight: bold;
  font-style: italic;
  vertical-align: middle;
}

.title {
  display: inline-block;
  color: white;
  font-size: 150%;
  font-family: Times, "Times New Roman", serif;
  font-weight: bold;
  font-style: italic;
  vertical-align: middle;
}

.footer {
  text-align: left;
  font-size: 85%;
}
</style>
