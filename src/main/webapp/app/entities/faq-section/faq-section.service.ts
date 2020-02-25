import axios from 'axios';

import { IFaqSection } from '@/shared/model/faq-section.model';

const baseApiUrl = 'api/faq-sections';

export default class FaqSectionService {
  public find(id: number): Promise<IFaqSection> {
    return new Promise<IFaqSection>(resolve => {
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

  public create(entity: IFaqSection): Promise<IFaqSection> {
    return new Promise<IFaqSection>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IFaqSection): Promise<IFaqSection> {
    return new Promise<IFaqSection>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
