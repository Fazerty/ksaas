import Component from 'vue-class-component';
import { Vue } from 'vue-property-decorator';
import { Language, defaultLanguage } from '@/shared/config/store/translation-store';
import { Getter } from 'vuex-class';

export interface Employee {
  firstName: string;
  lastName: string;
  title: TranslatedText[];
  translatedTitle?: string;
  about: TranslatedText[];
  translatedAbout?: string;
  picture: string;
}

export interface TranslatedText {
  text: string;
  language: Language;
}

@Component({
  components: {}
})
export default class AboutUs extends Vue {
  @Getter('currentLanguage')
  private currentLanguage!: string;

  /**
   * The list of the company employees
   *
   * @private
   * @type {Employee[]}
   * @memberof AboutUs
   */
  private employees: Employee[] = [
    {
      firstName: 'Fabien',
      lastName: 'Dubail',
      title: [{ language: Language.EN, text: 'CEO' }, { language: Language.FR, text: 'CEO' }],
      about: [
        {
          language: Language.FR,
          text:
            'Fabien est le fondateur d’Upurion. Il a créé l’entreprise en 2019 chez lui,' +
            'travaillant sur ce projet à temps plein pendant un an.' +
            'et espère faire d’Upurion l’outil de gestion de données en ligne et desktop le plus populaire,' +
            'Fabien a appris l’informatique sur le tas.' +
            'Il s’intéresse notamment à la permaculture, au bricolage, aux chasses au trésor. '
        },
        {
          language: Language.EN,
          text:
            'Fabien is the founder of Upurion. He started the business in 2019 at home, ' +
            'working on this project full time for a year.' +
            'and hopes to make Upurion the most popular online data management and desktop tool,' +
            'Fabien learned computer science on the job.' +
            'He is particularly interested in permaculture, DIY, treasure hunts. '
        }
      ],
      picture: 'fabien_dubail.jpeg'
    }
  ];

  /**
   * Gets the title of the employee in the current language or if not found in the default language.
   *
   * @private
   * @param {Employee} employee
   * @returns
   * @memberof AboutUs
   */
  private getEmployeeTitle(employee: Employee): string {
    const title: TranslatedText[] = employee.title.filter((currTitle: TranslatedText) => {
      return currTitle.language.toLowerCase() === this.currentLanguage;
    });
    if (title.length > 0) {
      return title[0].text;
    } else {
      return employee.title.filter((currTitle: TranslatedText) => {
        return currTitle.language.toLowerCase() === defaultLanguage;
      })[0].text;
    }
  }

  /**
   * Gets the about text of the employee in the current language or if not found in the default language.
   *
   * @private
   * @param {Employee} employee
   * @returns
   * @memberof AboutUs
   */
  private getEmployeeAbout(employee: Employee): string {
    console.log(this.currentLanguage);
    const about: TranslatedText[] = employee.about.filter((currAbout: TranslatedText) => {
      return currAbout.language.toLowerCase() === this.currentLanguage;
    });
    if (about.length > 0) {
      return about[0].text;
    } else {
      return employee.about.filter((currAbout: TranslatedText) => {
        return currAbout.language.toLowerCase() === defaultLanguage;
      })[0].text;
    }
  }
}
