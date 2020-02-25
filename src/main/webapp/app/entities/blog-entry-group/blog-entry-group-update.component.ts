import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import BlogSectionService from '../blog-section/blog-section.service';
import { IBlogSection } from '@/shared/model/blog-section.model';

import BlogEntryService from '../blog-entry/blog-entry.service';
import { IBlogEntry } from '@/shared/model/blog-entry.model';

import AlertService from '@/shared/alert/alert.service';
import { IBlogEntryGroup, BlogEntryGroup } from '@/shared/model/blog-entry-group.model';
import BlogEntryGroupService from './blog-entry-group.service';

const validations: any = {
  blogEntryGroup: {}
};

@Component({
  validations
})
export default class BlogEntryGroupUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('blogEntryGroupService') private blogEntryGroupService: () => BlogEntryGroupService;
  public blogEntryGroup: IBlogEntryGroup = new BlogEntryGroup();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('blogSectionService') private blogSectionService: () => BlogSectionService;

  public blogSections: IBlogSection[] = [];

  @Inject('blogEntryService') private blogEntryService: () => BlogEntryService;

  public blogEntries: IBlogEntry[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.blogEntryGroupId) {
        vm.retrieveBlogEntryGroup(to.params.blogEntryGroupId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.blogEntryGroup.id) {
      this.blogEntryGroupService()
        .update(this.blogEntryGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.blogEntryGroup.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.blogEntryGroupService()
        .create(this.blogEntryGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.blogEntryGroup.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveBlogEntryGroup(blogEntryGroupId): void {
    this.blogEntryGroupService()
      .find(blogEntryGroupId)
      .then(res => {
        this.blogEntryGroup = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.blogSectionService()
      .retrieve()
      .then(res => {
        this.blogSections = res.data;
      });
    this.blogEntryService()
      .retrieve()
      .then(res => {
        this.blogEntries = res.data;
      });
  }
}
