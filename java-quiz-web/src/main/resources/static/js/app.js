const quizQuestions = [
  { key:'quiz-flow', priority:'1순위 · 실행 흐름', title:'다음 코드가 출력하는 값은?', code:'int count = 2;\ncount = count + 3;\nSystem.out.println(count);', options:['2','3','5','컴파일 오류'], correct:2, explain:'대입문 오른쪽을 먼저 계산합니다. 2 + 3의 결과 5가 count에 다시 저장됩니다.' },
  { key:'quiz-method', priority:'1순위 · 메서드', title:'add(2, 3)을 호출했을 때 반환값은?', code:'static int add(int a, int b) {\n    return a + b;\n}', options:['2','3','5','반환값 없음'], correct:2, explain:'a에는 2, b에는 3이 들어가며 return이 계산 결과 5를 호출한 곳으로 돌려줍니다.' },
  { key:'quiz-object', priority:'1순위 · 객체', title:'다음 코드에서 order는 무엇인가?', code:'Order order = new Order("apple", 3);', options:['클래스 정의','Order 객체를 가리키는 변수','메서드','문자열'], correct:1, explain:'new Order(...)가 객체를 만들고, order 변수는 그 객체를 참조합니다.' },
  { key:'quiz-list-map', priority:'1순위 · 컬렉션', title:'상품별 총수량을 저장하기 가장 알맞은 타입은?', code:'apple → 7\nbanana → 3\ncherry → 5', options:['List<String>','Set<Integer>','Map<String, Integer>','String[]'], correct:2, explain:'상품명이라는 key와 수량이라는 value의 관계이므로 Map<String, Integer>가 알맞습니다.' },
  { key:'quiz-generic', priority:'2순위 · 제네릭', title:'ProcessResult<List<Order>>에서 T는 무엇인가?', code:'record ProcessResult<T>(boolean success, T data) {}', options:['Order','List','List<Order>','boolean'], correct:2, explain:'T 자리에 들어간 전체 타입은 List<Order>입니다. 제네릭 안에 제네릭이 들어갈 수도 있습니다.' },
  { key:'quiz-lambda', priority:'2순위 · 람다', title:'다음 filter의 역할은?', code:'orders.stream()\n      .filter(order -> order.quantity() >= 3)', options:['모든 수량을 3으로 변경','수량 3 이상만 선택','수량을 합산','상품명순 정렬'], correct:1, explain:'filter는 조건이 true인 데이터만 통과시킵니다. 원본 값을 변경하지 않습니다.' },
  { key:'quiz-exception', priority:'2순위 · 예외', title:'NumberFormatException이 발생할 수 있는 입력은?', code:'int quantity = Integer.parseInt(text);', options:['"42"','"0"','"-3"','"apple"'], correct:3, explain:'Integer.parseInt는 정수 모양의 문자열만 변환할 수 있습니다. apple은 정수가 아닙니다.' },
  { key:'quiz-spring', priority:'3순위 · Spring', title:'일반적인 Spring 요청 흐름으로 알맞은 것은?', code:'HTTP 요청 → ? → ? → ? → DB', options:['Repository → Controller → Service','Controller → Service → Repository','Service → Repository → Controller','Controller → Repository → HTML'], correct:1, explain:'Controller가 요청을 받고, Service가 업무 로직을 처리하며, Repository가 DB 접근을 담당합니다.' }
];

