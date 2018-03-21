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
	@JsonProperty(value = "idCliente")
	private long idCliente;
	
	@JsonProperty(value = "idEspacio")
	private long idEspacio;
	
	@JsonProperty(value = "fechaInicio")
	private Date fechaInicio;
	
	@JsonProperty(value = "duracion")
	private int duracion;
	
	public Reserva(@JsonProperty(value = "idCliente") long idCliente, @JsonProperty(value = "idEspacio") long idEspacio, @JsonProperty(value = "fechaInicio") Date fechaInicio, @JsonProperty(value = "duracion") int duracion)
	{
		this.idCliente = idCliente;
		this.idEspacio = idEspacio;
		this.fechaInicio = fechaInicio;
		this.duracion = duracion;
	}
	
	public long getIdCliente() 
	{
		return idCliente;
	}


	public void setIdCliente(long idCliente)
	{
		this.idCliente = idCliente;
	}


	public long getIdEspacio()
	{
		return idEspacio;
	}


	public void setIdEspacio(long idEspacio)
	{
		this.idEspacio = idEspacio;
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
