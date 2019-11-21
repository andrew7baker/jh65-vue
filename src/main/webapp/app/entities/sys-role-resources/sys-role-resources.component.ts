import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISysRoleResources } from '@/shared/model/sys-role-resources.model';
import AlertService from '@/shared/alert/alert.service';

import SysRoleResourcesService from './sys-role-resources.service';

@Component
export default class SysRoleResources extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysRoleResourcesService') private sysRoleResourcesService: () => SysRoleResourcesService;
  private removeId: number = null;
  public sysRoleResources: ISysRoleResources[] = [];

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
    this.retrieveAllSysRoleResourcess();
  }

  public clear(): void {
    this.retrieveAllSysRoleResourcess();
  }

  public retrieveAllSysRoleResourcess(): void {
    this.isFetching = true;

    this.sysRoleResourcesService()
      .retrieve()
      .then(
        res => {
          this.sysRoleResources = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISysRoleResources): void {
    this.removeId = instance.id;
  }

  public removeSysRoleResources(): void {
    this.sysRoleResourcesService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A SysRoleResources is deleted with identifier ' + this.removeId;
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllSysRoleResourcess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
