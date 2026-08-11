package java_basic.oop.missions;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 미션 2: Object 클래스의 toString(), equals(), hashCode() 재정의하기
 *
 * 목표:
 * StockKey 객체는 market과 code가 모두 같으면 같은 종목으로 판단해야 합니다.
 *
 * 해야 할 일:
 * 1. toString()을 재정의해 "KOSPI:005930" 형태로 출력하세요.
 * 2. equals()를 재정의해 market과 code가 모두 같은지 비교하세요.
 * 3. hashCode()를 재정의해 같은 객체가 같은 해시값을 갖게 하세요.
 *
 * 모든 TODO를 완료한 뒤 예상 결과:
 * 첫 번째 종목: KOSPI:005930
 * first.equals(second): true
 * first.equals(third): false
 * Set에 저장된 종목 수: 2
 *
 * 힌트:
 * - 현재 클래스에서 Ctrl+Space 또는 Source Action을 사용하면 Override 메서드를 만들 수 있습니다.
 * - 문자열 비교에는 Objects.equals(a, b)를 사용할 수 있습니다.
 * - hashCode는 Objects.hash(market, code)를 사용할 수 있습니다.
 * - equals를 재정의하면 hashCode도 함께 재정의해야 합니다.
 */
public class ObjectMethodsMission {
    public static void main(String[] args) {
        StockKey first = new StockKey("KOSPI", "005930");
        StockKey second = new StockKey("KOSPI", "005930");
        StockKey third = new StockKey("NASDAQ", "AAPL");

        System.out.println("첫 번째 종목: " + first);
        System.out.println("first.equals(second): " + first.equals(second));
        System.out.println("first.equals(third): " + first.equals(third));

        Set<StockKey> stocks = new HashSet<>();
        stocks.add(first);
        stocks.add(second);
        stocks.add(third);

        // equals와 hashCode가 올바르면 first와 second는 중복으로 처리됩니다.
        System.out.println("Set에 저장된 종목 수: " + stocks.size());
    }
}

class StockKey {
    private final String market;
    private final String code;

    StockKey(String market, String code) {
        this.market = market;
        this.code = code;
    }

    // TODO 1: @Override를 붙여 toString()을 재정의하세요.

    // TODO 2: @Override를 붙여 equals(Object object)를 재정의하세요.
    // 비교 순서 힌트:
    // 1. this와 object가 같은 참조인지 확인
    // 2. object가 StockKey 타입인지 확인
    // 3. market과 code의 내용 비교

    // TODO 3: @Override를 붙여 hashCode()를 재정의하세요.

    // Objects import가 아직 사용되지 않아 경고가 보여도 TODO 완료 전에는 정상입니다.
    static boolean sameText(String first, String second) {
        return Objects.equals(first, second);
    }
}
