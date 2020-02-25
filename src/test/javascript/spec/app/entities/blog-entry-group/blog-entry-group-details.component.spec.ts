/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BlogEntryGroupDetailComponent from '@/entities/blog-entry-group/blog-entry-group-details.vue';
import BlogEntryGroupClass from '@/entities/blog-entry-group/blog-entry-group-details.component';
import BlogEntryGroupService from '@/entities/blog-entry-group/blog-entry-group.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BlogEntryGroup Management Detail Component', () => {
    let wrapper: Wrapper<BlogEntryGroupClass>;
    let comp: BlogEntryGroupClass;
    let blogEntryGroupServiceStub: SinonStubbedInstance<BlogEntryGroupService>;

    beforeEach(() => {
      blogEntryGroupServiceStub = sinon.createStubInstance<BlogEntryGroupService>(BlogEntryGroupService);

      wrapper = shallowMount<BlogEntryGroupClass>(BlogEntryGroupDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { blogEntryGroupService: () => blogEntryGroupServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBlogEntryGroup = { id: 123 };
        blogEntryGroupServiceStub.find.resolves(foundBlogEntryGroup);

        // WHEN
        comp.retrieveBlogEntryGroup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.blogEntryGroup).toBe(foundBlogEntryGroup);
      });
    });
  });
});
