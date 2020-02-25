import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumentationSection } from '@/shared/model/documentation-section.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DocumentationSectionService from './documentation-section.service';

@Component
export default class DocumentationSection extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('documentationSectionService') private documentationSectionService: () => DocumentationSectionService;
  private removeId: number = null;
  public documentationSections: IDocumentationSection[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentationSections();
  }

  public clear(): void {
    this.retrieveAllDocumentationSections();
  }

  public retrieveAllDocumentationSections(): void {
    this.isFetching = true;

    this.documentationSectionService()
      .retrieve()
      .then(
        res => {
          this.documentationSections = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDocumentationSection): void {
    this.removeId = instance.id;
  }

  public removeDocumentationSection(): void {
    this.documentationSectionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.documentationSection.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllDocumentationSections();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
