/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FaqEntryGroupDetailComponent from '@/entities/faq-entry-group/faq-entry-group-details.vue';
import FaqEntryGroupClass from '@/entities/faq-entry-group/faq-entry-group-details.component';
import FaqEntryGroupService from '@/entities/faq-entry-group/faq-entry-group.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FaqEntryGroup Management Detail Component', () => {
    let wrapper: Wrapper<FaqEntryGroupClass>;
    let comp: FaqEntryGroupClass;
    let faqEntryGroupServiceStub: SinonStubbedInstance<FaqEntryGroupService>;

    beforeEach(() => {
      faqEntryGroupServiceStub = sinon.createStubInstance<FaqEntryGroupService>(FaqEntryGroupService);

      wrapper = shallowMount<FaqEntryGroupClass>(FaqEntryGroupDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { faqEntryGroupService: () => faqEntryGroupServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFaqEntryGroup = { id: 123 };
        faqEntryGroupServiceStub.find.resolves(foundFaqEntryGroup);

        // WHEN
        comp.retrieveFaqEntryGroup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.faqEntryGroup).toBe(foundFaqEntryGroup);
      });
    });
  });
});
