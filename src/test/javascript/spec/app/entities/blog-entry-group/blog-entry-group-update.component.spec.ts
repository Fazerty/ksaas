/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import BlogEntryGroupUpdateComponent from '@/entities/blog-entry-group/blog-entry-group-update.vue';
import BlogEntryGroupClass from '@/entities/blog-entry-group/blog-entry-group-update.component';
import BlogEntryGroupService from '@/entities/blog-entry-group/blog-entry-group.service';

import UserService from '@/admin/user-management/user-management.service';

import BlogSectionService from '@/entities/blog-section/blog-section.service';

import BlogEntryService from '@/entities/blog-entry/blog-entry.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('BlogEntryGroup Management Update Component', () => {
    let wrapper: Wrapper<BlogEntryGroupClass>;
    let comp: BlogEntryGroupClass;
    let blogEntryGroupServiceStub: SinonStubbedInstance<BlogEntryGroupService>;

    beforeEach(() => {
      blogEntryGroupServiceStub = sinon.createStubInstance<BlogEntryGroupService>(BlogEntryGroupService);

      wrapper = shallowMount<BlogEntryGroupClass>(BlogEntryGroupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          blogEntryGroupService: () => blogEntryGroupServiceStub,

          userService: () => new UserService(),

          blogSectionService: () => new BlogSectionService(),

          blogEntryService: () => new BlogEntryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.blogEntryGroup = entity;
        blogEntryGroupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(blogEntryGroupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.blogEntryGroup = entity;
        blogEntryGroupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(blogEntryGroupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
