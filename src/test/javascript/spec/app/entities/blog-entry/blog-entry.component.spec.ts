/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import BlogEntryComponent from '@/entities/blog-entry/blog-entry.vue';
import BlogEntryClass from '@/entities/blog-entry/blog-entry.component';
import BlogEntryService from '@/entities/blog-entry/blog-entry.service';

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
  describe('BlogEntry Management Component', () => {
    let wrapper: Wrapper<BlogEntryClass>;
    let comp: BlogEntryClass;
    let blogEntryServiceStub: SinonStubbedInstance<BlogEntryService>;

    beforeEach(() => {
      blogEntryServiceStub = sinon.createStubInstance<BlogEntryService>(BlogEntryService);
      blogEntryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BlogEntryClass>(BlogEntryComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          blogEntryService: () => blogEntryServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      blogEntryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBlogEntrys();
      await comp.$nextTick();

      // THEN
      expect(blogEntryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.blogEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      blogEntryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(blogEntryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.blogEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      blogEntryServiceStub.retrieve.reset();
      blogEntryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(blogEntryServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.blogEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      blogEntryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeBlogEntry();
      await comp.$nextTick();

      // THEN
      expect(blogEntryServiceStub.delete.called).toBeTruthy();
      expect(blogEntryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
