package com.hkg.quarkus.airport.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.hkg.quarkus.airport.entity.Airport;

@ApplicationScoped
public class AirportRepository {

	/**
	 * 
	 */
	@Inject
	EntityManager entityManager;

	/**
	 * 
	 * @param airport
	 * @return
	 */
	@Transactional
	public Airport createOrUpdate(Airport airport) {
		if (airport.getId() == null) {
			this.entityManager.persist(airport);
			return airport;
		} else {
			return this.entityManager.merge(airport);
		}
	}

	/**
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteById(Long id) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaDelete<Airport> delete = cb.createCriteriaDelete(Airport.class);
		Root<Airport> root = delete.from(Airport.class);
		delete.where(cb.equal(root.get("id"), id));
		this.entityManager.createQuery(delete).executeUpdate();
	}

	/**
	 * 
	 * @return
	 */
	public List<Airport> getAllAirports() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Airport> cq = cb.createQuery(Airport.class);
		Root<Airport> rootEntry = cq.from(Airport.class);
		CriteriaQuery<Airport> all = cq.select(rootEntry);
		TypedQuery<Airport> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();

	}


}