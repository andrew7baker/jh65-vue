/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SysRoleComponent from '@/entities/sys-role/sys-role.vue';
import SysRoleClass from '@/entities/sys-role/sys-role.component';
import SysRoleService from '@/entities/sys-role/sys-role.service';

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
  describe('SysRole Management Component', () => {
    let wrapper: Wrapper<SysRoleClass>;
    let comp: SysRoleClass;
    let sysRoleServiceStub: SinonStubbedInstance<SysRoleService>;

    beforeEach(() => {
      sysRoleServiceStub = sinon.createStubInstance<SysRoleService>(SysRoleService);
      sysRoleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SysRoleClass>(SysRoleComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          sysRoleService: () => sysRoleServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      sysRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSysRoles();
      await comp.$nextTick();

      // THEN
      expect(sysRoleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.sysRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      sysRoleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeSysRole();
      await comp.$nextTick();

      // THEN
      expect(sysRoleServiceStub.delete.called).toBeTruthy();
      expect(sysRoleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
