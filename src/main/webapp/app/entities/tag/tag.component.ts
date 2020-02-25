import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITag } from '@/shared/model/tag.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import TagService from './tag.service';

@Component
export default class Tag extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('tagService') private tagService: () => TagService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = true;
  public totalItems = 0;
  public tags: ITag[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTags();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllTags();
  }

  public retrieveAllTags(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.tagService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.tags = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITag): void {
    this.removeId = instance.id;
  }

  public removeTag(): void {
    this.tagService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.tag.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllTags();
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
    this.retrieveAllTags();
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
