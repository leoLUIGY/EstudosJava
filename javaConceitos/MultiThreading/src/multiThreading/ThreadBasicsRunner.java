package multiThreading;

class Task1 extends Thread{
	public void run() {
		System.out.print("\ntask started");
		for(int i = 101; i<= 199; i++) {
			System.out.print(i);
		}
		System.out.print("\ntask done");
	}
}

class Task2 implements Runnable{
	public void run() {
		System.out.print("\ntask2 started");
		for(int i = 201; i<= 299; i++) {
			System.out.print(i);
		}
		System.out.print("\ntask2 done");
	}
}
public class ThreadBasicsRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Task1 task1 = new Task1();
		task1.setPriority(10);
		task1.start();
		
		Task2 task2 = new Task2();
		Thread task2Thread = new Thread(task2);
		task2Thread.start();
		
		try {
			task1.join();
			task2Thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 301; i<= 399; i++) {
			System.out.print(i);
		}
		System.out.print("\ntask done");


	}

}
