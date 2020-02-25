/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FaqSectionDetailComponent from '@/entities/faq-section/faq-section-details.vue';
import FaqSectionClass from '@/entities/faq-section/faq-section-details.component';
import FaqSectionService from '@/entities/faq-section/faq-section.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FaqSection Management Detail Component', () => {
    let wrapper: Wrapper<FaqSectionClass>;
    let comp: FaqSectionClass;
    let faqSectionServiceStub: SinonStubbedInstance<FaqSectionService>;

    beforeEach(() => {
      faqSectionServiceStub = sinon.createStubInstance<FaqSectionService>(FaqSectionService);

      wrapper = shallowMount<FaqSectionClass>(FaqSectionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { faqSectionService: () => faqSectionServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFaqSection = { id: 123 };
        faqSectionServiceStub.find.resolves(foundFaqSection);

        // WHEN
        comp.retrieveFaqSection(123);
        await comp.$nextTick();

        // THEN
        expect(comp.faqSection).toBe(foundFaqSection);
      });
    });
  });
});
