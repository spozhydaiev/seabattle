package com.game;

import java.io.*;
import java.util.*;

public class Ship {
	private String name; // имя корабля
	private String typeOfShip; // тип корабля "линкор, крейсер и т. д."
	private int numberOfDecks; // количество палуб "количество занимаемых клеток"
	ArrayList<String> location = new ArrayList<>();

	// конструктор класса, принимающий три значения "имя, тип, количество палуб"
	public Ship(String n, String t, int num) {
		name = n;
		typeOfShip = t;
		numberOfDecks = num;
	}

	// возвращает имя корабля
	public String getName() {
		return name;
	}

	// возвращает тип корабля
	public String getTypeOfShip() {
		return typeOfShip;
	}

	// возвращает количество палуб
	public int getNumberOfDecks() {
		return numberOfDecks;
	}

	public void setLocationCells(ArrayList<String> place) {		
		location = place;
	}

	public String checkYourself(String userInput) {
        String result = "Мимо!";
        int index = location.indexOf(userInput);
        if (index >= 0) {
            location.remove(index);

            if (location.isEmpty()) {
				result = "Потопил!";
				System.out.println("Ой! Вы потопили " + typeOfShip + " " + name + ":(");
            } else {
				result = "Попал!";
            }
        }

        return result;
    }
}