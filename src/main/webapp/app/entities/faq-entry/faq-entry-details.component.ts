import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IFaqEntry } from '@/shared/model/faq-entry.model';
import FaqEntryService from './faq-entry.service';

@Component
export default class FaqEntryDetails extends mixins(JhiDataUtils) {
  @Inject('faqEntryService') private faqEntryService: () => FaqEntryService;
  public faqEntry: IFaqEntry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.faqEntryId) {
        vm.retrieveFaqEntry(to.params.faqEntryId);
      }
    });
  }

  public retrieveFaqEntry(faqEntryId) {
    this.faqEntryService()
      .find(faqEntryId)
      .then(res => {
        this.faqEntry = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
