/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SysRoleResourcesDetailComponent from '@/entities/sys-role-resources/sys-role-resources-details.vue';
import SysRoleResourcesClass from '@/entities/sys-role-resources/sys-role-resources-details.component';
import SysRoleResourcesService from '@/entities/sys-role-resources/sys-role-resources.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SysRoleResources Management Detail Component', () => {
    let wrapper: Wrapper<SysRoleResourcesClass>;
    let comp: SysRoleResourcesClass;
    let sysRoleResourcesServiceStub: SinonStubbedInstance<SysRoleResourcesService>;

    beforeEach(() => {
      sysRoleResourcesServiceStub = sinon.createStubInstance<SysRoleResourcesService>(SysRoleResourcesService);

      wrapper = shallowMount<SysRoleResourcesClass>(SysRoleResourcesDetailComponent, {
        store,
        localVue,
        provide: { sysRoleResourcesService: () => sysRoleResourcesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSysRoleResources = { id: 123 };
        sysRoleResourcesServiceStub.find.resolves(foundSysRoleResources);

        // WHEN
        comp.retrieveSysRoleResources(123);
        await comp.$nextTick();

        // THEN
        expect(comp.sysRoleResources).toBe(foundSysRoleResources);
      });
    });
  });
});
