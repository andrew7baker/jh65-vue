import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
const Home = () => import('../core/home/home.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
/* tslint:disable */
// prettier-ignore
const SysRole = () => import('../entities/sys-role/sys-role.vue');
// prettier-ignore
const SysRoleUpdate = () => import('../entities/sys-role/sys-role-update.vue');
// prettier-ignore
const SysRoleDetails = () => import('../entities/sys-role/sys-role-details.vue');
// prettier-ignore
const SysRoleResources = () => import('../entities/sys-role-resources/sys-role-resources.vue');
// prettier-ignore
const SysRoleResourcesUpdate = () => import('../entities/sys-role-resources/sys-role-resources-update.vue');
// prettier-ignore
const SysRoleResourcesDetails = () => import('../entities/sys-role-resources/sys-role-resources-details.vue');
// prettier-ignore
const SysUser = () => import('../entities/sys-user/sys-user.vue');
// prettier-ignore
const SysUserUpdate = () => import('../entities/sys-user/sys-user-update.vue');
// prettier-ignore
const SysUserDetails = () => import('../entities/sys-user/sys-user-details.vue');
// prettier-ignore
const SysUserRole = () => import('../entities/sys-user-role/sys-user-role.vue');
// prettier-ignore
const SysUserRoleUpdate = () => import('../entities/sys-user-role/sys-user-role-update.vue');
// prettier-ignore
const SysUserRoleDetails = () => import('../entities/sys-user-role/sys-user-role-details.vue');
// prettier-ignore
const SysResources = () => import('../entities/sys-resources/sys-resources.vue');
// prettier-ignore
const SysResourcesUpdate = () => import('../entities/sys-resources/sys-resources-update.vue');
// prettier-ignore
const SysResourcesDetails = () => import('../entities/sys-resources/sys-resources-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    }
    ,
    {
      path: '/entity/sys-role',
      name: 'SysRole',
      component: SysRole,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-role/new',
      name: 'SysRoleCreate',
      component: SysRoleUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-role/:sysRoleId/edit',
      name: 'SysRoleEdit',
      component: SysRoleUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-role/:sysRoleId/view',
      name: 'SysRoleView',
      component: SysRoleDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/sys-role-resources',
      name: 'SysRoleResources',
      component: SysRoleResources,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-role-resources/new',
      name: 'SysRoleResourcesCreate',
      component: SysRoleResourcesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-role-resources/:sysRoleResourcesId/edit',
      name: 'SysRoleResourcesEdit',
      component: SysRoleResourcesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-role-resources/:sysRoleResourcesId/view',
      name: 'SysRoleResourcesView',
      component: SysRoleResourcesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/sys-user',
      name: 'SysUser',
      component: SysUser,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-user/new',
      name: 'SysUserCreate',
      component: SysUserUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-user/:sysUserId/edit',
      name: 'SysUserEdit',
      component: SysUserUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-user/:sysUserId/view',
      name: 'SysUserView',
      component: SysUserDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/sys-user-role',
      name: 'SysUserRole',
      component: SysUserRole,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-user-role/new',
      name: 'SysUserRoleCreate',
      component: SysUserRoleUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-user-role/:sysUserRoleId/edit',
      name: 'SysUserRoleEdit',
      component: SysUserRoleUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-user-role/:sysUserRoleId/view',
      name: 'SysUserRoleView',
      component: SysUserRoleDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/sys-resources',
      name: 'SysResources',
      component: SysResources,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-resources/new',
      name: 'SysResourcesCreate',
      component: SysResourcesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-resources/:sysResourcesId/edit',
      name: 'SysResourcesEdit',
      component: SysResourcesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/sys-resources/:sysResourcesId/view',
      name: 'SysResourcesView',
      component: SysResourcesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
});
