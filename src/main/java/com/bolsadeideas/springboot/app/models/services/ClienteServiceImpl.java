package com.bolsadeideas.springboot.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Service
@Primary //Por si hay más de una implementación, le decimos que queremos que esta sea la principal, que la tome
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return this.clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		this.clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return this.clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.clienteDao.delete(id);
	}

}
