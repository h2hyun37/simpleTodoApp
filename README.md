# simpleTodoApp
* 간단한 to-do 앱입니다.

## 기능
* to-do 등록
  * 간단하게 to-do 내용을 text로 등록 가능합니다.
  * 등록시 다른 to-do 참조가 가능합니다.
* to-do 내용수정
  * to-do 내용 수정이 가능합니다.
* to-do 완료처리
  * 처음 등록시 `진행중`으로 표시되고, 완료처리가 가능합니다.
  * 다른 to-do가 해당 to-do를 참조하고 있다면, 다른 to-do를 먼저 완료해야 처리가 가능합니다.

## 설치 및 실행
* java,maven,git 사용이 가능해야 합니다
#### 1. 이 git repository를 로컬pc에 clone하여 다운로드 받습니다.
```git clone https://github.com/h2hyun37/simpleTodoApp```

#### 2. 다음 maven 명령어를 실행하여 빌드합니다.
```mvn clean package -DskipTests```
* 전체 테스트 시 오류가 발생하여 부득이하게 test skip합니다.
* 각각의 테스트케이스  실행은 다음과 같이 하면 됩니다.
```
mvn clean test -Dtest=TodoServiceTest
mvn clean test -Dtest=TodoRepositoryTest
```
#### 3. 정상적으로 빌드가 완료되면, 다음 java 명령어를 실행하여 구동합니다.
```java -jar target/todoApp-0.0.1-SNAPSHOT.jar```

#### 4. 브라우저를 이용하여 todoApp 페이지를 오픈합니다.
```http://localhost:8080/view/list```

#### 5. DB 확인이 필요하다면 DB 콘솔 페이지를 이용합니다.
```http://localhost:8080/h2-console```

## 기타
#### 문제해결전략
##### 요구사항 분석
* to-do가 원형으로 참조되지 않게끔 해야합니다.(circular-reference)
  * 원형 참조시, 다른 to-do를 먼저 완료할 수가 없으므로 어느것도 완료처리를 할 수 없습니다.
  * 신규로 to-do 등록시에는 원형 참조가 발생하지 않으므로, 업데이트 시에만 원형 참조가 생기지 않게끔 해야합니다. 
  (현재 요구사항에서는 참조를 수정하는 내용이 없으므로, 일단 이 부분은 제외했으나 추후 해당 요구사항 발생시 로직 추가가 필요합니다.)
* 향후 상태값이 추가될 것으로 고려하여 enum으로 관리
##### 사용기술
* API 서버와 front-end 구현이 필요.
  * API 서버
    * java 8, spring boot 2.1.1, spring data jpa
    * JSON 포맷으로 API 요청/응답이 가능하게 구현
  * front-end
    * bootstrap, jQuery 이용하여 웹UI 구현
  
  
  
