import { Module } from 'vuex';

export type Langages = 'nl' | 'en' | 'fr' | 'de' | 'it' | 'ro' | 'es';

export enum Language {
  FR = 'FR',
  EN = 'EN',
  ES = 'ES',
  IT = 'IT',
  RO = 'RO',
  DU = 'DU',
  NL = 'NL'
}

export const defaultLanguage: Langages = 'en';

export const translationStore: Module<any, any> = {
  state: {
    currentLanguage: localStorage.getItem('currentLanguage') || 'en',
    languages: {
      // nl: { name: 'Nederlands' },
      en: { name: 'English' },
      fr: { name: 'Français' }
      // de: { name: 'Deutsch' },
      // it: { name: 'Italiano' },
      // ro: { name: 'Română' },
      // es: { name: 'Español' }
      // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    }
  },
  getters: {
    currentLanguage: state => state.currentLanguage,
    languages: state => state.languages
  },
  mutations: {
    currentLanguage(state, newLanguage) {
      state.currentLanguage = newLanguage;
      localStorage.setItem('currentLanguage', newLanguage);
    }
  }
};
