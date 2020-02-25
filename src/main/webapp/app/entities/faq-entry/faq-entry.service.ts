import axios from 'axios';

import { IFaqEntry } from '@/shared/model/faq-entry.model';

const baseApiUrl = 'api/faq-entries';

export default class FaqEntryService {
  public find(id: number): Promise<IFaqEntry> {
    return new Promise<IFaqEntry>(resolve => {
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

  public create(entity: IFaqEntry): Promise<IFaqEntry> {
    return new Promise<IFaqEntry>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IFaqEntry): Promise<IFaqEntry> {
    return new Promise<IFaqEntry>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
