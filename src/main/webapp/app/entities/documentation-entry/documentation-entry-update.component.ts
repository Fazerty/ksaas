import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentationEntry, DocumentationEntry } from '@/shared/model/documentation-entry.model';
import DocumentationEntryService from './documentation-entry.service';

const validations: any = {
  documentationEntry: {
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
export default class DocumentationEntryUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentationEntryService') private documentationEntryService: () => DocumentationEntryService;
  public documentationEntry: IDocumentationEntry = new DocumentationEntry();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentationEntryId) {
        vm.retrieveDocumentationEntry(to.params.documentationEntryId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.documentationEntry.id) {
      this.documentationEntryService()
        .update(this.documentationEntry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.documentationEntry.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentationEntryService()
        .create(this.documentationEntry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.documentationEntry.created', { param: param.id });
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
      this.documentationEntry[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.documentationEntry[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.documentationEntry[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.documentationEntry[field] = null;
    }
  }

  public retrieveDocumentationEntry(documentationEntryId): void {
    this.documentationEntryService()
      .find(documentationEntryId)
      .then(res => {
        res.date = new Date(res.date);
        this.documentationEntry = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
