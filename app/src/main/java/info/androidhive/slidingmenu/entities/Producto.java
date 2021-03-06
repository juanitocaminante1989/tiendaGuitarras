package info.androidhive.slidingmenu.entities;


import android.util.SparseArray;

import java.util.ArrayList;

public class Producto {

    public String codArticulo;
    public String codSubCat;
    public String codCat;
    public String articulo;
    public String marca;
    public int idMarca;
    public String modelo;
    public String descripcion;
    public double precio;
    double IVA;
    int views;
    public SparseArray<Images> directorio;


    public Producto() {

    }

    public Producto(String codArticulo, String codSubCat, String codCat, String articulo, String marca, String modelo, String descripcion, double precio, double IVA, SparseArray<Images> directorio) {
        this.codArticulo = codArticulo;
        this.codSubCat = codSubCat;
        this.codCat = codCat;
        this.articulo = articulo;
        this.marca = marca;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.IVA = IVA;
        this.directorio = directorio;
    }


    public Producto(String codArticulo, String articulo, String marca, String modelo, String descripcion) {
        this.codArticulo = codArticulo;
        this.articulo = articulo;
        this.marca = marca;
        this.modelo = modelo;
        this.descripcion = descripcion;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getCodSubCat() {
        return codSubCat;
    }

    public void setCodSubCat(String codSubCat) {
        this.codSubCat = codSubCat;
    }

    public String getCodCat() {
        return codCat;
    }

    public void setCodCat(String codCat) {
        this.codCat = codCat;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getIVA() {
        return IVA;
    }

    public void setIVA(double IVA) {
        this.IVA = IVA;
    }

    public SparseArray<Images> getDirectorio() {
        return directorio;
    }

    public void setDirectorio(SparseArray<Images> directorio) {
        this.directorio = directorio;
    }
}
