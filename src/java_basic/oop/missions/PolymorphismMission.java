package java_basic.oop.missions;

/** 미션 4: 같은 메서드 호출이 실제 객체에 따라 다르게 동작하게 하기 */
public class PolymorphismMission {
    public static void main(String[] args) {
        // TODO 1: Robot 타입 배열에 CleaningRobot과 CookingRobot을 하나씩 담으세요.
        Robot[] robots = {
                null,
                null
        };

        // TODO 2: for-each로 모든 robot.work()의 결과를 result에 이어 붙이세요.
        String result = "";

        // TODO 3: 성공 후 세 TODO 주석을 COMPLETED로 바꾸고, 왜 다형성인지 한 문장으로 적어보세요.
        check("청소요리".equals(result),
                "Robot[]이라는 하나의 타입에 두 구현체를 담고 work()를 호출하세요.");
    }

    private static void check(boolean success, String hint) {
        System.out.println(success ? "[SUCCESS]" : "[KEEP GOING] " + hint);
    }
}

interface Robot {
    String work();
}

class CleaningRobot implements Robot {
    @Override
    public String work() {
        return "청소";
    }
}

class CookingRobot implements Robot {
    @Override
    public String work() {
        return "요리";
    }
}
