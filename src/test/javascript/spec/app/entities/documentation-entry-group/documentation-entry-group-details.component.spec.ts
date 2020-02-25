/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DocumentationEntryGroupDetailComponent from '@/entities/documentation-entry-group/documentation-entry-group-details.vue';
import DocumentationEntryGroupClass from '@/entities/documentation-entry-group/documentation-entry-group-details.component';
import DocumentationEntryGroupService from '@/entities/documentation-entry-group/documentation-entry-group.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DocumentationEntryGroup Management Detail Component', () => {
    let wrapper: Wrapper<DocumentationEntryGroupClass>;
    let comp: DocumentationEntryGroupClass;
    let documentationEntryGroupServiceStub: SinonStubbedInstance<DocumentationEntryGroupService>;

    beforeEach(() => {
      documentationEntryGroupServiceStub = sinon.createStubInstance<DocumentationEntryGroupService>(DocumentationEntryGroupService);

      wrapper = shallowMount<DocumentationEntryGroupClass>(DocumentationEntryGroupDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { documentationEntryGroupService: () => documentationEntryGroupServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDocumentationEntryGroup = { id: 123 };
        documentationEntryGroupServiceStub.find.resolves(foundDocumentationEntryGroup);

        // WHEN
        comp.retrieveDocumentationEntryGroup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.documentationEntryGroup).toBe(foundDocumentationEntryGroup);
      });
    });
  });
});
