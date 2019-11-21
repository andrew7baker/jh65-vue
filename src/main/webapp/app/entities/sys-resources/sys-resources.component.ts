import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISysResources } from '@/shared/model/sys-resources.model';
import AlertService from '@/shared/alert/alert.service';

import SysResourcesService from './sys-resources.service';

@Component
export default class SysResources extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sysResourcesService') private sysResourcesService: () => SysResourcesService;
  private removeId: number = null;
  public sysResources: ISysResources[] = [];

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
    this.retrieveAllSysResourcess();
  }

  public clear(): void {
    this.retrieveAllSysResourcess();
  }

  public retrieveAllSysResourcess(): void {
    this.isFetching = true;

    this.sysResourcesService()
      .retrieve()
      .then(
        res => {
          this.sysResources = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISysResources): void {
    this.removeId = instance.id;
  }

  public removeSysResources(): void {
    this.sysResourcesService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A SysResources is deleted with identifier ' + this.removeId;
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllSysResourcess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
