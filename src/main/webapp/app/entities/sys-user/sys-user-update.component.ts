import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ISysUser, SysUser } from '@/shared/model/sys-user.model';
import SysUserService from './sys-user.service';

const validations: any = {
  sysUser: {
    username: {},
    password: {},
    nickname: {},
    mobile: {},
    email: {},
    qq: {}
  }
};

@Component({
  validations
})
export default class SysUserUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysUserService') private sysUserService: () => SysUserService;
  public sysUser: ISysUser = new SysUser();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sysUserId) {
        vm.retrieveSysUser(to.params.sysUserId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.sysUser.id) {
      this.sysUserService()
        .update(this.sysUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysUser is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sysUserService()
        .create(this.sysUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SysUser is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSysUser(sysUserId): void {
    this.sysUserService()
      .find(sysUserId)
      .then(res => {
        this.sysUser = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
