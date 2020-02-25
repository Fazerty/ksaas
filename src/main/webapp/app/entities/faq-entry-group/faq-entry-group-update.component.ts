import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import FaqSectionService from '../faq-section/faq-section.service';
import { IFaqSection } from '@/shared/model/faq-section.model';

import FaqEntryService from '../faq-entry/faq-entry.service';
import { IFaqEntry } from '@/shared/model/faq-entry.model';

import AlertService from '@/shared/alert/alert.service';
import { IFaqEntryGroup, FaqEntryGroup } from '@/shared/model/faq-entry-group.model';
import FaqEntryGroupService from './faq-entry-group.service';

const validations: any = {
  faqEntryGroup: {}
};

@Component({
  validations
})
export default class FaqEntryGroupUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('faqEntryGroupService') private faqEntryGroupService: () => FaqEntryGroupService;
  public faqEntryGroup: IFaqEntryGroup = new FaqEntryGroup();

  @Inject('faqSectionService') private faqSectionService: () => FaqSectionService;

  public faqSections: IFaqSection[] = [];

  @Inject('faqEntryService') private faqEntryService: () => FaqEntryService;

  public faqEntries: IFaqEntry[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.faqEntryGroupId) {
        vm.retrieveFaqEntryGroup(to.params.faqEntryGroupId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.faqEntryGroup.id) {
      this.faqEntryGroupService()
        .update(this.faqEntryGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.faqEntryGroup.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.faqEntryGroupService()
        .create(this.faqEntryGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.faqEntryGroup.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveFaqEntryGroup(faqEntryGroupId): void {
    this.faqEntryGroupService()
      .find(faqEntryGroupId)
      .then(res => {
        this.faqEntryGroup = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.faqSectionService()
      .retrieve()
      .then(res => {
        this.faqSections = res.data;
      });
    this.faqEntryService()
      .retrieve()
      .then(res => {
        this.faqEntries = res.data;
      });
  }
}
