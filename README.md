프로그 설명
=================
아카 인 액션 한글판 (Akka 코딩 공작소)의 2장 ticket 예제를 이용하여 myStack 액터 모델 구현

TO DO
=================
1. future 사용
2. dispatcher 사용
3. thread pool 


myStack 구현
=================

1. Main.scala
   
2. RestApi.scala
   
3. myStack.scala
   
4. DataMarchalling.scala
    - 데이터 포맷 지정을 위한 설정
   
How to run
    
    sbt clean
    sbt assembly
    sbt run

프로그램 컴파일 시 아래와 같이 출력됨

    INFO  [Slf4jLogger]: Slf4jLogger started
    INFO  [myStack]: RestApi bound to /0:0:0:0:0:0:0:0:5000


myStack에 데이터를 push할 때와 pop할 때 명령어

    $curl -X GET -H 'Content type: application/json' 'http://localhost:5000/storage'
    $curl -X POST -H 'Content type: application/json' 'http://localhost:5000/storage/1' -d 
            '{ 
                "data" : 1
            }'



