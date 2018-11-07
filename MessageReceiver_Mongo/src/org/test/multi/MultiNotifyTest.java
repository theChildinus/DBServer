package org.test.multi;

import wsn.wsnclient.command.SendWSNCommand;

public class MultiNotifyTest extends Thread {

	public int counter = 0;
	private static final String WEBSERVICE_ADDR = "http://10.108.166.191:9000/INotificationProcess";
	private static final String WSN_ADDR = "http://10.108.166.191:8192";

	public MultiNotifyTest() {
		setDaemon(true);
		start();
	}

	public MultiNotifyTest(int counter) {
		this.counter = counter;
		setDaemon(true);
		start();
	}

	public void run() {
		while (true) {
			try {
				SendWSNCommand command = new SendWSNCommand(WEBSERVICE_ADDR, WSN_ADDR);
				String content = "<wenpeng xmlns=\"\" xmlns:ns6=\"http://docs.oasis-open.org/wsn/b-2\">";
				content = content.concat("并发消息##########1");
				content = content.concat("</wenpeng>");
				System.out.println(content);
				command.notify("TEST1", content);
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
