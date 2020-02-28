import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';

@Component({
  components: {}
})
export default class Pricing extends Vue {
  // TODO: change the string by keys for translation
  private plans: { name: string; features: string[]; price?: string; disabled?: boolean; monthly?: boolean }[] = [
    {
      name: 'Desktop',
      features: ['Save data', '1M file upload max size', 'Windows/Mac/Linux']
    },
    {
      name: 'Standard',
      disabled: true,
      price: '20€',
      monthly: true,
      features: ['Multiple users', '10M file upload max size', 'Web']
    },
    {
      name: 'Enterprise',
      disabled: true,
      monthly: false,
      price: '250€',
      features: ['Multiple users', '10M file upload max size', 'In house']
    }
  ];

  private choose(name: String) {
    switch (name) {
      case 'Desktop':
        this.$router.push('/desktop/download');
        break;
      case 'Standard':
        break;
      case 'Enterprise':
        break;
    }
  }

  // Fields of the plan details table
  private fields = [
    { key: 'property', label: '' },
    { key: 'desktop', label: 'Desktop' },
    { key: 'standard', label: 'Standard' },
    { key: 'enterprise', label: 'Enterprise' }
  ];

  // Items of the plan details table
  // TODO: change the string by keys for translation
  private items = [
    { property: 'Save data', desktop: true, standard: false, enterprise: false },
    { property: 'File upload max size', desktop: '1 M', standard: '10 M', enterprise: '10 M' },
    { property: 'Multiple users', desktop: false, standard: true, enterprise: true }
  ];

  // Properties of the plan details table
  private striped = false;
  private bordered = false;
  private borderless = false;
  private outlined = false;
  private small = false;
  private hover = false;
  private dark = false;
  private fixed = false;
  private footClone = true;
  private headVariant: 'light' | 'dark' | null = 'light';
  private tableVariant = null;
  private noCollapse: false;
}
