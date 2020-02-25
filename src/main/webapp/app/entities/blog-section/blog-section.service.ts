import axios from 'axios';

import { IBlogSection } from '@/shared/model/blog-section.model';

const baseApiUrl = 'api/blog-sections';

export default class BlogSectionService {
  public find(id: number): Promise<IBlogSection> {
    return new Promise<IBlogSection>(resolve => {
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

  public create(entity: IBlogSection): Promise<IBlogSection> {
    return new Promise<IBlogSection>(resolve => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(error => {
          console.log('create error');
          console.log(error);
        });
    });
  }

  public async update(entity: IBlogSection): Promise<IBlogSection> {
    return await axios
      .put(`${baseApiUrl}`, entity)
      .then(function(res) {
        return res.data as IBlogSection;
      })
      .catch(error => {
        throw error.response.data;
      });
  }
}
