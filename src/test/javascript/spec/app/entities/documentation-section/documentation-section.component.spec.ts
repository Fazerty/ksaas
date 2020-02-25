/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentationSectionComponent from '@/entities/documentation-section/documentation-section.vue';
import DocumentationSectionClass from '@/entities/documentation-section/documentation-section.component';
import DocumentationSectionService from '@/entities/documentation-section/documentation-section.service';

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
  describe('DocumentationSection Management Component', () => {
    let wrapper: Wrapper<DocumentationSectionClass>;
    let comp: DocumentationSectionClass;
    let documentationSectionServiceStub: SinonStubbedInstance<DocumentationSectionService>;

    beforeEach(() => {
      documentationSectionServiceStub = sinon.createStubInstance<DocumentationSectionService>(DocumentationSectionService);
      documentationSectionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DocumentationSectionClass>(DocumentationSectionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          documentationSectionService: () => documentationSectionServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      documentationSectionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDocumentationSections();
      await comp.$nextTick();

      // THEN
      expect(documentationSectionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.documentationSections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      documentationSectionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDocumentationSection();
      await comp.$nextTick();

      // THEN
      expect(documentationSectionServiceStub.delete.called).toBeTruthy();
      expect(documentationSectionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
