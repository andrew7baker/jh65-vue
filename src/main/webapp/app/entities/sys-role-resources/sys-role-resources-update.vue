<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhvueApp.sysRoleResources.home.createOrEditLabel">Create or edit a SysRoleResources</h2>
                <div>
                    <div class="form-group" v-if="sysRoleResources.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sysRoleResources.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-resources-roleId">Role Id</label>
                        <input type="text" class="form-control" name="roleId" id="sys-role-resources-roleId"
                            :class="{'valid': !$v.sysRoleResources.roleId.$invalid, 'invalid': $v.sysRoleResources.roleId.$invalid }" v-model="$v.sysRoleResources.roleId.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-resources-resourcesId">Resources Id</label>
                        <input type="text" class="form-control" name="resourcesId" id="sys-role-resources-resourcesId"
                            :class="{'valid': !$v.sysRoleResources.resourcesId.$invalid, 'invalid': $v.sysRoleResources.resourcesId.$invalid }" v-model="$v.sysRoleResources.resourcesId.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-resources-createTime">Create Time</label>
                        <div class="d-flex">
                            <input id="sys-role-resources-createTime" type="datetime-local" class="form-control" name="createTime" :class="{'valid': !$v.sysRoleResources.createTime.$invalid, 'invalid': $v.sysRoleResources.createTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.sysRoleResources.createTime.$model)"
                            @change="updateInstantField('createTime', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-resources-updateTime">Update Time</label>
                        <div class="d-flex">
                            <input id="sys-role-resources-updateTime" type="datetime-local" class="form-control" name="updateTime" :class="{'valid': !$v.sysRoleResources.updateTime.$invalid, 'invalid': $v.sysRoleResources.updateTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.sysRoleResources.updateTime.$model)"
                            @change="updateInstantField('updateTime', $event)"/>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sysRoleResources.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sys-role-resources-update.component.ts">
</script>
