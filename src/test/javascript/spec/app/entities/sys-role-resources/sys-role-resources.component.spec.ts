/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SysRoleResourcesComponent from '@/entities/sys-role-resources/sys-role-resources.vue';
import SysRoleResourcesClass from '@/entities/sys-role-resources/sys-role-resources.component';
import SysRoleResourcesService from '@/entities/sys-role-resources/sys-role-resources.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('SysRoleResources Management Component', () => {
    let wrapper: Wrapper<SysRoleResourcesClass>;
    let comp: SysRoleResourcesClass;
    let sysRoleResourcesServiceStub: SinonStubbedInstance<SysRoleResourcesService>;

    beforeEach(() => {
      sysRoleResourcesServiceStub = sinon.createStubInstance<SysRoleResourcesService>(SysRoleResourcesService);
      sysRoleResourcesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SysRoleResourcesClass>(SysRoleResourcesComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          sysRoleResourcesService: () => sysRoleResourcesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      sysRoleResourcesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSysRoleResourcess();
      await comp.$nextTick();

      // THEN
      expect(sysRoleResourcesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.sysRoleResources[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      sysRoleResourcesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeSysRoleResources();
      await comp.$nextTick();

      // THEN
      expect(sysRoleResourcesServiceStub.delete.called).toBeTruthy();
      expect(sysRoleResourcesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
