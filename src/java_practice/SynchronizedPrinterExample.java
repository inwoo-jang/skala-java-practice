package java_practice;

/**
 * 공용 프린터 예제 — synchronized 로 "순서 섞임" 막기.
 *
 * [상황]
 * 사무실에 프린터가 한 대뿐인데 철수와 영희가 동시에 인쇄를 건다.
 * 프린터를 잠그지 않으면 두 문서의 페이지가 뒤죽박죽 섞여 나온다.
 *   철수 - 회의 자료 1페이지
 *   영희 - 업무 보고서 1페이지   ← 섞임!
 *   철수 - 회의 자료 2페이지
 *
 * [BankAccount 예제와의 차이]
 * - 은행 예제: 데이터(잔액)가 망가지는 것을 막는 목적
 * - 프린터 예제: 여러 줄의 작업이 "중간에 끊기지 않게" 묶는 목적
 * 둘 다 "한 번에 한 스레드만"이라는 같은 원리로 해결된다.
 */
class Printer {

    /**
     * synchronized 를 붙였으므로 이 메서드는 한 번에 한 스레드만 실행할 수 있다.
     * 먼저 들어온 스레드가 3페이지를 다 끝낼 때까지 다른 스레드는 문 앞에서 기다린다.
     *
     * → 결과적으로 "철수 3장 전부 → 영희 3장 전부" 순서로 깔끔하게 출력된다.
     *   (누가 먼저 자물쇠를 잡을지는 OS가 정하므로 철수/영희 순서는 실행마다 다를 수 있다)
     *
     * ※ synchronized 를 지우고 실행해보면 두 문서가 섞여 나온다. 꼭 비교해볼 것!
     */
    public synchronized void print(String document) {
        // 현재 이 메서드를 실행 중인 스레드의 이름 ("철수" 또는 "영희").
        // 반복해서 쓰이므로 지역 변수에 담아두면 코드가 짧아진다.
        String threadName = Thread.currentThread().getName();

        System.out.println(threadName + " 출력 시작");

        // 문서 한 부를 3페이지씩 출력한다고 가정.
        for (int page = 1; page <= 3; page++) {
            System.out.println(
                    threadName + " - " + document
                            + " " + page + "페이지 출력");

            try {
                // 한 장 인쇄에 1초 걸린다고 가정.
                // 이 대기 시간이 있어야 스레드가 섞일 여지가 생겨 실습 효과가 있다.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 대기 중 다른 곳에서 interrupt()로 중단을 요청한 경우.
                //
                // sleep()이 InterruptedException을 던지면서 "중단 요청 표시"를 지워버리기 때문에
                // interrupt()를 다시 호출해 그 표시를 복원해준다. (권장되는 표준 처리 방식)
                // 그리고 인쇄를 계속하지 않고 return 으로 작업을 정리하며 빠져나간다.
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(threadName + " 출력 완료");
    }
}

public class SynchronizedPrinterExample {
    public static void main(String[] args) {
        // 프린터는 단 한 대 = 두 스레드가 공유하는 자원.
        // 자물쇠는 "객체 하나당 하나"이므로, 같은 객체를 공유해야 대기가 발생한다.
        // (프린터를 각자 하나씩 만들면 서로 기다리지 않고 그냥 동시에 출력된다)
        Printer sharedPrinter = new Printer();

        // () -> sharedPrinter.print("회의 자료") : 이 스레드가 시작되면 실행할 코드(람다식)
        // 두 번째 인자 "철수" : 스레드 이름. 안 정하면 Thread-0, Thread-1 같은 이름이 붙는다.
        Thread employee1 = new Thread(
                () -> sharedPrinter.print("회의 자료"),
                "철수");

        Thread employee2 = new Thread(
                () -> sharedPrinter.print("업무 보고서"),
                "영희");

        // start()를 호출한 순간 두 스레드가 동시에 달리기 시작한다.
        // 하지만 print()가 synchronized라서 실제로는 한 명씩 차례로 사용하게 된다.
        employee1.start();
        employee2.start();

        // 실행 결과 예시 (약 6초 소요):
        //   철수 출력 시작
        //   철수 - 회의 자료 1페이지 출력
        //   철수 - 회의 자료 2페이지 출력
        //   철수 - 회의 자료 3페이지 출력
        //   철수 출력 완료
        //   영희 출력 시작
        //   ... (영희 3페이지) ...
    }
}
