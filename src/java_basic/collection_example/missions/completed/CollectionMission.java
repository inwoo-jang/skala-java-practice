package java_basic.collection_example.missions.completed;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;

/**
 * COLLECTION 미션: 주문 기록 정리하기
 *
 * 계산기 예제(java_basic.collection_example)에서 배운
 * List / Set / Map / Iterator 를 실제 데이터에 적용해 봅니다.
 *
 * 아래 orders 배열에는 "상품명,수량" 형태의 주문 기록이 들어 있습니다.
 * (같은 상품이 여러 번 주문될 수 있습니다.)
 *
 * 문제:
 * 1. 배열을 List<String>에 담고, Iterator로 전체 주문을 출력하세요.
 * 2. 주문된 상품 "종류"만 중복 없이 Set<String>에 모아 출력하세요.
 * 3. 상품별 총 주문 수량을 Map<String, Integer>에 집계하세요.
 * 4. 가장 많이 팔린 상품과 그 수량을 구하세요.
 * 5. 수량이 3개 미만인 주문만 List에서 제거하고 남은 개수를 출력하세요.
 *
 * 정답 확인용 결과:
 * - 전체 주문 건수: 8건
 * - 상품 종류 수: 4개 (apple, banana, cherry, durian)
 * - 상품별 수량: apple=9, banana=7, cherry=2, durian=5
 * - 최다 판매 상품: apple (9개)
 * - 수량 3개 미만 주문 제거 후 남은 건수: 4건 (apple,3 / banana,5 / apple,4 / durian,5)
 *
 * 힌트:
 * - "apple,3" 을 상품명과 수량으로 나누려면 String.split(",") 을 쓰고,
 * 수량은 Integer.parseInt() 로 int 로 바꿉니다.
 * - List에 배열을 한 번에 담고 싶다면 반복문으로 add() 하거나
 * new ArrayList<>(Arrays.asList(orders)) 를 사용할 수 있습니다.
 * - Set은 중복을 허용하지 않습니다. add()만 계속 호출하면 종류만 남습니다.
 * 입력 순서를 유지하고 싶다면 LinkedHashSet 을 고려하세요.
 * - Map에 누적할 때는 이미 키가 있는지 확인해야 합니다.
 * map.getOrDefault(key, 0) + 수량 → map.put(key, ...) 패턴이 편합니다.
 * - Map 순회는 for (Map.Entry<String, Integer> entry : map.entrySet()) 형태입니다.
 * entry.getKey() / entry.getValue() 로 키와 값을 꺼냅니다.
 * - 최댓값 찾기는 ForMission의 최고가 찾기와 같은 방식입니다.
 * - 반복문(for-each) 안에서 list.remove()를 호출하면
 * ConcurrentModificationException 이 납니다.
 * Iterator를 직접 만들고 iterator.remove() 를 사용하세요.
 */
public class CollectionMission {
    public static void main(String[] args) {
        String[] orders = {
                "apple,3", "banana,5", "apple,2", "cherry,1",
                "banana,2", "apple,4", "durian,5", "cherry,1"
        };

        List<String> orderList = new ArrayList<>(Arrays.asList(orders));
        // COMPLETED 1: orders를 List<String> orderList에 담으세요.

        Iterator<String> iterator = orderList.iterator();
        int orderNumber = 1;

        while (iterator.hasNext()) {
            String order = iterator.next();
            System.out.println(orderNumber + "번 주문: " + order);
            orderNumber++;
        }
        // COMPLETED 2: Iterator로 orderList를 순회하며 전체 주문을 출력하세요.
        // 출력 예) 1번 주문: apple,3
        // hasNext()는 Interator 인터페이스가 제공하는 메서드

        Set<String> productSet = new HashSet<>();

        for (String order : orders) {
            String[] parts = order.split(",");
            String productName = parts[0];

            productSet.add(productName);
        }
        System.out.println(productSet + " (상품 종류 수: " + productSet.size() + "개)");

        // COMPLETED 3: Set<String> productSet에 상품명만 담아 종류와 개수를 출력하세요.
        // split(",")의 결과 배열에서 [0]이 상품명, [1]이 수량입니다.

        Map<String, Integer> totalMap = new HashMap<>();

        for (String order : orders) {
            String[] parts = order.split(",");
            String productName = parts[0];
            int quantity = Integer.parseInt(parts[1]);

            int previousTotal = totalMap.getOrDefault(productName, 0);
            totalMap.put(productName, previousTotal + quantity);
        }
        // COMPLETED 4: Map<String, Integer> totalMap에 상품별 총 수량을 집계하세요.
        // 이미 담긴 값에 이번 수량을 더해서 다시 put 해야 합니다.

        for (Map.Entry<String, Integer> entry : totalMap.entrySet()) {
            String productName = entry.getKey();
            int totalQuantity = entry.getValue();

            System.out.println(productName + "=" + totalQuantity);
        }
        // COMPLETED 5: entrySet()으로 totalMap을 순회하며 "상품명=수량" 형태로 출력하세요.

        String bestProduct = "";
        int bestCount = 0;

        for (Map.Entry<String, Integer> entry : totalMap.entrySet()) {
            if (entry.getValue() > bestCount) {
                bestProduct = entry.getKey();
                bestCount = entry.getValue();
            }
        }

        System.out.println("최다 판매 상품: " + bestProduct + " (" + bestCount + "개)");
        // COMPLETED 6: 최다 판매 상품과 수량을 구해 출력하세요.

        for (Iterator<String> it = orderList.iterator(); it.hasNext();) {
            String order = it.next();
            String[] parts = order.split(",");
            int quantity = Integer.parseInt(parts[1]);

            if (quantity < 3) {
                it.remove();
            }
        }
        System.out.println("수량 3개 미만 주문 제거 후 남은 건수: " + orderList.size() + "건");
        // COMPLETED 7: Iterator를 사용해 수량이 3개 미만인 주문을 orderList에서 제거하고,
        // 남은 주문 건수를 출력하세요. (list.remove()가 아니라 iterator.remove())
    }
}
