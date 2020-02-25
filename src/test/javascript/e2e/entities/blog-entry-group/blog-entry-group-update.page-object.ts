import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class BlogEntryGroupUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('ksaasApp.blogEntryGroup.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  userSelect = element(by.css('select#blog-entry-group-user'));

  blogSelect = element(by.css('select#blog-entry-group-blog'));

  entrySelect = element(by.css('select#blog-entry-group-entry'));
}
