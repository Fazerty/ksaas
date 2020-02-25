/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentationChapterUpdateComponent from '@/entities/documentation-chapter/documentation-chapter-update.vue';
import DocumentationChapterClass from '@/entities/documentation-chapter/documentation-chapter-update.component';
import DocumentationChapterService from '@/entities/documentation-chapter/documentation-chapter.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('DocumentationChapter Management Update Component', () => {
    let wrapper: Wrapper<DocumentationChapterClass>;
    let comp: DocumentationChapterClass;
    let documentationChapterServiceStub: SinonStubbedInstance<DocumentationChapterService>;

    beforeEach(() => {
      documentationChapterServiceStub = sinon.createStubInstance<DocumentationChapterService>(DocumentationChapterService);

      wrapper = shallowMount<DocumentationChapterClass>(DocumentationChapterUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          documentationChapterService: () => documentationChapterServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.documentationChapter = entity;
        documentationChapterServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentationChapterServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.documentationChapter = entity;
        documentationChapterServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentationChapterServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
