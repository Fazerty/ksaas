import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import { ITranslatedName } from '@/shared/model/translated-name.model';

import AlertService from '@/shared/alert/alert.service';
import { IFaqSection, FaqSection } from '@/shared/model/faq-section.model';
import FaqSectionService from './faq-section.service';

const validations: any = {
  faqSection: {
    info: {
      required,
      minLength: minLength(2)
    }
  }
};

@Component({
  validations
})
export default class FaqSectionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('faqSectionService') private faqSectionService: () => FaqSectionService;
  public faqSection: IFaqSection = new FaqSection();

  public translatedNames: ITranslatedName[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.faqSectionId) {
        vm.retrieveFaqSection(to.params.faqSectionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.faqSection.id) {
      this.faqSectionService()
        .update(this.faqSection)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.faqSection.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.faqSectionService()
        .create(this.faqSection)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.faqSection.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveFaqSection(faqSectionId): void {
    this.faqSectionService()
      .find(faqSectionId)
      .then(res => {
        this.faqSection = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
