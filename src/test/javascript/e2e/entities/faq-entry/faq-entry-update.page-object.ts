import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class FaqEntryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('ksaasApp.faqEntry.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  languageSelect = element(by.css('select#faq-entry-language'));

  titleInput: ElementFinder = element(by.css('input#faq-entry-title'));

  contentInput: ElementFinder = element(by.css('textarea#faq-entry-content'));

  dateInput: ElementFinder = element(by.css('input#faq-entry-date'));
}
