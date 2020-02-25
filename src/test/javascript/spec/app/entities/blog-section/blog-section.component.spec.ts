/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import BlogSectionComponent from '@/entities/blog-section/blog-section.vue';
import BlogSectionClass from '@/entities/blog-section/blog-section.component';
import BlogSectionService from '@/entities/blog-section/blog-section.service';

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
  describe('BlogSection Management Component', () => {
    let wrapper: Wrapper<BlogSectionClass>;
    let comp: BlogSectionClass;
    let blogSectionServiceStub: SinonStubbedInstance<BlogSectionService>;

    beforeEach(() => {
      blogSectionServiceStub = sinon.createStubInstance<BlogSectionService>(BlogSectionService);
      blogSectionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BlogSectionClass>(BlogSectionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          blogSectionService: () => blogSectionServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      blogSectionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBlogSections();
      await comp.$nextTick();

      // THEN
      expect(blogSectionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.blogSections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      blogSectionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeBlogSection();
      await comp.$nextTick();

      // THEN
      expect(blogSectionServiceStub.delete.called).toBeTruthy();
      expect(blogSectionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
