import { Store } from 'vuex';

export default class JhiAlertService {
  private store: Store<{}>;

  constructor(store: Store<{}>) {
    this.store = store;
  }

  public showAlert(alertMessage: any, alertType = 'info', title?: String) {
    this.store.commit('newMessage', { msg: alertMessage, type: alertType, title });
  }
}
