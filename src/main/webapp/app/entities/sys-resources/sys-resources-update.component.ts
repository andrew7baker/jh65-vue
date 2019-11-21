import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ISysResources, SysResources } from '@/shared/model/sys-resources.model';
import SysResourcesService from './sys-resources.service';

const validations: any = {
  sysResources: {
    name: {},
    type: {},
    url: {},
    permission: {},
    parentId: {},
    sort: {}
  }
};

@Component({
  validations
})
export default class SysResourcesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysResourcesService') private sysResourcesService: () => SysResourcesService;
  public sysResources: ISysResources = new SysResources();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysResourcesId) {
        vm.retrieveSysResources(to.params.sysResourcesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.sysResources.id) {
      this.sysResourcesService()
        .update(this.sysResources)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysResources is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sysResourcesService()
        .create(this.sysResources)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysResources is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSysResources(sysResourcesId): void {
    this.sysResourcesService()
      .find(sysResourcesId)
      .then(res => {
        this.sysResources = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
