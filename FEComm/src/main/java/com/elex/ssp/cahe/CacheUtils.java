package com.elex.ssp.cahe;

public class CacheUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testLocal();
		//testRemote();
	}

	public static void testLocal() {
		LocalMemcache.getInstance().getClient().set("raw", "raw");
		System.out.println(LocalMemcache.getInstance().getClient().get("raw"));
	}

	public static void testRemote() {
		//RemoteMemcache.getInstance().getClient().set("raw", "raw");
		System.out.println(RemoteMemcache.getInstance().getClient().get("raw"));
	}
}
