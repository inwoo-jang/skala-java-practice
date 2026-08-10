package java_practice;

import java.time.LocalDateTime;

/**
 * printf() 서식 문자열(format string) 실습.
 *
 * [println vs printf]
 * - println("나이: " + age + "세")  → 문자열을 +로 이어붙임. 정렬/자릿수 조절이 어렵다.
 * - printf("나이: %d세%n", age)     → 자리에 %d 같은 "서식 지정자"를 두고 값을 끼워 넣는다.
 *
 * [서식 지정자 구조]
 *   %[인자번호$][플래그][너비][.정밀도]변환문자
 *   예) %-10.2f  →  왼쪽정렬(-), 전체 10칸, 소수점 2자리, 실수(f)
 *
 * [자주 쓰는 변환 문자]
 *   %s 문자열 / %d 정수 / %f 실수 / %b 논리값 / %c 문자
 *   %x 16진수 / %o 8진수 / %n 줄바꿈 / %% 퍼센트 기호 자체
 *
 * ※ %n 과 \n 의 차이: \n 은 항상 LF 한 글자지만,
 *   %n 은 OS에 맞는 줄바꿈(윈도우 CRLF / 맥·리눅스 LF)을 알아서 넣어준다. → %n 권장
 */
public class FormatterExample {
    public static void main(String[] args) {
        // 1. 기본 자료형 출력
        // 각 자료형마다 대응하는 변환 문자가 정해져 있다. 안 맞으면 실행 중 예외가 난다.
        String name = "스칼라";
        int age = 30;
        double height = 175.567;
        boolean student = true;
        char grade = 'A';

        System.out.printf("이름: %s%n", name);       // 문자열
        System.out.printf("나이: %d세%n", age);     // 정수
        System.out.printf("키: %.1fcm%n", height);  // 실수 → 175.6 (반올림됨)
        System.out.printf("학생: %b%n", student);   // 논리값
        System.out.printf("등급: %c%n", grade);     // 문자

        System.out.println();

        // 2. 소수점 자릿수
        // %.숫자f 로 소수점 이하 몇 자리까지 보여줄지 정한다. 잘리는 게 아니라 반올림된다.
        double pi = Math.PI; // 3.141592653589793

        System.out.printf("기본값: %f%n", pi);        // %f 기본은 소수점 6자리 → 3.141593
        System.out.printf("소수점 2자리: %.2f%n", pi); // 3.14
        System.out.printf("소수점 4자리: %.4f%n", pi); // 3.1416

        System.out.println();

        // 3. 문자열과 숫자 정렬
        // 숫자(너비)를 적으면 그 칸 수만큼 자리를 확보한다. 기본은 오른쪽 정렬,
        // 앞에 - 를 붙이면 왼쪽 정렬. 표를 만들 때 핵심이 되는 기능.
        System.out.printf("|%10s|%n", "Java");   // 오른쪽 정렬 → |      Java|
        System.out.printf("|%-10s|%n", "Java");  // 왼쪽 정렬   → |Java      |

        System.out.printf("|%10d|%n", 123);
        System.out.printf("|%-10d|%n", 123);

        System.out.println();

        // 4. 빈자리를 0으로 채우기
        // 너비 앞에 0 을 붙이면 남는 칸을 공백 대신 0으로 채운다.
        // 주문번호, 사번처럼 자릿수를 고정해야 할 때 쓴다.
        System.out.printf("상품 번호: %05d%n", 42);
        // 결과: 00042

        System.out.println();

        // 5. 큰 숫자에 쉼표 넣기
        // , 플래그를 붙이면 3자리마다 천 단위 구분 기호가 들어간다.
        int price = 123456789;

        System.out.printf("가격: %,d원%n", price);
        // 결과: 123,456,789원

        System.out.println();

        // 6. 양수와 음수 표시
        // + 플래그: 양수에도 부호를 강제로 붙인다 (증감률 표시 등에 유용).
        System.out.printf("양수: %+d%n", 100);   // +100
        System.out.printf("음수: %+d%n", -100);  // -100

        // 음수를 괄호로 표시
        // ( 플래그: 음수를 회계 방식(괄호)으로 표현한다.
        System.out.printf("손실: %(d원%n", -5000);
        // 결과: 손실: (5000)원

        System.out.println();

        // 7. 정수를 여러 진법으로 출력
        // 같은 값 255를 진법만 바꿔서 표현한다. 대문자 변환문자(%X)를 쓰면 결과도 대문자.
        int number = 255;

        System.out.printf("10진수: %d%n", number);        // 255
        System.out.printf("16진수: %x%n", number);        // ff
        System.out.printf("16진수 대문자: %X%n", number);  // FF
        System.out.printf("8진수: %o%n", number);         // 377

        System.out.println();

        // 8. 같은 값을 여러 번 사용
        // %숫자$ → "몇 번째 인자를 쓸지" 직접 지정한다(1부터 시작).
        // 같은 값을 두 번 이상 넣어야 할 때 인자를 중복해서 넘기지 않아도 된다.
        System.out.printf(
                "%1$s는 %2$d살입니다. 다시 말하면 이름은 %1$s입니다.%n",
                name, // 1번 인자
                age   // 2번 인자
        );

        System.out.println();

        // 9. 날짜와 시간 출력
        // %t 뒤에 붙는 문자로 날짜의 어떤 부분을 뽑을지 정한다.
        //   tY 연도(4자리) / tm 월 / td 일 / tH 시(24시간) / tM 분 / tS 초
        // 하나의 날짜 객체에서 여러 부분을 뽑아야 하므로 1$ 인자 지정과 함께 쓰는 게 편하다.
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜+시각

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
        // 지금까지 배운 것을 조합하는 마무리 예제.
        // 헤더와 데이터 행에 "같은 너비"를 쓰는 것이 열을 맞추는 비결이다.
        //   %-10s → 상품명은 왼쪽 정렬 10칸
        //   %5d   → 수량은 오른쪽 정렬 5칸
        //   %,12d → 가격은 천 단위 쉼표 + 오른쪽 정렬 12칸
        System.out.printf("%-10s %5s %12s%n", "상품", "수량", "가격");
        System.out.println("--------------------------------");

        System.out.printf("%-10s %5d %,12d원%n", "노트북", 2, 1500000);
        System.out.printf("%-10s %5d %,12d원%n", "키보드", 10, 85000);
        System.out.printf("%-10s %5d %,12d원%n", "마우스", 7, 42000);
    }
}
