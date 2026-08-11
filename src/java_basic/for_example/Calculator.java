package java_basic.for_example;

import java.util.Scanner;

/**
 * 반복 계산 결과를 배열에 저장하고 for-each문으로 출력하는 예제입니다.
 */
public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // String 값을 최대 100개 저장할 수 있는 고정 길이 배열입니다.
        // 객체 배열의 각 칸은 처음에 null로 초기화됩니다.
        String[] history = new String[100];

        // 실제로 저장된 기록 개수이자 다음 기록을 넣을 배열 인덱스입니다.
        int historyCount = 0;

        boolean isRun = true;

        // 계산 입력은 횟수를 미리 모르므로 while문으로 반복합니다.
        while (isRun) {
            System.out.print("첫 번째 숫자: ");
            int firstNumber = scanner.nextInt();

            System.out.print("연산자(+ - * /): ");
            String operator = scanner.next().trim();

            System.out.print("두 번째 숫자: ");
            int secondNumber = scanner.nextInt();

            if (operator.equals("/") && secondNumber == 0) {
                System.out.println("0으로 나눌 수 없습니다.");
                continue;
            }

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

            // 계산에 사용한 값과 결과를 하나의 문자열로 조립합니다.
            String record = firstNumber + " " + operator + " "
                    + secondNumber + " = " + result;

            // 배열의 크기보다 많은 기록을 넣으면 오류가 발생하므로 먼저 검사합니다.
            if (historyCount < history.length) {
                // 첫 기록은 history[0], 다음 기록은 history[1]에 차례대로 저장됩니다.
                history[historyCount] = record;
                historyCount++;
            }

            System.out.print("계속하려면 c(continue) / 종료하려면 q(quit) 입력: ");
            String choice = scanner.next().trim().toLowerCase();

            if (choice.equals("q") || choice.equals("quit")) {
                isRun = false;
            }
        }

        System.out.println("\n=== 계산 기록 ===");

        // for-each문은 history 배열의 값을 처음부터 하나씩 rec에 담아 실행합니다.
        for (String rec : history) {
            // 기록이 저장되지 않은 첫 null 칸부터는 뒤에도 값이 없으므로 반복을 종료합니다.
            if (rec == null) {
                break;
            }
            System.out.println(rec);
        }

        /*
         * ================================================================
         * TODO: FOR 응용 미션 (기록 배열을 활용하세요)
         * ================================================================
         *
         * [미션 1 - 기록에 번호 붙이기]
         * for-each문 대신 기본 for문을 사용해 기록 앞에 번호를 출력하세요.
         *
         * 예상 결과:
         *   1. 10 + 20 = 30.0
         *   2. 8 * 3 = 24.0
         *
         * 힌트:
         *   - 반복 범위는 배열 전체 길이가 아니라 historyCount까지입니다.
         *   - 배열 인덱스는 0부터, 화면의 기록 번호는 1부터 시작합니다.
         *
         * [미션 2 - 최신 기록부터 출력]
         * 계산 기록을 마지막 계산부터 첫 계산 순서로 출력하세요.
         *
         * 힌트:
         *   - for문의 시작값은 historyCount - 1입니다.
         *   - 반복할 때 인덱스를 증가시키지 말고 감소시켜야 합니다.
         *
         * [미션 3 - 특정 연산 기록만 찾기]
         * 사용자에게 +, -, *, / 중 하나를 입력받고 해당 연산 기록만 출력하세요.
         *
         * 예상 흐름:
         *   찾을 연산자: *
         *   8 * 3 = 24.0
         *   5 * 5 = 25.0
         *
         * 스스로 답해볼 질문:
         *   - 문자열 안에 특정 내용이 있는지는 어떤 메서드로 검사할까요?
         *   - 검색 결과가 0개일 때는 어떻게 알 수 있을까요?
         */

        scanner.close();
    }
}
