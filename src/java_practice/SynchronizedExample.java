package java_practice;

/**
 * 은행 계좌 예제 — synchronized 로 "경쟁 상태(race condition)" 막기.
 *
 * [상황]
 * 잔액 1000원인 계좌에서 두 스레드가 동시에 800원씩 출금을 시도한다.
 * 정상이라면 한 명만 성공해야 한다 (800 + 800 = 1600 > 1000).
 *
 * [synchronized 가 없다면?]
 *   스레드-1: 잔액(1000) >= 800 확인 → 통과
 *   스레드-2: 잔액(1000) >= 800 확인 → 통과   ← 아직 1이 차감하기 전!
 *   둘 다 출금 → 잔액이 -600 이 되는 사고 발생.
 * 이렇게 "확인 후 변경" 사이에 다른 스레드가 끼어들어 생기는 문제를 경쟁 상태라 한다.
 *
 * [해결]
 * 메서드에 synchronized 를 붙이면 한 번에 하나의 스레드만 들어갈 수 있다.
 */
class BankAccount {
    // private → 외부에서 balance를 직접 건드릴 수 없게 막는다(캡슐화).
    // 잔액 변경은 반드시 아래 synchronized 메서드를 통해서만 가능해진다.
    // 만약 public이면 synchronized를 걸어도 소용이 없다.
    private int balance = 1000; // 잔액

    /**
     * synchronized 메서드 = 이 객체(this)에 걸린 "자물쇠(lock)"를 잡아야만 실행된다.
     *
     * 동작 순서:
     *   1. 먼저 도착한 스레드가 자물쇠를 획득하고 메서드에 진입
     *   2. 다른 스레드는 문 앞에서 대기(BLOCKED 상태)
     *   3. 첫 스레드가 메서드를 빠져나가면 자물쇠가 풀리고 대기 스레드가 진입
     *
     * ※ 자물쇠는 "객체 하나당 하나". 서로 다른 BankAccount 객체끼리는 서로 방해하지 않는다.
     */
    public synchronized void withdraw(int amount) {
        // 잔액 확인 → 차감 까지가 하나의 덩어리로 처리되어야 안전하다.
        // 이렇게 한 번에 하나의 스레드만 실행해야 하는 구간을 임계 영역(critical section)이라 한다.
        if (balance >= amount) {
            // Thread.currentThread().getName() → 지금 이 코드를 실행 중인 스레드 이름
            // (아래 main에서 "스레드-1", "스레드-2" 라고 직접 지어준 이름)
            System.out.println(Thread.currentThread().getName() + " 출금 시도: " + amount);
            try {
                // 실제 은행 시스템의 처리 시간을 흉내 낸 것.
                // 이 3초 동안 다른 스레드가 끼어들 틈이 생기므로,
                // synchronized 를 지웠을 때 문제가 확실히 드러난다. ← 직접 지워보고 실행해볼 것!
                //
                // ※ 중요: sleep() 중에도 자물쇠는 풀리지 않는다.
                //   그래서 두 번째 스레드는 3초를 그대로 기다리게 된다.
                Thread.sleep(3000); // 출금 처리 지연(동시성 문제 시뮬레이션)
            } catch (InterruptedException e) {
                // sleep()은 대기 중 누군가 깨우면(interrupt) 예외를 던지므로
                // 반드시 try-catch 로 감싸야 컴파일된다.
                e.printStackTrace();
            }
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " 출금 완료. 남은 잔액: " + balance);
        } else {
            // 두 번째 스레드는 3초 뒤 이 자리에 도착한다.
            // 그때 잔액은 이미 200원으로 줄어 있으므로 정상적으로 출금 실패 처리된다.
            System.out.println(Thread.currentThread().getName() + " 출금 실패. 잔액 부족");
        }
    }
}

public class SynchronizedExample {
    public static void main(String[] args) {
        // 두 스레드가 "같은" 계좌 객체를 공유한다.
        // 공유 자원이 하나여야 동시성 문제가 의미를 갖는다.
        BankAccount account = new BankAccount();

        // new Thread(실행할 코드, 스레드 이름)
        // () -> account.withdraw(800) 는 람다식으로,
        // "이 스레드가 시작되면 실행할 내용"을 Runnable 로 전달하는 축약 문법이다.
        Thread t1 = new Thread(() -> account.withdraw(800), "스레드-1");
        Thread t2 = new Thread(() -> account.withdraw(800), "스레드-2");

        // start() → 새로운 스레드를 만들어 그 안에서 실행한다. (동시 실행 O)
        //
        // ※ 흔한 실수: t1.run() 으로 호출하면 새 스레드가 생기지 않고
        //   그냥 main 스레드에서 순서대로 실행되는 평범한 메서드 호출이 된다.
        t1.start();
        t2.start();

        // 실행 결과 예시 (순서는 실행할 때마다 달라질 수 있다):
        //   스레드-1 출금 시도: 800
        //   (3초 대기)
        //   스레드-1 출금 완료. 남은 잔액: 200
        //   스레드-2 출금 실패. 잔액 부족
    }
}
