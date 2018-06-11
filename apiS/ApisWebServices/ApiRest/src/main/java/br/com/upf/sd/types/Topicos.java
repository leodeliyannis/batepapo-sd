package br.com.upf.sd.types;

public class Topicos {
	
	private String topico;
	private Integer quantidade;
	
	public String getTopico() {
		return topico;
	}
	public void setTopico(String topico) {
		this.topico = topico;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
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
		Topicos other = (Topicos) obj;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
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
		return "Topicos [topico=" + topico + ", quantidade=" + quantidade + "]";
	}
	
}
