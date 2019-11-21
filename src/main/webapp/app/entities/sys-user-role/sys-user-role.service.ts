import axios from 'axios';

import { ISysUserRole } from '@/shared/model/sys-user-role.model';

const baseApiUrl = 'api/sys-user-roles';

export default class SysUserRoleService {
  public find(id: number): Promise<ISysUserRole> {
    return new Promise<ISysUserRole>(resolve => {
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

  public create(entity: ISysUserRole): Promise<ISysUserRole> {
    return new Promise<ISysUserRole>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ISysUserRole): Promise<ISysUserRole> {
    return new Promise<ISysUserRole>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
