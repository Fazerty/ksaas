/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import DocumentationEntryService from '@/entities/documentation-entry/documentation-entry.service';
import { DocumentationEntry, Language } from '@/shared/model/documentation-entry.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('DocumentationEntry Service', () => {
    let service: DocumentationEntryService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new DocumentationEntryService();
      currentDate = new Date();

      elemDefault = new DocumentationEntry(0, Language.FR, 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            date: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a DocumentationEntry', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a DocumentationEntry', async () => {
        const returnedFromService = Object.assign(
          {
            language: 'BBBBBB',
            title: 'BBBBBB',
            content: 'BBBBBB',
            date: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of DocumentationEntry', async () => {
        const returnedFromService = Object.assign(
          {
            language: 'BBBBBB',
            title: 'BBBBBB',
            content: 'BBBBBB',
            date: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a DocumentationEntry', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
