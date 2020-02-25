import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFaqEntryGroup } from '@/shared/model/faq-entry-group.model';
import FaqEntryGroupService from './faq-entry-group.service';

@Component
export default class FaqEntryGroupDetails extends Vue {
  @Inject('faqEntryGroupService') private faqEntryGroupService: () => FaqEntryGroupService;
  public faqEntryGroup: IFaqEntryGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.faqEntryGroupId) {
        vm.retrieveFaqEntryGroup(to.params.faqEntryGroupId);
      }
    });
  }

  public retrieveFaqEntryGroup(faqEntryGroupId) {
    this.faqEntryGroupService()
      .find(faqEntryGroupId)
      .then(res => {
        this.faqEntryGroup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
