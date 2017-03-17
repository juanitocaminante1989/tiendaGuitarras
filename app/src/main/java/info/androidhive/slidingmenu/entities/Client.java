package info.androidhive.slidingmenu.entities;

/**
 * Created by Juan on 17/03/2017.
 */

public class Client {

    String nombre;
    String apellidos;
    String nif;
    String correo;
    String direccion;
    String codPos;
    String telefono;
    String clave;
    int logged;

    public Client(){

    }

    public Client(String nombre, String apellidos, String nif, String correo, String direccion, String codPos, String telefono, String clave) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.correo = correo;
        this.direccion = direccion;
        this.codPos = codPos;
        this.telefono = telefono;
        this.clave = clave;
        this.logged = 0;
    }

    public int getLogged() {
        return logged;
    }

    public void setLogged(int logged) {
        this.logged = logged;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodPos() {
        return codPos;
    }

    public void setCodPos(String codPos) {
        this.codPos = codPos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
