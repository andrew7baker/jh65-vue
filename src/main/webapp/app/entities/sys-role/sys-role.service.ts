import axios from 'axios';

import { ISysRole } from '@/shared/model/sys-role.model';

const baseApiUrl = 'api/sys-roles';

export default class SysRoleService {
  public find(id: number): Promise<ISysRole> {
    return new Promise<ISysRole>(resolve => {
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

  public create(entity: ISysRole): Promise<ISysRole> {
    return new Promise<ISysRole>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ISysRole): Promise<ISysRole> {
    return new Promise<ISysRole>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
