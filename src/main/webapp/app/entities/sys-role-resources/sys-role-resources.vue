<template>
    <div>
        <h2 id="page-heading">
            <span id="sys-role-resources-heading">Sys Role Resources</span>
            <router-link :to="{name: 'SysRoleResourcesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sys-role-resources">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new Sys Role Resources
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
        <div class="alert alert-warning" v-if="!isFetching && sysRoleResources && sysRoleResources.length === 0">
            <span>No sysRoleResources found</span>
        </div>
        <div class="table-responsive" v-if="sysRoleResources && sysRoleResources.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>ID</span></th>
                    <th><span>Role Id</span></th>
                    <th><span>Resources Id</span></th>
                    <th><span>Create Time</span></th>
                    <th><span>Update Time</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sysRoleResources in sysRoleResources"
                    :key="sysRoleResources.id">
                    <td>
                        <router-link :to="{name: 'SysRoleResourcesView', params: {sysRoleResourcesId: sysRoleResources.id}}">{{sysRoleResources.id}}</router-link>
                    </td>
                    <td>{{sysRoleResources.roleId}}</td>
                    <td>{{sysRoleResources.resourcesId}}</td>
                    <td>{{sysRoleResources.createTime | formatDate}}</td>
                    <td>{{sysRoleResources.updateTime | formatDate}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SysRoleResourcesView', params: {sysRoleResourcesId: sysRoleResources.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'SysRoleResourcesEdit', params: {sysRoleResourcesId: sysRoleResources.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sysRoleResources)"
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
            <span slot="modal-title"><span id="jhvueApp.sysRoleResources.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sysRoleResources-heading" >Are you sure you want to delete this Sys Role Resources?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sysRoleResources" v-on:click="removeSysRoleResources()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./sys-role-resources.component.ts">
</script>
