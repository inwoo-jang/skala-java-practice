package java_practice;

/**
 * 기본형(primitive) vs 래퍼 클래스(wrapper class) 비교 실습.
 *
 * [핵심 개념]
 * - 기본형(int, double, char, boolean...) : 값 자체를 변수에 담는다.
 * - 래퍼 클래스(Integer, Double, Character, Boolean...) : 값을 감싼 "객체"이고,
 *   변수에는 그 객체가 저장된 메모리 주소(참조)가 담긴다.
 *
 * [왜 래퍼 클래스가 필요한가?]
 * 컬렉션(List, Map 등)은 객체만 담을 수 있어서 List<int>는 불가능하고
 * List<Integer>로 써야 한다. 또 null(값 없음)을 표현할 수 있다.
 *
 * [이 예제가 보여주는 함정]
 * == 연산자는 기본형이면 "값"을, 참조형이면 "주소"를 비교한다.
 * 그래서 객체의 값을 비교할 때는 반드시 equals()를 써야 한다.
 */
public class NumberBox {
    public static void main(String[] args) {
        // 기본형: 100이라는 값이 변수에 직접 들어간다.
        int a = 100;
        int b = 100;

        // 래퍼 클래스: 100을 감싼 Integer 객체가 만들어지고 그 주소가 들어간다.
        // (int를 Integer로 자동 변환해주는 것을 오토박싱(auto-boxing)이라 한다)
        Integer A = 100;
        Integer B = 100;

        // 기본형 비교 (값 비교)
        // 100 == 100 → true
        System.out.println("a == b: " + (a == b));

        // 래퍼 클래스 비교 (주소 비교)
        // 두 객체의 "주소"가 같은지를 본다.
        //
        // ※ 주의: 여기서는 true가 나온다!
        //   자바는 -128 ~ 127 범위의 Integer를 미리 만들어 캐시(Integer Cache)에 넣어두고
        //   재사용하기 때문에 A와 B가 같은 객체를 가리킨다.
        //   값을 1000으로 바꿔서 실행해보면 false가 나온다. ← 꼭 직접 해보기!
        System.out.println("A == B: " + (A == B));

        // 래퍼 클래스 equals() 메서드 비교 (값 비교)
        // 주소와 상관없이 "안에 담긴 값"이 같은지 비교하므로 항상 안전하다.
        // → 객체 비교는 == 이 아니라 equals() 를 쓴다는 것이 결론.
        System.out.println("A.equals(B): " + A.equals(B));
    }
}
