import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBlogSection } from '@/shared/model/blog-section.model';
import BlogSectionService from './blog-section.service';

@Component
export default class BlogSectionDetails extends Vue {
  @Inject('blogSectionService') private blogSectionService: () => BlogSectionService;
  public blogSection: IBlogSection = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.blogSectionId) {
        vm.retrieveBlogSection(to.params.blogSectionId);
      }
    });
  }

  public retrieveBlogSection(blogSectionId) {
    this.blogSectionService()
      .find(blogSectionId)
      .then(res => {
        this.blogSection = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
