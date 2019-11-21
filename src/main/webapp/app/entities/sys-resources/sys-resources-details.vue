<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sysResources">
                <h2 class="jh-entity-heading"><span>SysResources</span> {{sysResources.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Name</span>
                    </dt>
                    <dd>
                        <span>{{sysResources.name}}</span>
                    </dd>
                    <dt>
                        <span>Type</span>
                    </dt>
                    <dd>
                        <span>{{sysResources.type}}</span>
                    </dd>
                    <dt>
                        <span>Url</span>
                    </dt>
                    <dd>
                        <span>{{sysResources.url}}</span>
                    </dd>
                    <dt>
                        <span>Permission</span>
                    </dt>
                    <dd>
                        <span>{{sysResources.permission}}</span>
                    </dd>
                    <dt>
                        <span>Parent Id</span>
                    </dt>
                    <dd>
                        <span>{{sysResources.parentId}}</span>
                    </dd>
                    <dt>
                        <span>Sort</span>
                    </dt>
                    <dd>
                        <span>{{sysResources.sort}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="sysResources.id" :to="{name: 'SysResourcesEdit', params: {sysResourcesId: sysResources.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sys-resources-details.component.ts">
</script>
