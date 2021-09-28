package com.Kermy.Lab6.collection;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

@XmlRootElement(name = "Dragons")
@XmlAccessorType(XmlAccessType.NONE)
public class Dragons implements Serializable {
    @XmlElement(name = "Dragon")
    public static ArrayList<Dragon> IOCollection = new ArrayList<>();
    protected Hashtable<Integer, Dragon> dragonHashtable = new Hashtable<>();
    private final ZonedDateTime creationDate;
    Dragons dragons;
    public Dragons() {
        creationDate = ZonedDateTime.now();
    }
    /**
     * Upload collection from xml-file
     * @param path path to xml file
     */
    public void uploadData(String path)  {
       try {
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);
            JAXBContext context = JAXBContext.newInstance(Dragons.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            dragons = (Dragons) unmarshaller.unmarshal(inputFile);
            DataCheck();
        }catch (UnmarshalException e) {
            System.out.println("Invalid file");
            System.exit(0);
        }catch (JAXBException et) {
            System.out.println("Parsing Error");
            System.exit(0);
        }catch (FileNotFoundException et) {
            System.out.println("No File");
            System.exit(0);
        }
    }

    public Dragons getDragons(){
        Dragons dragons = new Dragons();
        dragons.dataUpdate();
        return dragons;
    }
    public void DataCheck() {
        for (Dragon dragon : IOCollection) {
            if ((dragon.getName() == null) || (dragon.getName().equals("")) || dragon.getAge() < 0
                    || dragon.getCharacter() == null || dragon.getColor() == null || dragon.getType() == null || dragon.getCoordinates().getX() == null
                    || dragon.getCoordinates().getY() > 404 || dragon.getCave().getNumberOfTreasures() < 0) {
                System.out.println("Неккоректные значения в файле");
                System.exit(0);
            }
            dragon.setId();
        }
        dataUpdate();
    }

    /**
     * Save collection to xml-flie
     */
    public void save(){
        StringWriter sw = new StringWriter();
        System.out.println("s"+dragonHashtable);
        IOCollection=dataOutdate();
        System.out.println("s"+IOCollection);
        try {
            Dragons dragons = new Dragons();
            JAXBContext context = JAXBContext.newInstance(this.getClass());
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.marshal(dragons, new File("Data.xml"));
            jaxbMarshaller.marshal(dragons, sw);
        } catch (JAXBException e){
            System.out.println("Saving Error");}

    }


    public Hashtable<Integer, Dragon> getCollection() { return dragonHashtable; }

    public void dataUpdate(){
        for (Dragon dragon: IOCollection){
            dragon.setId();
            dragon.setCreationDate();
            this.dragonHashtable.put(dragon.setId(), dragon);
        }
        IOCollection.clear();
    }

    public ArrayList<Dragon> dataOutdate(){
        ArrayList<Dragon> outList = new ArrayList<>();
        outList.addAll(dragonHashtable.values());
        return outList;
    }
    /**
     * Clear collection
     */
    public void clear() { dragonHashtable.clear();}



    /**
     * Add dragon to collection
     */
    public void add(Dragon element) { dragonHashtable.put(element.getId(),element);}

    /**
     * Get dragon by ID
     */
    public Dragon get(int id) {
        for (Dragon element : dragonHashtable.values()) {
            if (element.getId() == id) {
                return element;
            }
        }
        return null;
    }
    /**
     * Remove elements with lower ID
     */
    public String removeLower(Dragon dr) {
        String out ="";
        Dragon dragon = dr; //dragonHashtable.get(id);
        Dragon drag;
        for (Iterator<Dragon> iter = dragonHashtable.values().iterator(); iter.hasNext(); ) {
            drag = iter.next();
            if (dragon.getId() > drag.getId()){
                iter.remove();
                out="Обьект удален";

            }
        }
        return out;
    }
    /**
     * Remove elements with greater ID
     */

    public void removeGreater(int id) {
        Dragon dragon = dragonHashtable.get(id);
        Dragon drag;
        for (Iterator<Dragon> iter = dragonHashtable.values().iterator(); iter.hasNext(); ) {
            drag = iter.next();
            if (dragon.getId() < drag.getId())
                iter.remove();
        }
    }

    /**
     * Remove element by ID
     */
    public boolean remove(int key) {
        Dragon element = get(key);
        Dragon drag;
        for (Iterator<Dragon> iter = dragonHashtable.values().iterator(); iter.hasNext(); ) {
            drag = iter.next();
            if (element.getId() == drag.getId()){
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        for (Dragon dragon : dragonHashtable.values()) {
            return "\nDragon{" +
                    "id=" + dragon.getId() +
                    ", name='" + dragon.getName() + '\'' +
                    ", age=" + dragon.getAge() +
                    ", coordinates='" + dragon.getCoordinates() + '\'' +
                    ", color='" + dragon.getColor() + '\'' +
                    ", type='" + dragon.getType() + '\'' +
                    ", character='" + dragon.getCharacter() + '\'' +
                    ", cave='" + dragon.getCave() + '\'' +
                    ", creationDate='" + dragon.getCreationDate() + '\'' +
                    '}';
        }

        return null;//getCollection().toString();
    }

    public ZonedDateTime getInitializationDate() {
        return creationDate;
    }

    /**
     * return collection size
     */
    public int getSize() {
        return dragonHashtable.size();
    }

    /**
     * Return dragon with minimal amount of treasures
     */

    public Dragon minCave(){
        Double cave= Double.MAX_VALUE;
        Dragon minCave = null;
        for(Dragon dragon : dragonHashtable.values()) {
            if(dragon.getCave().getNumberOfTreasures()<cave) {cave = dragon.getCave().getNumberOfTreasures();minCave=dragon;}
        }
        if(cave== Double.MAX_VALUE){System.out.println("коллекция пустая"); return null;}
        else return minCave;
    }
    /**
     * Count dragon with exact color
     */
    public int ColorCounter(Color color){
        int count = 0;
        for (Dragon dragon : dragonHashtable.values()) {
            if (dragon.getColor().equals(color)){
                count = count + 1;
            }
        }
        return count;
    }

    /**
     * находит всех драконов, чьё имя содержит подстроку
     */

    public HashSet<Dragon> subStringSearcher(String subString){
        HashSet<Dragon> set = new HashSet<>();
        for (Dragon dragon : dragonHashtable.values()) {
            if (dragon.getName().contains(subString)){
              set.add(dragon);
            }
        }
        return set;
    }
    public  String ShowDragons(){
        StringBuilder box = new StringBuilder();
        for (Dragon dragon : dragonHashtable.values()) {

                box.append(dragon.toString()).append("\n");

        }
        return box.toString();
    }

    public String getCollectionInfo() {
        for (Dragon dragon : dragonHashtable.values()) {
            return "\nDragon{" +
                    "id=" + dragon.getId() +
                    ", name='" + dragon.getName() + '\'' +
                    ", age=" + dragon.getAge() +
                    ", coordinates='" + dragon.getCoordinates() + '\'' +
                    ", color='" + dragon.getColor() + '\'' +
                    ", type='" + dragon.getType() + '\'' +
                    ", character='" + dragon.getCharacter() + '\'' +
                    ", cave='" + dragon.getCave() + '\'' +
                    ", creationDate='" + dragon.getCreationDate() + '\'' +
                    '}';
        }
        return null;
    }
}