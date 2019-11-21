package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.SysRoleResources;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysRoleResources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleResourcesRepository extends JpaRepository<SysRoleResources, Long> {

}
