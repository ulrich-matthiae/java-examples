package com.ulrich.matthiae.java.streams.model;

public class Animal {
    private String type;
    private String sound;
    private Boolean fluffy;

    public Animal(String type, String sound, Boolean fluffy) {
        this.type = type;
        this.sound = sound;
        this.fluffy = fluffy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Boolean getFluffy() {
        return fluffy;
    }

    public void setFluffy(Boolean fluffy) {
        this.fluffy = fluffy;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "type='" + type + '\'' +
                ", sound='" + sound + '\'' +
                ", fluffy=" + fluffy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal animal = (Animal) o;

        if (type != null ? !type.equals(animal.type) : animal.type != null) return false;
        if (sound != null ? !sound.equals(animal.sound) : animal.sound != null) return false;
        return fluffy != null ? fluffy.equals(animal.fluffy) : animal.fluffy == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        result = 31 * result + (fluffy != null ? fluffy.hashCode() : 0);
        return result;
    }
}
