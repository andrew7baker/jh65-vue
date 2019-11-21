<template>
    <div>
        <h2 id="page-heading">
            <span id="sys-user-heading">Sys Users</span>
            <router-link :to="{name: 'SysUserCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sys-user">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new Sys User
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
        <div class="alert alert-warning" v-if="!isFetching && sysUsers && sysUsers.length === 0">
            <span>No sysUsers found</span>
        </div>
        <div class="table-responsive" v-if="sysUsers && sysUsers.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>ID</span></th>
                    <th><span>Username</span></th>
                    <th><span>Password</span></th>
                    <th><span>Nickname</span></th>
                    <th><span>Mobile</span></th>
                    <th><span>Email</span></th>
                    <th><span>Qq</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sysUser in sysUsers"
                    :key="sysUser.id">
                    <td>
                        <router-link :to="{name: 'SysUserView', params: {sysUserId: sysUser.id}}">{{sysUser.id}}</router-link>
                    </td>
                    <td>{{sysUser.username}}</td>
                    <td>{{sysUser.password}}</td>
                    <td>{{sysUser.nickname}}</td>
                    <td>{{sysUser.mobile}}</td>
                    <td>{{sysUser.email}}</td>
                    <td>{{sysUser.qq}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SysUserView', params: {sysUserId: sysUser.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'SysUserEdit', params: {sysUserId: sysUser.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sysUser)"
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
            <span slot="modal-title"><span id="jhvueApp.sysUser.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sysUser-heading" >Are you sure you want to delete this Sys User?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sysUser" v-on:click="removeSysUser()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./sys-user.component.ts">
</script>
