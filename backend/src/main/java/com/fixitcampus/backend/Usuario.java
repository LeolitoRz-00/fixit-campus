package com.fixitcampus.backend;

public class Usuario {

    int id_rol;
    String nombre;
    String email;
    String pass_hash;
    String pass_salt;
    //Tu eres un booleano, pero de momento no
    String activo;

    public Usuario(int id_rol, String nombre, String email, String pass_hash, String pass_salt, String activo) {

        this.id_rol = id_rol;
        this.nombre = nombre;
        this.email = email;
        this.pass_hash = pass_hash;
        this.pass_salt = pass_salt;
        this.activo = activo;

    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_rol=" + id_rol + '\'' +
                "nombre='" + nombre +
                "email='" + email +
                "pass_hash='" + pass_hash +
                "pass_salt='" + pass_salt +
                "activo='" + activo + '\'' +
                '}';
    }
}
