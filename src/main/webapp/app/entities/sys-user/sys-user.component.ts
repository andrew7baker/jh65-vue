import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISysUser } from '@/shared/model/sys-user.model';
import AlertService from '@/shared/alert/alert.service';

import SysUserService from './sys-user.service';

@Component
export default class SysUser extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysUserService') private sysUserService: () => SysUserService;
  private removeId: number = null;
  public sysUsers: ISysUser[] = [];

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
    this.retrieveAllSysUsers();
  }

  public clear(): void {
    this.retrieveAllSysUsers();
  }

  public retrieveAllSysUsers(): void {
    this.isFetching = true;

    this.sysUserService()
      .retrieve()
      .then(
        res => {
          this.sysUsers = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISysUser): void {
    this.removeId = instance.id;
  }

  public removeSysUser(): void {
    this.sysUserService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A SysUser is deleted with identifier ' + this.removeId;
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllSysUsers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
