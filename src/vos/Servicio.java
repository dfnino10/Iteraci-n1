package vos;

import org.codehaus.jackson.annotate.*;

/**
 * @author Nicolás Mateo Hernández Rojas 	- 	nm.hernandez10@uniandes.edu.co
 * @author David Felipe Niño Romero		-	df.nino10@uniandes.edu.co
 * Clase que representa a los Servicios del modelo AlohAndes
 */

public class Servicio 
{
	@JsonProperty(value = "id")
	private long id;
	
	public enum CategoriaServicio
	{
		SEGURO,
		RECEPCIÓN,
		COCINA,
		BAÑO_COMPARTIDO,
		BAÑO_PRIVADO,
		GIMNASIO,
		ASEO,
		TV,
		INTERNET,
		SERVICIOS_PÚBLICOS,
		OTRO
	}
	
	@JsonProperty(value = "categoria")
	private CategoriaServicio categoria;
	
	@JsonProperty(value = "descripcion")
	private String descripcion;
	
	@JsonProperty(value = "precioAdicional")
	private double precioAdicional;
	
	@JsonProperty(value = "inicioHorario")
	private int inicioHorario;
	
	@JsonProperty(value = "finHorario")
	private int finHorario;
	
	public Servicio(@JsonProperty(value = "id") long id, @JsonProperty(value = "categoria") CategoriaServicio categoria, @JsonProperty(value = "descripcion") String descripcion, @JsonProperty(value = "precioAdicional") double precioAdicional, @JsonProperty(value = "inicioHorario") int inicioHorario, @JsonProperty(value = "finHorario") int finHorario)
	{
		this.id = id;
		this.categoria = categoria;
		this.descripcion = descripcion;
		this.precioAdicional = precioAdicional;
		this.inicioHorario = inicioHorario;
		this.finHorario = finHorario;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public CategoriaServicio getCategoria() 
	{
		return categoria;
	}

	public void setCategoria(CategoriaServicio categoria)
	{
		this.categoria = categoria;
	}

	public String getDescripcion() 
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	public double getPrecioAdicional() 
	{
		return precioAdicional;
	}

	public void setPrecioAdicional(double precioAdicional) 
	{
		this.precioAdicional = precioAdicional;
	}

	public int getInicioHorario()
	{
		return inicioHorario;
	}

	public void setInicioHorario(int inicioHorario)
	{
		this.inicioHorario = inicioHorario;
	}

	public int getFinHorario()
	{
		return finHorario;
	}

	public void setFinHorario(int finHorario) 
	{
		this.finHorario = finHorario;
	}	
}
