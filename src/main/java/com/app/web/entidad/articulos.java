package com.app.web.entidad;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class articulos {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_articulos")
	private Integer id;
	
	@NotBlank
	private String titulo;
	
	@NotBlank
	private String sinopsis;
	
	@NotNull
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate fechaEstreno;
	
	@NotBlank
	private String youtuberTrailerID;
	
	private String rutaPortada;
	 
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="categoria_articulo",joinColumns = @JoinColumn(name="id_articulos"),inverseJoinColumns = @JoinColumn(name="id_categoria"))
	private List<categoria> categorias;
	
	@Transient
	private MultipartFile portada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public LocalDate getFechaEstreno() {
		return fechaEstreno;
	}

	public void setFechaEstreno(LocalDate fechaEstreno) {
		this.fechaEstreno = fechaEstreno;
	}

	public String getYoutuberTrailerID() {
		return youtuberTrailerID;
	}

	public void setYoutuberTrailerID(String youtuberTrailerID) {
		this.youtuberTrailerID = youtuberTrailerID;
	}

	public String getRutaPortada() {
		return rutaPortada;
	}

	public void setRutaPortada(String rutaPortada) {
		this.rutaPortada = rutaPortada;
	}

	public List<categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<categoria> categorias) {
		this.categorias = categorias;
	}

	public MultipartFile getPortada() {
		return portada;
	}

	public void setPortada(MultipartFile portada) {
		this.portada = portada;
	}

	public articulos() {
		super();
	}

	public articulos(Integer id, @NotBlank String titulo, @NotBlank String sinopsis, @NotNull LocalDate fechaEstreno,
			@NotBlank String youtuberTrailerID, String rutaPortada, @NotEmpty List<categoria> categorias,
			MultipartFile portada) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.fechaEstreno = fechaEstreno;
		this.youtuberTrailerID = youtuberTrailerID;
		this.rutaPortada = rutaPortada;
		this.categorias = categorias;
		this.portada = portada;
	}

	public articulos(@NotBlank String titulo, @NotBlank String sinopsis, @NotNull LocalDate fechaEstreno,
			@NotBlank String youtuberTrailerID, String rutaPortada, @NotEmpty List<categoria> categorias,
			MultipartFile portada) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.fechaEstreno = fechaEstreno;
		this.youtuberTrailerID = youtuberTrailerID;
		this.rutaPortada = rutaPortada;
		this.categorias = categorias;
		this.portada = portada;
	}
	
	
	
	
	
	
	
	
}
