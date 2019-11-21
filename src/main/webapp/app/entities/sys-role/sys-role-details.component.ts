import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISysRole } from '@/shared/model/sys-role.model';
import SysRoleService from './sys-role.service';

@Component
export default class SysRoleDetails extends Vue {
  @Inject('sysRoleService') private sysRoleService: () => SysRoleService;
  public sysRole: ISysRole = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysRoleId) {
        vm.retrieveSysRole(to.params.sysRoleId);
      }
    });
  }

  public retrieveSysRole(sysRoleId) {
    this.sysRoleService()
      .find(sysRoleId)
      .then(res => {
        this.sysRole = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
