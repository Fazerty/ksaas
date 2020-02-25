import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IBlogEntry } from '@/shared/model/blog-entry.model';
import BlogEntryService from './blog-entry.service';

@Component
export default class BlogEntryDetails extends mixins(JhiDataUtils) {
  @Inject('blogEntryService') private blogEntryService: () => BlogEntryService;
  public blogEntry: IBlogEntry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.blogEntryId) {
        vm.retrieveBlogEntry(to.params.blogEntryId);
      }
    });
  }

  public retrieveBlogEntry(blogEntryId) {
    this.blogEntryService()
      .find(blogEntryId)
      .then(res => {
        this.blogEntry = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
