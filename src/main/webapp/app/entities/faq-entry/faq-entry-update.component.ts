import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { IFaqEntry, FaqEntry } from '@/shared/model/faq-entry.model';
import FaqEntryService from './faq-entry.service';

const validations: any = {
  faqEntry: {
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
export default class FaqEntryUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('faqEntryService') private faqEntryService: () => FaqEntryService;
  public faqEntry: IFaqEntry = new FaqEntry();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.faqEntryId) {
        vm.retrieveFaqEntry(to.params.faqEntryId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.faqEntry.id) {
      this.faqEntryService()
        .update(this.faqEntry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.faqEntry.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.faqEntryService()
        .create(this.faqEntry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ksaasApp.faqEntry.created', { param: param.id });
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
      this.faqEntry[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.faqEntry[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.faqEntry[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.faqEntry[field] = null;
    }
  }

  public retrieveFaqEntry(faqEntryId): void {
    this.faqEntryService()
      .find(faqEntryId)
      .then(res => {
        res.date = new Date(res.date);
        this.faqEntry = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
