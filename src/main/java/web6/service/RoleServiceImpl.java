package web6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web6.dao.RoleDao;
import web6.model.Role;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public Set<Role> findByIdRoles(List<Long> roles) {
        return new HashSet<>(roleDao.findAllById(roles));
    }

    @Override
    @PostConstruct
    public void addDefaultRole() {
        roleDao.save(new Role("ROLE_USER"));
        roleDao.save(new Role("ROLE_ADMIN"));
    }
}
