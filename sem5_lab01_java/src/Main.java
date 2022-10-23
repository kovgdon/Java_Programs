//  ЛАБОРАТОРНАЯ РАБОТА № 1
//  Создать класс контейнер, позволяющий хранить произвольное количество целых чисел.
//  Использование внутренних коллекций запрещено.
//  Задание можно реализовать с помощью массива или связанного списка.
//  Контейнер должен позволять добавлять, извлекать, удалять элементы.


import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

/**
 * @author Коваль Григорий (3 курс 3 группа)
 */
public class Main {
    public static void main(String[] args) {

        String charsetOut = System.out.charset().displayName(); // для корректного вывода сообщений на русском языке
        if (!"UTF-8".equals(charsetOut)) {
            System.out.println("The charset for System.out is " + charsetOut + ". Changing System.out to use charset UTF-8");
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
            System.out.println("The charset for System.out is now " + System.out.charset().displayName());
        }
        String charsetErr = System.err.charset().displayName(); // для корректного вывода сообщений об ошибках на русском языке
        if (!"UTF-8".equals(charsetErr)) {
            System.out.println("The charset for System.err is " + charsetErr + ". Changing System.err to use charset UTF-8");
            System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err), true, StandardCharsets.UTF_8));
            System.out.println("The charset for System.err is now " + System.err.charset().displayName());
        }

        KContainer cont = new KContainer();
        cont.addFirst(2);
        cont.addFirst(1);
        cont.addLast(3);
        cont.printDirect();

        cont.printReverse();

        cont.addLast(5);
        cont.addByIndex(3, 4);
        cont.printDirect();

        cont.deleteFirst();
        cont.deleteLast();
        cont.deleteByKey(3);
        cont.printDirect();

        try {
            cont.deleteByKey(3);
        } catch (NoSuchElementException e) {
            System.err.println("Ошибка удаления значения по ключу.");
        }
        cont.printDirect();

        int a = cont.extractElem(4);
        System.out.println("Извлечённый из контейнера элемент: " + a);
        cont.printDirect();

        try {
            cont.addByIndex(3, 6);
        } catch (NoSuchElementException e) {
            System.err.println("Ошибка добавления значения по ключу.");
        }
        cont.addByIndex(0, 0);
        cont.printDirect();

        a = cont.getByIndex(1);
        System.out.println("Полученный из контейнера элемент с индексом 1: " + a);
    }
}