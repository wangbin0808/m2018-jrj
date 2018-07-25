package com.jrj.pay.pc.component;

import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @description pc端A股头条-付费版 - httpClient线程组件
 * @author bin.wang
 * @date 2018.06.20
 *
 */
@Component
public class IdleConnectionEvictor extends Thread {

	@Autowired
	private HttpClientConnectionManager connMgr;

	private volatile boolean shutdown;

	public IdleConnectionEvictor() {
		super();
		super.start();
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// 关闭失效的连接
					connMgr.closeExpiredConnections();
				}
			}
		} catch (InterruptedException ex) {
			// 结束
		}
	}

	// 关闭清理无效连接的线程
	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}