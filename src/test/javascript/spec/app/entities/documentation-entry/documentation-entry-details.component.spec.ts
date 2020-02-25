/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DocumentationEntryDetailComponent from '@/entities/documentation-entry/documentation-entry-details.vue';
import DocumentationEntryClass from '@/entities/documentation-entry/documentation-entry-details.component';
import DocumentationEntryService from '@/entities/documentation-entry/documentation-entry.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DocumentationEntry Management Detail Component', () => {
    let wrapper: Wrapper<DocumentationEntryClass>;
    let comp: DocumentationEntryClass;
    let documentationEntryServiceStub: SinonStubbedInstance<DocumentationEntryService>;

    beforeEach(() => {
      documentationEntryServiceStub = sinon.createStubInstance<DocumentationEntryService>(DocumentationEntryService);

      wrapper = shallowMount<DocumentationEntryClass>(DocumentationEntryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { documentationEntryService: () => documentationEntryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDocumentationEntry = { id: 123 };
        documentationEntryServiceStub.find.resolves(foundDocumentationEntry);

        // WHEN
        comp.retrieveDocumentationEntry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.documentationEntry).toBe(foundDocumentationEntry);
      });
    });
  });
});
