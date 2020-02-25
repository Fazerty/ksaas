import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumentationChapter } from '@/shared/model/documentation-chapter.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DocumentationChapterService from './documentation-chapter.service';

@Component
export default class DocumentationChapter extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('documentationChapterService') private documentationChapterService: () => DocumentationChapterService;
  private removeId: number = null;
  public documentationChapters: IDocumentationChapter[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentationChapters();
  }

  public clear(): void {
    this.retrieveAllDocumentationChapters();
  }

  public retrieveAllDocumentationChapters(): void {
    this.isFetching = true;

    this.documentationChapterService()
      .retrieve()
      .then(
        res => {
          this.documentationChapters = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDocumentationChapter): void {
    this.removeId = instance.id;
  }

  public removeDocumentationChapter(): void {
    this.documentationChapterService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.documentationChapter.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllDocumentationChapters();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
