//  ЛАБОРАТОРНАЯ РАБОТА № 4
//Есть Два типа сущностей
//Человек с полями
//ID
//Имя
//Пол
//Подразделение(Сущность подразделение)
// Зарплата
//Дата рождения
//Подразделение с полями:
//ID(генерится в програме)
//Название
//Дан CSV файл(архив с ним есть внутри задания), который содержит в себе информацию о людях. Нужно считать данные о людях из этого файла в список
//В этой задаче нужно пользоваться встроенными Java  коллекциями
//Для работы с CSV файлом рекомендую использовать библиотеку opencsv(НО можете и без нее - это на ваше усмотрение)
//Ее можно либо скачать в виде jar  файла и подключить к проекту если не используете maven, либо подключить как maven зависимость
//Чтение из файла с помощью этой библиотеки может выглядеть так:
//try (InputStream in = getClass().getClassLoader().getResourceAsStream(csvFilePath);
//CSVReader reader = in == null ? null : new CSVReader(new InputStreamReader(in), separator)) {
//if (reader == null) {
//throw new FileNotFoundException(csvFilePath);
//}
//String[] nextLine;
//while ((nextLine = reader.readNext()) != null) {
////А тут работаете с nextLine котрый представляет из себя текущую строчку в файле, уже разбитую на массив по разделителю separator
////Попробуйте просто вывести на экран этот nextLine и, думаю, все будет понятно
//}
//csvFilePath - путь к файлу. Файл закидывайте в ресурсы. Если не получится, то укажите уж тогда полный путь.
//separator - разделитель. В нашем случае  - ';'
//Т.е. на выходе у вас должен получиться объект типа List в котором будут находится люди из файла.  Т.е. нужно не просто прочитать файл и вывести его на экран, а именно получить список
//ID подразделения можете сами генерировать

/*
 * @author Коваль Григорий (3 курс 3 группа)
 */
package org.example;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.ListIterator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class App {

    public static Gender getGender(String str) {
        if(str.equals("Female")||str.equals("female"))
            return Gender.Female;
        if(str.equals("Male") || str.equals("male"))
            return Gender.Male;
        return Gender.NULL;
    }

    public static LinkedList<Person> readPersonsFromCSVFile(String filepath, boolean skipFirstLine) throws Exception{
        LinkedList<Person> plist = new LinkedList<Person>();
        HashMap<String, Integer> divisions = new HashMap<String, Integer>();
        int DivisionId = 0;


        CSVReader reader = new CSVReader();
        reader.setSeparatorTo(';');
        boolean isOpen = reader.OpenFile(filepath);
        if(isOpen) {

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            boolean notDone = true;
            LinkedList<String> list = null;

            if(skipFirstLine) {
                if(reader.NextLine() == null)
                    notDone = false;
            }


            while(notDone) {
                Person pers= new Person();
                list = reader.NextLine();

                if(list != null) {
                    ListIterator<String> iter = list.listIterator(0);
                    pers.mPerson_ID = Integer.parseInt(iter.next());
                    pers.mName = iter.next();
                    pers.mGender = getGender(iter.next());
                    pers.mBirthDate = LocalDate.parse(iter.next(), format);
                    pers.mDivision = new Division();
                    pers.mDivision.mName = iter.next();
                    if(divisions.containsKey(pers.mDivision.mName)) {
                        pers.mDivision.mDivision_ID = divisions.get(pers.mDivision.mName);
                    }
                    else {
                        //generate new id
                        DivisionId++;

                        pers.mDivision.mDivision_ID = DivisionId;
                        divisions.put(pers.mDivision.mName, DivisionId);
                    }
                    pers.mSalary = Integer.parseInt(iter.next());
                    plist.add(pers);
                }
                else notDone = false;
            }
            reader.CloseFile();
        }
        return plist;
    }

    public static void main(String[] args) {

        try {
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

            LinkedList<Person> plist = readPersonsFromCSVFile("C:\\Users\\kovgd\\Java_Programs\\sem5_lab02_java\\Koval_sem5_lab04_java\\src\\main\\java\\foreign_names.csv", true);
            System.out.println("Готово");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}