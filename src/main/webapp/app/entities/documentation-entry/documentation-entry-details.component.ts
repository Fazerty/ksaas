import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IDocumentationEntry } from '@/shared/model/documentation-entry.model';
import DocumentationEntryService from './documentation-entry.service';

@Component
export default class DocumentationEntryDetails extends mixins(JhiDataUtils) {
  @Inject('documentationEntryService') private documentationEntryService: () => DocumentationEntryService;
  public documentationEntry: IDocumentationEntry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentationEntryId) {
        vm.retrieveDocumentationEntry(to.params.documentationEntryId);
      }
    });
  }

  public retrieveDocumentationEntry(documentationEntryId) {
    this.documentationEntryService()
      .find(documentationEntryId)
      .then(res => {
        this.documentationEntry = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
