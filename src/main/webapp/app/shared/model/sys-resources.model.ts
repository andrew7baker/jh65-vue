export interface ISysResources {
  id?: number;
  name?: string;
  type?: string;
  url?: string;
  permission?: string;
  parentId?: number;
  sort?: number;
}

export class SysResources implements ISysResources {
  constructor(
    public id?: number,
    public name?: string,
    public type?: string,
    public url?: string,
    public permission?: string,
    public parentId?: number,
    public sort?: number
  ) {}
}
