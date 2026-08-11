package java_basic.oop.constructor;

class Parent {
    Parent() {
        System.out.println("1. Parent 생성자 시작");
    }

    void introduce() {
        System.out.println("   나는 Parent");
    }
}

class Child extends Parent {
    String name = "홍길동";

    Child() {
        System.out.println("3. Child 생성자 실행, name = " + name);
    }

    @Override
    void introduce() {
        System.out.println("2. 나는 Child, 이름은 " + name + " 입니다");
    }
}

public class CtorTrap {
    public static void main(String[] args) {
        Child child = new Child();
        child.introduce();
    }
}
