/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import BlogEntryGroupComponent from '@/entities/blog-entry-group/blog-entry-group.vue';
import BlogEntryGroupClass from '@/entities/blog-entry-group/blog-entry-group.component';
import BlogEntryGroupService from '@/entities/blog-entry-group/blog-entry-group.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('BlogEntryGroup Management Component', () => {
    let wrapper: Wrapper<BlogEntryGroupClass>;
    let comp: BlogEntryGroupClass;
    let blogEntryGroupServiceStub: SinonStubbedInstance<BlogEntryGroupService>;

    beforeEach(() => {
      blogEntryGroupServiceStub = sinon.createStubInstance<BlogEntryGroupService>(BlogEntryGroupService);
      blogEntryGroupServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BlogEntryGroupClass>(BlogEntryGroupComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          blogEntryGroupService: () => blogEntryGroupServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      blogEntryGroupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBlogEntryGroups();
      await comp.$nextTick();

      // THEN
      expect(blogEntryGroupServiceStub.retrieve.called).toBeTruthy();
      expect(comp.blogEntryGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      blogEntryGroupServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeBlogEntryGroup();
      await comp.$nextTick();

      // THEN
      expect(blogEntryGroupServiceStub.delete.called).toBeTruthy();
      expect(blogEntryGroupServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
