import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISysRole } from '@/shared/model/sys-role.model';
import AlertService from '@/shared/alert/alert.service';

import SysRoleService from './sys-role.service';

@Component
export default class SysRole extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysRoleService') private sysRoleService: () => SysRoleService;
  private removeId: number = null;
  public sysRoles: ISysRole[] = [];

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
    this.retrieveAllSysRoles();
  }

  public clear(): void {
    this.retrieveAllSysRoles();
  }

  public retrieveAllSysRoles(): void {
    this.isFetching = true;

    this.sysRoleService()
      .retrieve()
      .then(
        res => {
          this.sysRoles = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISysRole): void {
    this.removeId = instance.id;
  }

  public removeSysRole(): void {
    this.sysRoleService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A SysRole is deleted with identifier ' + this.removeId;
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllSysRoles();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
