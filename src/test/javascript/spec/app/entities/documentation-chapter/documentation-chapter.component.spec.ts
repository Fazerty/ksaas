/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentationChapterComponent from '@/entities/documentation-chapter/documentation-chapter.vue';
import DocumentationChapterClass from '@/entities/documentation-chapter/documentation-chapter.component';
import DocumentationChapterService from '@/entities/documentation-chapter/documentation-chapter.service';

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
  describe('DocumentationChapter Management Component', () => {
    let wrapper: Wrapper<DocumentationChapterClass>;
    let comp: DocumentationChapterClass;
    let documentationChapterServiceStub: SinonStubbedInstance<DocumentationChapterService>;

    beforeEach(() => {
      documentationChapterServiceStub = sinon.createStubInstance<DocumentationChapterService>(DocumentationChapterService);
      documentationChapterServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DocumentationChapterClass>(DocumentationChapterComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          documentationChapterService: () => documentationChapterServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      documentationChapterServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDocumentationChapters();
      await comp.$nextTick();

      // THEN
      expect(documentationChapterServiceStub.retrieve.called).toBeTruthy();
      expect(comp.documentationChapters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      documentationChapterServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDocumentationChapter();
      await comp.$nextTick();

      // THEN
      expect(documentationChapterServiceStub.delete.called).toBeTruthy();
      expect(documentationChapterServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
