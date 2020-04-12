/**
 * @author Xiande Wen (905003)
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ServerSocketFactory;

public class DictionaryServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int port;
		String dictionaryFile;
		try {
			port = Integer.parseInt(args[0]);
			dictionaryFile = args[1];
		} catch (Exception e) {
			System.out.println("Usage: java ¨Cjar DictionaryServer.jar <port> <dictionary-file>");
			return;
		}

		try {
			ClientHandler.setupDictionaryController(dictionaryFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		DynamicBlockingQueue<Runnable> queue = new DynamicBlockingQueue<Runnable>();
		// TODO TEST
		int maxPoolSize = 1;
		ThreadPoolExecutor executor = new ThreadPoolExecutor(0, maxPoolSize, 60, TimeUnit.SECONDS, queue);
		queue.setThreadPoolExecutor(executor);

		ServerSocketFactory factory = ServerSocketFactory.getDefault();
		try (ServerSocket server = factory.createServerSocket(port)) {
			// Wait for connections.
			while (true) {
				Socket clientSocket = server.accept();
				try {
					executor.execute(new ClientHandler(clientSocket));
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
			}
		} catch (IOException e) {
			System.out.println("Error: Unable to create server socket");
			return;
		}
	}
}

/**
 * Ensure that the ThreadPoolExecutor only queues the job when the
 * maximumPoolSize is reached
 */
class DynamicBlockingQueue<E> extends LinkedBlockingQueue<E> {
	private static final long serialVersionUID = 3225200866953788056L;
	private ThreadPoolExecutor executor = null;

	public void setThreadPoolExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	@Override
	public synchronized boolean offer(E e) {
		if (this.executor != null && this.executor.getPoolSize() == this.executor.getMaximumPoolSize()) {
			return super.offer(e);
		} else {
			return false;
		}
	}
}
