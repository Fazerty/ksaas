import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBlogEntryGroup } from '@/shared/model/blog-entry-group.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import BlogEntryGroupService from './blog-entry-group.service';

@Component
export default class BlogEntryGroup extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('blogEntryGroupService') private blogEntryGroupService: () => BlogEntryGroupService;
  private removeId: number = null;
  public blogEntryGroups: IBlogEntryGroup[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBlogEntryGroups();
  }

  public clear(): void {
    this.retrieveAllBlogEntryGroups();
  }

  public retrieveAllBlogEntryGroups(): void {
    this.isFetching = true;

    this.blogEntryGroupService()
      .retrieve()
      .then(
        res => {
          this.blogEntryGroups = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IBlogEntryGroup): void {
    this.removeId = instance.id;
  }

  public removeBlogEntryGroup(): void {
    this.blogEntryGroupService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.blogEntryGroup.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllBlogEntryGroups();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
