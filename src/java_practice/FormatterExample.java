package java_practice;

import java.time.LocalDateTime;

public class FormatterExample {
    public static void main(String[] args) {
        // 1. 기본 자료형 출력
        String name = "스칼라";
        int age = 30;
        double height = 175.567;
        boolean student = true;
        char grade = 'A';

        System.out.printf("이름: %s%n", name);       // 문자열
        System.out.printf("나이: %d세%n", age);     // 정수
        System.out.printf("키: %.1fcm%n", height);  // 실수
        System.out.printf("학생: %b%n", student);   // 논리값
        System.out.printf("등급: %c%n", grade);     // 문자

        System.out.println();

        // 2. 소수점 자릿수
        double pi = Math.PI;

        System.out.printf("기본값: %f%n", pi);
        System.out.printf("소수점 2자리: %.2f%n", pi);
        System.out.printf("소수점 4자리: %.4f%n", pi);

        System.out.println();

        // 3. 문자열과 숫자 정렬
        System.out.printf("|%10s|%n", "Java");   // 오른쪽 정렬
        System.out.printf("|%-10s|%n", "Java");  // 왼쪽 정렬

        System.out.printf("|%10d|%n", 123);
        System.out.printf("|%-10d|%n", 123);

        System.out.println();

        // 4. 빈자리를 0으로 채우기
        System.out.printf("상품 번호: %05d%n", 42);
        // 결과: 00042

        System.out.println();

        // 5. 큰 숫자에 쉼표 넣기
        int price = 123456789;

        System.out.printf("가격: %,d원%n", price);
        // 결과: 123,456,789원

        System.out.println();

        // 6. 양수와 음수 표시
        System.out.printf("양수: %+d%n", 100);
        System.out.printf("음수: %+d%n", -100);

        // 음수를 괄호로 표시
        System.out.printf("손실: %(d원%n", -5000);
        // 결과: 손실: (5000)원

        System.out.println();

        // 7. 정수를 여러 진법으로 출력
        int number = 255;

        System.out.printf("10진수: %d%n", number);
        System.out.printf("16진수: %x%n", number);
        System.out.printf("16진수 대문자: %X%n", number);
        System.out.printf("8진수: %o%n", number);

        System.out.println();

        // 8. 같은 값을 여러 번 사용
        System.out.printf(
                "%1$s는 %2$d살입니다. 다시 말하면 이름은 %1$s입니다.%n",
                name,
                age
        );

        System.out.println();

        // 9. 날짜와 시간 출력
        LocalDateTime now = LocalDateTime.now();

        System.out.printf(
                "현재 날짜: %1$tY년 %1$tm월 %1$td일%n",
                now
        );

        System.out.printf(
                "현재 시간: %1$tH시 %1$tM분 %1$tS초%n",
                now
        );

        System.out.println();

        // 10. 표 형태로 출력
        System.out.printf("%-10s %5s %12s%n", "상품", "수량", "가격");
        System.out.println("--------------------------------");

        System.out.printf("%-10s %5d %,12d원%n", "노트북", 2, 1500000);
        System.out.printf("%-10s %5d %,12d원%n", "키보드", 10, 85000);
        System.out.printf("%-10s %5d %,12d원%n", "마우스", 7, 42000);
    }
}
