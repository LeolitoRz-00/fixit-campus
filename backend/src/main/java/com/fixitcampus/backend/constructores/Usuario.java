package com.fixitcampus.backend.constructores;

public class Usuario {

    int id_rol;
    String nombre;
    String email;
    String pass_hash;
    String pass_salt;
    boolean activo;

    public Usuario(int id_rol, String nombre, String email, String pass_hash, String pass_salt, boolean activo) {

        this.id_rol = id_rol;
        this.nombre = nombre;
        this.email = email;
        this.pass_hash = pass_hash;
        this.pass_salt = pass_salt;
        this.activo = activo;

    }

    @Override
    public String toString() {
        return "\nUsuario{" +
                "id_rol=" + id_rol +
                "nombre='" + nombre +
                "email='" + email +
                "pass_hash='" + pass_hash +
                "pass_salt='" + pass_salt +
                "activo='" + activo +
                '}';
    }

    public int getIdRol() {
        return id_rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return pass_hash;
    }

    public String getPass_salt() {
        return pass_salt;
    }

    public boolean isActivo() {
        return activo;
    }


}
