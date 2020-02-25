import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumentationEntry } from '@/shared/model/documentation-entry.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import DocumentationEntryService from './documentation-entry.service';

@Component
export default class DocumentationEntry extends mixins(JhiDataUtils, Vue2Filters.mixin, AlertMixin) {
  @Inject('documentationEntryService') private documentationEntryService: () => DocumentationEntryService;
  private removeId: number = null;
  public documentationEntries: IDocumentationEntry[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentationEntrys();
  }

  public clear(): void {
    this.retrieveAllDocumentationEntrys();
  }

  public retrieveAllDocumentationEntrys(): void {
    this.isFetching = true;

    this.documentationEntryService()
      .retrieve()
      .then(
        res => {
          this.documentationEntries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDocumentationEntry): void {
    this.removeId = instance.id;
  }

  public removeDocumentationEntry(): void {
    this.documentationEntryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.documentationEntry.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllDocumentationEntrys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
