/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import BlogEntryGroupComponentsPage, { BlogEntryGroupDeleteDialog } from './blog-entry-group.page-object';
import BlogEntryGroupUpdatePage from './blog-entry-group-update.page-object';
import BlogEntryGroupDetailsPage from './blog-entry-group-details.page-object';

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

describe('BlogEntryGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: BlogEntryGroupUpdatePage;
  let detailsPage: BlogEntryGroupDetailsPage;
  let listPage: BlogEntryGroupComponentsPage;
  let deleteDialog: BlogEntryGroupDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load BlogEntryGroups', async () => {
    await navBarPage.getEntityPage('blog-entry-group');
    listPage = new BlogEntryGroupComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create BlogEntryGroup page', async () => {
      await listPage.createButton.click();
      updatePage = new BlogEntryGroupUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/ksaasApp.blogEntryGroup.home.createOrEditLabel/);
    });

    it('should create and save BlogEntryGroups', async () => {
      // await  selectLastOption(updatePage.userSelect);
      // await  selectLastOption(updatePage.blogSelect);
      // await  selectLastOption(updatePage.entrySelect);

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

        deleteDialog = new BlogEntryGroupDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/ksaasApp.blogEntryGroup.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details BlogEntryGroup page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new BlogEntryGroupDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit BlogEntryGroup page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
