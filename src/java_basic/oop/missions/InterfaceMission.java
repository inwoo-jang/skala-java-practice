package java_basic.oop.missions;

/** 미션 3: is-a 상태 공유가 아닌, can-do 능력을 인터페이스로 표현하기 */
public class InterfaceMission {
    public static void main(String[] args) {
        // TODO 1: MissionNotifier를 class에서 interface로 바꾸세요. 성공 후 TODO를 COMPLETED로 변경하세요.
        check(MissionNotifier.class.isInterface(), "MissionNotifier를 interface로 선언하세요.");

        // TODO 2: EmailNotifier가 MissionNotifier를 implements하게 바꾸세요.
        boolean implemented = MissionNotifier.class.isAssignableFrom(EmailNotifier.class);
        check(implemented, "EmailNotifier에 implements MissionNotifier를 추가하세요.");

        // TODO 3: send()를 구현해 \"EMAIL: 합격입니다\"를 반환하세요.
        if (implemented) {
            MissionNotifier notifier = (MissionNotifier) (Object) new EmailNotifier();
            check("EMAIL: \uD569\uACA9\uC785\uB2C8\uB2E4".equals(notifier.send("합격입니다")),
                    "EmailNotifier.send()를 구현하세요.");
        }
    }

    private static void check(boolean success, String hint) {
        System.out.println(success ? "[SUCCESS]" : "[KEEP GOING] " + hint);
    }
}

// class를 interface로 바꾸고, send()를 구현 없는 메서드로 바꾸세요.
class MissionNotifier {
    public String send(String message) {
        return "";
    }
}

// MissionNotifier를 implements하세요.
class EmailNotifier {
    // send()를 구현하세요.
}

