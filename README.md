# 최적화 알고리즘  
1. 각각 하나의 독립변수와 종속변수로 이루어진 데이터 집합에 대해, 두 변수의 관계를 가장 잘 설명할 수 있는 수학적 모델 가정  
2. 에러를 최소화하는 모수 값을 최적화 알고리즘(모의담금질 기법)을 이용해 추정  
---


## 회귀 분석(Regression Analysis)  

* 회귀분석(Regression Test)이란?  
독립변인이 종속변인에 영향을 미치는지 알아보고자 할 떄 실시하는 분석방법  

* 회귀의 원리는 다음 그래프에서 보는 것과 같이 선형 회귀모델의 직선과 실제 값 사이의 차를 뜻하는 residual error를 최소화 시키는 것 

> 독립변수 : 온도 (° ⁣C)   
종속변수 : 강아지 산책 정도 (1~5)   
```
1 : 강아지 산책  즐기기 좋은 날씨  
2 : 강아지 산책 즐기기 좋은 날씨이지만 주의는 필요한 날씨   
3 : 체격에 따라 안전하지 않을 수 있는 날씨, 주의  
4 : 산책하기 위험성이 높은 날씨  
5 : 건강 위협이 높은 기온 장시간 산책을 피하세요  
```

![reg_eq](https://user-images.githubusercontent.com/98294597/173908978-3631917b-a802-4aa1-b6e2-24a11d01c416.png)


```
>>[a0 a1]  
ans =

    2.5721   -0.1597  

    ->  y= 2.57 - 0.16x
```


---
## 모의 담금질(Simulated Annealing,SA)
1. 최적화 알고리즘 구현(모의 담금질 기법)  
2. 기본 동작 방식  
3. 알고리즘에서 사용한 함수 및 매개변수 설명  
4. 이웃해 설정 방식, 이유
5. 모수 값 추정 (추정된 모수 값이 적절한지)  
6. 최적화 과정에 대한 분석(에러 감소 경향 표현)  

### Annealing  
> 풀림이라는 뜻으로 금속재료를 가열한 다음 조금씩 냉각해 결정 성장 후 결함을 낮추는 작업

### 최적화 알고리즘 구현(모의 담금질 기법)    
* SA기법은 높은 온도에서 액체상태인 물질이 온도가 점차 낮아지면서 결정체로 변하는 과정을 모방한 해 탐색 알고리즘  
* 온도가 점점 낮아지면 분자의 움직임이 줄어들어 결정체가 되는데 해 탐색과정도 이와 유사하게 점점 더 규칙적인 방식으로 이루어진다  

* 전역최적화 문제에 대한 확률적 알고리즘으로 많은 탐색공간 안에서 전역 최적해에 대한 좋은 근사를 찾는 것  

**모든 경우의 수를 탐색하지 않고 최적의 해를 찾는다** 

---

### 기본 동작 방식   
```
1. 임의의 후보해 s를 선택
2. 초기 T설정  
3. 반복
4. for i = 1 to iteration { 
5. s의 이웃해 중에서 랜덤하게 하나의 해 s'를 선택한다.
6. d = (s'의 값) - (s의 값)
7. if (d < 0) // 이웃해인 s'가 더 우수한 경우
8. s ← s'
9. else // s'가 s보다 우수하지 않은 경우
10. q ← (0,1) 사이에서 랜덤하게 선택한 수
11. if ( q < p ) s ← s'  
 }
12. T ← αT // 1보다 작은 상수 α 를 T에 곱하여 새로운 T를 계산
13. until (종료 조건이 만족될 때까지)
14. return s
```
---
### 알고리즘에서 사용한 함수 및 매개변수 설명

알고리즘에서 사용한 함수 
``` 
public static double[] simulated_annealing  
 : iter,temperatur,range,step_size를 매개변수로 받아 새로 업데이트되는 이웃해 x,y를 반환하는 함수 

 public static double objective_function  
  : 데이터를 통해 얻은 회귀식을 업데이트 하기 위해 이웃해들을 대입하는 함수를 만들려고 했으나 코드로는 구현을 못함. 그냥 회귀식 자체에 대입    
```

* s : 임의의 후보해  
```
double rand =Math.random();
    	long seed = System.currentTimeMillis();
    	
    	double random_x;
        random_x=range.get(0)+rand*(range.get(1)-range.get(0));
```

* T : 초기 온도  ( 온도는 일정한 비율로 감소)  
* d : 이웃해와 후보해의 차이를 계산  
d의 값이 0보다 작은 경우 이웃해가 후보해보다 더 우수한 경우 이웃해를 선택   
* p : 자유롭게 탐색할 확률  
확률 p는 T가 높을 떄 (초반) 자유롭게 탐색가능  
T가 0에 가까워지면 p도 작아서 적절한 이웃해를 선택하도록 한다.  
```
//반복이 클수록 천천히 감소
//이웃해와 후보해의 차이를 계산 
double d=neighbor_y-current_y;
//T는 큰값에서 점점 줄어들도록 설정
double t=temperature/(i+1);
// p는 자유롭게 탐색할 확률로 위에서와 같이 d값에 영향을 받도록 설정
double p=Math.exp(-d/t);
```  
* q < p  의 경우  
 랜덤으로 선택한 확률이 p보다 작을 경우에는
이웃해가 후보해보다 우수하지 않는 경우에도 해가 될 기회를 주는 것  
```
if(d<0 || rand<p) {
    	current_x=neighbor_x;
    	current_y=neighbor_y;
    }
```
* step_size :이웃해를 찾는 기준  
-randn.nextGaussian() 함수를 이용해 랜덤으로 정규 분포(평균 0, 표준편차 1)를 사용해서 지역적인 이웃만을 검색       
-정규분포 : 평균에 가까울수록 발생할 확률이 높고 평균에서 멀어질수록 발생할 확률이 적은 현상    
-확률적 측면에서 알고리즘의 진행에 따라 이웃의 랜덤 반경에 대한 기대치는 감소한다.    
-현재의 후보해의 x값에 대해 정규분포값에 step_size를 곱해 평균이 현재 지점이고 표준편차가 step_size로 정의되는 이웃해를 선정    
```
Random randn = new Random(seed);
double neighbor_x=current_x+ randn.nextGaussian()*step_size;
double neighbor_y=-0.16*neighbor_x+2.57;
```
---
## 모수 값 추정 (추정된 모수 값이 적절한지) 
***반복 수를 다르게 했을 경우***  
> iter=1000 , temperature = 1000  
![image](https://user-images.githubusercontent.com/98294597/173920727-50ef36a4-abd0-41db-ab6a-2b1fc79eed2e.png)

> iter=10000, temperature = 1000  
![image](https://user-images.githubusercontent.com/98294597/173920766-f3119b87-7302-4be3-8557-1fc3849e17f8.png)
---

***온도를 다르게 했을 경우***  
> iter = 1000, temperature = 100    
![image](https://user-images.githubusercontent.com/98294597/173920824-454cdbcb-b342-47fc-98dc-00a0057ab533.png)


> iter = 1000, temperature = 2000    
![image](https://user-images.githubusercontent.com/98294597/173920869-48abd8d4-6e8e-4f8d-88f5-464baa42430b.png)
---

***이웃해의 범위를 다르게 했을 경우***  
> step_size = 0.1  
![image](https://user-images.githubusercontent.com/98294597/173922034-e66d92a8-eb1c-4b6c-ab0e-4e2c889a4e7a.png)

> step_size = 0.01  
![image](https://user-images.githubusercontent.com/98294597/173921613-86c0efeb-7b00-491a-a4df-9885215430a8.png)

## 최적화 과정에 대한 분석  
***반복 수를 다르게 했을 경우***  
> L : iter =1000  R : iter=10000  
![image](https://user-images.githubusercontent.com/98294597/174037074-ebffdd6f-26f6-44af-99b8-a69b9124e89a.png)  

반복수가 많으면 최적화 과정에서 값이 크게 벗어나는 경향이 생긴다.  
확률적으로 10000개를 반복을 했을 때 700개가 확률적으로 뽑히기 때문에 정도를 평가하는 y값의 범위에서도 크게 벗어나기 때문에 적합한 것처럼 보이진 않는다.  

***온도를 다르게 했을 경우*** 
> L : temperature = 100  R : temperature = 2000  
![image](https://user-images.githubusercontent.com/98294597/174038719-b15589b0-4054-4798-9a16-3837025f7189.png)  

온도가 높은 경우를 보면 최적화된 값의 폭이 온도가 낮은 경우보다 크지 않다.  
온도가 낮은 경우는 온도변화가 크지 않기 때문에 높은 확률로 이웃해를 빠르게 찾기 때문에 같은 iteration을 돌더라도 더 많이 선택이 된 것을 볼 수 있다.  

***이웃해의 범위를 다르게 했을 경우***
> L : step_size = 0.1  R : step_size = 0.01
![image](https://user-images.githubusercontent.com/98294597/174039146-733b3c58-874c-4e3a-b5e5-847fc31c812e.png)  

step_size를 작게 한 경우는 최적화된 해의 폭이 크지 않은 것을 볼 수 있다.  
다른 조건이 모두 같기 때문에 이웃해를 선택하는 step_size의 영향도 최적해를 구하는데에 결정적인 변수가 될 수가 있다는 것을 알 수 있다.  

## 장단점
### 장점  
* 복잡하고 노이지 데이터나 제약이 많은 비선형 모델을 다룰 수 있다  
* 지역 탐색 방법에 대해서는 유연성이 강하고 전역최적화에 대한 접근을 가능하게 한다.
* 모델의 제한적인 특성에 의존하지 않기 때문에 다양한 방면에서 활용가능하다. 

### 단점
* 실제 알고리즘으로 바꾸기에는 많은 선택사항이 요구된다.
* 솔루션에대한 품질과 그것들을 계산하기 위한 시간에 대해 trade-off가 발생한다.  
(tradeoff : 질과 양 가운데 어느 한편을 늘리면 다른 한편은 그만큼 줄어드는 것) 
* 실험에 사용되는 수들의 정확성이 결과의 품질에 엄청난 영향을 끼칠 수 있다.  
