package java_basic.collection_example.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 컬렉션 퀴즈: 요구사항대로 구현되지 않은 곳을 찾아라
 *
 * 상황:
 * 아래 요구사항을 받아 작성한 코드가 리뷰 요청으로 올라왔습니다.
 * 컴파일은 4개 파트 모두 통과합니다. 하지만 요구사항을 만족하지 못하는 곳이 있습니다.
 * 리뷰어가 되어 문제를 찾아주세요.
 *
 * 입력 데이터는 ORDERS 배열 하나로 고정입니다. ("상품명,수량" 형식, 8건)
 *
 * ─────────────────────────────────────────────────────────────
 * 공통 요구사항 (모든 파트에 적용)
 * ─────────────────────────────────────────────────────────────
 * R1. 프로그램은 예외 없이 끝까지 실행되어야 한다.
 * R2. 같은 입력이면 어느 컴퓨터에서 몇 번을 돌리든 같은 결과가 나와야 한다.
 * R3. 실제로 쓰이지 않는 변수나 코드가 남아 있으면 안 된다.
 *
 * ─────────────────────────────────────────────────────────────
 * 파트별 요구사항과 기대 결과
 * ─────────────────────────────────────────────────────────────
 * PART 1. 전체 주문 목록을 "N번 주문: 상품명,수량" 형태로 출력한다.
 *         기대: 1번 주문: apple,3
 *               ...
 *               8번 주문: cherry,1          (총 8줄)
 *
 * PART 2. 주문 배열에 가장 먼저 등장한 상품의 이름을 출력한다.
 *         ORDERS의 첫 요소가 "apple,3"이므로 정답은 apple이다.
 *         기대: 가장 먼저 등록된 상품: apple
 *               apple이 맞는가? true
 *
 * PART 3. 수량이 3개 미만인 주문을 목록에서 제거하고 남은 주문을 출력한다.
 *         기대: 남은 주문: [apple,3, banana,5, apple,4, durian,5]
 *
 * PART 4. 첫 번째 주문을 취소하고 취소 전후 건수를 출력한다.
 *         기대: 취소 전: 8건
 *               취소 후: 7건
 *
 * ─────────────────────────────────────────────────────────────
 * 푸는 방법
 * ─────────────────────────────────────────────────────────────
 * 1. 먼저 "실행하지 말고" 코드만 읽으세요.
 * 2. 파트마다 위 기대 결과가 나올지 판단하고, 아니라면 그 이유를 적으세요.
 *    - 기대와 다른 값이 나오는가?
 *    - 예외가 터지는가? 터진다면 어떤 예외인가?
 *    - 결과는 맞는데 어떤 요구사항을 어겼는가?
 * 3. 그 다음 실행해서 예상과 맞춰보세요.
 * 4. 마지막으로 요구사항을 만족하도록 고쳐보세요.
 *
 * 문제는 총 4개이고 성격이 다릅니다:
 * - 기대 결과는 맞지만 공통 요구사항 하나를 어긴 것 1개
 * - 예외는 없지만 기대와 다른 값이 나오는 것 1개
 * - 실행하면 예외가 터지는 것 2개 (예외 이름이 서로 다릅니다)
 *
 * 힌트: 컴파일러는 4개 다 통과시킵니다. 컴파일 = 요구사항 충족이 아닙니다.
 */
public class OrderQuiz {

    static final String[] ORDERS = {
            "apple,3", "banana,5", "apple,2", "cherry,1",
            "banana,2", "apple,4", "durian,5", "cherry,1"
    };

    public static void main(String[] args) {
        // 한 번에 하나씩만 실행해 보세요.
        // 앞 파트에서 예외가 터지면 뒤 파트는 실행조차 되지 않습니다.
        part1();
        part2();
        part3();
        part4();
    }

    // =====================================================================
    // PART 1. 주문 목록 출력하기
    //   요구사항: 전체 주문을 "N번 주문: 상품명,수량" 형태로 출력한다.
    //   기대 결과: 1번 주문: apple,3  ~  8번 주문: cherry,1  (총 8줄)
    // =====================================================================
    static void part1() {
        System.out.println("--- PART 1 ---");
        List<String> orderList = new ArrayList<>(Arrays.asList(ORDERS));

        Iterator<String> orderIterator = orderList.iterator();

        int orderNumber = 1;
        for (String order : orderList) {
            System.out.println(orderNumber + "번 주문: " + order);
            orderNumber++;
        }
    }

    // =====================================================================
    // PART 2. 가장 먼저 등록된 상품 확인하기
    //   요구사항: ORDERS에 가장 먼저 등장한 상품명을 출력한다.
    //             ORDERS[0]이 "apple,3"이므로 정답은 apple이다.
    //   기대 결과: 가장 먼저 등록된 상품: apple
    //              apple이 맞는가? true
    // =====================================================================
    static void part2() {
        System.out.println("--- PART 2 ---");
        Map<String, Integer> totalMap = new HashMap<>();

        for (String order : ORDERS) {
            String[] parts = order.split(",");
            String productName = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            totalMap.put(productName, totalMap.getOrDefault(productName, 0) + quantity);
        }

        // 주문 배열의 맨 앞이 "apple,3"이니 apple이 가장 먼저 들어갔다.
        // 따라서 Map에서도 apple이 첫 번째로 나올 것이다.
        String firstProduct = totalMap.keySet().iterator().next();
        System.out.println("가장 먼저 등록된 상품: " + firstProduct);
        System.out.println("apple이 맞는가? " + firstProduct.equals("apple"));
    }

    // =====================================================================
    // PART 3. 수량이 3개 미만인 주문 제거하기
    //   요구사항: 수량 3개 미만 주문을 목록에서 빼고 남은 주문을 출력한다.
    //   기대 결과: 남은 주문: [apple,3, banana,5, apple,4, durian,5]
    // =====================================================================
    static void part3() {
        System.out.println("--- PART 3 ---");
        List<String> orderList = new ArrayList<>(Arrays.asList(ORDERS));

        for (String order : orderList) {
            int quantity = Integer.parseInt(order.split(",")[1]);
            if (quantity < 3) {
                orderList.remove(order);
            }
        }

        System.out.println("남은 주문: " + orderList);
    }

    // =====================================================================
    // PART 4. 첫 번째 주문 취소하기
    //   요구사항: 첫 번째 주문을 취소하고 취소 전후 건수를 출력한다.
    //   기대 결과: 취소 전: 8건
    //              취소 후: 7건
    // =====================================================================
    static void part4() {
        System.out.println("--- PART 4 ---");
        List<String> orderList = Arrays.asList(ORDERS);

        System.out.println("취소 전: " + orderList.size() + "건");
        orderList.remove(0);
        System.out.println("취소 후: " + orderList.size() + "건");
    }
}
