import axios from 'axios';

import { IDocumentationEntryGroup } from '@/shared/model/documentation-entry-group.model';

const baseApiUrl = 'api/documentation-entry-groups';

export default class DocumentationEntryGroupService {
  public find(id: number): Promise<IDocumentationEntryGroup> {
    return new Promise<IDocumentationEntryGroup>(resolve => {
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

  public create(entity: IDocumentationEntryGroup): Promise<IDocumentationEntryGroup> {
    return new Promise<IDocumentationEntryGroup>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IDocumentationEntryGroup): Promise<IDocumentationEntryGroup> {
    return new Promise<IDocumentationEntryGroup>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
