package com.Kermy.Lab6.collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Coordinates implements Serializable {
    @XmlElement
    private Integer x; //Поле не может быть null
    @XmlElement
    private long y;

    public Coordinates(){};
    public Coordinates(Integer x, long y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public long getY() {
        return y;
    }
    @Override
    public String toString() {
        return "coordinates: " + getX() + " " + getY();
    }
    @Override
    public int hashCode() {
        return getX() + (int) getY();
    }
}
