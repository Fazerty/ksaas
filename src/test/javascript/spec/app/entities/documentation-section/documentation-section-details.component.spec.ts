/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DocumentationSectionDetailComponent from '@/entities/documentation-section/documentation-section-details.vue';
import DocumentationSectionClass from '@/entities/documentation-section/documentation-section-details.component';
import DocumentationSectionService from '@/entities/documentation-section/documentation-section.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DocumentationSection Management Detail Component', () => {
    let wrapper: Wrapper<DocumentationSectionClass>;
    let comp: DocumentationSectionClass;
    let documentationSectionServiceStub: SinonStubbedInstance<DocumentationSectionService>;

    beforeEach(() => {
      documentationSectionServiceStub = sinon.createStubInstance<DocumentationSectionService>(DocumentationSectionService);

      wrapper = shallowMount<DocumentationSectionClass>(DocumentationSectionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { documentationSectionService: () => documentationSectionServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDocumentationSection = { id: 123 };
        documentationSectionServiceStub.find.resolves(foundDocumentationSection);

        // WHEN
        comp.retrieveDocumentationSection(123);
        await comp.$nextTick();

        // THEN
        expect(comp.documentationSection).toBe(foundDocumentationSection);
      });
    });
  });
});
