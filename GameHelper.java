package com.game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class GameHelper {

	private static final String alphabet = "abcdefghij"; // буквенные координаты
	private int gridLength = 10; // размер игрового поля 10 Х 10
	private int gridSize = 100; // количество ячеек
	private int[] grid = new int[gridSize]; // массив ячеек игрового поля
	private int[] board = new int[gridSize]; // массив ячеек для отображения игрового поля
	private int shipCount = 0; // количество кораблей
	private ArrayList<String> alphaCells = new ArrayList<String>(); // создание слоя именованых координат для определения попадания
	
	
	// обработка пользовательского ввода
	public String getUserInput(String prompt) {
        String inputLine = null;
        System.out.print(prompt + " ");
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0) return null;
        } catch (IOException exc) {
            System.out.println("IOException: " + exc);
        }

        return inputLine.toLowerCase();
    }

    // размещение кораблей на игровом поле
	public ArrayList<String> placeShip(int shipSize) {
		ArrayList<String> alphaTarget = new ArrayList<String>(); // координаты размещенных кораблей
        String temp = null; // временная переменная для хранения символьного значения координаты
        int[] coords = new int[shipSize]; // координаты ячеек для каждой палубы корабля
        int attempts = 0; // количество попыток разместить корабль
        boolean success = false; // удалась ли попытка размещения
        int location = 0; // координата, указывающая на положение ячейки в массиве игрового поля

        shipCount++;
        int incr = 1;

        // третий корабль будет размещен вертикально
        if ((shipCount % 2) == 1) { 
            incr = gridLength;
        }

        // цикл размещения кораблей
        while (!success & attempts++ < 200) {

            location = (int) (Math.random() * gridSize); // генерация координаты
            int x = 0;
            success = true;

            while (success && x < shipSize) {
            	// если ячейка пуста -- размещаем в ней палубу
                if (grid[location] == 0) {
                    coords[x++] = location; // помещаем координату в массив
                    location += incr; // переходим к следующей ячейке

                    // выход за краницы игрового поля
                    if (location >= gridSize) {
                        success = false;
                    }

                    // выход за границы игрового поля
                    if (x > 0 && (location % gridLength == 0)) {
                        success = false;
                    } 
                } else {
                    success = false; // ячейка занята
                }
            }
        }

        int x = 0;
        int row = 0;
        int column = 0;

        while (x < shipSize) {
            grid[coords[x]] = 1; // заполение массива ячеек игрового поля единицами в месте размещения корабля
            row = (int) (coords[x] / gridLength); // определение численной координаты столбца
            column = coords[x] % gridLength; // определение индекса символьной координаты
            
            temp = String.valueOf(alphabet.charAt(column)); // помещаем символьную координату во временную переменную
            alphaTarget.add(temp.concat(Integer.toString(row))); // помещаем цельную координату в массив 
            // System.out.print(alphaTarget.get(x) + " ");
            // System.out.println();

            x++;
        }

        return alphaTarget;      
	}

	// создаем отображение игрового поля
	public ArrayList<String> makeGrid() {
		int row = 0;
        int column = 0;
        String temp = null;
        for (int i = 0; i < gridSize; i++) {
        	board[i] = 1;
        }

        for (int i = 0; i < alphabet.length(); i++) {        	
        	for (int j = 0; j < alphabet.length(); j++) {
        		row = j;
            	column = i;
            
            	temp = String.valueOf(alphabet.charAt(column));
            	alphaCells.add(temp.concat(Integer.toString(row)));
        	}         
        }
        return alphaCells;
	}

	// отображаем игровое поле. Метод принимает значение пользовательского ввода,
	// результат проверки на попадание и массив ячеек слоя игрового поля
	public void printGrid(String guess, String result, ArrayList<String> cells) {
		int index = cells.indexOf(guess);

		// если фиксируется попадание ячейка принимает значение 8
		// иначе -- 0
		
		if (result.equals("Попал!") || result.equals("Потопил!")){
			board[index] = 8;
			cells.set(index, "0");
		} else {
			board[index] = 0;
			cells.set(index, "0");
		}
		
                
        int x = 0;
		System.out.print("  ");

        for (int i = 0; i < alphabet.length(); i++) {
            System.out.print(i + " ");
        }

        System.out.println();

        int s = 0;
        for (int i = 0; i < alphabet.length(); i++) {
        	System.out.print(alphabet.charAt(i));

        	for (int j = s; j < alphabet.length() + s; j++) {
        		System.out.print(" " + board[j]);
        		// System.out.print(" " + cells.get(x));
        		// x++;
        	}

        	System.out.println();
        	s += 10;
        }
	}
}