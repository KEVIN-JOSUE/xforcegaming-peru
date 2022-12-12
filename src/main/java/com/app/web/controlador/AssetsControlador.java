package com.app.web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.web.servicio.AlmacenServicioImpl;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/assets")
public class AssetsControlador {
	
	
	@Autowired
	private AlmacenServicioImpl servicio;
	
	@GetMapping("/{filename:.+}")
	public Resource obtenerRecurso(@PathVariable("filename") String filename) {
		return  servicio.cargarComoRecurso(filename);
	}

}
