import java.util.NoSuchElementException;

/**
 * Класс контейнер (методы двусвязного списка).
 *
 * @author Коваль Григорий (3 курс 3 группа)
 */
public class KContainer {
    /**
     * Начало списка
     */
    private KovalContainer head;
    /**
     * Конец списка
     */
    private KovalContainer tail;

    /**
     * Конструктор - создание нового объекта
     */
    public KContainer() {
        head = tail = null;
    }

    /**
     * Функция проверки контейнера на пустоту
     *
     * @return возвращает true, если список пуст
     */
    private boolean isEmpty() {
        return head == null;
    }

    /**
     * Процедура добавления элемента в начало списка
     *
     * @param a_data - целое число
     */
    public void addFirst(int a_data) {
        KovalContainer node = new KovalContainer(a_data);
        if (isEmpty())
            tail = node;
        else
            head.setPrev(node);
        node.setNext(head);
        head = node;
    }

    /**
     * Процедура добавления элемента в конец списка
     *
     * @param a_data - целое число
     */
    public void addLast(int a_data) {
        KovalContainer node = new KovalContainer(a_data);
        if (isEmpty())
            head = node;
        else
            tail.setNext(node);
        node.setPrev(tail);
        tail = node;
    }

    /**
     * Процедура добавления элемента в список по индексу
     * Допустимые значения индекса - от 0 (вставить элемент на место первого)
     * до размера списка (вставить элемент за последним)
     *
     * @param a_index - индекс
     * @param a_data - целое число
     * @throws NoSuchElementException исключение в случае ошибочного индекса
     */
    public void addByIndex(int a_index, int a_data) throws NoSuchElementException {
        KovalContainer insertBefore = head;
        int i = 0;
        while (insertBefore != null && i != a_index) {
            insertBefore = insertBefore.getNext();
            i++;
        }
        if (i == a_index) {
            if (insertBefore == null)
                /* хотим добавить элемент последним */
                addLast(a_data);
            else {
                /* добавляем элемент за найденным по индексу элементом */
                KovalContainer node = new KovalContainer(a_data);
                if (insertBefore.getPrev() == null)
                    head = node;
                else
                    insertBefore.getPrev().setNext(node);
                node.setPrev(insertBefore.getPrev());
                insertBefore.setPrev(node);
                node.setNext(insertBefore);
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * @param a_index - входной индекс элемента
     * @return node.getData()
     * @throws NoSuchElementException исключение в случае ошибочного индекса
     */
    public int getByIndex(int a_index) throws NoSuchElementException {
        KovalContainer node = head;
        int i = 0;
        while (node != null && i != a_index) {
            node = node.getNext();
            i++;
        }
        if (node == null)
            throw new NoSuchElementException();

        return node.getData();
    }

    /**
     * Функция извлечения элемента из списка
     *
     * @param a_data - искомое целое число
     * @return elem - извлечённое число
     */
    public int extractElem(int a_data) throws NoSuchElementException {
        KovalContainer node = searchNode(a_data);
        if (node == null)
            throw new NoSuchElementException();

        int elem = node.getData();
        deleteByKey(a_data);
        return elem;
    }

    /**
     * Процедура удаления элемента из начала списка
     */
    public void deleteFirst() throws NoSuchElementException {
        if (head == null)
            throw new NoSuchElementException();

        if (head.getNext() == null)
            tail = null;
        else
            head.getNext().setPrev(null);

        head = head.getNext();
    }

    /**
     * Процедура удаления элемента из конца списка
     */
    public void deleteLast() throws NoSuchElementException {
        if (tail == null)
            throw new NoSuchElementException();

        if (tail.getPrev() == null)
            head = null;
        else
            tail.getPrev().setNext(null);

        tail = tail.getPrev();
    }

    /**
     * Процедура удаления элемента из списка по ключу
     *
     * @param a_elem - ключ
     */
    public void deleteByKey(int a_elem) throws NoSuchElementException {
        KovalContainer node = searchNode(a_elem);
        if (node == null)
            throw new NoSuchElementException();

        if (node == head)
            deleteFirst();
        if (node == tail)
            deleteLast();
        else {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }
    }

    /**
     * Функция поиска элемента списка по ключу
     *
     * @param a_elem - ключ
     * @return node - ссылка на узел списака с заданным ключом
     */
    public KovalContainer searchNode(int a_elem) {
        KovalContainer node = head;

        while (node != null) {
            if (node.getData() == a_elem)
                return node;
            node = node.getNext();
        }
        return null;
    }

    /**
     * Процедура печати списка в прямом порядке
     */
    public void printDirect() {
        System.out.println("Элементы контейнера (в прямом порядке):");

        KovalContainer node = head;
        while (node != null) {
            System.out.println(node.getData());
            node = node.getNext();
        }
        System.out.println();
    }

    /**
     * Процедура печати списка в обратном порядке
     */
    public void printReverse() {
        System.out.println("Элементы контейнера (в обратном порядке):");

        KovalContainer node = tail;
        while (node != null) {
            System.out.println(node.getData());
            node = node.getPrev();
        }
        System.out.println();
    }
}
