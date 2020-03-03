import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
const AboutUs = () => import('../about/aboutUs.vue');
const Home = () => import('../core/home/home.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');

const PricingComponent = () => import('../pricing/pricing.vue');
const FaqComponent = () => import('../help/faq/faq.vue');
const DocumentationComponent = () => import('../help/documentation/documentation.vue');
const BlogComponent = () => import('../help/blog/blog.vue');
const CommonFeaturesComponent = () => import('../features/common/commonFeatures.vue');
const DesktopFeatures = () => import('../features/desktop/desktopFeatures.vue');
const WebappFeatures = () => import('../features/webapp/webappFeatures.vue');
const DownloadAppComponent = () => import('../desktop/download/downloadApp.vue');

/* tslint:disable */
// prettier-ignore
const BlogSection = () => import('../entities/blog-section/blog-section.vue');
// prettier-ignore
const BlogSectionUpdate = () => import('../entities/blog-section/blog-section-update.vue');
// prettier-ignore
const BlogSectionDetails = () => import('../entities/blog-section/blog-section-details.vue');
// prettier-ignore
const BlogEntryGroup = () => import('../entities/blog-entry-group/blog-entry-group.vue');
// prettier-ignore
const BlogEntryGroupUpdate = () => import('../entities/blog-entry-group/blog-entry-group-update.vue');
// prettier-ignore
const BlogEntryGroupDetails = () => import('../entities/blog-entry-group/blog-entry-group-details.vue');
// prettier-ignore
const BlogEntry = () => import('../entities/blog-entry/blog-entry.vue');
// prettier-ignore
const BlogEntryUpdate = () => import('../entities/blog-entry/blog-entry-update.vue');
// prettier-ignore
const BlogEntryDetails = () => import('../entities/blog-entry/blog-entry-details.vue');
// prettier-ignore
const Tag = () => import('../entities/tag/tag.vue');
// prettier-ignore
const TagUpdate = () => import('../entities/tag/tag-update.vue');
// prettier-ignore
const TagDetails = () => import('../entities/tag/tag-details.vue');
// prettier-ignore
const FaqSection = () => import('../entities/faq-section/faq-section.vue');
// prettier-ignore
const FaqSectionUpdate = () => import('../entities/faq-section/faq-section-update.vue');
// prettier-ignore
const FaqSectionDetails = () => import('../entities/faq-section/faq-section-details.vue');
// prettier-ignore
const FaqEntryGroup = () => import('../entities/faq-entry-group/faq-entry-group.vue');
// prettier-ignore
const FaqEntryGroupUpdate = () => import('../entities/faq-entry-group/faq-entry-group-update.vue');
// prettier-ignore
const FaqEntryGroupDetails = () => import('../entities/faq-entry-group/faq-entry-group-details.vue');
// prettier-ignore
const FaqEntry = () => import('../entities/faq-entry/faq-entry.vue');
// prettier-ignore
const FaqEntryUpdate = () => import('../entities/faq-entry/faq-entry-update.vue');
// prettier-ignore
const FaqEntryDetails = () => import('../entities/faq-entry/faq-entry-details.vue');
// prettier-ignore
const DocumentationChapter = () => import('../entities/documentation-chapter/documentation-chapter.vue');
// prettier-ignore
const DocumentationChapterUpdate = () => import('../entities/documentation-chapter/documentation-chapter-update.vue');
// prettier-ignore
const DocumentationChapterDetails = () => import('../entities/documentation-chapter/documentation-chapter-details.vue');
// prettier-ignore
const DocumentationSection = () => import('../entities/documentation-section/documentation-section.vue');
// prettier-ignore
const DocumentationSectionUpdate = () => import('../entities/documentation-section/documentation-section-update.vue');
// prettier-ignore
const DocumentationSectionDetails = () => import('../entities/documentation-section/documentation-section-details.vue');
// prettier-ignore
const DocumentationEntryGroup = () => import('../entities/documentation-entry-group/documentation-entry-group.vue');
// prettier-ignore
const DocumentationEntryGroupUpdate = () => import('../entities/documentation-entry-group/documentation-entry-group-update.vue');
// prettier-ignore
const DocumentationEntryGroupDetails = () => import('../entities/documentation-entry-group/documentation-entry-group-details.vue');
// prettier-ignore
const DocumentationEntry = () => import('../entities/documentation-entry/documentation-entry.vue');
// prettier-ignore
const DocumentationEntryUpdate = () => import('../entities/documentation-entry/documentation-entry-update.vue');
// prettier-ignore
const DocumentationEntryDetails = () => import('../entities/documentation-entry/documentation-entry-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/pricing',
      name: 'PricingComponent',
      component: PricingComponent
    },
    {
      path: '/help/faq',
      name: 'FaqComponent',
      component: FaqComponent
    },
    {
      path: '/help/documentation',
      name: 'DocumentationComponent',
      component: DocumentationComponent
    },
    {
      path: '/help/blog',
      name: 'BlogComponent',
      component: BlogComponent
    },
    {
      path: '/features/common',
      name: 'CommonFeaturesComponent',
      component: CommonFeaturesComponent
    },
    {
      path: '/features/desktop',
      name: 'Component',
      component: DesktopFeatures
    },
    {
      path: '/features/webapp',
      name: 'WebappComponent',
      component: WebappFeatures
    },
    {
      path: '/desktop/download',
      name: 'DownloadAppComponent',
      component: DownloadAppComponent
    }  ,
    {
      path: '/blog-section',
      name: 'BlogSection',
      component: BlogSection,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-section/new',
      name: 'BlogSectionCreate',
      component: BlogSectionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-section/:blogSectionId/edit',
      name: 'BlogSectionEdit',
      component: BlogSectionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-section/:blogSectionId/view',
      name: 'BlogSectionView',
      component: BlogSectionDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/blog-entry-group',
      name: 'BlogEntryGroup',
      component: BlogEntryGroup,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-entry-group/new',
      name: 'BlogEntryGroupCreate',
      component: BlogEntryGroupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-entry-group/:blogEntryGroupId/edit',
      name: 'BlogEntryGroupEdit',
      component: BlogEntryGroupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-entry-group/:blogEntryGroupId/view',
      name: 'BlogEntryGroupView',
      component: BlogEntryGroupDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/blog-entry',
      name: 'BlogEntry',
      component: BlogEntry,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-entry/new',
      name: 'BlogEntryCreate',
      component: BlogEntryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-entry/:blogEntryId/edit',
      name: 'BlogEntryEdit',
      component: BlogEntryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/blog-entry/:blogEntryId/view',
      name: 'BlogEntryView',
      component: BlogEntryDetails,
      meta: { authorities: ['ROLE_USER'] }
    } ,
    {
      path: '/about_us',
      name: 'AboutUs',
      component: AboutUs,
    },
    ,
    {
      path: '/tag',
      name: 'Tag',
      component: Tag,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/tag/new',
      name: 'TagCreate',
      component: TagUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/tag/:tagId/edit',
      name: 'TagEdit',
      component: TagUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/tag/:tagId/view',
      name: 'TagView',
      component: TagDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/faq-section',
      name: 'FaqSection',
      component: FaqSection,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-section/new',
      name: 'FaqSectionCreate',
      component: FaqSectionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-section/:faqSectionId/edit',
      name: 'FaqSectionEdit',
      component: FaqSectionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-section/:faqSectionId/view',
      name: 'FaqSectionView',
      component: FaqSectionDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/faq-entry-group',
      name: 'FaqEntryGroup',
      component: FaqEntryGroup,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-entry-group/new',
      name: 'FaqEntryGroupCreate',
      component: FaqEntryGroupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-entry-group/:faqEntryGroupId/edit',
      name: 'FaqEntryGroupEdit',
      component: FaqEntryGroupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-entry-group/:faqEntryGroupId/view',
      name: 'FaqEntryGroupView',
      component: FaqEntryGroupDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/faq-entry',
      name: 'FaqEntry',
      component: FaqEntry,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-entry/new',
      name: 'FaqEntryCreate',
      component: FaqEntryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-entry/:faqEntryId/edit',
      name: 'FaqEntryEdit',
      component: FaqEntryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/faq-entry/:faqEntryId/view',
      name: 'FaqEntryView',
      component: FaqEntryDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/documentation-chapter',
      name: 'DocumentationChapter',
      component: DocumentationChapter,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-chapter/new',
      name: 'DocumentationChapterCreate',
      component: DocumentationChapterUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-chapter/:documentationChapterId/edit',
      name: 'DocumentationChapterEdit',
      component: DocumentationChapterUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-chapter/:documentationChapterId/view',
      name: 'DocumentationChapterView',
      component: DocumentationChapterDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/documentation-section',
      name: 'DocumentationSection',
      component: DocumentationSection,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-section/new',
      name: 'DocumentationSectionCreate',
      component: DocumentationSectionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-section/:documentationSectionId/edit',
      name: 'DocumentationSectionEdit',
      component: DocumentationSectionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-section/:documentationSectionId/view',
      name: 'DocumentationSectionView',
      component: DocumentationSectionDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/documentation-entry-group',
      name: 'DocumentationEntryGroup',
      component: DocumentationEntryGroup,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-entry-group/new',
      name: 'DocumentationEntryGroupCreate',
      component: DocumentationEntryGroupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-entry-group/:documentationEntryGroupId/edit',
      name: 'DocumentationEntryGroupEdit',
      component: DocumentationEntryGroupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-entry-group/:documentationEntryGroupId/view',
      name: 'DocumentationEntryGroupView',
      component: DocumentationEntryGroupDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/documentation-entry',
      name: 'DocumentationEntry',
      component: DocumentationEntry,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-entry/new',
      name: 'DocumentationEntryCreate',
      component: DocumentationEntryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-entry/:documentationEntryId/edit',
      name: 'DocumentationEntryEdit',
      component: DocumentationEntryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/documentation-entry/:documentationEntryId/view',
      name: 'DocumentationEntryView',
      component: DocumentationEntryDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
});
