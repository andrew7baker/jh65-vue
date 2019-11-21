import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { ISysUserRole, SysUserRole } from '@/shared/model/sys-user-role.model';
import SysUserRoleService from './sys-user-role.service';

const validations: any = {
  sysUserRole: {
    userId: {},
    roleId: {},
    createTime: {},
    updateTime: {}
  }
};

@Component({
  validations
})
export default class SysUserRoleUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysUserRoleService') private sysUserRoleService: () => SysUserRoleService;
  public sysUserRole: ISysUserRole = new SysUserRole();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysUserRoleId) {
        vm.retrieveSysUserRole(to.params.sysUserRoleId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.sysUserRole.id) {
      this.sysUserRoleService()
        .update(this.sysUserRole)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysUserRole is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sysUserRoleService()
        .create(this.sysUserRole)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysUserRole is created with identifier ' + param.id;
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
      this.sysUserRole[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.sysUserRole[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.sysUserRole[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.sysUserRole[field] = null;
    }
  }

  public retrieveSysUserRole(sysUserRoleId): void {
    this.sysUserRoleService()
      .find(sysUserRoleId)
      .then(res => {
        res.createTime = new Date(res.createTime);
        res.updateTime = new Date(res.updateTime);
        this.sysUserRole = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
