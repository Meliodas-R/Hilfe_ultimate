package com.example.hilfe_ultimate;

import java.io.Serializable;

public class Contacto implements Serializable {

    private Integer idContacto;
    private String nombreContacto;
    private String telefonoContacto;

    public Contacto() {
    }

    public Contacto(Integer idContacto, String nombreContacto, String telefonoContacto) {
        this.idContacto = idContacto;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
    }

    public Integer getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "idContacto=" + idContacto +
                ", nombreContacto='" + nombreContacto + '\'' +
                ", telefonoContacto='" + telefonoContacto + '\'' +
                '}';
    }
}
