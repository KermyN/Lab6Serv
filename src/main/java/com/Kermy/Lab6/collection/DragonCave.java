package com.Kermy.Lab6.collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
/*
 * Представляет пещеру дракона с сокровищами
 */
@XmlRootElement
public class DragonCave implements Serializable {
    @XmlElement
    private double numberOfTreasures; //Значение поля должно быть больше 0
    DragonCave() {}
    public DragonCave(double numberOfTreasures){
        this.numberOfTreasures=numberOfTreasures;
    }

    @Override
    public String toString(){
        return "number of treasures = "+ numberOfTreasures;
    }
    public double getNumberOfTreasures() {
        return numberOfTreasures;
    }
}