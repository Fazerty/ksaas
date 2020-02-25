import axios from 'axios';

import { IDocumentationEntry } from '@/shared/model/documentation-entry.model';

const baseApiUrl = 'api/documentation-entries';

export default class DocumentationEntryService {
  public find(id: number): Promise<IDocumentationEntry> {
    return new Promise<IDocumentationEntry>(resolve => {
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

  public create(entity: IDocumentationEntry): Promise<IDocumentationEntry> {
    return new Promise<IDocumentationEntry>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IDocumentationEntry): Promise<IDocumentationEntry> {
    return new Promise<IDocumentationEntry>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
