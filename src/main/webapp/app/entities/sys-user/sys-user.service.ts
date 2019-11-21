import axios from 'axios';

import { ISysUser } from '@/shared/model/sys-user.model';

const baseApiUrl = 'api/sys-users';

export default class SysUserService {
  public find(id: number): Promise<ISysUser> {
    return new Promise<ISysUser>(resolve => {
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

  public create(entity: ISysUser): Promise<ISysUser> {
    return new Promise<ISysUser>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ISysUser): Promise<ISysUser> {
    return new Promise<ISysUser>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
