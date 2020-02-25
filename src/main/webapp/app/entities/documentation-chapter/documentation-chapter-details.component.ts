import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDocumentationChapter } from '@/shared/model/documentation-chapter.model';
import DocumentationChapterService from './documentation-chapter.service';

@Component
export default class DocumentationChapterDetails extends Vue {
  @Inject('documentationChapterService') private documentationChapterService: () => DocumentationChapterService;
  public documentationChapter: IDocumentationChapter = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentationChapterId) {
        vm.retrieveDocumentationChapter(to.params.documentationChapterId);
      }
    });
  }

  public retrieveDocumentationChapter(documentationChapterId) {
    this.documentationChapterService()
      .find(documentationChapterId)
      .then(res => {
        this.documentationChapter = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
