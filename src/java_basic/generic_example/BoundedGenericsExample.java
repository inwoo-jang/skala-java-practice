package java_basic.generic_example;

import java.util.ArrayList;
import java.util.List;

/**
 * 와일드카드(?) 연습.
 *
 * List<Number>와 List<Integer>는 상속 관계가 아니다.
 * (Integer가 Number의 자식이어도 List<Integer>는 List<Number>의 자식이 아니다!)
 * 그래서 "Number 계열 리스트라면 뭐든 받고 싶다"를 표현하려면 와일드카드가 필요하다.
 *
 * 외우는 법 - PECS: Producer Extends, Consumer Super
 *   꺼내 쓰기만 할 거면(생산자)  → ? extends
 *   집어넣기만 할 거면(소비자)   → ? super
 */
public class BoundedGenericsExample {

    // -------------------------------------------------------
    // Upper Bound Wildcard 메서드: <? extends Number>
    //   Number 하위 타입 리스트의 합계를 반환 (BoundedBox에서 이동)
    // -------------------------------------------------------
    // 읽는 법: "Number이거나 Number의 자식인, 정체를 모르는 어떤 타입의 List"
    // 정체를 모르니 안전하게 꺼낼 수는 있어도(최소한 Number는 확실) 넣을 수는 없다.
    public static double sumBox(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        // 컴파일 에러: upper bound wildcard 리스트에는 추가 불가
        // 실제로 List<Double>이 들어왔다면 10(Integer)을 넣는 순간 깨지기 때문이다.
        // list.add(10);
        return sum;
    }

    // -------------------------------------------------------
    // Lower Bound Wildcard 메서드: <? super Integer>
    //   Integer 및 그 상위 타입(Number, Object)만 허용
    //   → 값을 하나씩 받아서 리스트에 추가
    // -------------------------------------------------------
    // 읽는 법: "Integer이거나 Integer의 부모인, 정체를 모르는 어떤 타입의 List"
    // 무엇이 오든 Integer는 확실히 담을 수 있으니 넣기는 안전하다.
    public static void addBox(List<? super Integer> list, int value) {
        list.add(value);
        // 컴파일 에러: lower bound wildcard는 Object로만 꺼낼 수 있음
        // 실제로 List<Object>일 수 있으므로 꺼낸 게 Integer라는 보장이 없다.
        // Integer first = list.get(0);
    }

    public static void main(String[] args) {

        BoundedBox<Integer> intBox = new BoundedBox<>(100);
        System.out.println("Integer Box: " + intBox.getItem());

        BoundedBox<Double> doubleBox = new BoundedBox<>(3.14);
        System.out.println("Double  Box: " + doubleBox.getItem());

        // BoundedBox<String> bad = new BoundedBox<>("x"); // 컴파일 에러: String은 Number가 아니다.

        // ---- Upper Bound Wildcard: sumBox(List<? extends Number>) ----
        List<Integer> intList = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Double>  dblList = new ArrayList<>(List.of(1.1, 2.2, 3.3));

        // 매개변수가 List<Number>였다면 아래 두 줄 모두 컴파일 에러다.
        System.out.println("Integer 리스트 합계: " + sumBox(intList));
        System.out.println("Double  리스트 합계: " + sumBox(dblList));

        // ---- Lower Bound Wildcard: addBox(List<? super Integer>, int) ----
        List<Number> numberList = new ArrayList<>();  // Number는 Integer의 상위 타입 → 허용
        List<Object> objectList = new ArrayList<>();  // Object는 Integer의 상위 타입 → 허용

        // addBox를 값 하나씩 여러 번 호출
        addBox(numberList, 10);
        addBox(numberList, 20);
        addBox(numberList, 30);

        addBox(objectList, 100);
        addBox(objectList, 200);
        addBox(objectList, 300);

        System.out.println("Number 리스트 (addBox): " + numberList);
        System.out.println("Object 리스트 (addBox): " + objectList);
    }
}
