package java_basic.if_example;

import java.util.Scanner;

/**
 * 입력한 점수를 if-else if 조건문으로 검사하여 학점을 계산하는 예제입니다.
 */
public class GradeCalculator {
    public static void main(String[] args) {
        // System.in은 키보드 입력을 의미하며, Scanner가 입력값을 읽어 줍니다.
        Scanner scanner = new Scanner(System.in);

        System.out.print("점수를 입력하세요 (0~100): ");

        // nextInt()는 사용자가 입력한 값을 int 타입으로 읽습니다.
        int score = scanner.nextInt();

        // 조건문에서 결정한 학점을 저장할 변수입니다.
        // 아래 모든 분기에서 값이 할당된 후 출력에 사용됩니다.
        String grade;

        // if-else if는 위에서부터 조건을 확인하고,
        // 처음으로 true가 된 블록 하나만 실행한 뒤 전체 조건문을 빠져나갑니다.
        if (score >= 90) {
            // 90 이상인 점수는 여기에서 이미 처리되므로 아래 조건은 검사하지 않습니다.
            grade = "A";
        } else if (score >= 80) {
            // 앞의 score >= 90이 false라는 사실이 포함되므로 실제 범위는 80~89입니다.
            grade = "B";
        } else if (score >= 70) {
            // 실제 범위는 70~79입니다.
            grade = "C";
        } else if (score >= 60) {
            // 실제 범위는 60~69입니다.
            grade = "D";
        } else {
            // 앞의 모든 조건이 false인 60 미만의 점수가 이 블록으로 들어옵니다.
            grade = "F";
        }

        System.out.println("당신의 학점은: " + grade);

        // 사용이 끝난 입력 자원을 닫습니다.
        scanner.close();
    }
}
