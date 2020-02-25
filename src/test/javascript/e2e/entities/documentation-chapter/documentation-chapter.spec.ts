/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import DocumentationChapterComponentsPage, { DocumentationChapterDeleteDialog } from './documentation-chapter.page-object';
import DocumentationChapterUpdatePage from './documentation-chapter-update.page-object';
import DocumentationChapterDetailsPage from './documentation-chapter-details.page-object';

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

describe('DocumentationChapter e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: DocumentationChapterUpdatePage;
  let detailsPage: DocumentationChapterDetailsPage;
  let listPage: DocumentationChapterComponentsPage;
  let deleteDialog: DocumentationChapterDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load DocumentationChapters', async () => {
    await navBarPage.getEntityPage('documentation-chapter');
    listPage = new DocumentationChapterComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create DocumentationChapter page', async () => {
      await listPage.createButton.click();
      updatePage = new DocumentationChapterUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/ksaasApp.documentationChapter.home.createOrEditLabel/);
    });

    it('should create and save DocumentationChapters', async () => {
      await updatePage.infoInput.sendKeys('info');
      expect(await updatePage.infoInput.getAttribute('value')).to.match(/info/);

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

        deleteDialog = new DocumentationChapterDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/ksaasApp.documentationChapter.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details DocumentationChapter page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new DocumentationChapterDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit DocumentationChapter page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.infoInput.clear();
        await updatePage.infoInput.sendKeys('modified');
        expect(await updatePage.infoInput.getAttribute('value')).to.match(/modified/);

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
