import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import { ITranslatedName, Language } from '@/shared/model/translated-name.model';
import { IBlogSection, BlogSection } from '@/shared/model/blog-section.model';
import BlogSectionService from './blog-section.service';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';

const validations: any = {
  blogSection: {
    info: {
      required,
      minLength: minLength(2)
    },
    names: {
      $each: {
        language: {},
        name: {
          // required,
          // minLength: minLength(3)
        },
        slug: {
          // required,
          // minLength: minLength(3)
        }
      }
    }
  }
};

@Component({
  validations
})
export default class BlogSectionUpdate extends mixins(AlertMixin) {
  @Inject('blogSectionService') private blogSectionService: () => BlogSectionService;

  /**
   * The blog section to update/create
   *
   * @type {IBlogSection}
   * @memberof BlogSectionUpdate
   */
  public blogSection: IBlogSection = new BlogSection();

  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(async vm => {
      if (to.params.blogSectionId) {
        await vm.retrieveBlogSection(to.params.blogSectionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.blogSection.id) {
      this.blogSectionService()
        .update(this.blogSection)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.blogSection.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          const message = this.$t(error.message);
          this.alertService().showAlert(message, 'danger');
          this.isSaving = false;
        });
    } else {
      this.blogSectionService()
        .create(this.blogSection)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.blogSection.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          const message = this.$t(error.message);
          this.alertService().showAlert(error, 'danger');
          this.isSaving = false;
        });
    }
  }

  public async retrieveBlogSection(blogSectionId): Promise<void> {
    this.blogSection = await this.blogSectionService().find(blogSectionId);
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  // Returns an array from the Language enum.
  private getLanguageArray(): String[] {
    return Object.keys(Language).map(key => Language[key as keyof object]);
  }

  public initRelationships() {
    // Add names for missing languages.
    this.getLanguageArray().forEach((language: Language) => {
      let languageFound = false;
      this.blogSection.names.forEach((name: ITranslatedName) => {
        if (name.language === language) {
          languageFound = true;
        }
      });
      if (!languageFound) {
        this.blogSection.names.push({ language });
      }
    });
  }
}
