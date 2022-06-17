import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SA_1 {

	//SA기법 함수
	
    public static double[] simulated_annealing(ArrayList<Integer> range, int iteration,double step_size, int temperature ) {
    	//1. 임의의 후보해 s설정
    	//s: 입력값x의 범위를 0~1사이의 난수를 곱해서 랜덤으로 후보해를 선정
    	double rand =Math.random();
    	long seed = System.currentTimeMillis();
    	
    	double random_x;
        random_x=range.get(0)+rand*(range.get(1)-range.get(0));
    	
    	//회귀 분석을 통해 얻은 식 y=-0.16x +2.57에 후보해를 대입
    	//후보해의 x값에 대한 y의 해를 찾는다
    	double random_y = -0.16*random_x+2.57;

    	double current_x=random_x;
    	double current_y=random_y;
    	ArrayList<Double> scores=new ArrayList<>(); 
    	//repeat
    	for(int i=0;i<iteration;i++) {
//    		임의의 후보해의 이웃해 중에서 랜덤하게 하나의 해 선택
//    		이때 이웃해를 찾는 기준은 
//    		randn.Gaussian 정규 분포(평균 0, 표준편차 1)를 사용해서 지역적인 이웃만을 검색한다
//    		정규분포 : 평균에 가까울수록 발생할 확률이 높고 평균에서 멀어질수록 발생할 확률이 적은 현상
//    		확률적 측면에서 알고리즘의 진행에 따라 이웃의 랜덤 반경에 대한 기대치는 감소한다.
//    		현재의 후보해의 x값에 대해 정규분포값에 step_size를 곱해
//    		 평균이 현재 지점이고 표준편차가 step_size로 정의되는 이웃해를 선정
    		Random randn = new Random(seed);
    		double neighbor_x=current_x+ randn.nextGaussian()*step_size;
    		double neighbor_y=-0.16*neighbor_x+2.57;
    		
    		//원래의 후보해와 정규분포를 이용해 얻은 이웃해 중 좋은 것을 선택
    		
    		if (neighbor_y<current_y) {//이웃해가 우수한 해이면 업데이트 
    			
    			random_x=neighbor_x;
    			random_y=neighbor_y;
    			scores.add(random_y);
    			System.out.printf("iter: %d --f(%.4f) = %.4f", i, random_x, random_y);
    			System.out.println();
    		}
    		
    		//이웃해와 후보해의 차이를 계산 
    		double d=neighbor_y-current_y;
    		//T는 큰값에서 점점 줄어들도록 설정
    		double t=temperature/(i+1);
    		// p는 자유롭게 탐색할 확률로 위에서와 같이 d값에 영향을 받도록 설정
    		double p=Math.exp(-d/t);
    		
    		//d의 값이 0보다 작고 랜덤으로 선택한 확률이 p보다 작을 경우에는
    		//이웃해가 후보해보다 우수하지 않는 경우에도 해가 될 기회를 주는 것
    		
    		if(d<0 || rand<p) {
    			current_x=neighbor_x;
    			current_y=neighbor_y;
    		}
    	}
    	//업데이트를 모두 마친 이웃해에 대한 x,y값을 반환
    	double[] arr=new double[2];
    	arr[0]=random_x;
    	arr[1]=random_y;
		return arr;
    }
