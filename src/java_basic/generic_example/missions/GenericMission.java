package java_basic.generic_example.missions;

// Box는 옆 폴더(generic_example)의 예제 클래스를 그대로 가져다 씁니다.
// Pair는 이 파일 아래쪽에 직접 정의돼 있으므로 import가 필요 없습니다.
import java_basic.generic_example.Box;

/**
 * GENERIC 미션 1: 타입 파라미터 직접 써보기
 *
 * Box<T>가 값 하나를 담았다면, 여기서는 값 두 개를 담는 Pair<K, V>를 만듭니다.
 * (Map의 key-value 한 쌍을 떠올리면 됩니다.)
 *
 * 문제:
 * 1. Pair 클래스를 제네릭 클래스로 바꾸세요. 타입 파라미터는 K, V 두 개입니다.
 * 2. Pair를 두 가지 조합으로 만들어 출력하세요.
 * - Pair<String, Integer> : 종목명과 가격
 * - Pair<Integer, String> : 순번과 주문 내용
 * 3. 어떤 Box든 받아서 내용물을 출력하는 제네릭 "메서드" printBox를 완성하세요.
 * 4. 두 값 중 큰 쪽을 돌려주는 제네릭 메서드 max를 완성하세요.
 *
 * 예상 출력:
 * 삼성전자 -> 70000
 * 1 -> apple,3
 * 상자 내용물: Hello
 * 상자 내용물: 42
 * 큰 값: 99
 * 큰 값: banana
 *
 * 힌트:
 * - 클래스에 타입 파라미터가 두 개면 class Pair<K, V> 처럼 콤마로 나열합니다.
 * - 필드 타입도 String이 아니라 K, V로 바꿔야 합니다.
 * - 제네릭 "메서드"는 반환 타입 앞에 타입 파라미터를 적습니다.
 * static <T> void printBox(Box<T> box) { ... }
 * 클래스가 제네릭이 아니어도 메서드 하나만 제네릭으로 만들 수 있습니다.
 * - 크기를 비교하려면 그 타입이 compareTo를 가지고 있어야 합니다.
 * 즉 타입을 <T extends Comparable<T>> 로 제한해야 합니다. (implements가 아니라 extends)
 * - a.compareTo(b)는 a가 더 크면 양수, 같으면 0, 작으면 음수를 돌려줍니다.
 */
public class GenericMission {
    public static void main(String[] args) {

        Pair<String, Integer> stockPrice = new Pair("삼성전자", 70000);
        System.out.println(stockPrice);
        // TODO 2-1: Pair<String, Integer>로 ("삼성전자", 70000)을 만들어 출력하세요.
        // 출력 형태: 삼성전자 -> 70000

        Pair<Integer, String> order = new Pair<>(1, "apple,3");
        System.out.println(order);
        // TODO 2-2: Pair<Integer, String>로 (1, "apple,3")을 만들어 출력하세요.

        printBox(new Box<>("Hello"));
        printBox(new Box<>(42));
        // TODO 3-1: printBox에 Box<String>("Hello")를 넘겨 호출하세요.
        // TODO 3-2: printBox에 Box<Integer>(42)를 넘겨 호출하세요.
        // 같은 메서드 하나가 두 타입 모두를 처리하는지 확인하세요.

        // TODO 4-1: max(10, 99)의 결과를 "큰 값: 99" 형태로 출력하세요.
        // TODO 4-2: max("apple", "banana")의 결과를 출력하세요.
        // 문자열도 Comparable이라 사전순으로 비교됩니다.
    }

    // TODO 3: 어떤 타입의 Box든 받을 수 있도록 제네릭 메서드로 만드세요.
    // 지금은 Box<String>만 받을 수 있어 TODO 3-2에서 컴파일 에러가 납니다.
    static <T> void printBox(Box<T> box) {
        System.out.println("상자 내용물: " + box.getItem());
    }

    // TODO 4: 두 값 중 큰 쪽을 돌려주도록 완성하세요.
    // Object로는 비교가 불가능합니다. 타입 파라미터에 제한을 걸어야 합니다.
    static Object max(Object first, Object second) {
        return null;
    }
}

// TODO 1: 제네릭 클래스로 바꾸세요. class Pair<K, V> 형태입니다.
class Pair<K, V> {
    // TODO 1: 필드 타입을 K, V로 바꾸세요.
    private final K key;
    private final V value;

    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // TODO 1: 반환 타입도 K, V로 바꿔야 합니다.
    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + " -> " + value;
    }
}
