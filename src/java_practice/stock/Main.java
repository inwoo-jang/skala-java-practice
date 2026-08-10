package java_practice.stock;

/**
 * Stock 객체의 생성, 상태 변경, 메서드 호출을 실행하는 예제입니다.
 */
public class Main {
    // 전달받은 Stock 객체의 일반 메서드를 대신 호출하는 static 메서드입니다.
    static void printStockInfo(Stock stock) {
        stock.printInfo();
    }

    public static void main(String[] args) {
        // 같은 Stock 클래스로 서로 상태가 다른 객체 두 개를 생성합니다.
        Stock scalaEdu = new Stock("스칼라 에듀", 15000);
        Stock scalaAI = new Stock("스칼라 AI", 17500);

        // 첫 번째 객체의 가격만 변경하고 객체에서 직접 메서드를 호출합니다.
        scalaEdu.updatePrice(15800);
        scalaEdu.printInfo();

        // 두 번째 객체의 가격을 변경한 뒤 static 메서드에 전달합니다.
        scalaAI.updatePrice(18000);
        printStockInfo(scalaAI);
    }
}
