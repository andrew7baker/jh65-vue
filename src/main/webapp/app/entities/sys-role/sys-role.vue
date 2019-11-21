<template>
    <div>
        <h2 id="page-heading">
            <span id="sys-role-heading">Sys Roles</span>
            <router-link :to="{name: 'SysRoleCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sys-role">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new Sys Role
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
        <div class="alert alert-warning" v-if="!isFetching && sysRoles && sysRoles.length === 0">
            <span>No sysRoles found</span>
        </div>
        <div class="table-responsive" v-if="sysRoles && sysRoles.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>ID</span></th>
                    <th><span>Name</span></th>
                    <th><span>Description</span></th>
                    <th><span>Available</span></th>
                    <th><span>Create Time</span></th>
                    <th><span>Update Time</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sysRole in sysRoles"
                    :key="sysRole.id">
                    <td>
                        <router-link :to="{name: 'SysRoleView', params: {sysRoleId: sysRole.id}}">{{sysRole.id}}</router-link>
                    </td>
                    <td>{{sysRole.name}}</td>
                    <td>{{sysRole.description}}</td>
                    <td>{{sysRole.available}}</td>
                    <td>{{sysRole.createTime | formatDate}}</td>
                    <td>{{sysRole.updateTime | formatDate}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SysRoleView', params: {sysRoleId: sysRole.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'SysRoleEdit', params: {sysRoleId: sysRole.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sysRole)"
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
            <span slot="modal-title"><span id="jhvueApp.sysRole.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sysRole-heading" >Are you sure you want to delete this Sys Role?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sysRole" v-on:click="removeSysRole()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./sys-role.component.ts">
</script>
