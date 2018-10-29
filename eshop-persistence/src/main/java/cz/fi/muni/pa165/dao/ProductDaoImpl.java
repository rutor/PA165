/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Product;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

/**
 *
 * @author xrudolf
 */
@Repository
public class ProductDaoImpl implements ProductDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Product p) {
        em.persist(p);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = em.createQuery("select p from Product p", Product.class).getResultList();
        return products;
    }

    @Override
    public Product findById(Long id) {
        Product p = em.find(Product.class, id);
        return p;
    }

    @Override
    public void remove(Product p) {
        p = em.merge(p);
        em.remove(p);
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> p = em.createQuery("select p from Product p where p.name = :name", Product.class).setParameter("name", name).getResultList();
        return p;
    }
    
}
