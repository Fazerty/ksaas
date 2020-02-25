import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import LoginForm from '@/account/login-form/login-form.vue';
import { Watch } from 'vue-property-decorator';
import { Getter } from 'vuex-class';
import { BvToastOptions } from 'bootstrap-vue';

@Component({
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'login-form': LoginForm,
    'jhi-footer': JhiFooter
  }
})
export default class App extends Vue {
  @Getter('messages')
  private messages!: {
    text: string;
    displayed: boolean;
    timeout: number;
  }[];

  /**
   * Watch changes in app messages.
   * Creates a new toaster displayed at the top right for each messages.
   *
   * @private
   * @memberof App
   */
  @Watch('messages')
  private toast() {
    if (this.messages) {
      this.messages.forEach((message: { displayed: boolean; text: string; timeout: number; title?: string; type?: 'info' | 'warning' }) => {
        if (message.displayed === false) {
          const toaster = 'b-toaster-top-right';
          const options: BvToastOptions = {
            title: message.type ? message.type : `Info`,
            toaster,
            variant: message.type ? message.type : 'info',
            autoHideDelay: message.timeout,
            solid: true,
            appendToast: true
          };
          this.$bvToast.toast(message.text, options);
          message.displayed = true; // Messages displayed will be removed from the array in the store.
        }
      });
    }
  }
}
