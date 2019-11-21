import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISysRoleResources } from '@/shared/model/sys-role-resources.model';
import SysRoleResourcesService from './sys-role-resources.service';

@Component
export default class SysRoleResourcesDetails extends Vue {
  @Inject('sysRoleResourcesService') private sysRoleResourcesService: () => SysRoleResourcesService;
  public sysRoleResources: ISysRoleResources = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysRoleResourcesId) {
        vm.retrieveSysRoleResources(to.params.sysRoleResourcesId);
      }
    });
  }

  public retrieveSysRoleResources(sysRoleResourcesId) {
    this.sysRoleResourcesService()
      .find(sysRoleResourcesId)
      .then(res => {
        this.sysRoleResources = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
