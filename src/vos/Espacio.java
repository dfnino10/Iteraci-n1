package vos;

import java.util.Date;
import java.util.List;

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
	
	@JsonProperty(value="operador")
	private Operador operador;
	
	@JsonProperty(value="reservas")
	private List<Reserva> reservas;
	
	@JsonProperty(value="servicios")
	private List<Servicio> servicios;
	
	@JsonProperty(value="habitaciones")
	private List<Habitacion> habitaciones;
	
	public Espacio(@JsonProperty(value="id")long id,@JsonProperty(value="registro")long registro, @JsonProperty(value="capacidad")int capacidad, @JsonProperty(value="tama�o")double tama�o, @JsonProperty(value="ubicacion")String ubicacion, @JsonProperty(value="precio")double precio,@JsonProperty(value="fechaRetiro")Date fechaRetiro , @JsonProperty(value="operador") Operador operador, @JsonProperty(value="reservas") List<Reserva> reservas, @JsonProperty(value="servicios") List<Servicio> servicios, @JsonProperty(value="habitaciones") List<Habitacion> habitaciones)
	{
		this.id = id;
		this.registro = registro;
		this.capacidad = capacidad;
		this.tama�o = tama�o;
		this.ubicacion = ubicacion;
		this.precio = precio;
		this.fechaRetiro = fechaRetiro;
		this.operador = operador;
		this.reservas = reservas;
		this.servicios = servicios;
		this.habitaciones = habitaciones;
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

	public void setFechaRetiro(Date fechaCancelacion) 
	{
		this.fechaRetiro = fechaCancelacion;
	}

	public Operador getOperador()
	{
		return operador;
	}

	public void setOperador(Operador operador)
	{
		this.operador = operador;
	}

	public List<Reserva> getReservas()
	{
		return reservas;
	}

	public void setReservas(List<Reserva> reservas)
	{
		this.reservas = reservas;
	}

	public List<Servicio> getServicios()
	{
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) 
	{
		this.servicios = servicios;
	}

	public List<Habitacion> getHabitaciones() 
	{
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones)
	{
		this.habitaciones = habitaciones;
	}	
	
	public int calcularOcupacionEnFecha(java.util.Date date)
	{
		int ocupacion = 0;
		for(Reserva r : reservas)
		{
			if (r.getFechaInicio().before(date) && (r.getFechaInicio().getMonth()*30 + r.getFechaInicio().getDay() +r.getDuracion() <= date.getMonth()*30 + date.getDay()))
			{
				ocupacion ++;
			}
		}
		return ocupacion;
	}
	
	public void calcularCapacidad()
	{
		int capa = 0;
		
		for(Habitacion h: habitaciones)
		{
			capa += h.getCapacidad();
		}
		this.capacidad = capa;
	}
}
