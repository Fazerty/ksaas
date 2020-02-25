import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFaqSection } from '@/shared/model/faq-section.model';
import FaqSectionService from './faq-section.service';

@Component
export default class FaqSectionDetails extends Vue {
  @Inject('faqSectionService') private faqSectionService: () => FaqSectionService;
  public faqSection: IFaqSection = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.faqSectionId) {
        vm.retrieveFaqSection(to.params.faqSectionId);
      }
    });
  }

  public retrieveFaqSection(faqSectionId) {
    this.faqSectionService()
      .find(faqSectionId)
      .then(res => {
        this.faqSection = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
