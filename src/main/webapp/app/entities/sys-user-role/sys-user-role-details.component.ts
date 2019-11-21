import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISysUserRole } from '@/shared/model/sys-user-role.model';
import SysUserRoleService from './sys-user-role.service';

@Component
export default class SysUserRoleDetails extends Vue {
  @Inject('sysUserRoleService') private sysUserRoleService: () => SysUserRoleService;
  public sysUserRole: ISysUserRole = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysUserRoleId) {
        vm.retrieveSysUserRole(to.params.sysUserRoleId);
      }
    });
  }

  public retrieveSysUserRole(sysUserRoleId) {
    this.sysUserRoleService()
      .find(sysUserRoleId)
      .then(res => {
        this.sysUserRole = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
