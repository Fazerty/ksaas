import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import { ITranslatedName } from '@/shared/model/translated-name.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentationChapter, DocumentationChapter } from '@/shared/model/documentation-chapter.model';
import DocumentationChapterService from './documentation-chapter.service';

const validations: any = {
  documentationChapter: {
    info: {
      required,
      minLength: minLength(2)
    }
  }
};

@Component({
  validations
})
export default class DocumentationChapterUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentationChapterService') private documentationChapterService: () => DocumentationChapterService;
  public documentationChapter: IDocumentationChapter = new DocumentationChapter();

  public translatedNames: ITranslatedName[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentationChapterId) {
        vm.retrieveDocumentationChapter(to.params.documentationChapterId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.documentationChapter.id) {
      this.documentationChapterService()
        .update(this.documentationChapter)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.documentationChapter.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentationChapterService()
        .create(this.documentationChapter)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.documentationChapter.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocumentationChapter(documentationChapterId): void {
    this.documentationChapterService()
      .find(documentationChapterId)
      .then(res => {
        this.documentationChapter = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
