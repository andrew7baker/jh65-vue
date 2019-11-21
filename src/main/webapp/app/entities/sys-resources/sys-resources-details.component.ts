import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISysResources } from '@/shared/model/sys-resources.model';
import SysResourcesService from './sys-resources.service';

@Component
export default class SysResourcesDetails extends Vue {
  @Inject('sysResourcesService') private sysResourcesService: () => SysResourcesService;
  public sysResources: ISysResources = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysResourcesId) {
        vm.retrieveSysResources(to.params.sysResourcesId);
      }
    });
  }

  public retrieveSysResources(sysResourcesId) {
    this.sysResourcesService()
      .find(sysResourcesId)
      .then(res => {
        this.sysResources = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
