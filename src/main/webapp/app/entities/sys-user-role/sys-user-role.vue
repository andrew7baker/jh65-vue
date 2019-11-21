<template>
    <div>
        <h2 id="page-heading">
            <span id="sys-user-role-heading">Sys User Roles</span>
            <router-link :to="{name: 'SysUserRoleCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sys-user-role">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new Sys User Role
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && sysUserRoles && sysUserRoles.length === 0">
            <span>No sysUserRoles found</span>
        </div>
        <div class="table-responsive" v-if="sysUserRoles && sysUserRoles.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>ID</span></th>
                    <th><span>User Id</span></th>
                    <th><span>Role Id</span></th>
                    <th><span>Create Time</span></th>
                    <th><span>Update Time</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sysUserRole in sysUserRoles"
                    :key="sysUserRole.id">
                    <td>
                        <router-link :to="{name: 'SysUserRoleView', params: {sysUserRoleId: sysUserRole.id}}">{{sysUserRole.id}}</router-link>
                    </td>
                    <td>{{sysUserRole.userId}}</td>
                    <td>{{sysUserRole.roleId}}</td>
                    <td>{{sysUserRole.createTime | formatDate}}</td>
                    <td>{{sysUserRole.updateTime | formatDate}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SysUserRoleView', params: {sysUserRoleId: sysUserRole.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'SysUserRoleEdit', params: {sysUserRoleId: sysUserRole.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sysUserRole)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="jhvueApp.sysUserRole.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sysUserRole-heading" >Are you sure you want to delete this Sys User Role?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sysUserRole" v-on:click="removeSysUserRole()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./sys-user-role.component.ts">
</script>
