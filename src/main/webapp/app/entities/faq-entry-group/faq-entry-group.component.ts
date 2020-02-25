import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFaqEntryGroup } from '@/shared/model/faq-entry-group.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import FaqEntryGroupService from './faq-entry-group.service';

@Component
export default class FaqEntryGroup extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('faqEntryGroupService') private faqEntryGroupService: () => FaqEntryGroupService;
  private removeId: number = null;
  public faqEntryGroups: IFaqEntryGroup[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFaqEntryGroups();
  }

  public clear(): void {
    this.retrieveAllFaqEntryGroups();
  }

  public retrieveAllFaqEntryGroups(): void {
    this.isFetching = true;

    this.faqEntryGroupService()
      .retrieve()
      .then(
        res => {
          this.faqEntryGroups = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IFaqEntryGroup): void {
    this.removeId = instance.id;
  }

  public removeFaqEntryGroup(): void {
    this.faqEntryGroupService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.faqEntryGroup.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllFaqEntryGroups();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
