export interface ISysUser {
  id?: number;
  username?: string;
  password?: string;
  nickname?: string;
  mobile?: string;
  email?: string;
  qq?: string;
}

export class SysUser implements ISysUser {
  constructor(
    public id?: number,
    public username?: string,
    public password?: string,
    public nickname?: string,
    public mobile?: string,
    public email?: string,
    public qq?: string
  ) {}
}
