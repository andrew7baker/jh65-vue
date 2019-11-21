import axios from 'axios';

import { ISysResources } from '@/shared/model/sys-resources.model';

const baseApiUrl = 'api/sys-resources';

export default class SysResourcesService {
  public find(id: number): Promise<ISysResources> {
    return new Promise<ISysResources>(resolve => {
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

  public create(entity: ISysResources): Promise<ISysResources> {
    return new Promise<ISysResources>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ISysResources): Promise<ISysResources> {
    return new Promise<ISysResources>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
