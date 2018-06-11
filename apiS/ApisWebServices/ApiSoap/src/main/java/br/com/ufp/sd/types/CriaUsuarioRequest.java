package br.com.ufp.sd.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import br.com.ufp.sd.utils.JaxbValidator;
import br.com.ufp.sd.utils.ValidationException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "criaUsuarioRequest", 
		 namespace = "http://br.com.upf.sd/ApiSoap")
public class CriaUsuarioRequest {
	
	private UsuarioCreate usuario = new UsuarioCreate();

	public UsuarioCreate getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioCreate usuario) {
		this.usuario = usuario;
	}
	
	public void validateFields() throws ValidationException {
		// Bloco generico que realiza a validação a partir das annotations
		JaxbValidator.validateRequired(this,
				CriaUsuarioRequest.class);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		CriaUsuarioRequest other = (CriaUsuarioRequest) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CriaUsuarioRequest [usuario=" + usuario + "]";
	}

}
