package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.SysResources;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysResources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysResourcesRepository extends JpaRepository<SysResources, Long> {

}
