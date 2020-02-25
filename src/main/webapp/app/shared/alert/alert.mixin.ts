import { Component, Inject, Vue } from 'vue-property-decorator';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AlertMixin extends Vue {
  @Inject('alertService') protected alertService: () => AlertService;

  // TODO remove
  public dismissCountDown: number = 0;
  public dismissSecs: number = 0;
  public alertType: string = 'info';
  public alertMessage: any = 'msg';

  public getAlertFromStore() {
    this.dismissCountDown = 0;
    this.dismissSecs = 0;
    this.alertType = 'info';
    this.alertMessage = 'msg';
  }

  public countDownChanged(dismissCountDown: number) {
    this.getAlertFromStore();
  }
}
