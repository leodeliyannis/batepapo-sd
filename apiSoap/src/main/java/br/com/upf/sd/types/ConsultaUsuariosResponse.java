package br.com.upf.sd.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import br.com.upf.sd.utils.ResponseType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUsuariosServiceResponse", 
		 namespace = "http://br.com.upf.sd/ApiSoap")
public class ConsultaUsuariosResponse extends ResponseType{
	
	@XmlElement(required = true, name = "usuarios")
	@XmlElementWrapper(name = "usuariosCollection")
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
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
		ConsultaUsuariosResponse other = (ConsultaUsuariosResponse) obj;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConsultarUsuariosResponse [usuarios=" + usuarios + "]";
	}	
}
