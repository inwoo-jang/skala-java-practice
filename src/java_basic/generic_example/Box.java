package java_basic.generic_example;

/**
 * 제네릭 클래스: Box<T>
 *
 * T는 "아직 정해지지 않은 타입"을 담아두는 자리표시자(타입 파라미터)다.
 * 실제 타입은 객체를 만들 때 정해진다. → new Box<String>()
 *
 * 관례적으로 쓰는 글자:
 * T(Type), E(Element), K(Key), V(Value), N(Number)
 * 의미만 다를 뿐 문법적으로는 아무 글자나 써도 된다.
 *
 * 제네릭이 없으면 item을 Object로 두어야 하고,
 * 꺼낼 때마다 (String) 같은 캐스팅이 필요하며 잘못 캐스팅하면 실행 중에 터진다.
 * 제네릭은 그 실수를 "컴파일 시점"에 잡아준다.
 */
public class Box<T> {
    private T item;

    public Box() {
    }

    public Box(T item) {
        this.item = item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}
