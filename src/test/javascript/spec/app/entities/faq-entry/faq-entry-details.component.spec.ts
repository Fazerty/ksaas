/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FaqEntryDetailComponent from '@/entities/faq-entry/faq-entry-details.vue';
import FaqEntryClass from '@/entities/faq-entry/faq-entry-details.component';
import FaqEntryService from '@/entities/faq-entry/faq-entry.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FaqEntry Management Detail Component', () => {
    let wrapper: Wrapper<FaqEntryClass>;
    let comp: FaqEntryClass;
    let faqEntryServiceStub: SinonStubbedInstance<FaqEntryService>;

    beforeEach(() => {
      faqEntryServiceStub = sinon.createStubInstance<FaqEntryService>(FaqEntryService);

      wrapper = shallowMount<FaqEntryClass>(FaqEntryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { faqEntryService: () => faqEntryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFaqEntry = { id: 123 };
        faqEntryServiceStub.find.resolves(foundFaqEntry);

        // WHEN
        comp.retrieveFaqEntry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.faqEntry).toBe(foundFaqEntry);
      });
    });
  });
});
