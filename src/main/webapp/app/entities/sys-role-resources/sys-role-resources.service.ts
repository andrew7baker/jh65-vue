import axios from 'axios';

import { ISysRoleResources } from '@/shared/model/sys-role-resources.model';

const baseApiUrl = 'api/sys-role-resources';

export default class SysRoleResourcesService {
  public find(id: number): Promise<ISysRoleResources> {
    return new Promise<ISysRoleResources>(resolve => {
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

  public create(entity: ISysRoleResources): Promise<ISysRoleResources> {
    return new Promise<ISysRoleResources>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ISysRoleResources): Promise<ISysRoleResources> {
    return new Promise<ISysRoleResources>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
