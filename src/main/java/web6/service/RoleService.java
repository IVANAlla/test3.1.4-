package web6.service;

import web6.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    Set<Role> findByIdRoles(List<Long>roles);

    void addDefaultRole();
}
