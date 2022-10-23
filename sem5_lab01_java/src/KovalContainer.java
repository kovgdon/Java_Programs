/** Класс контейнер (двусвязный список).
 * @author Коваль Григорий (3 курс 3 группа)
 */
public class KovalContainer
{
    private int data;
    private KovalContainer next;
    private KovalContainer prev;
    /**
     * Конструктор - создание нового объекта
     */
    public KovalContainer (int a_data)
    {
        setData(a_data);
    }

    /** Поле значение эл-та списка */
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    /** Поле след. эл-т списка */
    public KovalContainer getNext() {
        return next;
    }

    public void setNext(KovalContainer next) {
        this.next = next;
    }

    /** Поле пред. эл-т списка */
    public KovalContainer getPrev() {
        return prev;
    }

    public void setPrev(KovalContainer prev) {
        this.prev = prev;
    }
}


