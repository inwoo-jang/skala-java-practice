package java_basic.generic_example;

// -------------------------------------------------------
// Upper Bound 적용 클래스: <T extends Number>
//   Number 및 그 하위 타입(Integer, Double, Long ...)만 허용
// -------------------------------------------------------
//
// 제한(bound)을 두면 얻는 것:
//   T가 무조건 Number라는 걸 컴파일러가 알기 때문에
//   클래스 안에서 item.doubleValue() 같은 Number의 메서드를 쓸 수 있다.
//   제한이 없는 <T>였다면 Object의 메서드만 쓸 수 있다.
//
// 주의: 인터페이스를 제한으로 걸 때도 implements가 아니라 extends를 쓴다.
//   예) <T extends Comparable<T>>
public class BoundedBox<T extends Number> {
    private T item;

    public BoundedBox(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    // T가 Number 하위 타입임이 보장되므로 이런 메서드를 쓸 수 있다.
    public double asDouble() {
        return item.doubleValue();
    }
}
