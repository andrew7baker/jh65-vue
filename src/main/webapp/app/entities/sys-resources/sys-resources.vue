<template>
    <div>
        <h2 id="page-heading">
            <span id="sys-resources-heading">Sys Resources</span>
            <router-link :to="{name: 'SysResourcesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sys-resources">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new Sys Resources
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
        <div class="alert alert-warning" v-if="!isFetching && sysResources && sysResources.length === 0">
            <span>No sysResources found</span>
        </div>
        <div class="table-responsive" v-if="sysResources && sysResources.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>ID</span></th>
                    <th><span>Name</span></th>
                    <th><span>Type</span></th>
                    <th><span>Url</span></th>
                    <th><span>Permission</span></th>
                    <th><span>Parent Id</span></th>
                    <th><span>Sort</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sysResources in sysResources"
                    :key="sysResources.id">
                    <td>
                        <router-link :to="{name: 'SysResourcesView', params: {sysResourcesId: sysResources.id}}">{{sysResources.id}}</router-link>
                    </td>
                    <td>{{sysResources.name}}</td>
                    <td>{{sysResources.type}}</td>
                    <td>{{sysResources.url}}</td>
                    <td>{{sysResources.permission}}</td>
                    <td>{{sysResources.parentId}}</td>
                    <td>{{sysResources.sort}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SysResourcesView', params: {sysResourcesId: sysResources.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'SysResourcesEdit', params: {sysResourcesId: sysResources.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sysResources)"
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
            <span slot="modal-title"><span id="jhvueApp.sysResources.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sysResources-heading" >Are you sure you want to delete this Sys Resources?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sysResources" v-on:click="removeSysResources()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./sys-resources.component.ts">
</script>
