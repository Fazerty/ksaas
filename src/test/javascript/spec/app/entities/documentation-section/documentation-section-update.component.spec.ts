/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentationSectionUpdateComponent from '@/entities/documentation-section/documentation-section-update.vue';
import DocumentationSectionClass from '@/entities/documentation-section/documentation-section-update.component';
import DocumentationSectionService from '@/entities/documentation-section/documentation-section.service';

import DocumentationChapterService from '@/entities/documentation-chapter/documentation-chapter.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('DocumentationSection Management Update Component', () => {
    let wrapper: Wrapper<DocumentationSectionClass>;
    let comp: DocumentationSectionClass;
    let documentationSectionServiceStub: SinonStubbedInstance<DocumentationSectionService>;

    beforeEach(() => {
      documentationSectionServiceStub = sinon.createStubInstance<DocumentationSectionService>(DocumentationSectionService);

      wrapper = shallowMount<DocumentationSectionClass>(DocumentationSectionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          documentationSectionService: () => documentationSectionServiceStub,
          documentationChapterService: () => new DocumentationChapterService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.documentationSection = entity;
        documentationSectionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentationSectionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.documentationSection = entity;
        documentationSectionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentationSectionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
