package java_basic.exception_example.missions;

/**
 * 미션 1: RuntimeException과 checked exception 비교하기
 *
 * 용어 정리:
 * - RuntimeException 계열: unchecked exception
 *   컴파일러가 try-catch 또는 throws 작성을 강제하지 않습니다.
 * - Exception 계열(RuntimeException 제외): checked exception
 *   컴파일러가 try-catch 또는 throws 작성을 강제합니다.
 * - "Non-RuntimeException"이라는 이름의 Java 클래스가 따로 있는 것은 아닙니다.
 *
 * 목표:
 * 1. 가격이 0 이하이면 InvalidPriceException을 발생시키세요.
 * 2. 데이터 서버를 사용할 수 없으면 StockDataException을 발생시키세요.
 * 3. main에서 두 예외를 각각 처리하고 메시지를 출력하세요.
 *
 * 예상 결과:
 * 가격 오류: 주식 가격은 0보다 커야 합니다.
 * 데이터 오류: 주식 데이터를 불러올 수 없습니다.
 *
 * 힌트:
 * - 예외 발생: throw new 예외클래스("메시지");
 * - checked exception을 발생시키는 메서드에는 throws 선언이 필요합니다.
 * - e.getMessage()로 예외가 가진 메시지를 읽을 수 있습니다.
 */
public class ExceptionTypesMission {
    public static void main(String[] args) {
        // TODO 1: validatePrice(-100)을 호출하고 InvalidPriceException을 catch하세요.

        // TODO 2: loadStockName(false)를 호출하고 StockDataException을 catch하세요.
    }

    /**
     * RuntimeException 계열은 메서드에 throws를 선언하지 않아도 됩니다.
     */
    static void validatePrice(int price) {
        if (price <= 0) {
            // TODO 3: InvalidPriceException을 직접 발생시키세요.
        }
    }

    /**
     * StockDataException은 checked exception이므로 throws 선언이 필요합니다.
     */
    static String loadStockName(boolean serverAvailable) throws StockDataException {
        if (!serverAvailable) {
            // TODO 4: StockDataException을 직접 발생시키세요.
        }

        return "스칼라 AI";
    }
}

/**
 * 잘못된 가격처럼 프로그래밍 입력 검증에 사용할 unchecked exception입니다.
 */
class InvalidPriceException extends RuntimeException {
    InvalidPriceException(String message) {
        super(message);
    }
}

/**
 * 외부 데이터 로딩 실패처럼 호출자가 반드시 대응해야 한다고 가정한 checked exception입니다.
 */
class StockDataException extends Exception {
    StockDataException(String message) {
        super(message);
    }
}
