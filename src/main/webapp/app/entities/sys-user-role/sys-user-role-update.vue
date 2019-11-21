<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhvueApp.sysUserRole.home.createOrEditLabel">Create or edit a SysUserRole</h2>
                <div>
                    <div class="form-group" v-if="sysUserRole.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sysUserRole.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-role-userId">User Id</label>
                        <input type="text" class="form-control" name="userId" id="sys-user-role-userId"
                            :class="{'valid': !$v.sysUserRole.userId.$invalid, 'invalid': $v.sysUserRole.userId.$invalid }" v-model="$v.sysUserRole.userId.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-role-roleId">Role Id</label>
                        <input type="text" class="form-control" name="roleId" id="sys-user-role-roleId"
                            :class="{'valid': !$v.sysUserRole.roleId.$invalid, 'invalid': $v.sysUserRole.roleId.$invalid }" v-model="$v.sysUserRole.roleId.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-role-createTime">Create Time</label>
                        <div class="d-flex">
                            <input id="sys-user-role-createTime" type="datetime-local" class="form-control" name="createTime" :class="{'valid': !$v.sysUserRole.createTime.$invalid, 'invalid': $v.sysUserRole.createTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.sysUserRole.createTime.$model)"
                            @change="updateInstantField('createTime', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-role-updateTime">Update Time</label>
                        <div class="d-flex">
                            <input id="sys-user-role-updateTime" type="datetime-local" class="form-control" name="updateTime" :class="{'valid': !$v.sysUserRole.updateTime.$invalid, 'invalid': $v.sysUserRole.updateTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.sysUserRole.updateTime.$model)"
                            @change="updateInstantField('updateTime', $event)"/>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sysUserRole.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sys-user-role-update.component.ts">
</script>
