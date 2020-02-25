import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TagService from '../tag/tag.service';
import { ITag } from '@/shared/model/tag.model';

import AlertService from '@/shared/alert/alert.service';
import { IBlogEntry, BlogEntry } from '@/shared/model/blog-entry.model';
import BlogEntryService from './blog-entry.service';

import marked from 'marked';

const validations: any = {
  blogEntry: {
    language: {},
    title: {
      required
    },
    content: {
      required
    },
    date: {
      required
    }
  }
};

@Component({
  validations
})
export default class BlogEntryUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('blogEntryService') private blogEntryService: () => BlogEntryService;
  public blogEntry: IBlogEntry = new BlogEntry();

  @Inject('tagService') private tagService: () => TagService;

  public tags: ITag[] = [];
  public isSaving = false;

  /**
   * Convert the blog content to markdown.
   *
   * @readonly
   * @memberof BlogEntryUpdate
   */
  get compiledMarkdown() {
    return marked(this.blogEntry.content, { sanitize: true });
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.blogEntryId) {
        vm.retrieveBlogEntry(to.params.blogEntryId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.blogEntry.tags = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.blogEntry.id) {
      this.blogEntryService()
        .update(this.blogEntry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.blogEntry.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.blogEntryService()
        .create(this.blogEntry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.blogEntry.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.blogEntry[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.blogEntry[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.blogEntry[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.blogEntry[field] = null;
    }
  }

  public retrieveBlogEntry(blogEntryId): void {
    this.blogEntryService()
      .find(blogEntryId)
      .then(res => {
        res.date = new Date(res.date);
        this.blogEntry = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.tagService()
      .retrieve()
      .then(res => {
        this.tags = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
