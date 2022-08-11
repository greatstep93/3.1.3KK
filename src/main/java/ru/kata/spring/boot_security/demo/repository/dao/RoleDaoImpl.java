package ru.kata.spring.boot_security.demo.repository.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getAllRole() {
        return entityManager.createQuery("select distinct r from Role r", Role.class)
                .getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> getRolesByIds(List<Long> ids) {
        return entityManager
                .createQuery("select r from Role r where r.id in :ids", Role.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
