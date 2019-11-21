<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhvueApp.sysRole.home.createOrEditLabel">Create or edit a SysRole</h2>
                <div>
                    <div class="form-group" v-if="sysRole.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sysRole.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-name">Name</label>
                        <input type="text" class="form-control" name="name" id="sys-role-name"
                            :class="{'valid': !$v.sysRole.name.$invalid, 'invalid': $v.sysRole.name.$invalid }" v-model="$v.sysRole.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-description">Description</label>
                        <input type="text" class="form-control" name="description" id="sys-role-description"
                            :class="{'valid': !$v.sysRole.description.$invalid, 'invalid': $v.sysRole.description.$invalid }" v-model="$v.sysRole.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-available">Available</label>
                        <input type="number" class="form-control" name="available" id="sys-role-available"
                            :class="{'valid': !$v.sysRole.available.$invalid, 'invalid': $v.sysRole.available.$invalid }" v-model.number="$v.sysRole.available.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-createTime">Create Time</label>
                        <div class="d-flex">
                            <input id="sys-role-createTime" type="datetime-local" class="form-control" name="createTime" :class="{'valid': !$v.sysRole.createTime.$invalid, 'invalid': $v.sysRole.createTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.sysRole.createTime.$model)"
                            @change="updateInstantField('createTime', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-role-updateTime">Update Time</label>
                        <div class="d-flex">
                            <input id="sys-role-updateTime" type="datetime-local" class="form-control" name="updateTime" :class="{'valid': !$v.sysRole.updateTime.$invalid, 'invalid': $v.sysRole.updateTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.sysRole.updateTime.$model)"
                            @change="updateInstantField('updateTime', $event)"/>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sysRole.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sys-role-update.component.ts">
</script>
