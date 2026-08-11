package java_basic.switch_example;

import java.util.Scanner;

/**
 * 사용자가 선택한 연산자에 따라 switch문으로 계산을 수행하는 예제입니다.
 */
public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("첫 번째 숫자: ");
        // Integer는 기본 자료형 int를 객체 형태로 표현하는 래퍼 클래스입니다.
        // 이 예제에서는 int를 사용해도 동일하게 계산할 수 있습니다.
        Integer firstNumber = scanner.nextInt();

        System.out.print("연산자(+ - * /): ");
        // next()는 공백 전까지 입력된 문자열 하나를 읽습니다.
        String operator = scanner.next();

        System.out.print("두 번째 숫자: ");
        Integer secondNumber = scanner.nextInt();

        // 나눗셈에서 두 번째 숫자가 0이면 계산할 수 없으므로 먼저 차단합니다.
        // 문자열의 내용은 == 대신 equals()로 비교합니다.
        if (operator.equals("/") && secondNumber == 0) {
            System.out.println("0으로 나눌 수 없습니다.");
            scanner.close();

            // main 메서드를 즉시 끝내 아래 switch문이 실행되지 않게 합니다.
            return;
        }

        // 모든 계산 결과를 실수로 표현하기 위해 결과 변수의 타입을 double로 선언합니다.
        double result;

        // operator의 값과 일치하는 case부터 실행합니다.
        switch (operator) {
            case "+":
                result = (double) firstNumber + secondNumber;
                // break가 없으면 다음 case까지 계속 실행되므로 여기서 switch를 종료합니다.
                break;
            case "-":
                result = (double) firstNumber - secondNumber;
                break;
            case "*":
                result = (double) firstNumber * secondNumber;
                break;
            case "/":
                // 하나를 double로 변환해야 5 / 2가 2가 아닌 2.5로 계산됩니다.
                result = (double) firstNumber / secondNumber;
                break;
            default:
                // 어느 case에도 해당하지 않는 연산자는 NaN(Not a Number)으로 표시합니다.
                result = Double.NaN;
                break;
        }

        // NaN이 아니면 정상 계산 결과이고, NaN이면 잘못된 연산자입니다.
        if (!Double.isNaN(result)) {
            System.out.println("결과: " + result);
        } else {
            System.out.println("잘못된 연산자입니다.");
        }

        scanner.close();
    }
}
