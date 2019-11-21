export interface ISysRoleResources {
  id?: number;
  roleId?: string;
  resourcesId?: string;
  createTime?: Date;
  updateTime?: Date;
}

export class SysRoleResources implements ISysRoleResources {
  constructor(
    public id?: number,
    public roleId?: string,
    public resourcesId?: string,
    public createTime?: Date,
    public updateTime?: Date
  ) {}
}
