package java_basic.oop.stock_polymorphism;

public class Main {
    static void printStockInfo(Stock stock) {
        stock.printInfo();
    }

    public static void main(String[] args) {
        Stock stock1 = new Stock("삼성전자", 80_000);
        PreferredStock stock2 = new PreferredStock("LG전자", 60_000, 5.0);

        stock1.printInfo();
        stock2.printInfo();
        stock2.printInfo("[오버로딩 호출] ");

        System.out.println("-----");
        printStockInfo(new Stock("SKALA", 80_000));
        printStockInfo(new PreferredStock("Netflix", 60_000, 10.0));
    }
}
