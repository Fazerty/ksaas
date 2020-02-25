/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentationEntryComponent from '@/entities/documentation-entry/documentation-entry.vue';
import DocumentationEntryClass from '@/entities/documentation-entry/documentation-entry.component';
import DocumentationEntryService from '@/entities/documentation-entry/documentation-entry.service';

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
  describe('DocumentationEntry Management Component', () => {
    let wrapper: Wrapper<DocumentationEntryClass>;
    let comp: DocumentationEntryClass;
    let documentationEntryServiceStub: SinonStubbedInstance<DocumentationEntryService>;

    beforeEach(() => {
      documentationEntryServiceStub = sinon.createStubInstance<DocumentationEntryService>(DocumentationEntryService);
      documentationEntryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DocumentationEntryClass>(DocumentationEntryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          documentationEntryService: () => documentationEntryServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      documentationEntryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDocumentationEntrys();
      await comp.$nextTick();

      // THEN
      expect(documentationEntryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.documentationEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      documentationEntryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDocumentationEntry();
      await comp.$nextTick();

      // THEN
      expect(documentationEntryServiceStub.delete.called).toBeTruthy();
      expect(documentationEntryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
