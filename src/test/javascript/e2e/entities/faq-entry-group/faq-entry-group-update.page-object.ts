import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class FaqEntryGroupUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('ksaasApp.faqEntryGroup.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  blogSelect = element(by.css('select#faq-entry-group-blog'));

  entrySelect = element(by.css('select#faq-entry-group-entry'));
}
