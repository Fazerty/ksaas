import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDocumentationSection } from '@/shared/model/documentation-section.model';
import DocumentationSectionService from './documentation-section.service';

@Component
export default class DocumentationSectionDetails extends Vue {
  @Inject('documentationSectionService') private documentationSectionService: () => DocumentationSectionService;
  public documentationSection: IDocumentationSection = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentationSectionId) {
        vm.retrieveDocumentationSection(to.params.documentationSectionId);
      }
    });
  }

  public retrieveDocumentationSection(documentationSectionId) {
    this.documentationSectionService()
      .find(documentationSectionId)
      .then(res => {
        this.documentationSection = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
