package java_basic.while_example;

import java.util.Scanner;

/**
 * while문을 이용해 사용자가 종료할 때까지 계산을 반복하는 예제입니다.
 */
public class Calculator {
    public static void main(String[] args) {
        // 키보드 입력을 읽기 위한 Scanner 객체입니다.
        Scanner scanner = new Scanner(System.in);

        // while문의 실행 여부를 결정하는 상태 변수입니다.
        // true인 동안 반복하고, 사용자가 종료를 선택하면 false로 변경합니다.
        boolean isRun = true;

        while (isRun) {
            // 반복문 본문은 계산 한 번을 처리하는 작업 단위입니다.
            System.out.print("첫 번째 숫자: ");
            int firstNumber = scanner.nextInt();

            System.out.print("연산자(+ - * /): ");
            // trim()은 문자열 앞뒤의 불필요한 공백을 제거합니다.
            String operator = scanner.next().trim();

            System.out.print("두 번째 숫자: ");
            int secondNumber = scanner.nextInt();

            // &&는 양쪽 조건이 모두 true인지 확인합니다.
            // 나눗셈이면서 두 번째 수가 0일 때 계산을 진행하지 않습니다.
            if (operator.equals("/") && secondNumber == 0) {
                System.out.println("0으로 나눌 수 없습니다.");

                // continue는 현재 반복의 남은 코드를 건너뛰고
                // while문의 조건 검사 위치로 즉시 돌아갑니다.
                continue;
            }

            double result;

            // operator와 일치하는 case 하나를 골라 계산합니다.
            // 화살표 switch 문법에서는 break를 작성하지 않아도 됩니다.
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

            System.out.print("계속하려면 c(continue) / 종료하려면 q(quit) 입력: ");
            // toLowerCase() 덕분에 Q와 q를 같은 입력으로 처리할 수 있습니다.
            String choice = scanner.next().trim().toLowerCase();

            // ||는 두 조건 중 하나라도 true인지 확인합니다.
            if (choice.equals("q") || choice.equals("quit")) {
                // 다음 while 조건 검사가 false가 되어 반복문이 종료됩니다.
                isRun = false;
            }
        }

        
        /*
         * ================================================================
         * TODO: WHILE 응용 미션 (위에서부터 하나씩 도전하세요)
         * ================================================================
         *
         * [미션 1 - 계산 횟수 세기]
         * 정상적으로 완료된 계산이 총 몇 번인지 세어 프로그램 종료 시 출력하세요.
         *
         * 예상 결과:
         *   총 계산 횟수: 3회
         *
         * 힌트:
         *   - while문 밖에 int 변수를 하나 선언합니다.
         *   - 잘못된 연산이나 0 나눗셈은 횟수에 포함하면 안 됩니다.
         *
         * [미션 2 - q로 즉시 종료]
         * 첫 번째 숫자를 입력받기 전에 q를 입력해도 종료되게 바꿔보세요.
         *
         * 생각할 점:
         *   - nextInt()는 q를 숫자로 읽을 수 없습니다.
         *   - 먼저 String으로 받은 뒤 숫자로 변환하는 방법을 찾아보세요.
         *
         * [미션 3 - 다시 입력 제한]
         * 사용자가 잘못된 연산자를 세 번 연속 입력하면 프로그램을 종료하세요.
         * 올바른 연산에 성공하면 연속 실패 횟수는 다시 0이 되어야 합니다.
         *
         * 스스로 답해볼 질문:
         *   - 실패 횟수 변수는 while문 안과 밖 중 어디에 있어야 유지될까요?
         *   - break와 isRun = false 중 무엇을 사용해도 될까요?
         */

        // 반복이 모두 끝난 뒤 입력 자원을 닫습니다.
        scanner.close();
    }
}
