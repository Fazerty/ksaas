/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import DocumentationEntryComponentsPage, { DocumentationEntryDeleteDialog } from './documentation-entry.page-object';
import DocumentationEntryUpdatePage from './documentation-entry-update.page-object';
import DocumentationEntryDetailsPage from './documentation-entry-details.page-object';

import {
  clear,
  click,
  getRecordsCount,
  isVisible,
  selectLastOption,
  waitUntilAllDisplayed,
  waitUntilAnyDisplayed,
  waitUntilCount,
  waitUntilDisplayed,
  waitUntilHidden
} from '../../util/utils';

const expect = chai.expect;

describe('DocumentationEntry e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: DocumentationEntryUpdatePage;
  let detailsPage: DocumentationEntryDetailsPage;
  let listPage: DocumentationEntryComponentsPage;
  let deleteDialog: DocumentationEntryDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load DocumentationEntries', async () => {
    await navBarPage.getEntityPage('documentation-entry');
    listPage = new DocumentationEntryComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create DocumentationEntry page', async () => {
      await listPage.createButton.click();
      updatePage = new DocumentationEntryUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/ksaasApp.documentationEntry.home.createOrEditLabel/);
    });

    it('should create and save DocumentationEntries', async () => {
      await selectLastOption(updatePage.languageSelect);

      await updatePage.titleInput.sendKeys('title');
      expect(await updatePage.titleInput.getAttribute('value')).to.match(/title/);

      await waitUntilDisplayed(updatePage.contentInput);
      await updatePage.contentInput.sendKeys('content');

      expect(await updatePage.contentInput.getAttribute('value')).to.match(/content/);

      await updatePage.dateInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');
      expect(await updatePage.dateInput.getAttribute('value')).to.contain('2001-01-01T02:30');

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilDisplayed(listPage.successAlert);
      expect(await listPage.successAlert.isDisplayed()).to.be.true;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });

    describe('Details, Update, Delete flow', () => {
      after(async () => {
        const deleteButton = listPage.getDeleteButton(listPage.records.last());
        await click(deleteButton);

        deleteDialog = new DocumentationEntryDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/ksaasApp.documentationEntry.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details DocumentationEntry page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new DocumentationEntryDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit DocumentationEntry page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.titleInput.clear();
        await updatePage.titleInput.sendKeys('modified');
        expect(await updatePage.titleInput.getAttribute('value')).to.match(/modified/);

        await updatePage.contentInput.clear();
        await updatePage.contentInput.sendKeys('updatedcontent');
        expect(await updatePage.contentInput.getAttribute('value')).to.match(/updatedcontent/);

        await updatePage.dateInput.clear();
        await updatePage.dateInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');
        expect(await updatePage.dateInput.getAttribute('value')).to.contain('2019-01-01T02:30');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
