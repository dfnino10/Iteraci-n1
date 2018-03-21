package vos;

import java.util.Date;

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
	
	@JsonProperty(value = "fechaReserva")
	private Date fechaReserva;
	
	@JsonProperty(value = "cancelado")
	private boolean cancelado;
	
	@JsonProperty(value = "precio")
	private double precio;
	
	public Reserva(@JsonProperty(value = "idCliente") long idCliente, @JsonProperty(value = "idEspacio") long idEspacio, @JsonProperty(value = "fechaInicio") Date fechaInicio, @JsonProperty(value = "duracion") int duracion, @JsonProperty(value = "fechaReserva") Date fechaReserva, @JsonProperty(value = "cancelado")boolean cancelado, @JsonProperty(value = "precio")double precio)
	{
		this.idCliente = idCliente;
		this.idEspacio = idEspacio;
		this.fechaInicio = fechaInicio;
		this.duracion = duracion;
		this.fechaReserva = fechaReserva;
		this.precio = precio;
		this.cancelado = cancelado;
	}
	
	public double getPrecio() 
	{		
		return precio;
	}

	public void setPrecio(double precio) 
	{
		this.precio = precio;
	}

	public long getIdCliente() 
	{
		return idCliente;
	}
	
	public boolean isCancelado()
	{
		return cancelado;
	}

	public void setCancelado(boolean cancelado) 
	{
		this.cancelado = cancelado;
	}

	public void setIdCliente(long idCliente)
	{
		this.idCliente = idCliente;
	}

	public Date getFechaReserva() 
	{
		return fechaReserva;
	}

	public void setFechaReserva(Date fechaReserva) 
	{
		this.fechaReserva = fechaReserva;
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
	
	public Date calcularFechaFin()
	{
		Date fechaFin = new Date();
		int meses = 0;
		if(duracion/30 > 0)
		{
			meses = (Integer)(duracion/30);
		}
		int dias = duracion - meses*30;
		if(fechaInicio.getMonth() + meses > 12)
		{
			fechaFin.setYear(fechaInicio.getYear()+1);
			fechaFin.setMonth(fechaInicio.getMonth()+meses-12);
			fechaFin.setDate(dias);
		}
		
		return fechaFin;		
	}
	
	public Date calcularFechaConDiasDespues(int diasMas)
	{
		Date fechaCon = new Date();
		int meses = 0;
		if(diasMas/30 > 0)
		{
			meses = (Integer)(diasMas/30);
		}
		int dias = diasMas - meses*30;
		if(fechaInicio.getMonth() + meses > 12)
		{
			fechaCon.setYear(fechaInicio.getYear()+1);
			fechaCon.setMonth(fechaInicio.getMonth()+meses-12);
			fechaCon.setDate(dias);
		}
		
		return fechaCon;
	}
}
