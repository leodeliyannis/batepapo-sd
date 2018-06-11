package br.com.ufp.sd.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseType")
public class ResponseType {
    
	public final static Integer STATUS_COD_OK = 0;
    public final static Integer STATUS_COD_VALIDATION = -1;
    public final static Integer STATUS_COD_EXPTION = -2;
    public final static String STATUS_MSG_OK = "Execução realizada com Sucesso!";
    public final static String STATUS_MSG_NOT_FOUND = "Nenhum Registro localizado!";
    
	@XmlElement(required=false, nillable=true)
    private Integer cdRetorno;
    
	@XmlElement(required=false, nillable=true)
    private String dsRetorno;

    public Integer getCdRetorno() {
        return cdRetorno;
    }

    public void setCdRetorno(Integer cdRetorno) {
        this.cdRetorno = cdRetorno;
    }

    public String getDsRetorno() {
        return dsRetorno;
    }

    public void setDsRetorno(String dsRetorno) {
        this.dsRetorno = dsRetorno;
    }
    
    public String toString(){
        return String.format("[cdRetorno=%s," +
            " dsRetorno=%s]", getCdRetorno(),
                              getDsRetorno());
    }
}
