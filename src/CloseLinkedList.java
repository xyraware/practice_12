import java.util.*;//для NoSuchElementException
public class CloseLinkedList<E> implements Iterable<E>, Cloneable {//включает в себа итератор, котороый принимает элементы и позволяет себя клонировать вне поля класса
    private Node first;//первый узел двусвязного циклического списка
    private Node last;//последний узел двусвязного циклического списка
    private int size;//размер двусвязного циклического списка
    CloseLinkedList() {
    }
    CloseLinkedList(E... values) {//инициализация элементов двусвязного циклического списка с помощью значений
        addAll(values);
    }
    public void clear() {//очистка двусвязного циклического списка
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    public int size() {//сеттер раазмера двусвязного циклического списка
        return this.size;
    }
    public boolean isEmpty() {//проверка двусвязного циклического списка не пустой ли он вообще
        return this.size == 0;
    }
    public boolean add(E value) {//проверка двусвязного цикличексого списка можно ли туда вообще значения добавлять
        addFirst(value);
        return true;
    }
    public void addFirst(E value) {//исполнение воторого условия задания при сохранении упорядоченности, что является третьим условия задания
        if (value != null) {
            if (this.first == null) {
                this.first = new Node(value);
                this.first.prev = this.first.next = this.first;
                this.last = this.first;
            } else {
                Node node = new Node(this.first, this.last, value);
                this.last.next = this.first.prev = node;
                this.first = node;
            }
            this.size++;
        }
    }
    public boolean addAll(E... values) {//функция дла инициализации двусвязного циклического списка (также добавляет значения в начало списка)
        boolean result;
        if (result = values != null) {
            for (E value : values) {
                result = result && add(value);
            }
        }
        return result;
    }
    @SafeVarargs
    public static <T> CloseLinkedList<T> of(T... values) {//инициализация с помощью образного типа
        return new CloseLinkedList<>(values);
    }
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Element not found...");
        }//проверяем не пустой ли двусвязный циклический список
        E oldElement;
        if (this.size == 1) {//если двусвязный циклический список состовлен из 1 элемента, просто очистим его,
            oldElement = this.first.value;
            clear();
        } else {//иначе изменим сам список, и вернем в виде значения первый элемент, которого больше нет в списке
            oldElement = this.first.value;
            Node newFirst = this.first.next;
            newFirst.prev = this.last;
            this.last.next = newFirst;
            this.first = newFirst;
            this.size--;
        }
        return oldElement;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Element not found...");
        }//проверяем не пустой ли двусвязный циклический список
        E oldElement;
        if (this.size == 1) {//если двусвязный циклический список состовлен из 1 элемента, просто очистим его,
            oldElement = this.first.value;
            clear();
        } else {
            oldElement = this.last.value;//иначе изменим сам список, и вернем в виде значения первый элемент, которого больше нет в списке
            Node newLast = this.last.prev;
            newLast.next = this.first;
            this.first.prev = newLast;
            this.last = newLast;
            this.size--;
        }
        return oldElement;
    }
    public E getFirst() {
        if (this.first == null) {
            throw new NoSuchElementException("Element not found...");
        }//если список пустой, то выдадим ошибку, иначе вернем первый элемент двусвязного циклического списка
        return this.first.value;
    }

    public E getLast() {
        if (this.last == null) {
            throw new NoSuchElementException("Element not found...");
        }//если список пустой, то выдадим ошибку, иначе вернем последний элемент двусвязного циклического списка
        return this.last.value;
    }
    @Override//нужен для итерации
    public Iterator<E> iterator() {
        return new IteratorLinked();
    }
    private class IteratorLinked implements Iterator<E> {
        private Node cursor = first;
        private int count = 0;

        @Override
        public boolean hasNext() {//проверяет ли есть следующий элемент
            return count < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Elements not found...");
            }//если следующего элемента нет, выдадим ошибку, иначе укажем значение
            E value = cursor.value;
            cursor = cursor.next;
            this.count++;
            return value;
        }
    }
    private class Node {//узел, хакартеристиками которого являются его значения, и предыдущий и последюущий элементы
        private Node next;//следующий
        private Node prev;//предыдущий
        private E value;//элемент (значение)
        Node(Node next, Node prev, E value) {//сеттер
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
        Node(E value) {//сеттер с вводом только значения
            this(null, null, value);
        }
    }
}