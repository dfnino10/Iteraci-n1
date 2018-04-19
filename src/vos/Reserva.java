package vos;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.*;

/**
 * @author Nicolás Mateo Hernández Rojas - nm.hernandez10@uniandes.edu.co
 * @author David Felipe Niño Romero - df.nino10@uniandes.edu.co Clase que
 *         representa a las Reservas del modelo AlohAndes
 */

public class Reserva {
	@JsonProperty(value = "idCliente")
	private long idCliente;

	@JsonProperty(value = "idEspacio")
	private long idEspacio;

	@JsonProperty(value = "fechaInicio")
	private String fechaInicio;

	private Date fechaInicioDate;
	
	@JsonProperty(value = "duracion")
	private int duracion;

	@JsonProperty(value = "fechaReserva")
	private String fechaReserva;

	private Date fechaReservaDate;
	
	@JsonProperty(value = "cancelado")
	private boolean cancelado;

	@JsonProperty(value = "precio")
	private double precio;

	public Reserva(@JsonProperty(value = "idCliente") long idCliente, @JsonProperty(value = "idEspacio") long idEspacio,
			@JsonProperty(value = "fechaInicio") String fechaInicio, @JsonProperty(value = "duracion") int duracion,
			@JsonProperty(value = "fechaReserva") String fechaReserva,
			@JsonProperty(value = "cancelado") boolean cancelado, @JsonProperty(value = "precio") double precio) {
		this.idCliente = idCliente;
		this.idEspacio = idEspacio;
		this.fechaInicio = fechaInicio;
		this.duracion = duracion;
		this.fechaReserva = fechaReserva;
		this.precio = precio;
		this.cancelado = cancelado;
		
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.fechaInicioDate = new Date(format.parse(this.fechaInicio).getTime());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.fechaReservaDate = new Date(format.parse(this.fechaReserva).getTime());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public String getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(String fechaReserva) {
		this.fechaReserva = fechaReserva;
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.fechaReservaDate = new Date(format.parse(this.fechaReserva).getTime());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public long getIdEspacio() {
		return idEspacio;
	}

	public void setIdEspacio(long idEspacio) {
		this.idEspacio = idEspacio;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.fechaInicioDate = new Date(format.parse(this.fechaInicio).getTime());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Date calcularFechaFin() {
		Date fechaFin = new Date();
		int meses = 0;
		if (duracion / 30 > 0) {
			meses = duracion / 30;
		}
		int dias = duracion - meses * 30;
		if (fechaInicioDate.getMonth() + meses > 12) {
			fechaFin.setYear(fechaInicioDate.getYear() + 1);
			fechaFin.setMonth(fechaInicioDate.getMonth() + meses - 12);
			fechaFin.setDate(dias);
		}

		return fechaFin;
	}

	public Date calcularFechaConDiasDespues(int diasMas) {
		Date fechaCon = new Date();
		int meses = 0;
		if (diasMas / 30 > 0) {
			meses = diasMas / 30;
		}
		int dias = diasMas - meses * 30;
		if (fechaInicioDate.getMonth() + meses > 12) {
			fechaCon.setYear(fechaInicioDate.getYear() + 1);
			fechaCon.setMonth(fechaInicioDate.getMonth() + meses - 12);
			fechaCon.setDate(dias);
		}

		return fechaCon;
	}

	public Date getFechaInicioDate() {
		return fechaInicioDate;
	}

	public void setFechaInicioDate(Date fechaInicioDate) {
		this.fechaInicioDate = fechaInicioDate;
		this.fechaInicio = (this.fechaInicioDate.getYear() +1900) + "-" + (this.fechaInicioDate.getMonth() +1) +"-" + this.fechaInicioDate.getDate();
	}

	public Date getFechaReservaDate() {
		return fechaReservaDate;
	}

	public void setFechaReservaDate(Date fechaReservaDate) {
		this.fechaReservaDate = fechaReservaDate;
		this.fechaReserva = (this.fechaReservaDate.getYear() +1900) + "-" + (this.fechaReservaDate.getMonth() +1) +"-" + this.fechaReservaDate.getDate();
	}	
}
