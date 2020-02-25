/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FaqEntryGroupComponent from '@/entities/faq-entry-group/faq-entry-group.vue';
import FaqEntryGroupClass from '@/entities/faq-entry-group/faq-entry-group.component';
import FaqEntryGroupService from '@/entities/faq-entry-group/faq-entry-group.service';

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
  describe('FaqEntryGroup Management Component', () => {
    let wrapper: Wrapper<FaqEntryGroupClass>;
    let comp: FaqEntryGroupClass;
    let faqEntryGroupServiceStub: SinonStubbedInstance<FaqEntryGroupService>;

    beforeEach(() => {
      faqEntryGroupServiceStub = sinon.createStubInstance<FaqEntryGroupService>(FaqEntryGroupService);
      faqEntryGroupServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FaqEntryGroupClass>(FaqEntryGroupComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          faqEntryGroupService: () => faqEntryGroupServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      faqEntryGroupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFaqEntryGroups();
      await comp.$nextTick();

      // THEN
      expect(faqEntryGroupServiceStub.retrieve.called).toBeTruthy();
      expect(comp.faqEntryGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      faqEntryGroupServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeFaqEntryGroup();
      await comp.$nextTick();

      // THEN
      expect(faqEntryGroupServiceStub.delete.called).toBeTruthy();
      expect(faqEntryGroupServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
