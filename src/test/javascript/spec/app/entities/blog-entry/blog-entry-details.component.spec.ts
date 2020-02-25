/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BlogEntryDetailComponent from '@/entities/blog-entry/blog-entry-details.vue';
import BlogEntryClass from '@/entities/blog-entry/blog-entry-details.component';
import BlogEntryService from '@/entities/blog-entry/blog-entry.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BlogEntry Management Detail Component', () => {
    let wrapper: Wrapper<BlogEntryClass>;
    let comp: BlogEntryClass;
    let blogEntryServiceStub: SinonStubbedInstance<BlogEntryService>;

    beforeEach(() => {
      blogEntryServiceStub = sinon.createStubInstance<BlogEntryService>(BlogEntryService);

      wrapper = shallowMount<BlogEntryClass>(BlogEntryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { blogEntryService: () => blogEntryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBlogEntry = { id: 123 };
        blogEntryServiceStub.find.resolves(foundBlogEntry);

        // WHEN
        comp.retrieveBlogEntry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.blogEntry).toBe(foundBlogEntry);
      });
    });
  });
});
