/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FaqEntryGroupUpdateComponent from '@/entities/faq-entry-group/faq-entry-group-update.vue';
import FaqEntryGroupClass from '@/entities/faq-entry-group/faq-entry-group-update.component';
import FaqEntryGroupService from '@/entities/faq-entry-group/faq-entry-group.service';

import FaqSectionService from '@/entities/faq-section/faq-section.service';

import FaqEntryService from '@/entities/faq-entry/faq-entry.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('FaqEntryGroup Management Update Component', () => {
    let wrapper: Wrapper<FaqEntryGroupClass>;
    let comp: FaqEntryGroupClass;
    let faqEntryGroupServiceStub: SinonStubbedInstance<FaqEntryGroupService>;

    beforeEach(() => {
      faqEntryGroupServiceStub = sinon.createStubInstance<FaqEntryGroupService>(FaqEntryGroupService);

      wrapper = shallowMount<FaqEntryGroupClass>(FaqEntryGroupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          faqEntryGroupService: () => faqEntryGroupServiceStub,

          faqSectionService: () => new FaqSectionService(),

          faqEntryService: () => new FaqEntryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.faqEntryGroup = entity;
        faqEntryGroupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(faqEntryGroupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.faqEntryGroup = entity;
        faqEntryGroupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(faqEntryGroupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
