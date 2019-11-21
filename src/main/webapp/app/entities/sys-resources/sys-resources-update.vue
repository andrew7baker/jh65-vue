<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhvueApp.sysResources.home.createOrEditLabel">Create or edit a SysResources</h2>
                <div>
                    <div class="form-group" v-if="sysResources.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sysResources.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-resources-name">Name</label>
                        <input type="text" class="form-control" name="name" id="sys-resources-name"
                            :class="{'valid': !$v.sysResources.name.$invalid, 'invalid': $v.sysResources.name.$invalid }" v-model="$v.sysResources.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-resources-type">Type</label>
                        <input type="text" class="form-control" name="type" id="sys-resources-type"
                            :class="{'valid': !$v.sysResources.type.$invalid, 'invalid': $v.sysResources.type.$invalid }" v-model="$v.sysResources.type.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-resources-url">Url</label>
                        <input type="text" class="form-control" name="url" id="sys-resources-url"
                            :class="{'valid': !$v.sysResources.url.$invalid, 'invalid': $v.sysResources.url.$invalid }" v-model="$v.sysResources.url.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-resources-permission">Permission</label>
                        <input type="text" class="form-control" name="permission" id="sys-resources-permission"
                            :class="{'valid': !$v.sysResources.permission.$invalid, 'invalid': $v.sysResources.permission.$invalid }" v-model="$v.sysResources.permission.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-resources-parentId">Parent Id</label>
                        <input type="number" class="form-control" name="parentId" id="sys-resources-parentId"
                            :class="{'valid': !$v.sysResources.parentId.$invalid, 'invalid': $v.sysResources.parentId.$invalid }" v-model.number="$v.sysResources.parentId.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-resources-sort">Sort</label>
                        <input type="number" class="form-control" name="sort" id="sys-resources-sort"
                            :class="{'valid': !$v.sysResources.sort.$invalid, 'invalid': $v.sysResources.sort.$invalid }" v-model.number="$v.sysResources.sort.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sysResources.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sys-resources-update.component.ts">
</script>
