import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class DocumentationEntryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('ksaasApp.documentationEntry.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  languageSelect = element(by.css('select#documentation-entry-language'));

  titleInput: ElementFinder = element(by.css('input#documentation-entry-title'));

  contentInput: ElementFinder = element(by.css('textarea#documentation-entry-content'));

  dateInput: ElementFinder = element(by.css('input#documentation-entry-date'));
}
