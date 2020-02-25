import axios from 'axios';

import { IFaqEntryGroup } from '@/shared/model/faq-entry-group.model';

const baseApiUrl = 'api/faq-entry-groups';

export default class FaqEntryGroupService {
  public find(id: number): Promise<IFaqEntryGroup> {
    return new Promise<IFaqEntryGroup>(resolve => {
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

  public create(entity: IFaqEntryGroup): Promise<IFaqEntryGroup> {
    return new Promise<IFaqEntryGroup>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IFaqEntryGroup): Promise<IFaqEntryGroup> {
    return new Promise<IFaqEntryGroup>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
