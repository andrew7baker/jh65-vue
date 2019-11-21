<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sysRoleResources">
                <h2 class="jh-entity-heading"><span>SysRoleResources</span> {{sysRoleResources.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Role Id</span>
                    </dt>
                    <dd>
                        <span>{{sysRoleResources.roleId}}</span>
                    </dd>
                    <dt>
                        <span>Resources Id</span>
                    </dt>
                    <dd>
                        <span>{{sysRoleResources.resourcesId}}</span>
                    </dd>
                    <dt>
                        <span>Create Time</span>
                    </dt>
                    <dd>
                        <span>{{sysRoleResources.createTime | formatDate}}</span>
                    </dd>
                    <dt>
                        <span>Update Time</span>
                    </dt>
                    <dd>
                        <span>{{sysRoleResources.updateTime | formatDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="sysRoleResources.id" :to="{name: 'SysRoleResourcesEdit', params: {sysRoleResourcesId: sysRoleResources.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sys-role-resources-details.component.ts">
</script>
