import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBlogSection } from '@/shared/model/blog-section.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import BlogSectionService from './blog-section.service';
import { TranslatedName } from '@/shared/model/translated-name.model';

@Component
export default class BlogSection extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('blogSectionService') private blogSectionService: () => BlogSectionService;

  private removeId: number = null;

  public blogSections: IBlogSection[] = [];

  private currentLanguage = this.$store.getters.currentLanguage;

  public isFetching = false;

  public getBlocSectionName(blogSection: IBlogSection): TranslatedName {
    return blogSection.names.filter((name: TranslatedName) => {
      return name.language.toLowerCase() === this.currentLanguage;
    })[0];
  }

  public mounted(): void {
    this.retrieveAllBlogSections();
  }

  public clear(): void {
    this.retrieveAllBlogSections();
  }

  public retrieveAllBlogSections(): void {
    this.isFetching = true;

    this.blogSectionService()
      .retrieve()
      .then(
        res => {
          this.blogSections = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IBlogSection): void {
    this.removeId = instance.id;
  }

  public removeBlogSection(): void {
    this.blogSectionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ksaasApp.blogSection.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllBlogSections();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
