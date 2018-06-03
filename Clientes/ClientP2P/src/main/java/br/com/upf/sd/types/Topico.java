package br.com.upf.sd.types;

import java.io.Serializable;

public class Topico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String topico;
	private String ipAddres;
	private String status;
	
	public String getTopico() {
		return topico;
	}
	public void setTopico(String topico) {
		this.topico = topico;
	}
	public String getIpAddres() {
		return ipAddres;
	}
	public void setIpAddres(String ipAddres) {
		this.ipAddres = ipAddres;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipAddres == null) ? 0 : ipAddres.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((topico == null) ? 0 : topico.hashCode());
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
		Topico other = (Topico) obj;
		if (ipAddres == null) {
			if (other.ipAddres != null)
				return false;
		} else if (!ipAddres.equals(other.ipAddres))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (topico == null) {
			if (other.topico != null)
				return false;
		} else if (!topico.equals(other.topico))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Topico [topico=" + topico + ", ipAddres=" + ipAddres + ", status=" + status + "]";
	}
}
