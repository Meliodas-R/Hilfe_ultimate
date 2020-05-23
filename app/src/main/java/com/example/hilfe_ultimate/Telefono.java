package com.example.hilfe_ultimate;

public class Telefono {

    private Integer idTelefono;
    private String nombreTelefono;
    private String telefonoTelefono;

    /**
     * Constructor vacío del objeto teléfono.
     *
     */
    public Telefono() {
    }

    public Telefono(Integer idTelefono, String nombreTelefono, String telefonoTelefono) {
        this.idTelefono = idTelefono;
        this.nombreTelefono = nombreTelefono;
        this.telefonoTelefono = telefonoTelefono;
    }

    public Integer getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(Integer idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNombreTelefono() {
        return nombreTelefono;
    }

    public void setNombreTelefono(String nombreTelefono) {
        this.nombreTelefono = nombreTelefono;
    }

    public String getTelefonoTelefono() {
        return telefonoTelefono;
    }

    public void setTelefonoTelefono(String telefonoTelefono) {
        this.telefonoTelefono = telefonoTelefono;
    }

    @Override
    public String toString() {
        return "Telefono{" +
                "idTelefono=" + idTelefono +
                ", nombreTelefono='" + nombreTelefono + '\'' +
                ", telefonoTelefono='" + telefonoTelefono + '\'' +
                '}';
    }
}
