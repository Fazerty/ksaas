import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class BlogEntryUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('ksaasApp.blogEntry.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  languageSelect = element(by.css('select#blog-entry-language'));

  titleInput: ElementFinder = element(by.css('input#blog-entry-title'));

  contentInput: ElementFinder = element(by.css('textarea#blog-entry-content'));

  dateInput: ElementFinder = element(by.css('input#blog-entry-date'));

  tagSelect = element(by.css('select#blog-entry-tag'));
}
