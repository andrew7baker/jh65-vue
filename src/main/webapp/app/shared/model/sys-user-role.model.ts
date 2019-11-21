export interface ISysUserRole {
  id?: number;
  userId?: string;
  roleId?: string;
  createTime?: Date;
  updateTime?: Date;
}

export class SysUserRole implements ISysUserRole {
  constructor(public id?: number, public userId?: string, public roleId?: string, public createTime?: Date, public updateTime?: Date) {}
}
