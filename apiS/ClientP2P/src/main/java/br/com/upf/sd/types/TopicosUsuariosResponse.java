package br.com.upf.sd.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.upf.sd.utils.ResponseType;

public class TopicosUsuariosResponse extends ResponseType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
		result = prime * result
				+ ((usuarios == null) ? 0 : usuarios.hashCode());
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
		TopicosUsuariosResponse other = (TopicosUsuariosResponse) obj;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TopicosUsuariosResponse [usuarios=" + usuarios + "]";
	}
	

}
