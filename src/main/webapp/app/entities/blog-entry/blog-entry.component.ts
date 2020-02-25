import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBlogEntry } from '@/shared/model/blog-entry.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import BlogEntryService from './blog-entry.service';

@Component
export default class BlogEntry extends mixins(JhiDataUtils, Vue2Filters.mixin, AlertMixin) {
  @Inject('blogEntryService') private blogEntryService: () => BlogEntryService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = true;
  public totalItems = 0;
  public blogEntries: IBlogEntry[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBlogEntrys();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllBlogEntrys();
  }

  public retrieveAllBlogEntrys(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.blogEntryService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.blogEntries = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IBlogEntry): void {
    this.removeId = instance.id;
  }

  public removeBlogEntry(): void {
    this.blogEntryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.blogEntry.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllBlogEntrys();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllBlogEntrys();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
