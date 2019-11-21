/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SysResourcesUpdateComponent from '@/entities/sys-resources/sys-resources-update.vue';
import SysResourcesClass from '@/entities/sys-resources/sys-resources-update.component';
import SysResourcesService from '@/entities/sys-resources/sys-resources.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('SysResources Management Update Component', () => {
    let wrapper: Wrapper<SysResourcesClass>;
    let comp: SysResourcesClass;
    let sysResourcesServiceStub: SinonStubbedInstance<SysResourcesService>;

    beforeEach(() => {
      sysResourcesServiceStub = sinon.createStubInstance<SysResourcesService>(SysResourcesService);

      wrapper = shallowMount<SysResourcesClass>(SysResourcesUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          sysResourcesService: () => sysResourcesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.sysResources = entity;
        sysResourcesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sysResourcesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.sysResources = entity;
        sysResourcesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sysResourcesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
