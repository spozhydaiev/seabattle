package com.game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Game {

	private GameHelper helper = new GameHelper(); // экземпляр вспомогательного класса для размещения кораблей
	private ArrayList<Ship> listOfShips = new ArrayList<>(); // список кораблей
	private ArrayList<String> guesses = new ArrayList<>();

	// настройка начальных параметров игры
	// создание и размещение кораблей

	private void setUpGame() {
		// создание трех кораблей
		Ship one = new Ship("Bismark", "Крейсер", 3);
		Ship two = new Ship("Tirpitz", "Линкор", 4);
		Ship three = new Ship("Georg V", "Крейсер", 3);

		// one.setLocationCells(helper.placeShip(one.getNumberOfDecks()));
		// two.setLocationCells(helper.placeShip(two.getNumberOfDecks()));
		// three.setLocationCells(helper.placeShip(three.getNumberOfDecks()));

		// добавление кораблей в список
		listOfShips.add(one);
		listOfShips.add(two);
		listOfShips.add(three);



		System.out.println("Ваша цель потопить все корабли за наименьшее количество ходов.");


		for (Ship shipToList : listOfShips) {
		 	ArrayList<String> newLocation = helper.placeShip(shipToList.getNumberOfDecks());
		 	shipToList.setLocationCells(newLocation);
		}
	}

	private void startGame() {
		ArrayList<String> cells = helper.makeGrid();
		
		while (!listOfShips.isEmpty()) {
			// System.out.println(helper.alphaTarget);
			String userGuess = helper.getUserInput("Ваш ход (пример: а1):");
			if (guesses.contains(userGuess)) {
				System.out.println("Вы уже стреляли по этим координатам! Попробуйте другие.");
				continue;
			} else {
				if (!cells.contains(userGuess)) {
					System.out.println("Координаты введены неверно! Попробуйте еще раз.");
					continue;
				} else {
					guesses.add(userGuess);
				}				
			}
			checkUserGuess(userGuess, cells);
		}

		finishGame();
	}

	private void checkUserGuess(String ug, ArrayList<String> cells) {
		String result = "Мимо.";

		for (Ship shipToTest : listOfShips) {
            result = shipToTest.checkYourself(ug);
	        // System.out.println(shipToTest.getTypeOfShip());
	        // System.out.println(shipToTest.location);

            if (result.equals("Попал!")) {
            	helper.printGrid(ug, result, cells);               
                break;
            }

            if (result.equals("Потопил!")) {
            	helper.printGrid(ug, result, cells);
                listOfShips.remove(shipToTest);                
                break;
            }

            
            //helper.printGrid(userGuess, result, cells);
            //System.out.println("00");
        }

        if (result.equals("Мимо!")) {
        	helper.printGrid(ug, result, cells);
        }

        System.out.println(result);
	}

	private void finishGame() {
        System.out.println("Game over. You win.");
        System.out.println("Вы использовали "+ guesses.size() + " попыток.");
    }

	public static void main(String[] args) {
		Game game = new Game();
		game.setUpGame();
		game.startGame();
	}
}