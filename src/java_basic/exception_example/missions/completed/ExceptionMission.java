package java_basic.exception_example.missions.completed;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * EXCEPTION 미션: 안전한 주식 주문 입력
 *
 * 사용자에게 주식 가격과 수량을 입력받아 총 주문 금액을 출력하세요.
 * 잘못된 입력이 들어와도 프로그램이 갑자기 종료되면 안 됩니다.
 *
 * 규칙:
 * - 숫자 대신 문자를 입력하면 "숫자만 입력해 주세요."를 출력합니다.
 * - 가격이나 수량이 0 이하이면 IllegalArgumentException을 발생시킵니다.
 * - IllegalArgumentException의 메시지는 "가격과 수량은 0보다 커야 합니다."입니다.
 * - 정상 입력이면 가격 * 수량으로 총 주문 금액을 출력합니다.
 * - 성공 여부와 관계없이 마지막에 "주문 입력을 종료합니다."를 출력합니다.
 *
 * 예상 실행 1:
 * 주식 가격: abc
 * 숫자만 입력해 주세요.
 * 주문 입력을 종료합니다.
 *
 * 예상 실행 2:
 * 주식 가격: 15000
 * 수량: -2
 * 가격과 수량은 0보다 커야 합니다.
 * 주문 입력을 종료합니다.
 *
 * 힌트:
 * - try 안에서 nextInt()로 두 값을 입력받으세요.
 * - 숫자가 아닌 입력의 예외 타입은 InputMismatchException입니다.
 * - 직접 예외를 발생시킬 때는 throw new IllegalArgumentException(...)을 사용합니다.
 * - 항상 실행할 코드는 finally에 작성합니다.
 */
public class ExceptionMission {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("주식 가격: ");
            int price = scanner.nextInt();
            System.out.print("수량: ");
            int quantity = scanner.nextInt();
            // DONE 1: 주식 가격과 수량을 입력받으세요.
            if (price <= 0 || quantity <= 0) {
                throw new IllegalArgumentException("가격과 수량은 0보다 커야 합니다.");
            }
            // DONE 2: 가격 또는 수량이 0 이하인지 검사하고 예외를 발생시키세요.
            int totalPrice = price * quantity;
            System.out.println("총 주문 금액: " + totalPrice + "원");
            // DONE 3: 정상 입력이면 총 주문 금액을 계산해서 출력하세요.

        } catch (InputMismatchException e) {
            System.out.println("숫자만 입력해 주세요.");
            // DONE 4: 숫자가 아닌 값을 입력했을 때의 메시지를 출력하세요.
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            // DONE 5: 직접 발생시킨 예외의 메시지를 출력하세요.
        } finally {
            System.out.println("주문 입력을 종료합니다.");
            // DONE 6: 성공과 실패에 관계없이 종료 메시지를 출력하세요.
            scanner.close();
        }
    }
}
