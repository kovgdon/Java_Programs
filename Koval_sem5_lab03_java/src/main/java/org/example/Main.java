//  ЛАБОРАТОРНАЯ РАБОТА № 3
// Сравнить производительность ArrayList и LinkedList
// Необходимо написать код, который бы вызывал основные методы коллекций определенное(1000 или 2000, или любое другое)
// количество раз. При этом должно засекаться время.
// Минимально необходимо протестировать методы:add, delete, get
// После этого вывести таблицу с результатами(метод, сколько раз выполнялся, время выполнения)

/*
 * @author Коваль Григорий (3 курс 3 группа)
 */

package org.example;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main
{
    public static void main( String[] args )
    {
        String charsetOut = System.out.charset().displayName(); // для корректного вывода сообщений на русском языке
        if (!"UTF-8".equals(charsetOut)) {
            System.out.println("The charset for System.out is " + charsetOut + ". Changing System.out to use charset UTF-8");
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
            System.out.println("The charset for System.out is now " + System.out.charset().displayName());
        }
        int iterations = 3000;
        long average;
        long cumulative = 0;
        long start;
        long end;
        ArrayList<Integer> array = new ArrayList<Integer>();
        LinkedList<Integer> linked = new LinkedList<Integer>();
        System.out.println("Сравнение производительности ArrayList и LinkedList");
        System.out.println("Тип данных\tОперация\tИтерации\tСовокупное время\tСреднее время");
        for(int i = 0; i < iterations; i++) {
            start = System.nanoTime();
            array.add(i);
            end = System.nanoTime();
            cumulative += end - start;
        }
        average = cumulative / iterations;
        System.out.println("ArrayList\t\tADD\t\t\t" + iterations + "\t\t\t" + cumulative + "\t\t\t\t" + average);
        cumulative = 0;
        for(int i = 0; i < iterations; i++) {
            start = System.nanoTime();
            linked.add(i);
            end = System.nanoTime();
            cumulative += end - start;
        }
        average = cumulative / iterations;
        System.out.println("LinkedList\t\tADD\t\t\t" + iterations + "\t\t\t" + cumulative + "\t\t\t\t" + average);
        System.out.println(); cumulative = 0;
        for(int i = 0; i < iterations; i++) {
            start = System.nanoTime(); array.get(i);
            end = System.nanoTime(); cumulative += end - start;
        }
        average = cumulative / iterations;
        System.out.println("ArrayList\t\tGET\t\t\t" + iterations + "\t\t\t" + cumulative + "\t\t\t\t" + average);
        cumulative = 0;
        for(int i = 0; i < iterations; i++) {
            start = System.nanoTime();
            linked.get(i);
            end = System.nanoTime();
            cumulative += end - start;
        }
        average = cumulative / iterations;
        System.out.println("LinkedList\t\tGET\t\t\t" + iterations + "\t\t\t" + cumulative + "\t\t\t\t" + average);
        System.out.println();
        cumulative = 0;
        for(int i = 0; i < iterations; i++) {
            start = System.nanoTime();
            array.remove(0);
            end = System.nanoTime();
            cumulative += end - start;
        }
        average = cumulative / iterations;
        System.out.println("ArrayList\t\tDEL\t\t\t" + iterations + "\t\t\t" + cumulative + "\t\t\t\t" + average);
        cumulative = 0;
        for(int i = 0; i < iterations; i++) {
            start = System.nanoTime();
            linked.remove(0);
            end = System.nanoTime();
            cumulative += end - start;
        }
        average = cumulative / iterations;
        System.out.println("LinkedList\t\tDEL\t\t\t" + iterations + "\t\t\t" + cumulative + "\t\t\t\t" + average);
        System.out.println();
        cumulative = 0;
    }
}