import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFaqEntry } from '@/shared/model/faq-entry.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import FaqEntryService from './faq-entry.service';

@Component
export default class FaqEntry extends mixins(JhiDataUtils, Vue2Filters.mixin, AlertMixin) {
  @Inject('faqEntryService') private faqEntryService: () => FaqEntryService;
  private removeId: number = null;
  public faqEntries: IFaqEntry[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFaqEntrys();
  }

  public clear(): void {
    this.retrieveAllFaqEntrys();
  }

  public retrieveAllFaqEntrys(): void {
    this.isFetching = true;

    this.faqEntryService()
      .retrieve()
      .then(
        res => {
          this.faqEntries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IFaqEntry): void {
    this.removeId = instance.id;
  }

  public removeFaqEntry(): void {
    this.faqEntryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.faqEntry.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllFaqEntrys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
