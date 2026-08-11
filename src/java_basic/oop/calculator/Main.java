package java_basic.oop.calculator;

public class Main {
    public static void main(String[] args) {
        // 부모 타입 변수에 자식 객체를 담는 업캐스팅입니다.
        Calculator calculator = new MyCalculator();
        calculator.run();
    }
}