const labSteps = [
  { key:'lab-filter', title:'01 · 성공 로그만 필터링', desc:'filter()와 람다로 SUCCESS 로그만 남기세요.', starter:'List<String> successLogs = logs.stream()\n        // 여기에 작성\n        .toList();', hint:'문자열의 endsWith("SUCCESS")를 사용하세요.', tests:[[/\.filter\s*\(/,'filter()가 필요합니다.'],[/->/,'람다식(->)을 작성하세요.'],[/endsWith\s*\(\s*"SUCCESS"\s*\)/,'SUCCESS로 끝나는지 검사하세요.']] },
  { key:'lab-map', title:'02 · 로그를 Order로 변환', desc:'map()으로 String 로그를 Order 객체로 바꾸세요.', starter:'List<Order> orders = successLogs.stream()\n        .map(log -> {\n            String[] parts = log.split(",");\n            // Order를 반환하세요\n        })\n        .toList();', hint:'new Order(parts[0], Integer.parseInt(parts[1]))를 반환하세요.', tests:[[/\.map\s*\(/,'map()이 필요합니다.'],[/return\s+new\s+Order\s*\(/,'새 Order 객체를 반환하세요.'],[/parts\s*\[\s*0\s*\]/,'상품명은 parts[0]입니다.'],[/Integer\.parseInt\s*\(\s*parts\s*\[\s*1\s*\]\s*\)/,'수량을 int로 변환하세요.']] },
  { key:'lab-group', title:'03 · 상품별 판매량 집계', desc:'groupingBy()와 summingInt()를 조합하세요.', starter:'Map<String, Integer> totals = orders.stream()\n        .collect(Collectors.groupingBy(\n                // 그룹 기준,\n                // 수량 합계\n        ));', hint:'Order::product와 Collectors.summingInt(Order::quantity)를 사용하세요.', tests:[[/Collectors\.groupingBy\s*\(/,'groupingBy()가 필요합니다.'],[/Order::product/,'상품명을 그룹 기준으로 지정하세요.'],[/Collectors\.summingInt\s*\(/,'summingInt()로 수량을 합치세요.'],[/Order::quantity/,'quantity를 합산하세요.']] },
  { key:'lab-max', title:'04 · 최다 판매 상품 찾기', desc:'Entry의 value를 비교해 최댓값을 찾으세요.', starter:'Map.Entry<String, Integer> best = totals.entrySet()\n        .stream()\n        // 최댓값 찾기\n        .orElseThrow();', hint:'max(Map.Entry.comparingByValue())를 사용하세요.', tests:[[/\.max\s*\(/,'max()가 필요합니다.'],[/Map\.Entry\.comparingByValue\s*\(\s*\)/,'Entry의 value를 비교하세요.']] },
  { key:'lab-generic', title:'05 · 제네릭 처리 결과 만들기', desc:'totals와 처리 건수를 ProcessResult에 담으세요.', starter:'ProcessResult<Map<String, Integer>> result =\n        // 여기에 작성;', hint:'new ProcessResult<>(true, orders.size(), totals)를 사용하세요.', tests:[[/new\s+ProcessResult\s*<>\s*\(/,'ProcessResult 객체를 생성하세요.'],[/true/,'성공 여부는 true입니다.'],[/orders\.size\s*\(\s*\)/,'처리 건수는 orders.size()입니다.'],[/totals/,'결과 totals를 전달하세요.']] }
];

const fullAnswer = `import java.util.List;
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

        List<String> successLogs = logs.stream()
                .filter(log -> log.endsWith("SUCCESS"))
                .toList();

        List<Order> orders = successLogs.stream().map(log -> {
            String[] parts = log.split(",");
            return new Order(parts[0], Integer.parseInt(parts[1]));
        }).toList();

        Map<String, Integer> totals = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::product,
                        Collectors.summingInt(Order::quantity)));

        Map.Entry<String, Integer> best = totals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        ProcessResult<Map<String, Integer>> result =
                new ProcessResult<>(true, orders.size(), totals);
        System.out.println("최다 판매 상품: " + best.getKey()
                + " (" + best.getValue() + "개)");
        System.out.println(result);
    }
}`;

const state = { token:localStorage.getItem('javaQuizToken'), username:localStorage.getItem('javaQuizUser'), records:new Map(), saveTimers:new Map() };
const $ = selector => document.querySelector(selector);

function render() {
  $('#quizList').innerHTML = quizQuestions.map((q,i) => `<article class="question-card" data-key="${q.key}"><div class="priority">${q.priority}</div><h3>${i+1}. ${q.title}</h3><pre>${escapeHtml(q.code)}</pre><div class="options">${q.options.map((o,n)=>`<label class="option"><input type="radio" name="${q.key}" value="${n}"><span>${o}</span></label>`).join('')}</div><div class="actions"><button data-grade-quiz="${q.key}">채점하기</button><span class="saved"></span></div><div class="result"></div></article>`).join('');
  $('#labList').innerHTML = labSteps.map(s => `<article class="lab-card" data-key="${s.key}"><h3>${s.title}</h3><p>${s.desc}</p><textarea spellcheck="false">${escapeHtml(s.starter)}</textarea><details><summary>힌트 보기</summary><p>${escapeHtml(s.hint)}</p></details><div class="actions"><button data-grade-lab="${s.key}">채점하기</button><button class="secondary" data-reset="${s.key}">초기화</button><span class="saved"></span></div><div class="result"></div></article>`).join('');
  $('#fullAnswer').textContent=fullAnswer;
}

async function api(path, options={}) {
  const headers={'Content-Type':'application/json',...(options.headers||{})};
  if(state.token) headers['X-Access-Token']=state.token;
  const response=await fetch(path,{...options,headers});
  if(!response.ok){let message='요청을 처리하지 못했습니다.';try{const e=await response.json();message=e.message||message}catch{}throw new Error(message)}
  return response.status===204?null:response.json();
}

async function checkUsername(){
  try{const result=await api('/api/users/check?username='+encodeURIComponent($('#username').value));$('#joinMessage').textContent=result.available?'사용 가능한 아이디입니다.':'이미 사용 중입니다. 로그인해 주세요.';$('#registerButton').disabled=!result.available}catch(e){showJoinError(e)}
}
async function register(){
  try{const session=await api('/api/users',{method:'POST',body:JSON.stringify(credentials())});startSession(session)}catch(e){showJoinError(e)}
}
async function login(){
  try{const session=await api('/api/sessions',{method:'POST',body:JSON.stringify(credentials())});startSession(session)}catch(e){showJoinError(e)}
}
function credentials(){return {username:$('#username').value,password:$('#password').value}}
function showJoinError(e){$('#joinMessage').textContent=e.message;$('#registerButton').disabled=true}
async function startSession(session){state.token=session.accessToken;state.username=session.username;localStorage.setItem('javaQuizToken',state.token);localStorage.setItem('javaQuizUser',state.username);await enterApp()}
async function enterApp(){
  $('#joinPanel').classList.add('hidden');$('#sessionPanel').classList.remove('hidden');$('#learningApp').classList.remove('hidden');$('#currentUser').textContent=state.username;
  try{const records=await api('/api/progress');state.records=new Map(records.map(r=>[r.questionKey,r]));applyRecords();updateProgress()}catch(e){logout();$('#joinMessage').textContent=e.message}
}
function applyRecords(){
  state.records.forEach((r,key)=>{const card=document.querySelector(`[data-key="${key}"]`);if(!card)return;if(key.startsWith('quiz-')){const input=card.querySelector(`input[value="${r.answer}"]`);if(input)input.checked=true}else card.querySelector('textarea').value=r.answer;showResult(card,r.passed,r.passed?'저장된 정답입니다.':'저장된 답안입니다. 다시 채점해 보세요.')})
}
function gradeQuiz(key){const q=quizQuestions.find(x=>x.key===key),card=document.querySelector(`[data-key="${key}"]`),chosen=card.querySelector('input:checked');if(!chosen){showResult(card,false,'답을 하나 선택하세요.');return}const passed=Number(chosen.value)===q.correct;showResult(card,passed,(passed?'정답! ':'다시 생각해 보세요. ')+q.explain);save(key,chosen.value,passed)}
function gradeLab(key){const step=labSteps.find(x=>x.key===key),card=document.querySelector(`[data-key="${key}"]`),answer=card.querySelector('textarea').value,failure=step.tests.find(([regex])=>!regex.test(answer));const passed=!failure;showResult(card,passed,passed?'통과! 핵심 문법과 데이터 흐름이 정확합니다.':'아직이에요 · '+failure[1]);save(key,answer,passed)}
function showResult(card,passed,message){const out=card.querySelector('.result');out.className='result '+(passed?'pass':'fail');out.textContent=message}
async function save(key,answer,passed){
  const card=document.querySelector(`[data-key="${key}"]`),label=card.querySelector('.saved');label.textContent='저장 중…';
  try{const record=await api('/api/progress/'+key,{method:'PUT',body:JSON.stringify({answer,passed})});state.records.set(key,record);label.textContent='DB 저장 완료';updateProgress()}catch(e){label.textContent='저장 실패: '+e.message}
}
function queueDraft(key,answer){clearTimeout(state.saveTimers.get(key));state.saveTimers.set(key,setTimeout(()=>{const old=state.records.get(key);save(key,answer,old?.passed&&old.answer===answer)},650))}
function updateProgress(){const count=[...state.records.values()].filter(r=>r.passed).length,total=quizQuestions.length+labSteps.length;$('#progressText').textContent=`${count} / ${total} 완료`;$('#progressBar').style.width=(count/total*100)+'%';if(count===total){$('#completionTitle').textContent='Java 필수 개념과 ETL 미션 완료!';$('#completionText').textContent='코드를 읽고 데이터 흐름을 설명할 수 있는 단계에 도달했습니다.'}}
function logout(){state.token=null;state.username=null;state.records.clear();localStorage.removeItem('javaQuizToken');localStorage.removeItem('javaQuizUser');$('#joinPanel').classList.remove('hidden');$('#sessionPanel').classList.add('hidden');$('#learningApp').classList.add('hidden')}
function escapeHtml(value){return value.replaceAll('&','&amp;').replaceAll('<','&lt;').replaceAll('>','&gt;')}

render();
$('#checkButton').addEventListener('click',checkUsername);$('#registerButton').addEventListener('click',register);$('#loginButton').addEventListener('click',login);$('#logoutButton').addEventListener('click',logout);
$('#username').addEventListener('input',()=>{$('#registerButton').disabled=true;$('#joinMessage').textContent='중복 확인이 필요합니다.'});
document.addEventListener('click',e=>{if(e.target.dataset.gradeQuiz)gradeQuiz(e.target.dataset.gradeQuiz);if(e.target.dataset.gradeLab)gradeLab(e.target.dataset.gradeLab);if(e.target.dataset.reset){const step=labSteps.find(x=>x.key===e.target.dataset.reset),card=document.querySelector(`[data-key="${step.key}"]`);card.querySelector('textarea').value=step.starter;showResult(card,false,'초기화했습니다.');save(step.key,step.starter,false)}});
document.addEventListener('input',e=>{const card=e.target.closest('.lab-card');if(card&&e.target.matches('textarea'))queueDraft(card.dataset.key,e.target.value)});
document.addEventListener('keydown',e=>{if(e.target.matches('textarea')&&e.key==='Tab'){e.preventDefault();const t=e.target,s=t.selectionStart,n=t.selectionEnd;t.value=t.value.slice(0,s)+'    '+t.value.slice(n);t.selectionStart=t.selectionEnd=s+4;t.dispatchEvent(new Event('input',{bubbles:true}))}});
$('#showAnswerButton').addEventListener('click',()=>$('#fullAnswer').classList.toggle('hidden'));
$('#copyAnswerButton').addEventListener('click',async()=>{await navigator.clipboard.writeText(fullAnswer);$('#copyAnswerButton').textContent='복사 완료';setTimeout(()=>$('#copyAnswerButton').textContent='완성 코드 복사',1200)});
if(state.token&&state.username)enterApp();
