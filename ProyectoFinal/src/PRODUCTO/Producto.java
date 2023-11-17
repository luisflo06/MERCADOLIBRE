/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author LUIS ANGEL FLOREZ
 */
public class Producto {

    public String idproducto;
    public String nomproducto;
    public LocalDate fechalote;
    public LocalDate fechavence;
    public Float preciou;

    public Producto(String idproducto, String nomproducto, LocalDate fechalote, LocalDate fechavence, Float preciou) {
        this.idproducto = idproducto;
        this.nomproducto = nomproducto;
        this.fechalote = fechalote;
        this.fechavence = fechavence;
        this.preciou = preciou;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getNomproducto() {
        return nomproducto;
    }

    public void setNomproducto(String nomproducto) {
        this.nomproducto = nomproducto;
    }

    public LocalDate getFechalote() {
        return fechalote;
    }

    public void setFechalote(LocalDate fechalote) {
        this.fechalote = fechalote;
    }

    public LocalDate getFechavence() {
        return fechavence;
    }

    public void setFechavence(LocalDate fechavence) {
        this.fechavence = fechavence;
    }

    public Float getPreciou() {
        return preciou;
    }

    public void setPreciou(Float preciou) {
        this.preciou = preciou;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (!Objects.equals(this.idproducto, other.idproducto)) {
            return false;
        }
        if (!Objects.equals(this.nomproducto, other.nomproducto)) {
            return false;
        }
        if (!Objects.equals(this.fechalote, other.fechalote)) {
            return false;
        }
        if (!Objects.equals(this.fechavence, other.fechavence)) {
            return false;
        }
        return Objects.equals(this.preciou, other.preciou);
    }

    

    
    
}
