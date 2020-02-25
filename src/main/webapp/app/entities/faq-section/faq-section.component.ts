import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFaqSection } from '@/shared/model/faq-section.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import FaqSectionService from './faq-section.service';

@Component
export default class FaqSection extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('faqSectionService') private faqSectionService: () => FaqSectionService;
  private removeId: number = null;
  public faqSections: IFaqSection[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFaqSections();
  }

  public clear(): void {
    this.retrieveAllFaqSections();
  }

  public retrieveAllFaqSections(): void {
    this.isFetching = true;

    this.faqSectionService()
      .retrieve()
      .then(
        res => {
          this.faqSections = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IFaqSection): void {
    this.removeId = instance.id;
  }

  public removeFaqSection(): void {
    this.faqSectionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.faqSection.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllFaqSections();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
