package java_basic.exception_example;

import java.util.Scanner;

/**
 * 잘못된 나눗셈 상황에서 예외를 직접 발생시키고 처리하는 예제입니다.
 */
public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] history = new String[100];
        int historyCount = 0;

        boolean isRun = true;
        while (isRun) {
            System.out.print("첫 번째 숫자: ");
            int firstNumber = scanner.nextInt();

            System.out.print("연산자(+ - * /): ");
            String operator = scanner.next().trim();

            System.out.print("두 번째 숫자: ");
            int secondNumber = scanner.nextInt();

            // try 블록에는 예외가 발생할 가능성이 있는 코드를 작성합니다.
            try {
                if (operator.equals("/") && secondNumber == 0) {
                    // throw는 예외 객체를 직접 만들어 호출 흐름 밖으로 던집니다.
                    // 아래 코드는 ArithmeticException 타입의 예외를 발생시킵니다.
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }
            } catch (ArithmeticException e) {
                // 발생한 ArithmeticException이 변수 e에 전달되어 이 블록에서 처리됩니다.
                // getMessage()는 예외를 만들 때 전달한 설명 문자열을 반환합니다.
                System.out.println(e.getMessage());

                // 오류가 발생한 위치와 호출 경로를 출력해 디버깅을 돕습니다.
                // 사용자용 서비스에서는 보통 로그에 기록하고 친절한 메시지만 보여줍니다.
                e.printStackTrace();

                // 잘못된 계산은 저장하지 않고 다음 while 반복으로 이동합니다.
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

            String record = firstNumber + " " + operator + " "
                    + secondNumber + " = " + result;

            if (historyCount < history.length) {
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
        for (String rec : history) {
            if (rec == null) {
                break;
            }
            System.out.println(rec);
        }

        /*
         * ================================================================
         * TODO: EXCEPTION 응용 미션 (프로그램이 종료되지 않게 방어하세요)
         * ================================================================
         *
         * [미션 1 - 문자 입력 처리]
         * 숫자를 입력할 자리에 "abc"를 입력해 현재 프로그램을 일부러 실패시켜 보세요.
         * 그 예외를 catch하여 "숫자만 입력해 주세요."라고 출력하세요.
         *
         * 힌트:
         *   - Scanner의 nextInt()에서 발생하는 예외 타입을 오류 메시지로 확인하세요.
         *   - 잘못 입력된 값은 scanner.next()로 비워줘야 반복할 수 있습니다.
         *
         * [미션 2 - 잘못된 연산자도 예외로 처리]
         * default에서 바로 출력하지 말고 IllegalArgumentException을 직접 발생시키세요.
         * catch에서는 사용자가 입력한 연산자를 포함한 안내 메시지를 출력하세요.
         *
         * 예상 결과:
         *   지원하지 않는 연산자입니다: %
         *
         * [미션 3 - finally 관찰]
         * try-catch 뒤에 finally를 추가하고 "계산 시도 종료"를 출력하세요.
         * 정상 계산과 예외 계산에서 모두 출력되는지 비교하세요.
         *
         * 스스로 답해볼 질문:
         *   - finally는 continue를 만나도 실행될까요?
         *   - 사용자에게 보여줄 메시지와 개발자용 오류 로그는 어떻게 다를까요?
         */

        scanner.close();
    }
}
