package com.elex.ssp.cahe;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;



public class RemoteMemcache {

	protected static RemoteMemcache mc = new RemoteMemcache();
	
	protected MemCachedClient mcc = new MemCachedClient();
	
	 static {
		  	String[] serverlist = { "10.1.20.242:11210","10.1.20.242:11211" };
		 	Integer[] weights = { new Integer(3), new Integer(2) };
		  	int initialConnections = 10;
		 	int minSpareConnections = 5;
		 	int maxSpareConnections = 50;
		  	int maxIdleTime = 1000 * 60 * 30; // 30 minutes
		 	long maxBusyTime = 1000 * 60 * 5; // 5 minutes
		  	long maintThreadSleep = 1000 * 5; // 5 seconds
		  	int socketTimeOut = 1000 * 3; // 3 seconds to block on reads
		 	int socketConnectTO = 1000 * 3; // 3 seconds to block on initial
		  									// connections. If 0, then will use blocking
		  									// connect (default)
		  	boolean failover = false; // turn off auto-failover in event of server down
		  	boolean nagleAlg = false; // turn off Nagle's algorithm on all sockets in
		 								// pool
		  	boolean aliveCheck = false; // disable health check of socket on checkout
		  	SockIOPool pool = SockIOPool.getInstance();
		  	pool.setServers(serverlist);
		  	pool.setWeights(weights);
		  	pool.setInitConn(initialConnections);
		  	pool.setMinConn(minSpareConnections);
		  	pool.setMaxConn(maxSpareConnections);
		  	pool.setMaxIdle(maxIdleTime);
		  	pool.setMaxBusyTime(maxBusyTime);
		  	pool.setMaintSleep(maintThreadSleep);
		  	pool.setSocketTO(socketTimeOut);
		  	pool.setNagle(nagleAlg);
		  	pool.setHashingAlg(SockIOPool.NEW_COMPAT_HASH);
		  	pool.setAliveCheck(true);
		  	pool.initialize();
		  }
	
	//保护构造函数，不允许实例化
	protected RemoteMemcache(){}
	
	public static RemoteMemcache getInstance(){
		return mc;
	}
	
	public MemCachedClient getClient(){
		return mcc;
	}
}

