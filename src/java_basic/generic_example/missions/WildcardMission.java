package java_basic.generic_example.missions;

import java.util.ArrayList;
import java.util.List;

/**
 * GENERIC 미션 2: 와일드카드(? extends / ? super)
 *
 * 출발점이 되는 사실 하나:
 *   Integer는 Number의 자식이다. 하지만 List<Integer>는 List<Number>의 자식이 아니다.
 *   → 그래서 List<Number>로 받는 메서드에는 List<Integer>를 넘길 수 없다.
 *   → 이 벽을 넘으려고 쓰는 것이 와일드카드 ? 이다.
 *
 * PECS: Producer Extends, Consumer Super
 *   꺼내 읽기만 한다 → ? extends
 *   집어넣기만 한다 → ? super
 *
 * 문제:
 * 1. printAll이 List<Integer>, List<Double>, List<String>을 모두 받도록 고치세요.
 * 2. average가 Number 계열 리스트라면 무엇이든 받도록 고치세요.
 * 3. fillNumbers가 List<Number>와 List<Object> 모두에 1~3을 넣을 수 있도록 고치세요.
 * 4. 아래 "생각해보기"의 주석 질문에 답을 적어보세요.
 *
 * 예상 출력:
 * [1, 2, 3]
 * [1.5, 2.5]
 * [a, b]
 * 정수 평균: 2.0
 * 실수 평균: 2.0
 * Number 리스트: [1, 2, 3]
 * Object 리스트: [1, 2, 3]
 *
 * 힌트:
 * - 타입을 전혀 안 따져도 되면 List<?> 만으로 충분합니다. (읽는 법: "무언가의 List")
 *   단 List<?>에는 요소를 add할 수 없습니다. 정체를 모르니까요.
 * - doubleValue()를 부르려면 최소한 Number라는 보장이 필요합니다. → <? extends Number>
 * - add(1)을 하려면 Integer를 받아줄 수 있다는 보장이 필요합니다. → <? super Integer>
 */
public class WildcardMission {

    // TODO 1: 어떤 타입의 List든 받을 수 있게 고치세요.
    //         힌트: 요소를 꺼내 출력만 하므로 타입을 알 필요가 전혀 없습니다.
    static void printAll(List<Object> list) {
        System.out.println(list);
    }

    // TODO 2: Number 하위 타입 리스트라면 모두 받도록 고치세요.
    //         (지금은 List<Integer>를 넘기면 컴파일 에러입니다.)
    static double average(List<Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum / list.size();
    }

    // TODO 3: Integer를 담을 수 있는 리스트라면 모두 받도록 고치세요.
    //         (지금은 List<Object>를 넘기면 컴파일 에러입니다.)
    static void fillNumbers(List<Number> list) {
        for (int i = 1; i <= 3; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>(List.of(1, 2, 3));
        List<Double> dblList = new ArrayList<>(List.of(1.5, 2.5));
        List<String> strList = new ArrayList<>(List.of("a", "b"));

        // TODO 1: 세 리스트를 모두 printAll로 출력하세요.

        // TODO 2: intList와 dblList의 평균을 출력하세요.
        //         출력 형태: 정수 평균: 2.0 / 실수 평균: 2.0

        List<Number> numberList = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();

        // TODO 3: 두 리스트에 fillNumbers를 호출하고 출력하세요.

        /*
         * 생각해보기 (주석으로 답을 적어보세요)
         *
         * Q1. average의 매개변수를 List<? extends Number>로 바꾸면
         *     메서드 안에서 list.add(10)을 할 수 없습니다. 왜일까요?
         *     A1.
         *
         * Q2. fillNumbers의 매개변수를 List<? super Integer>로 바꾸면
         *     꺼낸 값을 Integer 변수에 바로 담을 수 없고 Object로만 받을 수 있습니다. 왜일까요?
         *     A2.
         *
         * Q3. List<?>와 List<Object>는 무엇이 다른가요?
         *     A3.
         */
    }
}
