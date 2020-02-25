// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import * as config from './shared/config/config';
// import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import BootstrapVue from 'bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import AuditsService from './admin/audits/audits.service';

import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';

import BlogSectionService from '@/entities/blog-section/blog-section.service';
import BlogEntryGroupService from '@/entities/blog-entry-group/blog-entry-group.service';
import BlogEntryService from '@/entities/blog-entry/blog-entry.service';
import TagService from '@/entities/tag/tag.service';
import FaqSectionService from '@/entities/faq-section/faq-section.service';
import FaqEntryGroupService from '@/entities/faq-entry-group/faq-entry-group.service';
import FaqEntryService from '@/entities/faq-entry/faq-entry.service';
import DocumentationChapterService from '@/entities/documentation-chapter/documentation-chapter.service';
import DocumentationSectionService from '@/entities/documentation-section/documentation-section.service';
import DocumentationEntryGroupService from '@/entities/documentation-entry-group/documentation-entry-group.service';
import DocumentationEntryService from '@/entities/documentation-entry/documentation-entry.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
// bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(BootstrapVue);
Vue.use(Vue2Filters);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);

const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const alertService = new AlertService(store);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    if (!accountService.hasAnyAuthority(to.meta.authorities)) {
      sessionStorage.setItem('requested-url', to.fullPath);
      next('/forbidden');
    } else {
      next();
    }
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),

    auditsService: () => new AuditsService(),

    healthService: () => new HealthService(),

    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    alertService: () => alertService,
    translationService: () => translationService,
    blogSectionService: () => new BlogSectionService(),
    blogEntryGroupService: () => new BlogEntryGroupService(),
    blogEntryService: () => new BlogEntryService(),
    tagService: () => new TagService(),
    faqSectionService: () => new FaqSectionService(),
    faqEntryGroupService: () => new FaqEntryGroupService(),
    faqEntryService: () => new FaqEntryService(),
    documentationChapterService: () => new DocumentationChapterService(),
    documentationSectionService: () => new DocumentationSectionService(),
    documentationEntryGroupService: () => new DocumentationEntryGroupService(),
    documentationEntryService: () => new DocumentationEntryService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService
  },
  i18n,
  store
});
