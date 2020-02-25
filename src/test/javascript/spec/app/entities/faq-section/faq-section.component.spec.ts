/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FaqSectionComponent from '@/entities/faq-section/faq-section.vue';
import FaqSectionClass from '@/entities/faq-section/faq-section.component';
import FaqSectionService from '@/entities/faq-section/faq-section.service';

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
  describe('FaqSection Management Component', () => {
    let wrapper: Wrapper<FaqSectionClass>;
    let comp: FaqSectionClass;
    let faqSectionServiceStub: SinonStubbedInstance<FaqSectionService>;

    beforeEach(() => {
      faqSectionServiceStub = sinon.createStubInstance<FaqSectionService>(FaqSectionService);
      faqSectionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FaqSectionClass>(FaqSectionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          faqSectionService: () => faqSectionServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      faqSectionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFaqSections();
      await comp.$nextTick();

      // THEN
      expect(faqSectionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.faqSections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      faqSectionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeFaqSection();
      await comp.$nextTick();

      // THEN
      expect(faqSectionServiceStub.delete.called).toBeTruthy();
      expect(faqSectionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
