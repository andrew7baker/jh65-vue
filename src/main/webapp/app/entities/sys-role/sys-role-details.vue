<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sysRole">
                <h2 class="jh-entity-heading"><span>SysRole</span> {{sysRole.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Name</span>
                    </dt>
                    <dd>
                        <span>{{sysRole.name}}</span>
                    </dd>
                    <dt>
                        <span>Description</span>
                    </dt>
                    <dd>
                        <span>{{sysRole.description}}</span>
                    </dd>
                    <dt>
                        <span>Available</span>
                    </dt>
                    <dd>
                        <span>{{sysRole.available}}</span>
                    </dd>
                    <dt>
                        <span>Create Time</span>
                    </dt>
                    <dd>
                        <span>{{sysRole.createTime | formatDate}}</span>
                    </dd>
                    <dt>
                        <span>Update Time</span>
                    </dt>
                    <dd>
                        <span>{{sysRole.updateTime | formatDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="sysRole.id" :to="{name: 'SysRoleEdit', params: {sysRoleId: sysRole.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sys-role-details.component.ts">
</script>
