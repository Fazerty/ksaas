/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import BlogSectionUpdateComponent from '@/entities/blog-section/blog-section-update.vue';
import BlogSectionClass from '@/entities/blog-section/blog-section-update.component';
import BlogSectionService from '@/entities/blog-section/blog-section.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('BlogSection Management Update Component', () => {
    let wrapper: Wrapper<BlogSectionClass>;
    let comp: BlogSectionClass;
    let blogSectionServiceStub: SinonStubbedInstance<BlogSectionService>;

    beforeEach(() => {
      blogSectionServiceStub = sinon.createStubInstance<BlogSectionService>(BlogSectionService);

      wrapper = shallowMount<BlogSectionClass>(BlogSectionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          blogSectionService: () => blogSectionServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.blogSection = entity;
        blogSectionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(blogSectionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.blogSection = entity;
        blogSectionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(blogSectionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
