/**
 * @author Xiande Wen (905003)
 */
package server;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ServerSocketFactory;

public class DictionaryServer {

	private ServerSocket serverSocket;
	private ServerGUI window;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DictionaryServer();
	}

	public DictionaryServer() {
		// Setup thread pool
		DynamicBlockingQueue<Runnable> queue = new DynamicBlockingQueue<Runnable>();
		// TODO TEST
		int maxPoolSize = 1;
		ThreadPoolExecutor executor = new ThreadPoolExecutor(0, maxPoolSize, 60, TimeUnit.SECONDS, queue);
		queue.setThreadPoolExecutor(executor);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ServerGUI();
					window.btnStartServer.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							ServerSocketFactory factory = ServerSocketFactory.getDefault();
							try {
								serverSocket = factory.createServerSocket(
										Integer.parseInt(window.textFieldEnterPortNumber.getText()));
								ClientHandler.setupDictionaryController(window.selectedDictionaryFilePath);
							} catch (NumberFormatException e) {
								window.showMessageDialog("Port number must be numerical");
								return;
							} catch (IOException e) {
								window.showMessageDialog("Error: Unable to create server socket");
								return;
							} catch (Exception e) {
								window.showMessageDialog(e.getMessage());
								return;
							}

							window.showMessageDialog("Server started! You can stop the server by closing the window");
							window.btnStartServer.setEnabled(false);
							window.textFieldEnterPortNumber.setEnabled(false);
							window.btnSelectDictFile.setEnabled(false);

							// Create new thread to prevent the GUI from being blocked by the while loop
							new Thread(() -> {
								// listen for client connection
								while (true) {
									Socket clientSocket = null;

									try {
										clientSocket = serverSocket.accept();
									} catch (IOException e) {
										// e.printStackTrace();
										return;
									}

									executor.execute(new ClientHandler(clientSocket));

								}
							}).start();
						}
					});
					window.launch();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					ClientHandler.closeAllClientSockets();
					serverSocket.close();
					System.out.println("Server stopped");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
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
