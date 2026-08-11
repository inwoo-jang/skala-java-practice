package java_basic.oop.calculator;

import java.util.Scanner;

/**
 * 계산의 공통 실행 순서는 구현하고, 세부 계산과 기록 출력은 자식에게 맡기는 추상 클래스입니다.
 */
public abstract class Calculator {
    protected String[] history = new String[100];
    protected int historyCount = 0;

    /** 템플릿 메서드: 입력 → 계산 → 저장 → 출력이라는 전체 흐름을 담당합니다. */
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRun = true;
            while (isRun) {
                System.out.print("첫 번째 숫자: ");
                int firstNumber = scanner.nextInt();

                System.out.print("연산자(+ - * /): ");
                String operator = scanner.next().trim();

                System.out.print("두 번째 숫자: ");
                int secondNumber = scanner.nextInt();

                // 실제 계산 방식은 자식 클래스가 구현한 calculate()가 결정합니다.
                String record = calculate(firstNumber, operator, secondNumber);

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
        }

        printHistory();
    }

    // 추상 메서드는 규칙만 정의하고 구현은 자식 클래스에 강제합니다.
    protected abstract void printHistory();

    protected abstract String calculate(int firstNumber, String operator, int secondNumber);
}
