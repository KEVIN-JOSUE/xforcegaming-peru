package com.app.web.controlador;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.entidad.articulos;
import com.app.web.repositorios.articulosRepositorio;

@Controller
@RequestMapping("/")
public class HomeControlador {

	@Autowired
	private  articulosRepositorio articlerepositorio;
	
	
	@GetMapping("")
	public ModelAndView verPaginaDeInicio() {
		List<articulos> ultimosArticulos=articlerepositorio.findAll(PageRequest.of(0,3,Sort.by("fechaEstreno").descending())).toList();
		return new ModelAndView("index").addObject("ultimosArticulos",ultimosArticulos);
	}
	
	@GetMapping("/articulos")
	public ModelAndView listarArticulos(@PageableDefault(sort="fechaEstreno",direction = Sort.Direction.DESC)Pageable pageable) {
	     Page<articulos>articulo=articlerepositorio.findAll(pageable);
	     return new ModelAndView("articulos").addObject("articulos",articulo);
	}
	
	@GetMapping("/articulos/{id}")
	public ModelAndView mostrarDetallesDeArticulos(@PathVariable Integer id) {
		articulos articulo=articlerepositorio.getOne(id);
		return new ModelAndView("articulo").addObject("articulo",articulo);
	}
	
}
