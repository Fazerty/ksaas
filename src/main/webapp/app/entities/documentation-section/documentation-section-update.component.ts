import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import { ITranslatedName } from '@/shared/model/translated-name.model';

import DocumentationChapterService from '../documentation-chapter/documentation-chapter.service';
import { IDocumentationChapter } from '@/shared/model/documentation-chapter.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentationSection, DocumentationSection } from '@/shared/model/documentation-section.model';
import DocumentationSectionService from './documentation-section.service';

const validations: any = {
  documentationSection: {
    info: {
      required,
      minLength: minLength(2)
    }
  }
};

@Component({
  validations
})
export default class DocumentationSectionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentationSectionService') private documentationSectionService: () => DocumentationSectionService;
  public documentationSection: IDocumentationSection = new DocumentationSection();

  public translatedNames: ITranslatedName[] = [];

  @Inject('documentationChapterService') private documentationChapterService: () => DocumentationChapterService;

  public documentationChapters: IDocumentationChapter[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentationSectionId) {
        vm.retrieveDocumentationSection(to.params.documentationSectionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.documentationSection.id) {
      this.documentationSectionService()
        .update(this.documentationSection)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.documentationSection.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentationSectionService()
        .create(this.documentationSection)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.documentationSection.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocumentationSection(documentationSectionId): void {
    this.documentationSectionService()
      .find(documentationSectionId)
      .then(res => {
        this.documentationSection = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentationChapterService()
      .retrieve()
      .then(res => {
        this.documentationChapters = res.data;
      });
  }
}
