import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { ISysRole, SysRole } from '@/shared/model/sys-role.model';
import SysRoleService from './sys-role.service';

const validations: any = {
  sysRole: {
    name: {},
    description: {},
    available: {},
    createTime: {},
    updateTime: {}
  }
};

@Component({
  validations
})
export default class SysRoleUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysRoleService') private sysRoleService: () => SysRoleService;
  public sysRole: ISysRole = new SysRole();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysRoleId) {
        vm.retrieveSysRole(to.params.sysRoleId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.sysRole.id) {
      this.sysRoleService()
        .update(this.sysRole)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysRole is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sysRoleService()
        .create(this.sysRole)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysRole is created with identifier ' + param.id;
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
      this.sysRole[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.sysRole[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.sysRole[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.sysRole[field] = null;
    }
  }

  public retrieveSysRole(sysRoleId): void {
    this.sysRoleService()
      .find(sysRoleId)
      .then(res => {
        res.createTime = new Date(res.createTime);
        res.updateTime = new Date(res.updateTime);
        this.sysRole = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
