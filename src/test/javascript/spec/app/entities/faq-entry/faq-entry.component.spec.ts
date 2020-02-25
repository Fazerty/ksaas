/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FaqEntryComponent from '@/entities/faq-entry/faq-entry.vue';
import FaqEntryClass from '@/entities/faq-entry/faq-entry.component';
import FaqEntryService from '@/entities/faq-entry/faq-entry.service';

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
  describe('FaqEntry Management Component', () => {
    let wrapper: Wrapper<FaqEntryClass>;
    let comp: FaqEntryClass;
    let faqEntryServiceStub: SinonStubbedInstance<FaqEntryService>;

    beforeEach(() => {
      faqEntryServiceStub = sinon.createStubInstance<FaqEntryService>(FaqEntryService);
      faqEntryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FaqEntryClass>(FaqEntryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          faqEntryService: () => faqEntryServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      faqEntryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFaqEntrys();
      await comp.$nextTick();

      // THEN
      expect(faqEntryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.faqEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      faqEntryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeFaqEntry();
      await comp.$nextTick();

      // THEN
      expect(faqEntryServiceStub.delete.called).toBeTruthy();
      expect(faqEntryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
