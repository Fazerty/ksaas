/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentationEntryGroupUpdateComponent from '@/entities/documentation-entry-group/documentation-entry-group-update.vue';
import DocumentationEntryGroupClass from '@/entities/documentation-entry-group/documentation-entry-group-update.component';
import DocumentationEntryGroupService from '@/entities/documentation-entry-group/documentation-entry-group.service';

import DocumentationSectionService from '@/entities/documentation-section/documentation-section.service';

import FaqEntryService from '@/entities/faq-entry/faq-entry.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('DocumentationEntryGroup Management Update Component', () => {
    let wrapper: Wrapper<DocumentationEntryGroupClass>;
    let comp: DocumentationEntryGroupClass;
    let documentationEntryGroupServiceStub: SinonStubbedInstance<DocumentationEntryGroupService>;

    beforeEach(() => {
      documentationEntryGroupServiceStub = sinon.createStubInstance<DocumentationEntryGroupService>(DocumentationEntryGroupService);

      wrapper = shallowMount<DocumentationEntryGroupClass>(DocumentationEntryGroupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          documentationEntryGroupService: () => documentationEntryGroupServiceStub,

          documentationSectionService: () => new DocumentationSectionService(),

          faqEntryService: () => new FaqEntryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.documentationEntryGroup = entity;
        documentationEntryGroupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentationEntryGroupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.documentationEntryGroup = entity;
        documentationEntryGroupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentationEntryGroupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
