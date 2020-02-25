/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentationEntryGroupComponent from '@/entities/documentation-entry-group/documentation-entry-group.vue';
import DocumentationEntryGroupClass from '@/entities/documentation-entry-group/documentation-entry-group.component';
import DocumentationEntryGroupService from '@/entities/documentation-entry-group/documentation-entry-group.service';

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
  describe('DocumentationEntryGroup Management Component', () => {
    let wrapper: Wrapper<DocumentationEntryGroupClass>;
    let comp: DocumentationEntryGroupClass;
    let documentationEntryGroupServiceStub: SinonStubbedInstance<DocumentationEntryGroupService>;

    beforeEach(() => {
      documentationEntryGroupServiceStub = sinon.createStubInstance<DocumentationEntryGroupService>(DocumentationEntryGroupService);
      documentationEntryGroupServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DocumentationEntryGroupClass>(DocumentationEntryGroupComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          documentationEntryGroupService: () => documentationEntryGroupServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      documentationEntryGroupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDocumentationEntryGroups();
      await comp.$nextTick();

      // THEN
      expect(documentationEntryGroupServiceStub.retrieve.called).toBeTruthy();
      expect(comp.documentationEntryGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      documentationEntryGroupServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDocumentationEntryGroup();
      await comp.$nextTick();

      // THEN
      expect(documentationEntryGroupServiceStub.delete.called).toBeTruthy();
      expect(documentationEntryGroupServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
