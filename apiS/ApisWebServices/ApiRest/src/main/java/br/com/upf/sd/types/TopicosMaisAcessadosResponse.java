package br.com.upf.sd.types;

import java.util.ArrayList;
import java.util.List;

import br.com.upf.sd.utils.ResponseType;

public class TopicosMaisAcessadosResponse extends ResponseType {

	private List<Topicos> topicos = new ArrayList<Topicos>();

	public List<Topicos> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<Topicos> topicos) {
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
		TopicosMaisAcessadosResponse other = (TopicosMaisAcessadosResponse) obj;
		if (topicos == null) {
			if (other.topicos != null)
				return false;
		} else if (!topicos.equals(other.topicos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TopicosMaisAcessadosResponse [topicos=" + topicos + "]";
	}
}
