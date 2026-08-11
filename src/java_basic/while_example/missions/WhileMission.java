package java_basic.while_example.missions;

import java.util.Scanner;

/**
 * WHILE 미션: 주식 매수 자금 관리 프로그램
 *
 * 목표:
 * 사용자가 종료 메뉴를 선택할 때까지 메뉴를 반복해서 보여주세요.
 *
 * 메뉴:
 * 1. 잔액 확인
 * 2. 투자금 입금
 * 3. 주식 매수
 * 0. 종료
 *
 * 규칙:
 * - 최초 잔액은 100_000원입니다.
 * - 입금액만큼 잔액을 증가시킵니다.
 * - 주식 매수 금액만큼 잔액을 감소시킵니다.
 * - 매수 금액이 잔액보다 크면 "잔액이 부족합니다."를 출력합니다.
 * - 0을 입력할 때까지 메뉴가 계속 반복되어야 합니다.
 *
 * 예상 실행 예:
 * 선택: 2
 * 입금액: 50000
 * 현재 잔액: 150000원
 *
 * 선택: 3
 * 매수 금액: 30000
 * 매수 완료. 현재 잔액: 120000원
 *
 * 힌트:
 * - 반복 여부를 저장할 boolean 변수를 만들어보세요.
 * - 메뉴 처리는 switch 또는 if-else를 사용할 수 있습니다.
 * - 잔액 부족 검사는 if문으로 처리하세요.
 */
public class WhileMission {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int balance = 100_000;

        // TODO 1: while문 밖에 반복 여부를 저장할 변수를 선언하세요.

        // TODO 2: 종료를 선택할 때까지 실행되는 while문을 작성하세요.
        // TODO 3: 반복할 때마다 메뉴를 출력하고 번호를 입력받으세요.
        // TODO 4: 메뉴 번호에 맞춰 잔액 확인, 입금, 매수, 종료를 처리하세요.
        // TODO 5: 반복문이 끝나면 "프로그램을 종료합니다."를 출력하세요.

        System.out.println("현재 시작 잔액: " + balance + "원");
        scanner.close();
    }
}
