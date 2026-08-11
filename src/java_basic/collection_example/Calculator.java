package java_basic.collection_example;

import java.util.Scanner;
import java.util.Iterator;

import java.util.List;
import java.util.ArrayList;

/**
 * 배열 → List(ArrayList) 로 계산 기록을 옮겨보는 예제.
 *
 * 배열의 한계:
 * - 크기를 미리 정해야 한다(new String[100]).
 * - 실제로 몇 개가 들어있는지 따로 세어야 한다(historyCount).
 * List의 장점:
 * - add() 하면 크기가 알아서 늘어난다.
 * - size()로 실제 개수를 바로 알 수 있다.
 */
public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //*** String[] history = new String[100]; // 최대 100개의 계산 기록 저장
        //*** int historyCount = 0; // 저장된 기록 개수
        // List는 인터페이스, ArrayList는 구현체 → 변수는 인터페이스 타입으로 선언한다.
        List<String> history = new ArrayList<>(); // 계산 기록 저장(크기 제한 없음)

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
            // if (historyCount < history.length) {
            //     history[historyCount] = record;
            //     historyCount++;
            // }
            history.add(record); // 인덱스 관리가 필요 없다.

            // 계속 여부 입력
            System.out.print("계속하려면 c(continue) / 종료하려면 q(quit) 입력: ");
            String choice = scanner.next().trim().toLowerCase();

            if (choice.equals("q") || choice.equals("quit")) {
                isRun = false;
            }
        }

        // for-each로 기록 출력
        // System.out.println("\n=== 계산 기록 ===");
        // for (String rec : history) {
        //     if (rec == null) break; // 저장된 만큼만 출력
        //     System.out.println(rec);
        // }

        // Iterator: 컬렉션을 순회하는 표준 방식.
        // hasNext()로 남은 요소가 있는지 확인하고, next()로 하나씩 꺼낸다.
        Iterator<String> iterator = history.iterator();
        System.out.println("\n=== 계산 기록 ===");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        scanner.close();
    }
}
