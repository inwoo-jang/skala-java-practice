package java_basic.for_example.missions.completed;

/**
 * FOR 미션: 일주일간 주가 분석하기
 *
 * 아래 prices 배열에는 일주일간의 종가가 저장되어 있습니다.
 * for문을 사용해서 다음 결과를 구하세요.
 *
 * 문제:
 * 1. 모든 가격을 "1일차: 15000원" 형태로 출력하세요.
 * 2. 전체 가격의 합계와 평균을 구하세요.
 * 3. 가장 높은 가격과 가장 낮은 가격을 구하세요.
 * 4. 전날보다 가격이 오른 날이 총 며칠인지 구하세요.
 *
 * 정답 확인용 결과:
 * - 합계: 109900원
 * - 평균: 15700.0원
 * - 최고가: 16200원
 * - 최저가: 15000원
 * - 상승한 날: 3일
 *
 * 힌트:
 * - 합계는 0부터 시작해서 각 가격을 더합니다.
 * - 최고가와 최저가는 prices[0]으로 시작하면 편리합니다.
 * - 전날과 비교하려면 prices[i]와 prices[i - 1]을 비교하세요.
 * - i - 1을 사용할 때 반복문의 시작 인덱스에 주의하세요.
 */
public class ForMission {
    public static void main(String[] args) {
        int[] prices = { 15_000, 15_500, 15_200, 16_000, 15_800, 16_200, 16_200 };

        // TODO 1: 기본 for문으로 일차와 가격을 모두 출력하세요.
        for (int i = 0; i < prices.length; i++) {
            System.out.println(
                    (i + 1) + "일차: " + prices[i] + "원");
        }

        // TODO 2: for문으로 모든 가격을 total에 더하세요.
        int total = 0;
        for (int i = 0; i < prices.length; i++) {
            total += prices[i];
        }

        // TODO 3: total과 prices.length를 이용해 평균을 계산하세요.
        // 정수 나눗셈이 되지 않도록 자료형에 주의하세요.
        double average = (double) total / prices.length;
        int highest = prices[0];
        int lowest = prices[0];
        // TODO 4: for문과 if문으로 최고가와 최저가를 찾으세요.
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > highest) {
                highest = prices[i];
            }
            if (prices[i] < lowest) {
                lowest = prices[i];
            }
        }
        int upDays = 0;
        // TODO 5: 전날보다 가격이 오른 날마다 upDays를 증가시키세요.
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                upDays++;
            }
        }
        // TODO 6: 합계, 평균, 최고가, 최저가, 상승한 날을 출력하세요.
        System.out.printf("합계: %,d원%n", total);
        System.out.printf("평균: %.1f원%n", average); // double → 소수점 1자리
        System.out.printf("최고가: %,d원%n", highest); // int → 쉼표 넣기
        System.out.printf("최저가: %,d원%n", lowest);
        System.out.printf("상승한 날: %d일%n", upDays); // 그냥 정수
        System.out.println("분석할 가격 개수: " + prices.length);
    }
}
