import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { ISysRoleResources, SysRoleResources } from '@/shared/model/sys-role-resources.model';
import SysRoleResourcesService from './sys-role-resources.service';

const validations: any = {
  sysRoleResources: {
    roleId: {},
    resourcesId: {},
    createTime: {},
    updateTime: {}
  }
};

@Component({
  validations
})
export default class SysRoleResourcesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysRoleResourcesService') private sysRoleResourcesService: () => SysRoleResourcesService;
  public sysRoleResources: ISysRoleResources = new SysRoleResources();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysRoleResourcesId) {
        vm.retrieveSysRoleResources(to.params.sysRoleResourcesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.sysRoleResources.id) {
      this.sysRoleResourcesService()
        .update(this.sysRoleResources)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysRoleResources is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sysRoleResourcesService()
        .create(this.sysRoleResources)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysRoleResources is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.sysRoleResources[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.sysRoleResources[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.sysRoleResources[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.sysRoleResources[field] = null;
    }
  }

  public retrieveSysRoleResources(sysRoleResourcesId): void {
    this.sysRoleResourcesService()
      .find(sysRoleResourcesId)
      .then(res => {
        res.createTime = new Date(res.createTime);
        res.updateTime = new Date(res.updateTime);
        this.sysRoleResources = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
