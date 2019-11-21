/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SysResourcesComponent from '@/entities/sys-resources/sys-resources.vue';
import SysResourcesClass from '@/entities/sys-resources/sys-resources.component';
import SysResourcesService from '@/entities/sys-resources/sys-resources.service';

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
  describe('SysResources Management Component', () => {
    let wrapper: Wrapper<SysResourcesClass>;
    let comp: SysResourcesClass;
    let sysResourcesServiceStub: SinonStubbedInstance<SysResourcesService>;

    beforeEach(() => {
      sysResourcesServiceStub = sinon.createStubInstance<SysResourcesService>(SysResourcesService);
      sysResourcesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SysResourcesClass>(SysResourcesComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          sysResourcesService: () => sysResourcesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      sysResourcesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSysResourcess();
      await comp.$nextTick();

      // THEN
      expect(sysResourcesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.sysResources[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      sysResourcesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeSysResources();
      await comp.$nextTick();

      // THEN
      expect(sysResourcesServiceStub.delete.called).toBeTruthy();
      expect(sysResourcesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
