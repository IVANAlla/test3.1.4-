package web6.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import web6.model.Role;

public interface RoleDao extends JpaRepository<Role, Long> {


}
