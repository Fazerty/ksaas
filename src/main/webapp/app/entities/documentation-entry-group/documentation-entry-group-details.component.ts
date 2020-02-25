import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDocumentationEntryGroup } from '@/shared/model/documentation-entry-group.model';
import DocumentationEntryGroupService from './documentation-entry-group.service';

@Component
export default class DocumentationEntryGroupDetails extends Vue {
  @Inject('documentationEntryGroupService') private documentationEntryGroupService: () => DocumentationEntryGroupService;
  public documentationEntryGroup: IDocumentationEntryGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentationEntryGroupId) {
        vm.retrieveDocumentationEntryGroup(to.params.documentationEntryGroupId);
      }
    });
  }

  public retrieveDocumentationEntryGroup(documentationEntryGroupId) {
    this.documentationEntryGroupService()
      .find(documentationEntryGroupId)
      .then(res => {
        this.documentationEntryGroup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
