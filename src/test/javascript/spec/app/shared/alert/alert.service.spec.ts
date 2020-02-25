import { createLocalVue, Wrapper, shallowMount } from '@vue/test-utils';
import * as config from '@/shared/config/config';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
const store = config.initVueXStore(localVue);

describe('Alert service', () => {
  let alertService: AlertService;
});
