package br.com.upf.sd.types;

import java.io.Serializable;

public class Token implements Serializable {
	
	public Token(String token) {
		this.token = token;
	}

	private static final long serialVersionUID = 1L;
	
	private String token;

	public String gettoken() {
		return token;
	}

	public void settoken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		Token other = (Token) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "token [token=" + token + "]";
	}
	
}
