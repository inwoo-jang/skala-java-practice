package java_practice;

public class StringBuilderExample {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();

        // 1. append(): 문자열 뒤에 내용 추가
        builder.append("상품명: ");
        builder.append("키보드");
        builder.append("\n");

        builder.append("가격: ");
        builder.append(85000);
        builder.append("원\n");

        builder.append("판매 여부: ");
        builder.append(true);

        printStep("1. append 후", builder);

        // 2. insert(): 원하는 위치에 내용 삽입
        builder.insert(0, "[상품 정보]\n");

        printStep("2. insert 후", builder);

        // 3. replace(): 특정 범위의 내용 교체
        int start = builder.indexOf("키보드");
        int end = start + "키보드".length();

        builder.replace(start, end, "기계식 키보드");

        printStep("3. replace 후", builder);

        // 4. delete(): 특정 범위 삭제
        start = builder.indexOf("판매 여부:");
        builder.delete(start, builder.length());

        printStep("4. delete 후", builder);

        // 5. 메서드 연결하기
        builder.append("수량: ")
               .append(2)
               .append("개\n")
               .append("총 가격: ")
               .append(85000 * 2)
               .append("원");

        printStep("5. 메서드 연결 후", builder);

        // 6. 최종적으로 String으로 변환
        String result = builder.toString();

        System.out.println("===== 최종 String =====");
        System.out.println(result);
    }

    private static void printStep(
            String step,
            StringBuilder builder
    ) {
        System.out.println("===== " + step + " =====");
        System.out.println(builder);
        System.out.println();
    }
}
