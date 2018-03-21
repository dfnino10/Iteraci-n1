package vos;

import java.util.List;

import org.codehaus.jackson.annotate.*;

/**
 * @author Nicol�s Mateo Hern�ndez Rojas 	- 	nm.hernandez10@uniandes.edu.co
 * @author David Felipe Ni�o Romero		-	df.nino10@uniandes.edu.co
 * Clase que representa a los Operadores del modelo AlohAndes
 */

public class Operador 
{
	@JsonProperty(value = "id")
	private long id;
	
	public enum CategoriaOperador
	{
		HOTEL,
		HOSTAL,
		PERSONA_NATURAL,
		MIEMBRO_DE_LA_COMUNIDAD,
		VECINO,
		VIVIENDA_UNIVERSITARIA
	}
	
	@JsonProperty(value = "categoria")
	private CategoriaOperador categoria;
	
	@JsonProperty(value = "nombre")
	private String nombre;
	
	@JsonProperty(value = "registro")
	private long registro;
	
	@JsonProperty(value = "espacios")
	private List<Espacio> espacios;
	
	public Operador(@JsonProperty(value = "id") long id, @JsonProperty(value = "registro") long registro, @JsonProperty(value = "nombre") String nombre, @JsonProperty(value = "categoria") CategoriaOperador categoria, @JsonProperty(value = "espacios") List<Espacio> espacios)
	{
		this.id = id;
		this.nombre = nombre;
		this.registro = registro;
		this.categoria = categoria;
		this.espacios = espacios;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public CategoriaOperador getCategoria() 
	{
		return categoria;
	}

	public void setCategoria(CategoriaOperador categoria) 
	{
		this.categoria = categoria;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public long getRegistro()
	{
		return registro;
	}

	public void setRegistro(long registro) 
	{
		this.registro = registro;
	}

	public List<Espacio> getEspacios() 
	{
		return espacios;
	}

	public void setEspacios(List<Espacio> espacios)
	{
		this.espacios = espacios;
	}	
}
