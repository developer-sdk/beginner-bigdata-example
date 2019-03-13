# CCTV 분석 
이이번에는 복합키를 이용하여 데이터를 정렬하는 방법을 확인해 보겠습니다.

관리기관과 설치목적을 이용하여 관리기관별 설치목적 건수와 모든 CCTV의 건수를 조회하도록 하겠습니다. 기존과 같이 관리기관을 키로 하면 설치목적을 리듀서별로 전달할 수 없게 됩니다. 관리기관과 설치목적을 연결하여 하나의 키(ex: 관리기관_설치목적)로 이용하면, 서로 다른 리듀서로 보내질 수 있습니다. 이럴때는 세컨더리 소트(Secondary Sort)를 이용하여 처리할 수 있습니다.

세컨더리 소트를 이용하기 위해서는 리듀서의 전체 입력을 정렬하는 기준이 되는 SortComparator와 리듀서의 reduce() 메소드로 데이터를 전달할 때 Iterable(values)를 만들기 위한 그룹핑 기준이 되는 GroupingComparator를 구현해야 합니다.

매퍼의 출력으로 <<관리기관, 설치 목적>, 설치 목적>과 같이 출력합니다. 파티셔너는 복합키 중 관리기관을 기준으로 리듀서를 나누어줍니다. 리듀서는 복합키를 이용하여 관리기관, 목적 순으로 정렬을 수행합니다. 그리고 리듀서에 데이터를 넘기는 그룹핑 기준을 복합키의 관리기관을 이용하여 처리합니다. 리듀서는 입력으로 <<관리기관, 설치 목적>, List<목적>>을 입력으로 받습니다. 여기서 목적의 유니크한 값과 리스트의 전체 개수를 세면 관리기관별 설치목적의 건수와 모든 CCTV의 건수를 알 수 있게 됩니다.


## 매퍼(CctvMapper.java)
매퍼에서는 관리기관과 설치목적을 추출하고, 복합키(CctvComparePair)를 이용하여 매퍼의 처리 결과를 출력합니다. 

## 복합키(CctvComparePair.java)
복합키는 관리기관과 설치목적을 같이 키로 이용하기 위해서 생성하는 클래스 입니다. 비교연산과 매퍼의 결과 출력을 위해서 WritableComparable을 구현합니다. compareTo()를 구현하여 복합키 비교의 우선순위를 구해줍니다. 매퍼의 출력을 <<관리기관, 설치목적>, 설치목적>으로 설정할 수 있게 도와줍니다.  

## 파티셔너(CctvPartitioner.java)
파티셔너는 리듀서로 보낼 데이터를 구분하는 기준이 됩니다. 관리기관을 기준으로 파티션을 구분하여 리듀서로 데이터를 보내줍니다. 이제 매퍼의 출력결과를 관리기관 기준으로 동일하게 리듀서로 전달합니다. 

## 소트컴패어래터(CctvSortComparator.java)
리듀서의 전체 입력을 정렬하는 기준이 되는 SortComparator를 구현해 줍니다. 리듀서에 입력된 데이터를 비교하여 정렬합니다. 복합키에 구현된 방식으로 관리기관으로 먼저 비교하고, 설치목적으로 또 비교하여 정렬하여 줍니다. 

## 그룹핑 컴패어래터(CctvGroupingComparator.java)
리듀서에서 실제 작업을 진행하는 reduce(KEYIN key, Iterable values, Context context) 메소드에 values 데이터를 전달하기 위한 그룹핑을 처리하는 클래스를 구현합니다.

관리기관을 기준으로 그룹핑을 처리하여 전달하기 위해 관리기관명으로 데이터를 비교 합니다. 이제 reduce() 메소드에는 관리기관을 기준으로 정렬된 List<설치목적> 값이 전달되게 됩니다.

## 리듀서(CctvReducer.java)
리듀서는 관리주체를 기준으로 정렬된 List<설치 목적> 데이터를 입력으로 받습니다. 이를 이용하여 유니크한 설치 목적의 개수(관리주체별 설치 목적), 전체 설치 목적의 개수(CCTV의 개수)를 세어서 출력하면 됩니다.

## 메인(CctvMain.java)
작업 관련된 클래스를 정의합니다. 