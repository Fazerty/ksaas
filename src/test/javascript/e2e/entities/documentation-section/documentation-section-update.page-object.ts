import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class DocumentationSectionUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('ksaasApp.documentationSection.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  infoInput: ElementFinder = element(by.css('input#documentation-section-info'));

  blogSelect = element(by.css('select#documentation-section-blog'));
}
