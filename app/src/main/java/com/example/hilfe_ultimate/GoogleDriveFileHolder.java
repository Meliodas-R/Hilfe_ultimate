package com.example.hilfe_ultimate;

import com.google.api.client.util.DateTime;

/**
 *
 */
public class GoogleDriveFileHolder {

    private String id;
    private String name;
    private DateTime modifiedTime;
    private long size;

    /**
     * Devuelve el valor del atributo id.
     *
     * @return Identificador.
     */
    public String getId() {
        return id;
    }

    /**
     * Proporciona valor al id.
     *
     * @param id Identificador.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el valor del atributo name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Proporciona valor al atributo name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Proporciona valor al atributo modifiedTime.
     *
     * @param modifiedTime modifiedTime.
     */
    public void setModifiedTime(DateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * Devuelve el valor del atributo size.
     *
     * @return size.
     */
    public long getSize() {
        return size;
    }

    /**
     * Proporciona valor al atributo size.
     *
     * @param size Size.
     */
    public void setSize(long size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "GoogleDriveFileHolder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", modifiedTime=" + modifiedTime +
                ", size=" + size +
                '}';
    }
}
