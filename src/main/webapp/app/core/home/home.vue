<i18n>
{
    "de": {
        "home": {
            "subtitle": "Mit dieser Anwendung können Sie einfach eine Struktur für Daten erstellen! Sie können dann Ihre Daten eingeben. Sie müssen die Struktur jederzeit ändern",
            "title": "Einfache Verwaltung Ihrer Daten!"
        }
    },
    "en": {
        "home": {
            "subtitle": "This application will allow you to easily create a structure for data! You can then enter your data. Need to change the structure, do it at any time",
            "title": "Easy management of your data !"
        }
    },
    "es": {
        "home": {
            "subtitle": "¡Esta aplicación le permitirá crear fácilmente una estructura para datos! Luego puede ingresar sus datos. Necesita cambiar la estructura, hágalo en cualquier momento",
            "title": "¡Fácil gestión de sus datos!"
        }
    },
    "fr": {
        "home": {
            "subtitle": "Cette application va vous permettre de créer facilement une structure pour données! Vous pourrez ensuite entrer vos données. Besoin de changer la structure , faites le à tout moment",
            "title": "La Gestion facile de vos données !"
        }
    },
    "it": {
        "home": {
            "subtitle": "Questa applicazione ti permetterà di creare facilmente una struttura per i dati! È quindi possibile inserire i dati. Hai bisogno di cambiare la struttura, fallo in qualsiasi momento",
            "title": "Facile gestione dei tuoi dati!"
        }
    },
    "nl": {
        "home": {
            "subtitle": "Met deze applicatie kunt u eenvoudig een structuur voor gegevens maken! U kunt vervolgens uw gegevens invoeren. Moet de structuur wijzigen, doe dit op elk gewenst moment",
            "title": "Eenvoudig beheer van uw gegevens!"
        }
    },
    "ro": {
        "home": {
            "subtitle": "Această aplicație vă va permite să creați cu ușurință o structură pentru date! Puteți apoi să introduceți datele. Trebuie să schimbați structura, faceți-o oricând",
            "title": "Gestionarea ușoară a datelor dvs.!"
        }
    }
}
</i18n>
<template>
  <div class="home row">
    <div class="col-md-3">
      <span class="hipster img-fluid rounded"></span>
    </div>
    <div class="col-md-9">
      <h1 class="display-4" v-text="$t('home.title')">Welcome, upurion!</h1>
      <p class="lead" v-text="$t('home.subtitle')">This is your homepage</p>

      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span
            v-if="username"
            v-text="$t('home.logged.message', { 'username': username})"
          >You are logged in as user "{{username}}"</span>
        </div>

        <div class="alert alert-warning" v-if="!authenticated">
          <span v-text="$t('global.messages.info.authenticated.prefix')">If you want to</span>
          <a
            class="alert-link"
            v-on:click="openLogin()"
            v-text="$t('global.messages.info.authenticated.link')"
          >sign in</a>
          <span v-html="$t('global.messages.info.authenticated.suffix')">
            , you can try the default accounts:
            <br />- Administrator (login="admin" and password="admin")
            <br />- User (login="user" and password="user").
          </span>
        </div>
        <div class="alert alert-warning" v-if="!authenticated">
          <span
            v-text="$t('global.messages.info.register.noaccount')"
          >You don't have an account yet?</span>&nbsp;
          <router-link
            class="alert-link"
            to="/register"
            v-text="$t('global.messages.info.register.link')"
          >Register a new account</router-link>
        </div>
      </div>

      <p v-text="$t('home.question')">If you have any question on Upurion:</p>

      <ul>
        <li>
          <a
            href="https://gitter.im/upurion"
            target="_blank"
            rel="noopener"
            v-text="$t('home.link.chat')"
          >Upurion public chat room</a>
        </li>
      </ul>

      <p>
        <span v-text="$t('home.like')">If you like JHipster, don't forget to give us a star on</span>
        <a
          href="https://github.com/jhipster/generator-jhipster"
          target="_blank"
          rel="noopener"
          v-text="$t('home.github')"
        >GitHub</a>!
      </p>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts">
</script>
