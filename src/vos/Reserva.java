package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.*;

/**
 * @author Nicolás Mateo Hernández Rojas 	- 	nm.hernandez10@uniandes.edu.co
 * @author David Felipe Niño Romero		-	df.nino10@uniandes.edu.co
 * Clase que representa a las Reservas del modelo AlohAndes
 */


public class Reserva 
{
	@JsonProperty(value = "id")
	private long id;
	
	@JsonProperty(value = "fechaInicio")
	private Date fechaInicio;
	
	@JsonProperty(value = "duracion")
	private int duracion;
	
	public Reserva(@JsonProperty(value = "id") long id,@JsonProperty(value = "fechaInicio") Date fechaInicio, @JsonProperty(value = "duracion") int duracion)
	{
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.duracion = duracion;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Date getFechaInicio() 
	{
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) 
	{
		this.fechaInicio = fechaInicio;
	}

	public int getDuracion()
	{
		return duracion;
	}

	public void setDuracion(int duracion) 
	{
		this.duracion = duracion;
	}	
}
