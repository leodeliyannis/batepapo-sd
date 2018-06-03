package br.com.upf.sd.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.upf.sd.utils.ResponseType;

public class UsuariosTopicosResponse extends ResponseType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Topico> topicos = new ArrayList<Topico>();

	public List<Topico> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<Topico> topicos) {
		this.topicos = topicos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((topicos == null) ? 0 : topicos.hashCode());
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
		UsuariosTopicosResponse other = (UsuariosTopicosResponse) obj;
		if (topicos == null) {
			if (other.topicos != null)
				return false;
		} else if (!topicos.equals(other.topicos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuariosTopicosResponse [topicos=" + topicos + "]";
	}
}
