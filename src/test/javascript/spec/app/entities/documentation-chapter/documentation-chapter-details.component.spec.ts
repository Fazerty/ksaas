/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DocumentationChapterDetailComponent from '@/entities/documentation-chapter/documentation-chapter-details.vue';
import DocumentationChapterClass from '@/entities/documentation-chapter/documentation-chapter-details.component';
import DocumentationChapterService from '@/entities/documentation-chapter/documentation-chapter.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DocumentationChapter Management Detail Component', () => {
    let wrapper: Wrapper<DocumentationChapterClass>;
    let comp: DocumentationChapterClass;
    let documentationChapterServiceStub: SinonStubbedInstance<DocumentationChapterService>;

    beforeEach(() => {
      documentationChapterServiceStub = sinon.createStubInstance<DocumentationChapterService>(DocumentationChapterService);

      wrapper = shallowMount<DocumentationChapterClass>(DocumentationChapterDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { documentationChapterService: () => documentationChapterServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDocumentationChapter = { id: 123 };
        documentationChapterServiceStub.find.resolves(foundDocumentationChapter);

        // WHEN
        comp.retrieveDocumentationChapter(123);
        await comp.$nextTick();

        // THEN
        expect(comp.documentationChapter).toBe(foundDocumentationChapter);
      });
    });
  });
});
