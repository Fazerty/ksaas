import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IBlogEntry } from '@/shared/model/blog-entry.model';

const baseApiUrl = 'api/blog-entries';

export default class BlogEntryService {
  public find(id: number): Promise<IBlogEntry> {
    return new Promise<IBlogEntry>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity: IBlogEntry): Promise<IBlogEntry> {
    return new Promise<IBlogEntry>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IBlogEntry): Promise<IBlogEntry> {
    return new Promise<IBlogEntry>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
