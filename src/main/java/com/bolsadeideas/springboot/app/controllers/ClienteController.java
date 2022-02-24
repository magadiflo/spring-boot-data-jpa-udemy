package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.services.IClienteService;

@Controller
@SessionAttributes("cliente") //Guardaremos en el atributo de la sessión el objeto cliente
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", this.clienteService.findAll());
		return "listar";
	}

	/**
	 * Para enviar datos a la vista podemos usar Model (como en el método listar())
	 * o Map como en este caso
	 */
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);

		return "form";
	}
	
	@RequestMapping(value = "/form/{id}") //Por defecto es método GET si no se le agrega
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if(id > 0) {
			cliente = this.clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "El id del cliente no existe en la BD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("titulo", "Editar Cliente");
		model.put("cliente", cliente);
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		String mensajeFlash = (cliente.getId() != null) ? "Los datos del cliente fueron actualizados con éxito" : "Se creó un nuevo cliente!";
		this.clienteService.save(cliente);
		status.setComplete();//Eliminamos el objeto cliente de la sessión
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listar";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if(id > 0) {
			this.clienteService.delete(id);
		}
		
		flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		return "redirect:/listar";
	}

}
