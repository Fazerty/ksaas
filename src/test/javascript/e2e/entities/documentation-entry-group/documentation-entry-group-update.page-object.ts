import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class DocumentationEntryGroupUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('ksaasApp.documentationEntryGroup.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  blogSelect = element(by.css('select#documentation-entry-group-blog'));

  entrySelect = element(by.css('select#documentation-entry-group-entry'));
}
