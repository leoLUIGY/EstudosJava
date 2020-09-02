package multiThreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task extends Thread{
	private int number;
	public Task(int number) {
		this.number = number;
	}
	
	public void run() {
		System.out.print("\ntask "+number+" started");
		for(int i = number*100; i<= number*100 + 99; i++) {
			System.out.print(i);
		}
		System.out.print("\ntask done");
	}
}




public class ExecutorServiceRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ExecutorService executorService = Executors.newSingleThreadExecutor();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Task(1));
		executorService.execute(new Task(2));
		executorService.execute(new Task(3));
		
		executorService.shutdown();
	}

}
