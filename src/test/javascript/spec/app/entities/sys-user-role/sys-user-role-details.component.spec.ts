/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SysUserRoleDetailComponent from '@/entities/sys-user-role/sys-user-role-details.vue';
import SysUserRoleClass from '@/entities/sys-user-role/sys-user-role-details.component';
import SysUserRoleService from '@/entities/sys-user-role/sys-user-role.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SysUserRole Management Detail Component', () => {
    let wrapper: Wrapper<SysUserRoleClass>;
    let comp: SysUserRoleClass;
    let sysUserRoleServiceStub: SinonStubbedInstance<SysUserRoleService>;

    beforeEach(() => {
      sysUserRoleServiceStub = sinon.createStubInstance<SysUserRoleService>(SysUserRoleService);

      wrapper = shallowMount<SysUserRoleClass>(SysUserRoleDetailComponent, {
        store,
        localVue,
        provide: { sysUserRoleService: () => sysUserRoleServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSysUserRole = { id: 123 };
        sysUserRoleServiceStub.find.resolves(foundSysUserRole);

        // WHEN
        comp.retrieveSysUserRole(123);
        await comp.$nextTick();

        // THEN
        expect(comp.sysUserRole).toBe(foundSysUserRole);
      });
    });
  });
});
