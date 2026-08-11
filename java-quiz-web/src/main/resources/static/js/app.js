/* =========================================================================
 * Java Essential x Data Lab - 프런트엔드
 *
 * 이 파일을 읽는 순서:
 *   1) BANK        : 트랙과 문제 데이터 (화면에 나오는 모든 내용)
 *   2) render()    : 데이터를 HTML로 그리는 부분
 *   3) grade()     : 채점 규칙
 *   4) api()       : 서버(/api)와 통신하는 부분
 *
 * 문제 유형은 세 가지입니다.
 *   mcq   : 객관식
 *   blank : 코드 속 빈칸을 보기에서 고르기
 *   write : 코드 직접 작성 (힌트 1단계 -> 2단계)
 * ========================================================================= */

/* -------------------------------------------------------------------------
 * 1) 문제 은행
 * ---------------------------------------------------------------------- */

const BANK = [
  {
    id: 'types',
    name: '변수와 자료형',
    chapter: '교재 1권 · 5장 자바 변수와 자료형 / 11장 자바 필수 클래스',
    day: '1일차',
    desc: '숫자를 어떻게 담느냐가 집계 결과를 바꿉니다. 데이터 엔지니어링에서 가장 조용하게 틀리는 영역입니다.',
    questions: [
      {
        key: 'acc-double',
        type: 'mcq',
        title: '0.1 + 0.2는 0.3이 아니다',
        ask: '이 코드는 무엇을 출력할까요?',
        code: 'double total = 0.1 + 0.2;\nSystem.out.println(total);',
        options: [{ text: '0.3' }, { text: '0.30000000000000004' }, { text: '0.300000' }, { text: '0.0' }],
        correct: 1,
        explain: 'double은 2진수로 소수를 표현합니다. 0.1과 0.2는 2진수로 정확히 떨어지지 않아 미세한 오차가 남고, 더하면 그 오차가 드러납니다.',
        why: '금액을 double로 집계하면 건수가 쌓일수록 오차가 커집니다. 돈은 BigDecimal이나 정수(원 단위)로 다루세요.'
      },
      {
        key: 'read-null',
        type: 'mcq',
        title: '래퍼 클래스와 언박싱',
        ask: '없는 키 "banana"를 꺼내 int에 담으면 어떻게 될까요?',
        code: 'Map<String, Integer> totals = new HashMap<>();\ntotals.put("apple", 7);\n\nint banana = totals.get("banana");\nSystem.out.println(banana);',
        options: [{ text: '0이 출력된다' }, { text: 'null이 출력된다' }, { text: 'NullPointerException이 발생한다' }, { text: '컴파일 오류가 난다' }],
        correct: 2,
        explain: 'Map이 담고 있는 것은 int가 아니라 래퍼 클래스 Integer입니다. 없는 키는 null을 돌려주고, 그 null을 int로 바꾸는 순간(언박싱) NullPointerException이 납니다. getOrDefault(key, 0)을 쓰면 안전합니다.',
        why: '교재 5장의 기본형과 래퍼 클래스 구분이 실무에서 처음 아프게 다가오는 지점입니다. 실데이터에는 빈 키가 반드시 섞여 있습니다.'
      },
      {
        key: 'acc-bigdecimal',
        type: 'blank',
        title: 'BigDecimal을 만드는 올바른 방법',
        ask: '오차 없이 정확히 0.1을 담으려면 빈칸에 무엇을 넣어야 할까요?',
        code: 'BigDecimal price = new BigDecimal(__BLANK__);\nSystem.out.println(price);',
        options: [{ text: '0.1', mono: true }, { text: '"0.1"', mono: true }, { text: '0.1d', mono: true }, { text: '(double) 0.1', mono: true }],
        correct: 1,
        explain: 'double 리터럴을 넘기면 이미 오차가 섞인 값이 그대로 들어옵니다. 실제로 new BigDecimal(0.1)은 0.1000000000000000055511151231257827...를 담습니다. 문자열로 넘겨야 사람이 적은 그대로 저장됩니다.',
        why: 'BigDecimal을 쓰면서도 double로 만들어 넣어 오차가 남는 실수가 매우 흔합니다.'
      },
      {
        key: 'acc-parse',
        type: 'mcq',
        title: '깨진 레코드를 만났을 때',
        ask: '수량 자리에 숫자가 아닌 값이 들어왔습니다. 어떻게 될까요?',
        code: 'String[] parts = "apple,three,SUCCESS".split(",");\nint quantity = Integer.parseInt(parts[1]);',
        options: [{ text: '0이 들어간다' }, { text: 'null이 들어간다' }, { text: 'NumberFormatException이 발생한다' }, { text: '컴파일 오류가 난다' }],
        correct: 2,
        explain: 'Integer.parseInt는 정수 모양의 문자열만 변환합니다. "three"는 정수가 아니므로 실행 중에 NumberFormatException이 납니다.',
        why: '수백만 줄 중 한 줄만 깨져도 배치 전체가 멈춥니다. 깨진 레코드를 건너뛸지, 따로 모을지, 즉시 중단할지 미리 정해야 합니다.'
      }
    ]
  },

  {
    id: 'object',
    name: '클래스와 객체',
    chapter: '교재 1권 · 7장 클래스와 객체 / 12장 객체지향 프로그래밍',
    day: '1일차',
    desc: '변수가 값을 담는지 주소를 담는지, 그리고 "같다"는 것이 무슨 뜻인지. 여기서 헷갈리면 그 위의 모든 것이 흔들립니다.',
    questions: [
      {
        key: 'read-reference',
        type: 'mcq',
        title: '두 변수가 같은 객체를 가리킬 때',
        ask: '마지막 줄은 무엇을 출력할까요?',
        code: 'List<String> a = new ArrayList<>(List.of("x"));\nList<String> b = a;\n\nb.add("y");\nSystem.out.println(a.size());',
        options: [{ text: '1' }, { text: '2' }, { text: '컴파일 오류' }, { text: '실행 중 예외 발생' }],
        correct: 1,
        explain: 'b = a는 객체를 복사하지 않습니다. 두 변수가 같은 리스트 하나를 가리킬 뿐입니다. 그래서 b에 넣으면 a에서도 보입니다.',
        why: '메서드에 리스트를 넘겨 수정하면 호출한 쪽 데이터까지 바뀝니다. 원본을 지키려면 복사본을 만들어야 합니다.'
      },
      {
        key: 'obj-static',
        type: 'mcq',
        title: 'static은 누구의 것인가',
        ask: '마지막 줄의 출력은?',
        code: 'class Counter {\n    static int total = 0;   // 클래스 전체가 공유\n    int mine = 0;           // 객체마다 따로\n\n    void add() { total++; mine++; }\n}\n\nCounter a = new Counter();\nCounter b = new Counter();\na.add();\nb.add();\nb.add();\n\nSystem.out.println(Counter.total + ", " + b.mine);',
        options: [{ text: '3, 2' }, { text: '2, 2' }, { text: '3, 3' }, { text: '1, 2' }],
        correct: 0,
        explain: 'total은 객체가 몇 개든 하나뿐이라 세 번의 호출이 모두 쌓여 3입니다. mine은 객체마다 따로 있으므로 b의 것은 2입니다.',
        why: '배치 작업에서 static 카운터를 여러 스레드가 동시에 건드리면 값이 깨집니다. 교재 4장 동시성 실습과 이어지는 이야기입니다.'
      },
      {
        key: 'read-record',
        type: 'blank',
        title: 'record의 값 꺼내기',
        ask: 'apple이 출력되도록 빈칸을 채우세요.',
        code: 'record Order(String product, int quantity) {}\n\nOrder order = new Order("apple", 3);\nSystem.out.println(order.__BLANK__());   // apple',
        options: [{ text: 'product', mono: true }, { text: 'getProduct', mono: true }, { text: 'getproduct', mono: true }, { text: 'value', mono: true }],
        correct: 0,
        explain: 'record는 필드 이름과 똑같은 이름의 메서드를 자동으로 만들어 줍니다. get 접두사가 붙지 않습니다.',
        why: 'ETL에서 한 줄짜리 데이터 구조를 만들 때 record가 가장 편합니다. equals/hashCode/toString도 자동으로 만들어집니다.'
      },
      {
        key: 'coll-equals',
        type: 'mcq',
        title: 'equals와 hashCode를 재정의하지 않으면',
        ask: '내용이 같은 객체를 두 번 넣었습니다. size()는?',
        code: 'class StockKey {\n    String market;\n    String code;\n    // equals()와 hashCode()를 재정의하지 않았다\n}\n\nSet<StockKey> keys = new HashSet<>();\nkeys.add(new StockKey("KOSPI", "005930"));\nkeys.add(new StockKey("KOSPI", "005930"));\n\nSystem.out.println(keys.size());',
        options: [{ text: '1' }, { text: '2' }, { text: '0' }, { text: '컴파일 오류' }],
        correct: 1,
        explain: 'Object가 물려준 기본 equals는 "내용이 같은가"가 아니라 "같은 객체인가(주소)"를 봅니다. new를 두 번 했으니 서로 다른 객체라 둘 다 들어갑니다.',
        why: '중복 제거(dedup)와 조인 키가 조용히 깨지는 원인입니다. class 대신 record를 쓰면 둘 다 자동으로 만들어져 이 사고가 사라집니다.'
      }
    ]
  },

  {
    id: 'oop',
    name: '상속 · 인터페이스 · 다형성',
    chapter: '교재 1권 · 14~17장',
    day: '2일차',
    desc: 'Spring이 객체를 갈아 끼울 수 있는 이유가 전부 이 세 장에 들어 있습니다.',
    questions: [
      {
        key: 'oop-polymorphism',
        type: 'mcq',
        title: '변수 타입과 실제 객체',
        ask: '무엇이 출력될까요?',
        code: 'class Stock {\n    void printInfo() { System.out.println("[일반주]"); }\n}\n\nclass PreferredStock extends Stock {\n    @Override\n    void printInfo() { System.out.println("[우선주]"); }\n}\n\nStock stock = new PreferredStock();\nstock.printInfo();',
        options: [{ text: '[일반주]' }, { text: '[우선주]' }, { text: '둘 다 출력된다' }, { text: '컴파일 오류' }],
        correct: 1,
        explain: '어떤 메서드가 실행될지는 변수의 타입이 아니라 실제로 만들어진 객체가 정합니다. 이것이 다형성입니다.',
        why: '변수는 Repository 인터페이스인데 실제로는 JPA 구현체가 동작하는 것 — Spring이 매일 하는 일이 바로 이겁니다.'
      },
      {
        key: 'oop-abstract',
        type: 'mcq',
        title: '추상 클래스로 객체를 만들면',
        ask: '마지막 줄은 어떻게 될까요?',
        code: 'abstract class Asset {\n    abstract void printInfo();   // 본문이 없다\n}\n\nAsset asset = new Asset();',
        options: [
          { text: '정상 실행된다' },
          { text: '컴파일 오류 — 추상 클래스는 직접 객체로 만들 수 없다' },
          { text: '실행 중 예외가 난다' },
          { text: 'printInfo()가 빈 채로 실행된다' }
        ],
        correct: 1,
        explain: 'printInfo()의 본문이 없어서 만들어도 할 수 있는 일이 없습니다. 추상 클래스는 상속받아 빈 부분을 채운 자식만 객체가 될 수 있습니다.',
        why: '"공통 부분은 부모에, 달라지는 부분은 자식에" — 여러 데이터 소스를 다룰 때 반복 코드를 줄이는 기본 틀입니다.'
      },
      {
        key: 'read-interface',
        type: 'mcq',
        title: '왜 인터페이스로 받는가',
        ask: 'run()이 매개변수를 구체 클래스가 아니라 인터페이스 타입으로 받는 이유는?',
        code: 'interface OrderReader { List<Order> read(); }\n\nclass CsvOrderReader implements OrderReader { /* ... */ }\nclass DbOrderReader  implements OrderReader { /* ... */ }\n\nstatic void run(OrderReader reader) {\n    List<Order> orders = reader.read();\n}',
        options: [
          { text: '인터페이스가 클래스보다 메모리를 적게 쓴다' },
          { text: 'CSV 파일을 읽는 속도가 빨라진다' },
          { text: '읽는 방식이 바뀌어도 run()의 코드는 그대로 둘 수 있다' },
          { text: 'OrderReader 안에 실제 읽기 코드가 들어 있다' }
        ],
        correct: 2,
        explain: 'run()은 "read()를 부를 수 있는 무언가"만 알면 됩니다. CSV에서 DB로, DB에서 Kafka로 소스가 바뀌어도 run()은 손대지 않습니다.',
        why: '교재 26장 SOLID의 DIP가 이 이야기이고, 2권의 의존성 주입이 이 구조 위에 서 있습니다.'
      }
    ]
  },

  {
    id: 'collection',
    name: 'Collection Type',
    chapter: '교재 1권 · 18장 Collection Type',
    day: '2일차',
    desc: '데이터를 어디에 담을 것인가. 집계 코드의 대부분은 이 장의 선택 하나로 결정됩니다.',
    questions: [
      {
        key: 'coll-type',
        type: 'mcq',
        title: '어떤 자료구조에 담을까',
        ask: '상품별 총수량을 담기에 가장 알맞은 타입은?',
        code: 'apple  -> 7\nbanana -> 3\ncherry -> 5',
        options: [{ text: 'List<String>', mono: true }, { text: 'Set<Integer>', mono: true }, { text: 'Map<String, Integer>', mono: true }, { text: 'String[]', mono: true }],
        correct: 2,
        explain: '"상품명"이라는 키로 "수량"이라는 값을 찾는 관계입니다. 키-값 관계는 Map입니다.',
        why: 'groupingBy가 돌려주는 것도 Map입니다. 집계 결과의 기본 모양이라고 생각하세요.'
      },
      {
        key: 'coll-accumulate',
        type: 'blank',
        title: '누적 합계 만들기',
        ask: '이미 담긴 값에 이번 수량을 더하려면 빈칸에 무엇이 들어가야 할까요?',
        code: 'Map<String, Integer> totals = new HashMap<>();\n\nfor (Order order : orders) {\n    totals.put(order.product(),\n        totals.__BLANK__(order.product(), 0) + order.quantity());\n}',
        options: [{ text: 'get', mono: true }, { text: 'getOrDefault', mono: true }, { text: 'containsKey', mono: true }, { text: 'putIfAbsent', mono: true }],
        correct: 1,
        explain: 'get()은 첫 상품에서 null을 돌려줘 NullPointerException이 납니다. getOrDefault(key, 0)은 없으면 0을 주므로 if 분기 없이 누적할 수 있습니다.',
        why: '스트림을 쓰지 않는 집계 코드에서 가장 자주 등장하는 한 줄입니다.'
      },
      {
        key: 'coll-cme',
        type: 'mcq',
        title: '순회하면서 지우면',
        ask: 'for-each로 돌면서 리스트에서 요소를 지우면 어떻게 될까요?',
        code: 'List<String> orders = new ArrayList<>(\n        List.of("apple,3", "cherry,1", "banana,5", "durian,2"));\n\nfor (String order : orders) {\n    if (order.endsWith(",1")) {\n        orders.remove(order);\n    }\n}\nSystem.out.println(orders);',
        options: [
          { text: '정상적으로 제거되고 3건이 출력된다' },
          { text: 'ConcurrentModificationException이 발생한다' },
          { text: 'UnsupportedOperationException이 발생한다' },
          { text: '컴파일 오류가 난다' }
        ],
        correct: 1,
        explain: 'for-each는 내부적으로 Iterator를 씁니다. 그 Iterator는 리스트가 몇 번 수정됐는지 기억해두고 next()마다 확인하는데, list.remove()는 Iterator 몰래 리스트를 고치므로 다음 next()에서 걸립니다. Iterator를 직접 만들어 it.remove()를 쓰거나 removeIf()를 쓰세요.',
        why: '이 예외는 항상 나지 않습니다. 끝에서 두 번째 요소를 지우면 조용히 통과해 버립니다. "가끔 되는" 버그라 더 위험합니다.'
      },
      {
        key: 'coll-order',
        type: 'mcq',
        title: '순회 순서를 믿어도 되는가',
        ask: '순회 순서를 예측할 수 없는 것은?',
        code: 'for (var entry : someMap.entrySet()) { ... }',
        options: [{ text: 'ArrayList', mono: true }, { text: 'LinkedHashMap', mono: true }, { text: 'TreeMap', mono: true }, { text: 'HashMap', mono: true }],
        correct: 3,
        explain: 'HashMap은 키의 해시값으로 저장 위치를 정하므로 넣은 순서와 무관합니다. LinkedHashMap은 넣은 순서, TreeMap은 키 정렬 순서를 지킵니다.',
        why: '출력 순서를 비교하는 테스트가 "내 컴퓨터에선 통과"하는 대표적 원인입니다. 순서가 필요하면 자료구조로 보장하세요.'
      }
    ]
  },

  {
    id: 'generic',
    name: 'Generic Type',
    chapter: '교재 1권 · 19장 Generic Type',
    day: '2일차',
    desc: '제네릭을 직접 설계할 일은 드뭅니다. 하지만 라이브러리를 쓰려면 읽을 줄 알아야 합니다.',
    questions: [
      {
        key: 'gen-read',
        type: 'mcq',
        title: 'T 자리에 무엇이 들어갔나',
        ask: 'box.getItem()의 타입은 무엇일까요?',
        code: 'class Box<T> {\n    private T item;\n    public T getItem() { return item; }\n}\n\nBox<String> box = new Box<>("hello");\nvar item = box.getItem();',
        options: [{ text: 'Object', mono: true }, { text: 'String', mono: true }, { text: 'T', mono: true }, { text: 'Box<String>', mono: true }],
        correct: 1,
        explain: 'Box<String>이라고 적는 순간 그 객체 안의 T는 전부 String으로 확정됩니다. 그래서 꺼낼 때 캐스팅이 필요 없고, 바로 .length()를 부를 수 있습니다.',
        why: '제네릭이 없던 시절에는 Object로 담고 꺼낼 때마다 캐스팅했습니다. 잘못 캐스팅하면 실행 중에 터졌고요. 제네릭은 그 실수를 컴파일 시점으로 옮겨 줍니다.'
      },
      {
        key: 'gen-wildcard',
        type: 'blank',
        title: '와일드카드로 받는 범위 넓히기',
        ask: 'List<Integer>와 List<Double>을 모두 받으려면 빈칸에 무엇이 들어가야 할까요?',
        code: 'static double sum(List<__BLANK__> list) {\n    double total = 0;\n    for (Number n : list) total += n.doubleValue();\n    return total;\n}\n\nsum(List.of(1, 2, 3));      // List<Integer>\nsum(List.of(1.5, 2.5));     // List<Double>',
        options: [{ text: 'Number', mono: true }, { text: '? extends Number', mono: true }, { text: '? super Number', mono: true }, { text: 'T extends Number', mono: true }],
        correct: 1,
        explain: 'Integer는 Number의 자식이지만 List<Integer>는 List<Number>의 자식이 아닙니다. 그래서 List<Number>로 받으면 둘 다 거부됩니다. "Number이거나 그 자식"을 뜻하는 ? extends Number가 필요합니다.',
        why: '외우는 법 — PECS. 꺼내 읽기만 하면(Producer) extends, 집어넣기만 하면(Consumer) super입니다.'
      },
      {
        key: 'lab-generic',
        type: 'write',
        title: '제네릭 처리 결과 만들기',
        ask: 'totals와 처리 건수를 ProcessResult에 담으세요.',
        starter: 'record ProcessResult<T>(boolean success, int count, T data) {}\n\nProcessResult<Map<String, Integer>> result =\n        // 여기에 작성;',
        hints: [
          'ProcessResult는 record입니다. 새 객체를 만들어 대입하면 됩니다. 인자는 성공 여부, 처리 건수, 결과 데이터 순서입니다.',
          'new ProcessResult<>(true, orders.size(), totals) 입니다.'
        ],
        tests: [
          [/new\s+ProcessResult\s*<\s*>\s*\(/, 'ProcessResult 객체를 생성하세요. 다이아몬드(<>)를 쓰면 타입이 추론됩니다.'],
          [/true/, '성공 여부는 true입니다.'],
          [/orders\.size\s*\(\s*\)/, '처리 건수는 orders.size()입니다.'],
          [/totals/, '결과 데이터로 totals를 전달하세요.']
        ],
        why: '"결과 + 메타데이터"를 한 덩어리로 돌려주는 형태입니다. 배치 작업의 반환 타입으로 흔히 쓰입니다.'
      }
    ]
  },

  {
    id: 'stream',
    name: 'Lambda · Stream API',
    chapter: '교재 1권 · 20장 Lambda / 21장 Stream API',
    day: '2일차',
    desc: '데이터 엔지니어가 매일 쓰는 영역입니다. 이 트랙만큼은 읽기가 아니라 직접 쓸 수 있어야 합니다.',
    desc2: 'Extract → Transform → Load 순서로 주문 로그를 정제하고 집계합니다.',
    questions: [
      {
        key: 'stream-lazy',
        type: 'mcq',
        title: '아무 일도 일어나지 않는 스트림',
        ask: '이 코드의 출력은?',
        code: 'List<String> logs = List.of("apple,3,SUCCESS", "banana,2,FAILED");\n\nlogs.stream()\n    .filter(log -> {\n        System.out.println("검사: " + log);\n        return log.endsWith("SUCCESS");\n    });\n\nSystem.out.println("끝");',
        options: [
          { text: '"검사:" 2줄이 찍히고 "끝"' },
          { text: '"끝" 한 줄만 찍힌다' },
          { text: '"검사:" 1줄이 찍히고 "끝"' },
          { text: '컴파일 오류' }
        ],
        correct: 1,
        explain: 'filter는 중간 연산입니다. toList(), collect(), forEach() 같은 최종 연산이 붙어야 비로소 실행됩니다. 최종 연산이 없으면 람다는 한 번도 실행되지 않습니다.',
        why: '이것을 지연 연산(lazy evaluation)이라고 합니다. Spark를 비롯한 대부분의 데이터 처리 엔진이 같은 방식으로 동작합니다.'
      },
      {
        key: 'lab-filter',
        type: 'write',
        title: '01 · 성공 로그만 필터링',
        ask: 'filter()와 람다로 SUCCESS로 끝나는 로그만 남기세요.',
        starter: 'List<String> successLogs = logs.stream()\n        // 여기에 작성\n        .toList();',
        hints: [
          '조건에 맞는 것만 남기는 중간 연산이 있습니다. 이름부터 떠올려 보세요. 조건은 문자열이 "SUCCESS"로 끝나는지입니다.',
          '.filter(log -> log.endsWith("SUCCESS")) 형태입니다.'
        ],
        tests: [
          [/\.filter\s*\(/, 'filter()가 필요합니다.'],
          [/->/, '람다식(->)을 작성하세요.'],
          [/endsWith\s*\(\s*"SUCCESS"\s*\)/, 'SUCCESS로 끝나는지 검사하세요.']
        ],
        why: 'Extract 단계입니다. 쓸 데이터와 버릴 데이터를 가르는 첫 관문입니다.'
      },
      {
        key: 'lab-map',
        type: 'write',
        title: '02 · 로그를 Order로 변환',
        ask: 'map()으로 String 로그 한 줄을 Order 객체로 바꾸세요.',
        starter: 'List<Order> orders = successLogs.stream()\n        .map(log -> {\n            String[] parts = log.split(",");\n            // Order를 반환하세요\n        })\n        .toList();',
        hints: [
          'map()은 값 하나를 다른 값 하나로 바꿉니다. 여기서는 String을 Order로 바꿉니다. 수량은 문자열이므로 int로 바꿔야 합니다.',
          '중괄호 람다이므로 return이 필요합니다. return new Order(parts[0], Integer.parseInt(parts[1]));'
        ],
        tests: [
          [/\.map\s*\(/, 'map()이 필요합니다.'],
          [/return\s+new\s+Order\s*\(/, '새 Order 객체를 반환하세요.'],
          [/parts\s*\[\s*0\s*\]/, '상품명은 parts[0]입니다.'],
          [/Integer\.parseInt\s*\(\s*parts\s*\[\s*1\s*\]\s*\)/, '수량을 int로 변환하세요.']
        ],
        why: 'Transform 단계입니다. 문자열 한 줄을 타입이 있는 객체로 바꾸는 순간부터 컴파일러가 실수를 잡아주기 시작합니다.'
      },
      {
        key: 'lab-group',
        type: 'write',
        title: '03 · 상품별 판매량 집계',
        ask: 'groupingBy()와 summingInt()를 조합해 상품별 수량 합계를 구하세요.',
        starter: 'Map<String, Integer> totals = orders.stream()\n        .collect(Collectors.groupingBy(\n                // 그룹 기준,\n                // 수량 합계\n        ));',
        hints: [
          'groupingBy는 인자를 두 개 받을 수 있습니다. 첫째는 무엇으로 묶을지, 둘째는 묶인 것들을 어떻게 합칠지입니다.',
          'Order::product 와 Collectors.summingInt(Order::quantity) 를 넣으세요.'
        ],
        tests: [
          [/Collectors\.groupingBy\s*\(/, 'groupingBy()가 필요합니다.'],
          [/Order::product/, '상품명을 그룹 기준으로 지정하세요.'],
          [/Collectors\.summingInt\s*\(/, 'summingInt()로 수량을 합치세요.'],
          [/Order::quantity/, 'quantity를 합산하세요.']
        ],
        why: 'SQL의 GROUP BY와 같은 일입니다. 데이터 집계의 기본형이라 통째로 외워둘 가치가 있습니다.'
      },
      {
        key: 'stream-method-ref',
        type: 'blank',
        title: '람다를 더 짧게 — 메서드 참조',
        ask: 'log -> log.trim() 과 똑같은 뜻이 되도록 빈칸을 채우세요.',
        code: 'List<String> trimmed = logs.stream()\n        .map(__BLANK__)\n        .toList();',
        options: [
          { text: 'String::trim', mono: true },
          { text: 'String.trim()', mono: true },
          { text: 'log::trim', mono: true },
          { text: 'trim(String)', mono: true }
        ],
        correct: 0,
        explain: '"각 원소에 대해 그 원소의 trim()을 부른다"는 뜻입니다. 람다가 인자를 그대로 한 메서드에 넘기기만 할 때는 이렇게 줄일 수 있습니다.',
        why: 'groupingBy(Order::product)에서 봤던 그 표기입니다. 스트림 코드를 읽으려면 이 형태에 익숙해져야 합니다.'
      }
    ]
  },

  {
    id: 'util',
    name: 'Optional · Reflection · Annotation',
    chapter: '교재 1권 · 22장 Util / 23장 Reflection / 24장 Annotation',
    day: '2일차 마지막',
    desc: '교재가 이 세 장을 2일차 끝에 배치한 이유가 있습니다. Spring이 어떻게 동작하는지를 여는 열쇠입니다.',
    questions: [
      {
        key: 'stream-optional',
        type: 'mcq',
        title: 'max()가 돌려주는 것',
        ask: 'best 자리에 들어갈 실제 타입은 무엇일까요?',
        code: 'var best = totals.entrySet().stream()\n        .max(Map.Entry.comparingByValue());',
        options: [
          { text: 'Map.Entry<String, Integer>', mono: true },
          { text: 'Optional<Map.Entry<String, Integer>>', mono: true },
          { text: 'List<Map.Entry<String, Integer>>', mono: true },
          { text: 'int', mono: true }
        ],
        correct: 1,
        explain: '데이터가 비어 있으면 최댓값이 없습니다. 그래서 max()는 "있을 수도, 없을 수도 있음"을 뜻하는 Optional로 감싸 돌려줍니다. 꺼낼 때는 orElseThrow()나 orElse()를 씁니다.',
        why: 'Optional은 null 대신 "없음"을 타입으로 표현하는 장치입니다. 빈 데이터셋을 만났을 때 조용히 넘어가지 않게 해 줍니다.'
      },
      {
        key: 'util-orelse',
        type: 'blank',
        title: 'Optional에서 값 꺼내기',
        ask: '키가 없을 때 0이 출력되도록 빈칸을 채우세요.',
        code: 'Map<String, Integer> totals = Map.of("apple", 9);\n\nOptional<Integer> banana = Optional.ofNullable(totals.get("banana"));\nSystem.out.println(banana.__BLANK__(0));   // 0',
        options: [
          { text: 'orElse', mono: true },
          { text: 'get', mono: true },
          { text: 'ifPresent', mono: true },
          { text: 'filter', mono: true }
        ],
        correct: 0,
        explain: 'orElse(0)은 값이 있으면 그 값을, 없으면 인자로 준 기본값을 돌려줍니다. get()은 값이 없으면 NoSuchElementException을 던지므로 이 자리에 쓸 수 없습니다.',
        why: 'Optional을 만들어 놓고 결국 get()으로 꺼내면 null 검사와 다를 바가 없습니다. orElse / orElseThrow / ifPresent로 "없을 때"를 명시하는 것이 핵심입니다.'
      },
      {
        key: 'acc-resource',
        type: 'mcq',
        title: '파일을 안전하게 닫기',
        ask: 'reader를 try 괄호 안에 선언하면 무엇이 달라질까요?',
        code: 'try (BufferedReader reader = Files.newBufferedReader(path)) {\n    reader.lines().forEach(System.out::println);\n}',
        options: [
          { text: '읽기 속도가 빨라진다' },
          { text: '블록을 벗어날 때 close()가 자동으로 호출된다' },
          { text: '예외가 아예 발생하지 않는다' },
          { text: '파일 전체가 메모리에 한 번에 올라간다' }
        ],
        correct: 1,
        explain: 'try-with-resources 구문입니다. 정상 종료든 예외 발생이든 블록을 벗어나는 순간 close()가 호출됩니다.',
        why: '파일 핸들과 DB 커넥션이 닫히지 않고 쌓이면 서버가 죽습니다. 배치 작업에서 특히 자주 겪는 문제입니다.'
      },
      {
        key: 'util-annotation',
        type: 'mcq',
        title: '애너테이션은 누가 읽는가',
        ask: '@Entity는 그 자체로는 아무 동작도 하지 않는 표시일 뿐입니다. 그럼 누가 읽어서 동작시킬까요?',
        code: '@Entity\n@Table(name = "progress")\nclass Progress {\n    @Id\n    private Long id;\n}',
        options: [
          { text: '컴파일러가 코드를 바꿔 넣는다' },
          { text: '실행 중에 프레임워크가 리플렉션으로 읽어서 처리한다' },
          { text: 'JVM이 자동으로 테이블을 만든다' },
          { text: 'IDE가 실행 전에 처리한다' }
        ],
        correct: 1,
        explain: '애너테이션은 "이 클래스에 이런 표시가 붙어 있다"는 메타데이터일 뿐입니다. Spring과 JPA가 실행 중에 리플렉션으로 클래스를 훑어 표시를 찾아내고, 그에 맞춰 객체를 만들거나 SQL을 생성합니다.',
        why: '교재가 Reflection과 Annotation을 2일차 마지막에 붙여 놓은 이유입니다. 이 두 장을 이해하면 Spring이 마법이 아니라 평범한 Java 코드로 보이기 시작합니다.'
      }
    ]
  },

  {
    id: 'spring',
    name: 'Spring Boot 구조',
    chapter: '교재 2권 · 5~7장 MVC·Controller·Service / 11장 DI와 IoC',
    day: '3일차',
    desc: '지금 이 페이지를 띄우고 있는 서버가 그대로 교재입니다. 요청이 어디를 거쳐 DB까지 가는지 짚어보세요.',
    questions: [
      {
        key: 'spring-layer',
        type: 'mcq',
        title: '요청이 지나가는 길',
        ask: '일반적인 Spring 요청 흐름으로 알맞은 것은?',
        code: 'HTTP 요청 -> ? -> ? -> ? -> DB',
        options: [
          { text: 'Repository → Controller → Service' },
          { text: 'Controller → Service → Repository' },
          { text: 'Service → Repository → Controller' },
          { text: 'Controller → Repository → HTML' }
        ],
        correct: 1,
        explain: 'Controller가 요청을 받고, Service가 업무 로직을 처리하며, Repository가 DB 접근을 담당합니다.',
        why: '이 프로젝트의 LearningApiController.java와 ProgressRepository.java가 정확히 그 구조입니다.'
      },
      {
        key: 'spring-di',
        type: 'mcq',
        title: '이 객체는 누가 만들어 주나',
        ask: 'service 자리에 들어갈 객체는 누가 만들어 넣어 줄까요?',
        code: '@RestController\nclass OrderController {\n    private final OrderService service;\n\n    OrderController(OrderService service) {   // 직접 new 하지 않는다\n        this.service = service;\n    }\n}',
        options: [
          { text: '개발자가 main에서 직접 new로 만든다' },
          { text: 'Spring 컨테이너가 만들어 생성자에 넣어 준다' },
          { text: '브라우저가 요청할 때마다 만든다' },
          { text: 'DB가 만들어 준다' }
        ],
        correct: 1,
        explain: 'Spring이 시작할 때 필요한 객체들을 만들어 보관해 두고, 생성자가 요구하는 타입에 맞춰 넣어 줍니다. 이것을 의존성 주입(DI)이라고 합니다.',
        why: '앞 트랙의 "왜 인터페이스로 받는가"와 같은 이야기입니다. 주입받는 타입을 인터페이스로 두면 구현을 바꿔 끼울 수 있습니다.'
      },
      {
        key: 'spring-json',
        type: 'mcq',
        title: '반환한 객체는 무엇이 되어 도착하나',
        ask: 'list()가 돌려준 List<Order>는 브라우저에 무엇으로 도착할까요?',
        code: '@RestController\nclass OrderController {\n\n    @GetMapping("/api/orders")\n    List<Order> list() {\n        return orderService.findAll();\n    }\n}',
        options: [
          { text: 'HTML 페이지' },
          { text: 'JSON 배열' },
          { text: '파일 다운로드' },
          { text: '아무것도 오지 않는다' }
        ],
        correct: 1,
        explain: '@RestController는 반환값을 그대로 응답 본문에 담되, JSON으로 변환(직렬화)해서 보냅니다. @Controller였다면 같은 반환값이 "보여줄 화면의 이름"으로 해석됩니다.',
        why: '이 페이지의 app.js가 fetch로 받아 쓰는 데이터가 정확히 그 JSON입니다. 브라우저 개발자도구 Network 탭에서 직접 확인해 보세요.'
      }
    ]
  },

  {
    id: 'jpa',
    name: 'JPA',
    chapter: '교재 2권 · 18장 JPA / 19장 JPA Repository',
    day: '3일차',
    desc: 'Java 객체와 DB 테이블을 이어 붙이는 층입니다. 앞에서 배운 애너테이션과 제네릭이 여기서 한꺼번에 쓰입니다.',
    questions: [
      {
        key: 'spring-entity',
        type: 'mcq',
        title: 'Java 객체가 테이블이 되는 지점',
        ask: '이 클래스의 역할은 무엇일까요?',
        code: '@Entity\n@Table(name = "progress")\nclass Progress {\n    @Id @GeneratedValue\n    private Long id;\n    private String answer;\n    private boolean passed;\n}',
        options: [
          { text: 'HTTP 요청을 받는다' },
          { text: 'progress 테이블의 한 행을 Java 객체로 표현한다' },
          { text: 'SQL 쿼리문을 문자열로 담아 둔다' },
          { text: '화면 HTML을 그린다' }
        ],
        correct: 1,
        explain: '필드 하나가 컬럼 하나, 객체 하나가 행 하나에 대응합니다. @Id는 기본키를 가리킵니다.',
        why: '이 프로젝트의 Progress.java가 바로 그 파일입니다. 열어서 컬럼과 필드를 대응시켜 보세요.'
      },
      {
        key: 'spring-repo',
        type: 'mcq',
        title: '제네릭 두 자리 읽기',
        ask: '<Progress, Long>의 두 타입은 각각 무엇을 뜻할까요?',
        code: 'interface ProgressRepository extends JpaRepository<Progress, Long> { }',
        options: [
          { text: '테이블 이름과 컬럼 개수' },
          { text: '다룰 엔티티 타입과 그 엔티티의 기본키(@Id) 타입' },
          { text: '입력 타입과 출력 타입' },
          { text: '부모 클래스와 자식 클래스' }
        ],
        correct: 1,
        explain: '첫째 자리는 이 저장소가 다룰 엔티티(Progress), 둘째 자리는 그 엔티티의 @Id 필드 타입(Long)입니다. 이 두 가지만 알려주면 기본 CRUD 메서드가 자동으로 생깁니다.',
        why: '제네릭을 직접 만들 일은 드물지만, 이렇게 읽을 일은 매일 있습니다. 라이브러리를 쓰려면 읽을 줄 알아야 합니다.'
      }
    ]
  }
];

const FULL_ANSWER = `import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderEtlMission {
    record Order(String product, int quantity) {}
    record ProcessResult<T>(boolean success, int count, T data) {}

    public static void main(String[] args) {
        List<String> logs = List.of(
                "apple,3,SUCCESS", "banana,2,FAILED",
                "apple,4,SUCCESS", "cherry,5,SUCCESS",
                "banana,3,SUCCESS", "apple,1,FAILED");

        // 01 Extract - 성공 로그만
        List<String> successLogs = logs.stream()
                .filter(log -> log.endsWith("SUCCESS"))
                .toList();

        // 02 Transform - 문자열을 객체로
        List<Order> orders = successLogs.stream().map(log -> {
            String[] parts = log.split(",");
            return new Order(parts[0], Integer.parseInt(parts[1]));
        }).toList();

        // 03 Load - 상품별 집계
        Map<String, Integer> totals = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::product,
                        Collectors.summingInt(Order::quantity)));

        Map.Entry<String, Integer> best = totals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        // 04 결과 + 메타데이터
        ProcessResult<Map<String, Integer>> result =
                new ProcessResult<>(true, orders.size(), totals);

        System.out.println("최다 판매 상품: " + best.getKey()
                + " (" + best.getValue() + "개)");
        System.out.println(result);
    }
}`;

/* 모든 문제를 한 줄로 펼쳐 둔 목록 (검색과 개수 세기에 씁니다) */
const ALL = BANK.flatMap(track => track.questions.map(q => ({ ...q, trackId: track.id })));

/* -------------------------------------------------------------------------
 * 2) 화면 상태
 * ---------------------------------------------------------------------- */

const state = {
  token: localStorage.getItem('javaQuizToken'),
  username: localStorage.getItem('javaQuizUser'),
  records: new Map(),      // questionKey -> 서버가 돌려준 답안 기록
  hintsShown: new Map(),   // questionKey -> 열어 본 힌트 단계 수
  saveTimers: new Map(),   // questionKey -> 자동 저장 타이머
  hideDone: false,
  deleteTarget: null       // 삭제 확인 창이 노리고 있는 대상
};

const $ = selector => document.querySelector(selector);
const cardOf = key => document.querySelector(`[data-key="${key}"]`);

function escapeHtml(value) {
  return String(value).replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;');
}

/* -------------------------------------------------------------------------
 * 3) 그리기
 * ---------------------------------------------------------------------- */

function render() {
  $('#factTotal').textContent = ALL.length;
  $('#factTracks').textContent = BANK.length;

  /* 사이드바는 교재의 1일차 / 2일차 / 3일차 묶음을 그대로 따릅니다. */
  let lastDay = null;
  $('#trackNav').innerHTML = BANK.map(track => {
    const heading = track.day !== lastDay ? `<span class="day-label">${track.day}</span>` : '';
    lastDay = track.day;
    return heading + `
      <a class="track-link" href="#track-${track.id}" data-track-link="${track.id}">
        <span>${track.name}</span>
        <span class="track-count" data-track-count="${track.id}">0/${track.questions.length}</span>
      </a>`;
  }).join('');

  let number = 0;
  $('#trackList').innerHTML = BANK.map(track => {
    const cards = track.questions.map(question => {
      number += 1;
      return renderCard(question, number);
    }).join('');

    return `
      <section class="track" id="track-${track.id}">
        <div class="track-head">
          <span class="eyebrow">${track.day} · ${track.name}</span>
          <h2>${track.desc2 ? track.desc2 : track.name}</h2>
          <p class="chapter-ref">${track.chapter}</p>
          <p>${track.desc}</p>
        </div>
        <div class="cards">${cards}</div>
      </section>`;
  }).join('');

  $('#fullAnswer').textContent = FULL_ANSWER;
}

const TYPE_LABEL = { mcq: '객관식', blank: '빈칸 채우기', write: '코드 작성' };

function renderCard(question, number) {
  const body = question.type === 'write' ? renderWriteBody(question) : renderChoiceBody(question);

  return `
    <article class="card" data-key="${question.key}">
      <div class="card-head">
        <span class="state-dot"></span>
        <span class="card-no">${String(number).padStart(2, '0')}</span>
        <h3>${escapeHtml(question.title)}</h3>
        <span class="type-chip" data-type="${question.type}">${TYPE_LABEL[question.type]}</span>
      </div>
      <div class="card-body">
        <p class="ask">${escapeHtml(question.ask)}</p>
        ${body}
        ${question.why ? `
          <div class="why-matters">
            <span class="label">왜 중요한가</span>
            <span>${escapeHtml(question.why)}</span>
          </div>` : ''}
        <div class="actions">
          <button class="primary" data-grade="${question.key}">채점하기</button>
          ${question.type === 'write' ? `<button data-reset="${question.key}">초기화</button>` : ''}
          ${hasHints(question) ? `<button data-hint="${question.key}">힌트 1단계 보기</button>` : ''}
          <span class="saved" data-saved="${question.key}"></span>
        </div>
        <div class="verdict hidden" data-verdict="${question.key}"></div>
      </div>
    </article>`;
}

function hasHints(question) {
  return Array.isArray(question.hints) && question.hints.length > 0;
}

/* 객관식과 빈칸 채우기는 보기 목록이 같은 모양입니다. */
function renderChoiceBody(question) {
  const code = question.type === 'blank'
    ? escapeHtml(question.code).replace('__BLANK__', '<span class="blank-slot" data-blank>____</span>')
    : escapeHtml(question.code);

  const options = question.options.map((option, index) => `
    <label class="opt">
      <input type="radio" name="${question.key}" value="${index}">
      <span class="${option.mono ? 'opt-mono' : ''}">${escapeHtml(option.text)}</span>
    </label>`).join('');

  return `
    ${question.code ? `<pre><code>${code}</code></pre>` : ''}
    <div class="options" data-options="${question.key}">${options}</div>
    <div class="hints" data-hints="${question.key}"></div>`;
}

function renderWriteBody(question) {
  return `
    <textarea spellcheck="false" data-editor="${question.key}">${escapeHtml(question.starter)}</textarea>
    <div class="hints" data-hints="${question.key}"></div>`;
}

/* -------------------------------------------------------------------------
 * 4) 채점
 * ---------------------------------------------------------------------- */

function grade(key) {
  const question = ALL.find(q => q.key === key);
  return question.type === 'write' ? gradeWrite(question) : gradeChoice(question);
}

function gradeChoice(question) {
  const card = cardOf(question.key);
  const chosen = card.querySelector('input:checked');

  if (!chosen) {
    showVerdict(question, false, '보기를 하나 선택한 뒤 채점하세요.');
    return;
  }

  const picked = Number(chosen.value);
  const passed = picked === question.correct;

  const options = card.querySelector('[data-options]');
  options.dataset.locked = 'true';
  options.querySelectorAll('input').forEach(input => { input.disabled = true; });
  options.children[question.correct].dataset.mark = 'correct';
  if (!passed) options.children[picked].dataset.mark = 'wrong';

  showVerdict(question, passed, question.explain);
  save(question.key, String(picked), passed, true);
}

function gradeWrite(question) {
  const card = cardOf(question.key);
  const answer = card.querySelector('textarea').value;
  const failed = question.tests.find(([pattern]) => !pattern.test(answer));
  const passed = !failed;

  showVerdict(
    question,
    passed,
    passed ? '통과! 핵심 문법과 데이터 흐름이 정확합니다.' : failed[1]
  );
  save(question.key, answer, passed, true);
}

function showVerdict(question, passed, message) {
  const card = cardOf(question.key);
  const box = card.querySelector('[data-verdict]');

  card.dataset.state = passed ? 'pass' : 'fail';
  box.innerHTML = `
    <div class="verdict-head">
      <span class="chip" data-kind="${passed ? 'pass' : 'fail'}">${passed ? '정답' : '다시'}</span>
    </div>
    <p>${escapeHtml(message)}</p>`;
  box.classList.remove('hidden');
}

/* 빈칸 유형은 보기를 고르면 코드 안 빈칸에 그 값이 채워집니다. */
function fillBlank(question, index) {
  const slot = cardOf(question.key).querySelector('[data-blank]');
  if (!slot) return;
  slot.textContent = question.options[index].text;
  slot.dataset.filled = 'true';
}

/* -------------------------------------------------------------------------
 * 5) 힌트 (1단계 -> 2단계)
 * ---------------------------------------------------------------------- */

function showNextHint(key) {
  const question = ALL.find(q => q.key === key);
  const shown = state.hintsShown.get(key) || 0;
  if (shown >= question.hints.length) return;

  const box = cardOf(key).querySelector('[data-hints]');
  const hint = document.createElement('div');
  hint.className = 'hint';
  hint.innerHTML = `<span class="label">힌트 ${shown + 1}</span><span>${escapeHtml(question.hints[shown])}</span>`;
  box.appendChild(hint);

  state.hintsShown.set(key, shown + 1);

  const button = cardOf(key).querySelector('[data-hint]');
  if (shown + 1 >= question.hints.length) {
    button.disabled = true;
    button.textContent = '힌트 모두 열림';
  } else {
    button.textContent = `힌트 ${shown + 2}단계 보기`;
  }
}

/* -------------------------------------------------------------------------
 * 6) 서버 통신
 * ---------------------------------------------------------------------- */

async function api(path, options = {}) {
  const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
  if (state.token) headers['X-Access-Token'] = state.token;

  const response = await fetch(path, { ...options, headers });
  if (!response.ok) {
    let message = '요청을 처리하지 못했습니다.';
    try { message = (await response.json()).message || message; } catch { /* 본문 없음 */ }
    throw new Error(message);
  }
  return response.status === 204 ? null : response.json();
}

/* attempted = "채점 버튼을 눌러 본 적이 있는가".
   자동 임시 저장과 실제 채점을 구분해야 오답 노트에 초안이 섞이지 않습니다. */
async function save(key, answer, passed, attempted) {
  const label = document.querySelector(`[data-saved="${key}"]`);
  label.textContent = '저장 중…';
  try {
    const record = await api('/api/progress/' + key, {
      method: 'PUT',
      body: JSON.stringify({ answer, passed, attempted })
    });
    state.records.set(key, record);
    label.textContent = 'DB 저장 완료';
    updateProgress();
  } catch (error) {
    label.textContent = '저장 실패: ' + error.message;
  }
}

/* 코드 작성 칸은 입력이 멈추고 650ms 뒤에 초안을 저장합니다. */
function queueDraft(key, answer) {
  clearTimeout(state.saveTimers.get(key));
  state.saveTimers.set(key, setTimeout(() => {
    const previous = state.records.get(key);
    save(key, answer,
         Boolean(previous?.passed && previous.answer === answer),
         Boolean(previous?.attempted));
  }, 650));
}

/* -------------------------------------------------------------------------
 * 7) 로그인 / 세션
 * ---------------------------------------------------------------------- */

function credentials() {
  return { username: $('#username').value.trim(), password: $('#password').value };
}

function setMessage(text, tone) {
  const box = $('#joinMessage');
  box.textContent = text;
  if (tone) box.dataset.tone = tone; else delete box.dataset.tone;
}

async function checkUsername() {
  if (!credentials().username) { setMessage('아이디를 입력하세요.', 'bad'); return; }
  try {
    const result = await api('/api/users/check?username=' + encodeURIComponent(credentials().username));
    setMessage(
      result.available ? '사용 가능한 아이디입니다. 가입하기를 누르세요.' : '이미 사용 중인 아이디입니다. 로그인해 주세요.',
      result.available ? 'good' : 'bad'
    );
    $('#registerButton').disabled = !result.available;
  } catch (error) { setMessage(error.message, 'bad'); }
}

async function register() {
  try {
    startSession(await api('/api/users', { method: 'POST', body: JSON.stringify(credentials()) }));
  } catch (error) { setMessage(error.message, 'bad'); $('#registerButton').disabled = true; }
}

async function login() {
  try {
    startSession(await api('/api/sessions', { method: 'POST', body: JSON.stringify(credentials()) }));
  } catch (error) { setMessage(error.message, 'bad'); }
}

async function startSession(session) {
  state.token = session.accessToken;
  state.username = session.username;
  localStorage.setItem('javaQuizToken', state.token);
  localStorage.setItem('javaQuizUser', state.username);
  await enterApp();
}

async function enterApp() {
  $('#joinPanel').classList.add('hidden');
  $('#learningApp').classList.remove('hidden');
  $('#finishPanel').classList.remove('hidden');
  $('#whoBox').classList.remove('hidden');
  $('#meterBox').classList.remove('hidden');
  $('#currentUser').textContent = state.username;

  try {
    const records = await api('/api/progress');
    state.records = new Map(records.map(record => [record.questionKey, record]));
    applyRecords();
    updateProgress();
  } catch (error) {
    logout();
    setMessage(error.message, 'bad');
  }
}

/* 서버에 저장돼 있던 답안을 화면에 복원합니다. */
function applyRecords() {
  state.records.forEach((record, key) => {
    const question = ALL.find(q => q.key === key);
    const card = cardOf(key);
    if (!question || !card) return;   // 예전 버전의 문제 기록은 건너뜁니다.

    if (question.type === 'write') {
      card.querySelector('textarea').value = record.answer;
    } else {
      const input = card.querySelector(`input[value="${record.answer}"]`);
      if (input) {
        input.checked = true;
        if (question.type === 'blank') fillBlank(question, Number(record.answer));
      }
    }

    card.dataset.state = record.passed ? 'pass' : 'fail';
    const box = card.querySelector('[data-verdict]');
    box.innerHTML = `
      <div class="verdict-head">
        <span class="chip" data-kind="${record.passed ? 'pass' : 'fail'}">${record.passed ? '정답' : '저장됨'}</span>
      </div>
      <p>${record.passed ? escapeHtml(question.explain || '통과한 답안입니다.') : '저장된 답안입니다. 다시 채점해 보세요.'}</p>`;
    box.classList.remove('hidden');
  });
}

function logout() {
  state.token = null;
  state.username = null;
  state.records.clear();
  localStorage.removeItem('javaQuizToken');
  localStorage.removeItem('javaQuizUser');
  $('#joinPanel').classList.remove('hidden');
  $('#learningApp').classList.add('hidden');
  $('#finishPanel').classList.add('hidden');
  $('#whoBox').classList.add('hidden');
  $('#meterBox').classList.add('hidden');
  render();
  bindCards();
  renderWrongList();
}

/* -------------------------------------------------------------------------
 * 8) 진행률
 * ---------------------------------------------------------------------- */

function updateProgress() {
  const passedKeys = new Set(
    [...state.records.values()].filter(record => record.passed).map(record => record.questionKey)
  );
  const done = ALL.filter(question => passedKeys.has(question.key)).length;

  $('#meterText').textContent = `${done} / ${ALL.length}`;
  $('#meterFill').style.width = (done / ALL.length * 100) + '%';

  BANK.forEach(track => {
    const trackDone = track.questions.filter(q => passedKeys.has(q.key)).length;
    document.querySelector(`[data-track-count="${track.id}"]`).textContent = `${trackDone}/${track.questions.length}`;
    document.querySelector(`[data-track-link="${track.id}"]`).dataset.done = String(trackDone === track.questions.length);
  });

  if (done === ALL.length) {
    $('#finishTitle').textContent = '전 트랙 완료';
    $('#finishText').textContent = '코드를 읽고 데이터 흐름을 설명할 수 있는 단계에 도달했습니다. 이제 완성 코드를 직접 실행해 보세요.';
  } else {
    $('#finishTitle').textContent = `${ALL.length - done}문제 남았습니다`;
    $('#finishText').textContent = '작성 내용과 통과 여부는 입력 직후 자동으로 DB에 저장됩니다.';
  }

  renderWrongList();
  if (state.hideDone) applyFilter();
}

/* -------------------------------------------------------------------------
 * 8-1) 오답 노트
 *
 * 채점을 해 본 적이 있고(attempted) 아직 통과하지 못한(passed=false) 문제만 모읍니다.
 * 코드 칸에 글자만 쳐 둔 초안은 채점 이력이 없으므로 여기 들어오지 않습니다.
 * ---------------------------------------------------------------------- */

function renderWrongList() {
  const panel = $('#wrongPanel');
  const badge = $('#wrongBadge');

  const wrong = ALL.filter(question => {
    const record = state.records.get(question.key);
    return record && record.attempted && !record.passed;
  });

  badge.textContent = wrong.length;
  badge.dataset.has = String(wrong.length > 0);

  if (!wrong.length) {
    panel.innerHTML = '<p class="wrong-empty">아직 오답이 없습니다. 채점해서 틀린 문제가 여기에 모입니다.</p>';
    return;
  }

  panel.innerHTML = wrong.map(question => {
    const track = BANK.find(t => t.id === question.trackId);
    return `
      <div class="wrong-item">
        <a href="#track-${question.trackId}" data-goto="${question.key}">${escapeHtml(question.title)}</a>
        <p>${escapeHtml(track.name)} · ${TYPE_LABEL[question.type]}</p>
        <div class="actions">
          <button class="tiny" data-goto="${question.key}">다시 풀기</button>
          <button class="tiny danger" data-delete="${question.key}">기록 삭제</button>
        </div>
      </div>`;
  }).join('');
}

/* 오답 노트에서 문제를 누르면 해당 카드로 이동하고, 필터가 켜져 있어도 보이게 합니다. */
function goToQuestion(key) {
  const card = cardOf(key);
  if (!card) return;
  card.classList.remove('hidden');
  card.scrollIntoView({ behavior: 'smooth', block: 'center' });
}

/* -------------------------------------------------------------------------
 * 8-2) 기록 삭제 (되돌릴 수 없으므로 반드시 확인 창을 거칩니다)
 * ---------------------------------------------------------------------- */

function openDeleteModal(target) {
  state.deleteTarget = target;
  const all = target === 'all';
  const question = all ? null : ALL.find(q => q.key === target);

  $('#modalTitle').textContent = all ? '전체 학습 기록을 삭제할까요?' : '이 문제의 기록을 삭제할까요?';
  $('#modalText').textContent = all
    ? '모든 답안과 채점 결과가 지워집니다. 되돌릴 수 없습니다.'
    : `"${question ? question.title : target}"의 답안과 채점 결과가 지워집니다. 되돌릴 수 없습니다.`;
  $('#deleteModal').showModal();
}

async function confirmDelete() {
  const target = state.deleteTarget;
  if (!target) return;

  try {
    if (target === 'all') {
      await api('/api/progress', { method: 'DELETE' });
      state.records.clear();
      render();
      bindCards();
    } else {
      await api('/api/progress/' + target, { method: 'DELETE' });
      state.records.delete(target);
      resetCard(target);
    }
    updateProgress();
    $('#deleteModal').close();
  } catch (error) {
    $('#modalText').textContent = '삭제하지 못했습니다: ' + error.message;
  }
}

/* 카드 한 장을 처음 상태로 되돌립니다. */
function resetCard(key) {
  const question = ALL.find(q => q.key === key);
  const card = cardOf(key);
  if (!question || !card) return;

  delete card.dataset.state;
  card.querySelector('[data-verdict]').classList.add('hidden');

  if (question.type === 'write') {
    card.querySelector('textarea').value = question.starter;
    return;
  }

  const options = card.querySelector('[data-options]');
  delete options.dataset.locked;
  options.querySelectorAll('input').forEach(input => { input.disabled = false; input.checked = false; });
  options.querySelectorAll('.opt').forEach(opt => { delete opt.dataset.mark; });

  const slot = card.querySelector('[data-blank]');
  if (slot) { slot.textContent = '____'; delete slot.dataset.filled; }
}

function applyFilter() {
  ALL.forEach(question => {
    const card = cardOf(question.key);
    if (!card) return;
    const passed = state.records.get(question.key)?.passed;
    card.classList.toggle('hidden', Boolean(state.hideDone && passed));
  });
}

/* -------------------------------------------------------------------------
 * 9) 이벤트 연결
 * ---------------------------------------------------------------------- */

function bindCards() {
  document.querySelectorAll('[data-options]').forEach(box => {
    box.addEventListener('change', event => {
      const question = ALL.find(q => q.key === box.dataset.options);
      if (question.type === 'blank') fillBlank(question, Number(event.target.value));
    });
  });
}

document.addEventListener('click', event => {
  const target = event.target;
  if (target.dataset.grade) grade(target.dataset.grade);
  if (target.dataset.hint) showNextHint(target.dataset.hint);
  if (target.dataset.goto) goToQuestion(target.dataset.goto);
  if (target.dataset.delete) openDeleteModal(target.dataset.delete);
  if (target.dataset.reset) {
    const question = ALL.find(q => q.key === target.dataset.reset);
    cardOf(question.key).querySelector('textarea').value = question.starter;
    save(question.key, question.starter, false, Boolean(state.records.get(question.key)?.attempted));
  }
});

document.addEventListener('input', event => {
  if (event.target.matches('textarea[data-editor]')) {
    queueDraft(event.target.dataset.editor, event.target.value);
  }
});

/* 코드 칸에서 Tab 키가 포커스를 옮기지 않고 들여쓰기가 되도록 합니다. */
document.addEventListener('keydown', event => {
  if (!event.target.matches('textarea[data-editor]') || event.key !== 'Tab') return;
  event.preventDefault();
  const area = event.target;
  const start = area.selectionStart;
  const end = area.selectionEnd;
  area.value = area.value.slice(0, start) + '    ' + area.value.slice(end);
  area.selectionStart = area.selectionEnd = start + 4;
  area.dispatchEvent(new Event('input', { bubbles: true }));
});

$('#joinForm').addEventListener('submit', event => { event.preventDefault(); login(); });
$('#checkButton').addEventListener('click', checkUsername);
$('#registerButton').addEventListener('click', register);
$('#logoutButton').addEventListener('click', logout);
$('#username').addEventListener('input', () => {
  $('#registerButton').disabled = true;
  setMessage('아이디를 바꿨습니다. 중복 확인을 다시 눌러 주세요.');
});

$('#deleteAllButton').addEventListener('click', () => openDeleteModal('all'));
$('#confirmDeleteButton').addEventListener('click', event => {
  event.preventDefault();   // form method="dialog" 의 기본 닫기를 막고 삭제 후 직접 닫습니다.
  confirmDelete();
});

$('#filterButton').addEventListener('click', () => {
  state.hideDone = !state.hideDone;
  $('#filterButton').textContent = state.hideDone ? '전체 문제 보기' : '아직 못 푼 문제만 보기';
  applyFilter();
});

$('#showAnswerButton').addEventListener('click', () => {
  const box = $('#fullAnswer');
  box.classList.toggle('hidden');
  $('#showAnswerButton').textContent = box.classList.contains('hidden') ? 'ETL 완성 코드 보기' : '완성 코드 접기';
});

$('#copyAnswerButton').addEventListener('click', async () => {
  await navigator.clipboard.writeText(FULL_ANSWER);
  $('#copyAnswerButton').textContent = '복사 완료';
  setTimeout(() => { $('#copyAnswerButton').textContent = '완성 코드 복사'; }, 1200);
});

/* -------------------------------------------------------------------------
 * 시작
 * ---------------------------------------------------------------------- */

render();
bindCards();
if (state.token && state.username) enterApp();
