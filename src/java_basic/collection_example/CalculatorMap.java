package java_basic.collection_example;

import java.util.Scanner;

import java.util.Map;
import java.util.HashMap;

/**
 * List → Map(HashMap) 으로 계산 기록을 옮겨보는 예제.
 *
 * List: 순서(인덱스)로 값을 찾는다.
 * Map : 키(key)로 값(value)을 찾는다. 키는 중복될 수 없다.
 *
 * 순회는 entrySet()으로 (키, 값) 쌍을 한 번에 꺼내는 것이 기본이다.
 */
public class CalculatorMap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //*** String[] history = new String[100]; // 최대 100개의 계산 기록 저장
        //*** int historyCount = 0; // 저장된 기록 개수
        // List<String> history = new ArrayList<>();
        Map<Integer, String> historyMap = new HashMap<>(); // HashMap으로 계산 기록 저장

        boolean isRun = true;
        while (isRun) {
            // 첫 번째 숫자 입력
            System.out.print("첫 번째 숫자: ");
            int firstNumber = scanner.nextInt();

            // 연산자 입력
            System.out.print("연산자(+ - * /): ");
            String operator = scanner.next().trim();

            // 두 번째 숫자 입력
            System.out.print("두 번째 숫자: ");
            int secondNumber = scanner.nextInt();

            // 0으로 나누기 예외 처리
            try {
                if (operator.equals("/") && secondNumber == 0) {
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                continue;
            }

            // switch문으로 계산 (잘못된 연산자는 default에서 처리)
            double result;
            switch (operator) {
                case "+" -> result = firstNumber + secondNumber;
                case "-" -> result = firstNumber - secondNumber;
                case "*" -> result = firstNumber * secondNumber;
                case "/" -> result = (double) firstNumber / secondNumber;
                default -> {
                    System.out.println("잘못된 연산자입니다.");
                    continue;
                }
            }

            System.out.println("결과: " + result);

            String record = firstNumber + " " + operator + " " + secondNumber + " = " + result;
            // 키를 record.hashCode()로 쓰면 같은 계산을 두 번 하면 덮어써진다(키 중복).
            // 순번(1, 2, 3...)을 키로 쓰면 어떻게 달라질지 생각해 보자.
            historyMap.put(record.hashCode(), record);

            // 계속 여부 입력
            System.out.print("계속하려면 c(continue) / 종료하려면 q(quit) 입력: ");
            String choice = scanner.next().trim().toLowerCase();

            if (choice.equals("q") || choice.equals("quit")) {
                isRun = false;
            }
        }

        System.out.println("\n=== 계산 기록 ===");

        // Map의 entrySet()을 이용하여 기록 출력
        // HashMap은 입력 순서를 보장하지 않는다. 순서가 필요하면 LinkedHashMap을 쓴다.
        for (Map.Entry<Integer, String> entry : historyMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        scanner.close();
    }
}
