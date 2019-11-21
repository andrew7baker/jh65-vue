import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISysUser } from '@/shared/model/sys-user.model';
import SysUserService from './sys-user.service';

@Component
export default class SysUserDetails extends Vue {
  @Inject('sysUserService') private sysUserService: () => SysUserService;
  public sysUser: ISysUser = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysUserId) {
        vm.retrieveSysUser(to.params.sysUserId);
      }
    });
  }

  public retrieveSysUser(sysUserId) {
    this.sysUserService()
      .find(sysUserId)
      .then(res => {
        this.sysUser = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
