package com.Kermy.Lab6.collection;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.ZonedDateTime;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Dragon implements Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement(name = "creationDate")
    private String dateTimeString;
    @XmlTransient
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически\
    @XmlElement
    private Coordinates coordinates; //Поле не может быть null
    @XmlElement
    private int age; //Значение поля должно быть больше 0
    @XmlElement
    private Color color; //Поле может быть null
    @XmlElement
    private DragonType type; //Поле может быть null
    @XmlElement
    private DragonCharacter character; //Поле не может быть null
    @XmlElement
    private DragonCave cave; //Поле не может быть null
    public Dragon() {
    }

    public Dragon(int id,String name, Coordinates coordinates, java.time.ZonedDateTime creationDate, int age, Color Color, DragonType type, DragonCharacter character, DragonCave cave){
        setId();
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        setCreationDate();
        this.color = Color;
        this.type = type;
        this.character = character;
        this.cave = cave;
    }

    public Integer getRandNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getAge() {
        return age;
    }

    public Color getColor() {
        return color;
    }

    public DragonType getType() {
        return type;
    }

    public DragonCave getCave() {
        return cave;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public java.time.ZonedDateTime getCreationDate() {
        return ZonedDateTime.now();
    }

    public   Integer setId() {
        this.id = getRandNumber(10,100000);
        return id;
    }
    public   void setId(int id) {
        this.id = id;
    }
    public void setCreationDate() {
        this.creationDate = ZonedDateTime.now();
        this.dateTimeString = creationDate.toString();
    }
    @Override
    public String toString(){
        return "\nDragon{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", coordinates='" + getCoordinates() + '\'' +
                ", color='" + getColor() + '\'' +
                ", type='" + getType() + '\'' +
                ", character='" + getCharacter() + '\'' +
                ", cave='" + getCave() + '\'' +
                ", creationDate='" + getCreationDate() + '\'' +
                '}';
    }

}