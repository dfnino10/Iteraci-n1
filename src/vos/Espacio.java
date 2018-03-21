package vos;

import java.sql.Date;
import org.codehaus.jackson.annotate.*;


/**
 * @author Nicolás Mateo Hernández Rojas 	- 	nm.hernandez10@uniandes.edu.co
 * @author David Felipe Niño Romero		-	df.nino10@uniandes.edu.co
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
	
	@JsonProperty(value="tamaño")
	private double tamaño;
	
	@JsonProperty(value="ubicacion")
	private String ubicacion;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="fechaRetiro")
	private Date fechaRetiro;
	
	public Espacio(@JsonProperty(value="id")long id,@JsonProperty(value="registro")long registro, @JsonProperty(value="capacidad")int capacidad, @JsonProperty(value="tamaño")double tamaño, @JsonProperty(value="ubicacion")String ubicacion, @JsonProperty(value="precio")double precio,@JsonProperty(value="fechaRetiro")Date fechaRetiro )
	{
		this.id = id;
		this.registro = registro;
		this.capacidad = capacidad;
		this.tamaño = tamaño;
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

	public double getTamaño() 
	{
		return tamaño;
	}

	public void setTamaño(double tamaño) 
	{
		this.tamaño = tamaño;
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
