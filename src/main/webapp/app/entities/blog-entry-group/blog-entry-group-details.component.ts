import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBlogEntryGroup } from '@/shared/model/blog-entry-group.model';
import BlogEntryGroupService from './blog-entry-group.service';

@Component
export default class BlogEntryGroupDetails extends Vue {
  @Inject('blogEntryGroupService') private blogEntryGroupService: () => BlogEntryGroupService;
  public blogEntryGroup: IBlogEntryGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.blogEntryGroupId) {
        vm.retrieveBlogEntryGroup(to.params.blogEntryGroupId);
      }
    });
  }

  public retrieveBlogEntryGroup(blogEntryGroupId) {
    this.blogEntryGroupService()
      .find(blogEntryGroupId)
      .then(res => {
        this.blogEntryGroup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
