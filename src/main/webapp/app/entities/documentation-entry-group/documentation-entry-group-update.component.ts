import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import DocumentationSectionService from '../documentation-section/documentation-section.service';
import { IDocumentationSection } from '@/shared/model/documentation-section.model';

import FaqEntryService from '../faq-entry/faq-entry.service';
import { IFaqEntry } from '@/shared/model/faq-entry.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentationEntryGroup, DocumentationEntryGroup } from '@/shared/model/documentation-entry-group.model';
import DocumentationEntryGroupService from './documentation-entry-group.service';

const validations: any = {
  documentationEntryGroup: {}
};

@Component({
  validations
})
export default class DocumentationEntryGroupUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentationEntryGroupService') private documentationEntryGroupService: () => DocumentationEntryGroupService;
  public documentationEntryGroup: IDocumentationEntryGroup = new DocumentationEntryGroup();

  @Inject('documentationSectionService') private documentationSectionService: () => DocumentationSectionService;

  public documentationSections: IDocumentationSection[] = [];

  @Inject('faqEntryService') private faqEntryService: () => FaqEntryService;

  public faqEntries: IFaqEntry[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentationEntryGroupId) {
        vm.retrieveDocumentationEntryGroup(to.params.documentationEntryGroupId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.documentationEntryGroup.id) {
      this.documentationEntryGroupService()
        .update(this.documentationEntryGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.documentationEntryGroup.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentationEntryGroupService()
        .create(this.documentationEntryGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.documentationEntryGroup.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocumentationEntryGroup(documentationEntryGroupId): void {
    this.documentationEntryGroupService()
      .find(documentationEntryGroupId)
      .then(res => {
        this.documentationEntryGroup = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentationSectionService()
      .retrieve()
      .then(res => {
        this.documentationSections = res.data;
      });
    this.faqEntryService()
      .retrieve()
      .then(res => {
        this.faqEntries = res.data;
      });
  }
}
