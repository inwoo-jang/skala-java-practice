package java_basic.oop.calculator;

/** Calculator의 추상 메서드를 실제로 구현한 클래스입니다. */
public class MyCalculator extends Calculator {
    @Override
    protected void printHistory() {
        System.out.println("\n=== 계산 기록 ===");
        for (String record : history) {
            if (record == null) {
                break;
            }
            System.out.println(record);
        }
    }

    @Override
    protected String calculate(int firstNumber, String operator, int secondNumber) {
        if (operator.equals("/") && secondNumber == 0) {
            return firstNumber + " / " + secondNumber + " = 오류(0으로 나눔)";
        }

        double result = switch (operator) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "*" -> firstNumber * secondNumber;
            case "/" -> (double) firstNumber / secondNumber;
            default -> Double.NaN;
        };

        if (Double.isNaN(result)) {
            System.out.println("잘못된 연산자입니다.");
            return firstNumber + " " + operator + " " + secondNumber + " = 오류(잘못된 연산자)";
        }

        System.out.println("결과: " + result);
        return firstNumber + " " + operator + " " + secondNumber + " = " + result;
    }
}
