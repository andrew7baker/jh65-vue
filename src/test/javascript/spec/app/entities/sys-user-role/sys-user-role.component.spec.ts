/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SysUserRoleComponent from '@/entities/sys-user-role/sys-user-role.vue';
import SysUserRoleClass from '@/entities/sys-user-role/sys-user-role.component';
import SysUserRoleService from '@/entities/sys-user-role/sys-user-role.service';

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
  describe('SysUserRole Management Component', () => {
    let wrapper: Wrapper<SysUserRoleClass>;
    let comp: SysUserRoleClass;
    let sysUserRoleServiceStub: SinonStubbedInstance<SysUserRoleService>;

    beforeEach(() => {
      sysUserRoleServiceStub = sinon.createStubInstance<SysUserRoleService>(SysUserRoleService);
      sysUserRoleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SysUserRoleClass>(SysUserRoleComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          sysUserRoleService: () => sysUserRoleServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      sysUserRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSysUserRoles();
      await comp.$nextTick();

      // THEN
      expect(sysUserRoleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.sysUserRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      sysUserRoleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeSysUserRole();
      await comp.$nextTick();

      // THEN
      expect(sysUserRoleServiceStub.delete.called).toBeTruthy();
      expect(sysUserRoleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
