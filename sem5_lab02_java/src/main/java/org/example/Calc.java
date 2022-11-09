//  ЛАБОРАТОРНАЯ РАБОТА № 2
//Разбор выражения и вычисление его значения.
// Выражение может содержать числа, знаки операций, скобки.
// В случае, если выражение записано корректно, вычислить значение, в противном случае — вывести сообщение об ошибке.
// Дополнительно приветствуется поддержка имен переменных и различных функций.
// В случае, если есть переменные, их значения нужно запросить у пользователя (для каждой из них — по одному разу).

/*
 * @author Коваль Григорий (3 курс 3 группа)
 */
package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.Character.isDigit;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Calc {

    public static void main(String[] args) throws Exception {
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

        BufferedReader a_str = new BufferedReader(new InputStreamReader(System.in));
        String isEnd;
        BufferedReader b_str = new BufferedReader(new InputStreamReader(System.in));
        String strIn;
        double number;

        System.out.println("Тест 1:");
        System.out.println("Входная строка:");
        strIn = "- 2 + 10/2+1";
        System.out.println(strIn);
        System.out.println("Выходное число:");
        number = Calculate(strIn);
        System.out.println(number);
        System.out.println("\n");

        System.out.println("Тест 2:");
        System.out.println("Входная строка:");
        strIn = "(+121  -  119  )^(-1) + 0.5 %2";
        System.out.println(strIn);
        System.out.println("Выходное число:");
        number = Calculate(strIn);
        System.out.println(number);
        System.out.println("\n");

        System.out.println("Тест 3:");
        System.out.println("Входная строка:");
        strIn = "(-(-3+5)) * 8";
        System.out.println(strIn);
        System.out.println("Выходное число:");
        number = Calculate(strIn);
        System.out.println(number);
        System.out.println("\n");


        do {

                System.out.println("Введите выражение для расчета.");
                System.out.println("Поддерживаются: действительные числа; операции +, -, *, /, ^, %; скобки (, ).");
                strIn = b_str.readLine();
                number = Calculate(strIn);
            System.out.println(number);
            System.out.println("Продолжить работу? (y/n?)");
            isEnd = a_str.readLine();
        } while (isEnd.equals("y"));

    }

    /**
     * Функция выдаёт вычисленное значение выражения
     * @param sIn - входная строка
     * @return result - результат вычислений
     */
    public static double Calculate(String sIn) {
        double result = -1;
        try {
            String sOut = sIn;
            sOut = prepare(sOut); //подгтовка входной строки к преобразованию в обратную польскую нотацию
            sOut = opn(sOut); // преобразование строки в обратную польскую нотацию
            result = CalculateExpression(sOut); // вычисление значения выражения
        } catch (Exception e) {
            System.out.println(e.getMessage()); // вывод сообщения об ошибке
        }
        return result;
    }

    // Символы "+" и "-" - могут быть, как знаком числа, так и операцией (унарной или бинарной)
    // Например: 5-2, -8, (-(3+5)), (-3)*2, 9+2, +2, (+(8+5)), (+4)*3
    // Поэтому необходимо подгтовить входную строку к преобразованию в обратную польскую нотацию
    /**
     * Процедура подготовки строки к преобразованию в обратную польскую нотацию
     * @param sIn - входная строка
     * @return Выходная строка, готовая к преобразованию в обратную польскую нотацию
     */
    private static String prepare(String sIn) {
        StringBuilder sOut = new StringBuilder();
        String str;
        char c = '\0', next , prev;
        int len;

        str = sIn.replace(" ", "");
        len = str.length();
        for (int i = 0; i < len; i++) {
            prev = c;
            c = str.charAt(i);
            next = (i < (len - 1)) ? str.charAt(i + 1) : '\0';

            if (c == '-' || c == '+') {
                if (prev == '\0' || prev == '(') { // у нас число или унарная операция
                    if (isDigit(next)) { // у нас число
                        if (c == '-') // у нас отрицательное число
                            sOut.append("m"); // добавляем спец. символ
                        continue;
                    }
                    else if (next == '(') { // у нас унарная операция
                        if (c == '-') // у нас изменение знака
                            sOut.append("m1*"); // (-1) *
                        continue;
                    }
                }
            }
            sOut.append(c);
        }
        return sOut.toString();
    }
    /**
     * Функция преобразования строки в обратную польскую нотацию
     * @param sIn - входная строка
     * @return sbOut.toString() - выходная строка в обратной польской нотации
     */
    private static String opn(String sIn) throws Exception {
        StringBuilder sbStack = new StringBuilder(), sbOut = new StringBuilder();
        char cIn, cTmp;

        for (int i = 0; i < sIn.length(); i++) {
            cIn = sIn.charAt(i);
            if (isOp(cIn)) {
                while (sbStack.length() > 0) {
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                    if (isOp(cTmp) && (opPrior(cIn) <= opPrior(cTmp))) {
                        sbOut.append(" ").append(cTmp).append(" ");
                        sbStack.setLength(sbStack.length()-1);
                    } else {
                        sbOut.append(" ");
                        break;
                    }
                }
                sbOut.append(" ");
                sbStack.append(cIn);
            } else if ('(' == cIn) {
                sbStack.append(cIn);
            } else if (')' == cIn) {
                cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                while ('(' != cTmp) {
                    if (sbStack.length() < 1) {
                        throw new Exception("Ошибка разбора скобок!");
                    }
                    sbOut.append(" ").append(cTmp);
                    sbStack.setLength(sbStack.length()-1);
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                }
                sbStack.setLength(sbStack.length()-1);
            } else { // если символ не является оператором, то добавляем его в выходную последовательность
                sbOut.append(cIn);
            }
        }

        while (sbStack.length() > 0) { // если в стеке остались операторы,то добавляем их в выходную строку
            sbOut.append(" ").append(sbStack.substring(sbStack.length()-1));
            sbStack.setLength(sbStack.length()-1);
        }

        return  sbOut.toString();
    }

    /**
     * Функция проверки, является ли текущий символ оператором
     */
    private static boolean isOp(char c) {
        return switch (c) {
            case '-', '+', '*', '/', '%', '^' -> true;
            default -> false;
        };
    }

    /**
     * Функция возврата приоритета операции
     * @param op - символ
     * @return byte
     */
    private static byte opPrior(char op) {
        return switch (op) {
            case '^' -> 3;
            case '*', '/', '%' -> 2;
            default -> 1;
        };
        // Для операторов '+', '-'
    }

    /**
     * Функция вычисляет значение выражения, которое записано в обратной польской нотации
     * @param sIn - входная строка
     * @return double result - результат вычислений
     */
    private static double CalculateExpression(String sIn) throws Exception {
        double dA, dB;
        String sTmp;
        Deque<Double> stack = new ArrayDeque<>();
        StringTokenizer st = new StringTokenizer(sIn);
        while(st.hasMoreTokens()) {
            try {
                sTmp = st.nextToken().trim();
                if (1 == sTmp.length() && isOp(sTmp.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("Ошибка! Неверное количество данных в стеке для операции " + sTmp + ".");
                    }
                    dB = stack.pop();
                    dA = stack.pop();
                    switch (sTmp.charAt(0)) {
                        case '+' -> dA += dB;
                        case '-' -> dA -= dB;
                        case '/' -> dA /= dB;
                        case '*' -> dA *= dB;
                        case '%' -> dA %= dB;
                        case '^' -> dA = Math.pow(dA, dB);
                        default -> throw new Exception("Ошибка! Недопустимая операция " + sTmp + ".");
                    }
                    stack.push(dA);
                } else {
                    sTmp = sTmp.replace('m', '-');
                    dA = Double.parseDouble(sTmp);
                    stack.push(dA);
                }
            } catch (Exception e) {
                throw new Exception("Ошибка! Недопустимый символ в выражении.");
            }
        }

        if (stack.size() > 1) {
            throw new Exception("Ошибка! Количество операторов не соответствует количеству операндов.");
        }

        return stack.pop();
    }
}