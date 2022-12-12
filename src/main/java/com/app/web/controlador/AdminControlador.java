package com.app.web.controlador;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Sort;

import com.app.web.entidad.articulos;
import com.app.web.entidad.categoria;
import com.app.web.repositorios.articulosRepositorio;
import com.app.web.repositorios.categoriaRepositorio;
import com.app.web.servicio.AlmacenServicioImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

	
	@Autowired
	private articulosRepositorio articlerepositorio;
	
	@Autowired
	private categoriaRepositorio categoryrepositorio;
	
	@Autowired
	private AlmacenServicioImpl servicio;
	
	@GetMapping("")
	public ModelAndView verPaginaDeInicio(@PageableDefault(sort="titulo",size=8)Pageable pageable) {
		 Page<articulos>articulo = articlerepositorio.findAll(pageable);
		 return new ModelAndView("admin/index").addObject("articulos",articulo);
	}
	
	@GetMapping("/articulos/nuevo")
	public ModelAndView mostrarFormularioDeNuevoArticulo() {
		List<categoria>categorias=categoryrepositorio.findAll(Sort.by("titulo"));
		return new ModelAndView("admin/nueva-articulos").addObject("articulo",new articulos()).addObject("categorias",categorias);
	}
	
	@PostMapping("/articulos/nuevo")
	public ModelAndView registrarArticulo(@Validated articulos articulo,BindingResult bindingResult) {
		if(bindingResult.hasErrors() || articulo.getPortada().isEmpty()) {
			if(articulo.getPortada().isEmpty()) {
				bindingResult.rejectValue("portada","MultipartNotEmpty"); 
			}
			List<categoria>categorias=categoryrepositorio.findAll(Sort.by("titulo"));
			return new ModelAndView("admin/nueva-articulos").addObject("articulo",articulo).addObject("categorias",categorias);
		}
		String rutaPortada =servicio.almacenarArchivo(articulo.getPortada());
		articulo.setRutaPortada(rutaPortada);
		
		articlerepositorio.save(articulo);
		return new ModelAndView("redirect:/admin");

	}
	
	@GetMapping("/articulos/{id}/editar")
	public ModelAndView mostrarFormularioDeEditarArticulo(@PathVariable Integer id) {
		articulos articulo =articlerepositorio.getOne(id);
		List<categoria>categorias=categoryrepositorio.findAll(Sort.by("titulo"));
		return new ModelAndView("admin/editar-articulos").addObject("articulo",articulo).addObject("categorias",categorias);
	}
	
	
	
	@PostMapping("/articulos/{id}/editar")
	public ModelAndView actualizarArticulo(@PathVariable Integer id,@Validated articulos articulo,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			List<categoria>categorias=categoryrepositorio.findAll(Sort.by("titulo"));
			return new ModelAndView("admin/editar-articulos").addObject("articulo",articulo).addObject("categorias",categorias);
		}
		articulos articuloDB =articlerepositorio.getOne(id);
		articuloDB.setTitulo(articulo.getTitulo());
		articuloDB.setSinopsis(articulo.getSinopsis());
		articuloDB.setFechaEstreno(articulo.getFechaEstreno());
		articuloDB.setYoutuberTrailerID(articulo.getYoutuberTrailerID());
		articuloDB.setCategorias(articulo.getCategorias());
		
		if(!articulo.getPortada().isEmpty()) {
			servicio.eliminarArchivo(articuloDB.getRutaPortada());
			String rutaPortada=servicio.almacenarArchivo(articulo.getPortada());
			articuloDB.setRutaPortada(rutaPortada);
		}
		articlerepositorio.save(articuloDB);
		return new ModelAndView("redirect:/admin");
	}
	
	@PostMapping("/articulos/{id}/eliminar")
	public String eliminarArticulo(@PathVariable Integer id){
		articulos articulo =articlerepositorio.getOne(id);
		articlerepositorio.delete(articulo);
		servicio.eliminarArchivo(articulo.getRutaPortada());
		
		return "redirect:/admin";
	}
	
	  
}
