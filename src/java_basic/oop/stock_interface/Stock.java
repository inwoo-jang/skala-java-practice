package java_basic.oop.stock_interface;

/** 구현 클래스가 제공해야 할 주식 기능의 규칙만 선언하는 인터페이스입니다. */
public interface Stock {
    String getName();

    double getPrice();

    // 원본의 본문 없는 default 선언은 문법 오류라 일반 추상 메서드로 수정했습니다.
    void printInfo();
}
