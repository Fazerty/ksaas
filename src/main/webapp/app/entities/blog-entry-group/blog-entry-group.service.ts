import axios from 'axios';

import { IBlogEntryGroup } from '@/shared/model/blog-entry-group.model';

const baseApiUrl = 'api/blog-entry-groups';

export default class BlogEntryGroupService {
  public find(id: number): Promise<IBlogEntryGroup> {
    return new Promise<IBlogEntryGroup>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl).then(function(res) {
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

  public create(entity: IBlogEntryGroup): Promise<IBlogEntryGroup> {
    return new Promise<IBlogEntryGroup>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IBlogEntryGroup): Promise<IBlogEntryGroup> {
    return new Promise<IBlogEntryGroup>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
