package multiThreading;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultipleCallableRunner {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		
		List<CallableTask> tasks= List.of(new CallableTask("leonidas"), new CallableTask("luigy"), new CallableTask("freitas"));
		
		List<Future<String>> results = executorService.invokeAll(tasks);
		
		for(Future<String> result : results) {
			System.out.println(result.get());
		}
		executorService.shutdown();
	}

}
