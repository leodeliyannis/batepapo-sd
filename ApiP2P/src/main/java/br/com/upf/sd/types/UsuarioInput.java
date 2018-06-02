package br.com.upf.sd.types;

import java.io.Serializable;
import java.util.List;

public class UsuarioInput implements Serializable {
	
	public UsuarioInput(String usuario, String senha, List<String> topicos) {
		this.usuario = usuario;
		this.senha = senha;
		this.topicos = topicos;
	}

	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private String senha;
	private List<String> topicos;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public List<String> getTopicos() {
		return topicos;
	}
	public void setTopicos(List<String> topicos) {
		this.topicos = topicos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((topicos == null) ? 0 : topicos.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		UsuarioInput other = (UsuarioInput) obj;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (topicos == null) {
			if (other.topicos != null)
				return false;
		} else if (!topicos.equals(other.topicos))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "UsuarioInput [usuario=" + usuario + ", senha=" + senha
				+ ", topicos=" + topicos + "]";
	}

}
