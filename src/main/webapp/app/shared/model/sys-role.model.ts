export interface ISysRole {
  id?: number;
  name?: string;
  description?: string;
  available?: number;
  createTime?: Date;
  updateTime?: Date;
}

export class SysRole implements ISysRole {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public available?: number,
    public createTime?: Date,
    public updateTime?: Date
  ) {}
}
