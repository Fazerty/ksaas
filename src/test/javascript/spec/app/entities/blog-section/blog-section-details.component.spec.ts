/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BlogSectionDetailComponent from '@/entities/blog-section/blog-section-details.vue';
import BlogSectionClass from '@/entities/blog-section/blog-section-details.component';
import BlogSectionService from '@/entities/blog-section/blog-section.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BlogSection Management Detail Component', () => {
    let wrapper: Wrapper<BlogSectionClass>;
    let comp: BlogSectionClass;
    let blogSectionServiceStub: SinonStubbedInstance<BlogSectionService>;

    beforeEach(() => {
      blogSectionServiceStub = sinon.createStubInstance<BlogSectionService>(BlogSectionService);

      wrapper = shallowMount<BlogSectionClass>(BlogSectionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { blogSectionService: () => blogSectionServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBlogSection = { id: 123 };
        blogSectionServiceStub.find.resolves(foundBlogSection);

        // WHEN
        comp.retrieveBlogSection(123);
        await comp.$nextTick();

        // THEN
        expect(comp.blogSection).toBe(foundBlogSection);
      });
    });
  });
});
