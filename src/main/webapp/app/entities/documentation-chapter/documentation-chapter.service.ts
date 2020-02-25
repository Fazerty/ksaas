import axios from 'axios';

import { IDocumentationChapter } from '@/shared/model/documentation-chapter.model';

const baseApiUrl = 'api/documentation-chapters';

export default class DocumentationChapterService {
  public find(id: number): Promise<IDocumentationChapter> {
    return new Promise<IDocumentationChapter>(resolve => {
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

  public create(entity: IDocumentationChapter): Promise<IDocumentationChapter> {
    return new Promise<IDocumentationChapter>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IDocumentationChapter): Promise<IDocumentationChapter> {
    return new Promise<IDocumentationChapter>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
