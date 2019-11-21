<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sysUser">
                <h2 class="jh-entity-heading"><span>SysUser</span> {{sysUser.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Username</span>
                    </dt>
                    <dd>
                        <span>{{sysUser.username}}</span>
                    </dd>
                    <dt>
                        <span>Password</span>
                    </dt>
                    <dd>
                        <span>{{sysUser.password}}</span>
                    </dd>
                    <dt>
                        <span>Nickname</span>
                    </dt>
                    <dd>
                        <span>{{sysUser.nickname}}</span>
                    </dd>
                    <dt>
                        <span>Mobile</span>
                    </dt>
                    <dd>
                        <span>{{sysUser.mobile}}</span>
                    </dd>
                    <dt>
                        <span>Email</span>
                    </dt>
                    <dd>
                        <span>{{sysUser.email}}</span>
                    </dd>
                    <dt>
                        <span>Qq</span>
                    </dt>
                    <dd>
                        <span>{{sysUser.qq}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="sysUser.id" :to="{name: 'SysUserEdit', params: {sysUserId: sysUser.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sys-user-details.component.ts">
</script>
