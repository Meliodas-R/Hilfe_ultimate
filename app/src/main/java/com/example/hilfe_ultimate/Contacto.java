package com.example.hilfe_ultimate;

import java.io.Serializable;

/**
 * Crea el objeto contacto.
 *
 */
public class Contacto implements Serializable {

    private Integer idContacto;
    private String nombreContacto;
    private String telefonoContacto;

    /**
     * Constructor vacío del objeto contacto.
     *
     */
    public Contacto() {
    }

    /**
     * Constructor completo del objeto contacto.
     *
     * @param idContacto Identificador del contacto.
     * @param nombreContacto Nombre del contacto.
     * @param telefonoContacto Teléfono del contacto.
     */
    public Contacto(Integer idContacto, String nombreContacto, String telefonoContacto) {
        this.idContacto = idContacto;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
    }

    /**
     * Obtiene el identificador del contacto.
     *
     * @return Identificador del contacto.
     */
    public Integer getIdContacto() {
        return idContacto;
    }

    /**
     * Otorga valor a la variable idContacto.
     *
     * @param idContacto Identificador del contacto.
     */
    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    /**
     * Obtiene el nombre del contacto.
     *
     * @return Nombre del contacto.
     */
    public String getNombreContacto() {
        return nombreContacto;
    }

    /**
     * Otorga valor a la variable nombreContacto.
     *
     * @param nombreContacto Nombre del contacto.
     */
    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    /**
     * Obtiene el teléfono del contacto.
     *
     * @return Telefono del contacto.
     */
    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    /**
     * Otorga valor a la variable telefonoContacto.
     *
     * @param telefonoContacto Teléfono del contacto.
     */
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
