package java_basic.oop.missions;

/** 미션 1: 중복 코드를 상속과 오버라이딩으로 바꾸기 */
public class InheritanceMission {
    public static void main(String[] args) {
        // TODO 1: MissionDog 선언에 extends MissionAnimal을 추가하세요. 성공 후 TODO를 COMPLETED로 변경하세요.
        boolean inherited = MissionAnimal.class.isAssignableFrom(MissionDog.class);
        check(inherited, "MissionDog은 MissionAnimal을 상속해야 합니다.");

        // TODO 2: MissionDog.sound()가 \"멍멍\"을 반환하도록 오버라이딩하세요. @Override도 붙이세요.
        if (inherited) {
            MissionAnimal animal = (MissionAnimal) (Object) new MissionDog();
            check("\uBA4D\uBA4D".equals(animal.sound()), "부모 타입으로 호출해도 멍멍이 나와야 합니다.");
        }
    }

    private static void check(boolean success, String hint) {
        System.out.println(success ? "[SUCCESS]" : "[KEEP GOING] " + hint);
    }
}

class MissionAnimal {
    public String sound() {
        return "동물 소리";
    }
}

// extends MissionAnimal을 추가하세요.
class MissionDog {
    // sound() 오버라이딩을 추가하세요.
}

