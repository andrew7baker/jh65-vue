<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sysUserRole">
                <h2 class="jh-entity-heading"><span>SysUserRole</span> {{sysUserRole.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>User Id</span>
                    </dt>
                    <dd>
                        <span>{{sysUserRole.userId}}</span>
                    </dd>
                    <dt>
                        <span>Role Id</span>
                    </dt>
                    <dd>
                        <span>{{sysUserRole.roleId}}</span>
                    </dd>
                    <dt>
                        <span>Create Time</span>
                    </dt>
                    <dd>
                        <span>{{sysUserRole.createTime | formatDate}}</span>
                    </dd>
                    <dt>
                        <span>Update Time</span>
                    </dt>
                    <dd>
                        <span>{{sysUserRole.updateTime | formatDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="sysUserRole.id" :to="{name: 'SysUserRoleEdit', params: {sysUserRoleId: sysUserRole.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sys-user-role-details.component.ts">
</script>
