import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumentationEntryGroup } from '@/shared/model/documentation-entry-group.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DocumentationEntryGroupService from './documentation-entry-group.service';

@Component
export default class DocumentationEntryGroup extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('documentationEntryGroupService') private documentationEntryGroupService: () => DocumentationEntryGroupService;
  private removeId: number = null;
  public documentationEntryGroups: IDocumentationEntryGroup[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentationEntryGroups();
  }

  public clear(): void {
    this.retrieveAllDocumentationEntryGroups();
  }

  public retrieveAllDocumentationEntryGroups(): void {
    this.isFetching = true;

    this.documentationEntryGroupService()
      .retrieve()
      .then(
        res => {
          this.documentationEntryGroups = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDocumentationEntryGroup): void {
    this.removeId = instance.id;
  }

  public removeDocumentationEntryGroup(): void {
    this.documentationEntryGroupService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.documentationEntryGroup.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllDocumentationEntryGroups();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
