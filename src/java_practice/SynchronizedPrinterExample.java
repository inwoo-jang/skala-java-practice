package java_practice;

class Printer {
    public synchronized void print(String document) {
        String threadName = Thread.currentThread().getName();

        System.out.println(threadName + " 출력 시작");

        for (int page = 1; page <= 3; page++) {
            System.out.println(
                    threadName + " - " + document
                            + " " + page + "페이지 출력");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(threadName + " 출력 완료");
    }
}

public class SynchronizedPrinterExample {
    public static void main(String[] args) {
        Printer sharedPrinter = new Printer();

        Thread employee1 = new Thread(
                () -> sharedPrinter.print("회의 자료"),
                "철수");

        Thread employee2 = new Thread(
                () -> sharedPrinter.print("업무 보고서"),
                "영희");

        employee1.start();
        employee2.start();
    }
}
