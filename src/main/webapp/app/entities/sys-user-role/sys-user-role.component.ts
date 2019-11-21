import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISysUserRole } from '@/shared/model/sys-user-role.model';
import AlertService from '@/shared/alert/alert.service';

import SysUserRoleService from './sys-user-role.service';

@Component
export default class SysUserRole extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysUserRoleService') private sysUserRoleService: () => SysUserRoleService;
  private removeId: number = null;
  public sysUserRoles: ISysUserRole[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllSysUserRoles();
  }

  public clear(): void {
    this.retrieveAllSysUserRoles();
  }

  public retrieveAllSysUserRoles(): void {
    this.isFetching = true;

    this.sysUserRoleService()
      .retrieve()
      .then(
        res => {
          this.sysUserRoles = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISysUserRole): void {
    this.removeId = instance.id;
  }

  public removeSysUserRole(): void {
    this.sysUserRoleService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A SysUserRole is deleted with identifier ' + this.removeId;
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllSysUserRoles();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
