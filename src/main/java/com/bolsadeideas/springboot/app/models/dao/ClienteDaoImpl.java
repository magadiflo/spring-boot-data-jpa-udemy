package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Repository("clienteDaoJPA") //Agregamos el nombre, solo por si hay más de una implementación
public class ClienteDaoImpl implements IClienteDao {

	//Al no haber configuración de persistencia, por defecto usará la BD H2 cuya dependencia está en el pom.xml
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return this.em.createQuery("SELECT c FROM Cliente AS c", Cliente.class).getResultList();
	}

}
