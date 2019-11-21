/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SysResourcesDetailComponent from '@/entities/sys-resources/sys-resources-details.vue';
import SysResourcesClass from '@/entities/sys-resources/sys-resources-details.component';
import SysResourcesService from '@/entities/sys-resources/sys-resources.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SysResources Management Detail Component', () => {
    let wrapper: Wrapper<SysResourcesClass>;
    let comp: SysResourcesClass;
    let sysResourcesServiceStub: SinonStubbedInstance<SysResourcesService>;

    beforeEach(() => {
      sysResourcesServiceStub = sinon.createStubInstance<SysResourcesService>(SysResourcesService);

      wrapper = shallowMount<SysResourcesClass>(SysResourcesDetailComponent, {
        store,
        localVue,
        provide: { sysResourcesService: () => sysResourcesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSysResources = { id: 123 };
        sysResourcesServiceStub.find.resolves(foundSysResources);

        // WHEN
        comp.retrieveSysResources(123);
        await comp.$nextTick();

        // THEN
        expect(comp.sysResources).toBe(foundSysResources);
      });
    });
  });
});
