<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="jhvueApp.sysUser.home.createOrEditLabel">Create or edit a SysUser</h2>
                <div>
                    <div class="form-group" v-if="sysUser.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sysUser.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-username">Username</label>
                        <input type="text" class="form-control" name="username" id="sys-user-username"
                            :class="{'valid': !$v.sysUser.username.$invalid, 'invalid': $v.sysUser.username.$invalid }" v-model="$v.sysUser.username.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-password">Password</label>
                        <input type="text" class="form-control" name="password" id="sys-user-password"
                            :class="{'valid': !$v.sysUser.password.$invalid, 'invalid': $v.sysUser.password.$invalid }" v-model="$v.sysUser.password.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-nickname">Nickname</label>
                        <input type="text" class="form-control" name="nickname" id="sys-user-nickname"
                            :class="{'valid': !$v.sysUser.nickname.$invalid, 'invalid': $v.sysUser.nickname.$invalid }" v-model="$v.sysUser.nickname.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-mobile">Mobile</label>
                        <input type="text" class="form-control" name="mobile" id="sys-user-mobile"
                            :class="{'valid': !$v.sysUser.mobile.$invalid, 'invalid': $v.sysUser.mobile.$invalid }" v-model="$v.sysUser.mobile.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-email">Email</label>
                        <input type="text" class="form-control" name="email" id="sys-user-email"
                            :class="{'valid': !$v.sysUser.email.$invalid, 'invalid': $v.sysUser.email.$invalid }" v-model="$v.sysUser.email.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="sys-user-qq">Qq</label>
                        <input type="text" class="form-control" name="qq" id="sys-user-qq"
                            :class="{'valid': !$v.sysUser.qq.$invalid, 'invalid': $v.sysUser.qq.$invalid }" v-model="$v.sysUser.qq.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sysUser.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sys-user-update.component.ts">
</script>
