package java_basic.oop.execution;

/** 부모 클래스: 자식 클래스보다 먼저 초기화됩니다. */
abstract class Device {
    // 클래스가 처음 초기화될 때 한 번만 실행됩니다.
    static {
        System.out.println("3. Device static 초기화");
    }

    // Device 객체 부분이 만들어질 때 생성자보다 먼저 실행됩니다.
    {
        System.out.println("5. Device 인스턴스 초기화 블록");
    }

    Device() {
        System.out.println("6. Device 생성자");
    }

    // 자식 클래스가 반드시 구현해야 하는 추상 메서드입니다.
    abstract void turnOn();
}

/** 자식 클래스: Device를 상속하고 turnOn()을 구체적으로 구현합니다. */
class LiveTv extends Device {
    static {
        System.out.println("4. LiveTv static 초기화");
    }

    // 부모 생성자가 끝난 뒤 자식 필드가 초기화됩니다.
    private String name = initializeName();

    {
        System.out.println("8. LiveTv 인스턴스 초기화 블록");
    }

    LiveTv() {
        System.out.println("9. LiveTv 생성자, name = " + name);
    }

    private String initializeName() {
        System.out.println("7. LiveTv 필드 초기화");
        return "SKALA Live TV";
    }

    @Override
    void turnOn() {
        System.out.println("13. LiveTv.turnOn(), name = " + name);
    }
}

/** TV 객체를 전달받아 업무 흐름을 실행하는 서비스 클래스입니다. */
class TvService {
    void run(Device device) {
        System.out.println("11. TvService.run() 시작");

        try {
            process(device);
            System.out.println("14. process()에서 run()으로 복귀");
        } finally {
            // try가 정상 종료되거나 예외가 발생해도 일반적으로 실행됩니다.
            System.out.println("15. finally 실행");
        }

        System.out.println("16. TvService.run() 종료");
    }

    private void process(Device device) {
        System.out.println("12. TvService.process() 실행");

        // 변수 타입은 Device지만 실제 객체가 LiveTv이므로
        // 오버라이딩된 LiveTv.turnOn()이 실행됩니다.
        device.turnOn();
    }
}

/**
 * Java의 복합 실행 순서를 관찰하는 시작 클래스입니다.
 *
 * 디버그 연습:
 * 1. main() 첫 줄에 브레이크포인트를 설정합니다.
 * 2. Step Into로 new LiveTv(), run(), process(), turnOn()에 들어갑니다.
 * 3. Call Stack에서 main → run → process → turnOn 순서를 확인합니다.
 */
public class ComplexFlow {
    static {
        System.out.println("1. ComplexFlow static 초기화");
    }

    public static void main(String[] args) {
        System.out.println("2. main() 시작");

        // 부모 타입 변수에 자식 객체를 담는 업캐스팅입니다.
        Device device = new LiveTv();

        System.out.println("10. LiveTv 객체 생성 완료");

        TvService service = new TvService();
        service.run(device);

        System.out.println("17. main() 종료");
    }
}
