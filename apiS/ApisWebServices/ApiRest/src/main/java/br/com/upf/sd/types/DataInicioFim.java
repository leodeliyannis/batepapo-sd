package br.com.upf.sd.types;

import java.util.Date;

import javax.validation.constraints.NotNull;

import br.com.upf.sd.utils.JaxbValidator;
import br.com.upf.sd.utils.ValidationException;

public class DataInicioFim {
	
	@NotNull
	private Date dataHoraInicio;
	@NotNull
	private Date dataHoraFim;
	
	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}
	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	public Date getDataHoraFim() {
		return dataHoraFim;
	}
	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}
	
	public void validateFields() throws ValidationException {
		// Bloco generico que realiza a validação a partir das annotations
		JaxbValidator.validateNotNull(this, DataInicioFim.class);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataHoraFim == null) ? 0 : dataHoraFim.hashCode());
		result = prime * result + ((dataHoraInicio == null) ? 0 : dataHoraInicio.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataInicioFim other = (DataInicioFim) obj;
		if (dataHoraFim == null) {
			if (other.dataHoraFim != null)
				return false;
		} else if (!dataHoraFim.equals(other.dataHoraFim))
			return false;
		if (dataHoraInicio == null) {
			if (other.dataHoraInicio != null)
				return false;
		} else if (!dataHoraInicio.equals(other.dataHoraInicio))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DataInicioFimRequest [dataHoraInicio=" + dataHoraInicio + ", dataHoraFim=" + dataHoraFim + "]";
	}
}
