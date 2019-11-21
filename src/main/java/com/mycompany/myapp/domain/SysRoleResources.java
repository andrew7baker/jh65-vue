package com.mycompany.myapp.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A SysRoleResources.
 */
@Entity
@Table(name = "sys_role_resources")
public class SysRoleResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "resources_id")
    private String resourcesId;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public SysRoleResources roleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourcesId() {
        return resourcesId;
    }

    public SysRoleResources resourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
        return this;
    }

    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public SysRoleResources createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public SysRoleResources updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysRoleResources)) {
            return false;
        }
        return id != null && id.equals(((SysRoleResources) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysRoleResources{" +
            "id=" + getId() +
            ", roleId='" + getRoleId() + "'" +
            ", resourcesId='" + getResourcesId() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
