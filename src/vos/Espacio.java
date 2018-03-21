package vos;

import java.sql.Date;
import org.codehaus.jackson.annotate.*;


/**
 * @author Nicol�s Mateo Hern�ndez Rojas 	- 	nm.hernandez10@uniandes.edu.co
 * @author David Felipe Ni�o Romero		-	df.nino10@uniandes.edu.co
 * Clase que representa a los Espacios del modelo AlohAndes
 */

public class Espacio 
{
	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="registro")
	private long registro;
	
	@JsonProperty(value="capacidad")
	private int capacidad;
	
	@JsonProperty(value="tama�o")
	private double tama�o;
	
	@JsonProperty(value="ubicacion")
	private String ubicacion;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="fechaRetiro")
	private Date fechaRetiro;
	
	public Espacio(@JsonProperty(value="id")long id,@JsonProperty(value="registro")long registro, @JsonProperty(value="capacidad")int capacidad, @JsonProperty(value="tama�o")double tama�o, @JsonProperty(value="ubicacion")String ubicacion, @JsonProperty(value="precio")double precio,@JsonProperty(value="fechaRetiro")Date fechaRetiro )
	{
		this.id = id;
		this.registro = registro;
		this.capacidad = capacidad;
		this.tama�o = tama�o;
		this.ubicacion = ubicacion;
		this.precio = precio;
		this.fechaRetiro = fechaRetiro;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public long getRegistro()
	{
		return registro;
	}

	public void setRegistro(long registro)
	{
		this.registro = registro;
	}

	public int getCapacidad() 
	{
		return capacidad;
	}

	public void setCapacidad(int capacidad) 
	{
		this.capacidad = capacidad;
	}

	public double getTama�o() 
	{
		return tama�o;
	}

	public void setTama�o(double tama�o) 
	{
		this.tama�o = tama�o;
	}

	public String getUbicacion() 
	{
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) 
	{
		this.ubicacion = ubicacion;
	}

	public double getPrecio() 
	{
		return precio;
	}

	public void setPrecio(double precio)
	{
		this.precio = precio;
	}

	public Date getFechaRetiro()
	{
		return fechaRetiro;
	}

	public void setFechaRetiro(Date fechaRetiro) 
	{
		this.fechaRetiro = fechaRetiro;
	}	
}
