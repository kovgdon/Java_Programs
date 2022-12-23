//  ЛАБОРАТОРНАЯ РАБОТА № 5
//  Создать класс контейнер, позволяющий хранить произвольное количество целых чисел.
//  Использование внутренних коллекций запрещено.
//  Задание можно реализовать с помощью массива или связанного списка.
//  Контейнер должен позволять добавлять, извлекать, удалять элементы.

/**
        * @author Коваль Григорий (3 курс 3 группа)
        * https://github.com/kovgdon/Java_Programs
        */

package org.example;

import org.example.SomeBean;
import java.util.*;

import java.io.*;

// Лабораторная работа № 5 была написана ДО лекции 23.12.2022

public class App {

    public static void main(String[] args) throws IOException {
        SomeBean sb = Injector.inject(new SomeBean());
        sb.foo();

    }

}