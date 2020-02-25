import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import { ITranslatedName } from '@/shared/model/translated-name.model';

import BlogEntryService from '../blog-entry/blog-entry.service';
import { IBlogEntry } from '@/shared/model/blog-entry.model';

import AlertService from '@/shared/alert/alert.service';
import { ITag, Tag } from '@/shared/model/tag.model';
import TagService from './tag.service';

const validations: any = {
  tag: {}
};

@Component({
  validations
})
export default class TagUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('tagService') private tagService: () => TagService;
  public tag: ITag = new Tag();

  public translatedNames: ITranslatedName[] = [];

  @Inject('blogEntryService') private blogEntryService: () => BlogEntryService;

  public blogEntries: IBlogEntry[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tagId) {
        vm.retrieveTag(to.params.tagId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.tag.id) {
      this.tagService()
        .update(this.tag)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.tag.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.tagService()
        .create(this.tag)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.tag.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTag(tagId): void {
    this.tagService()
      .find(tagId)
      .then(res => {
        this.tag = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.blogEntryService()
      .retrieve()
      .then(res => {
        this.blogEntries = res.data;
      });
  }
}
