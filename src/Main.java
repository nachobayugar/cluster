import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sockets.HttpSocket;
import sockets.HttpSocketManager;


public class Main {

	public static void main(String[] args) {
		logger.SystemLogger.consoleLogging = false;
		int initialPort = 9000;
		int maxThreadsPerRequest = 10;
		Map<String, Integer> clusters = new LinkedHashMap<String, Integer>();
		//args = new String[1];
		//args[0] = "clusters=api:90,frontEnd:3,static:30";
		System.out.println("Starting servers clusters");
		for(int i=0; i<args.length; i++){
			if(args[i].indexOf("=")>0){
				String key = args[i].split("=")[0];
				String value = args[i].split("=")[1];
				if("clusters".equalsIgnoreCase(key)){
					String[] clusterNames = value.split(",");
					for(int c=0; c<clusterNames.length; c++){
						String[] nameAndSize = clusterNames[c].split(":");
						String name = nameAndSize[0];
						Integer size = Integer.valueOf(nameAndSize[1]);
						clusters.put(name, size);
					}
				}
				if("maxThreadsPerRequest".equalsIgnoreCase(key)){
					maxThreadsPerRequest = Integer.valueOf(value);
				}
				if("initialPort".equalsIgnoreCase(key)){
					initialPort = Integer.valueOf(value);
				}
			}
		}
		
		Iterator<Map.Entry<String, Integer>> it = clusters.entrySet().iterator();
		int portIncrement = 0;
	    while (it.hasNext()) {
	        Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
	        String clusterName = (String) pair.getKey();
	        Integer clusterSize = pair.getValue();
	        if(clusterSize>30){
	        	clusterSize=30;
	        }
	        int initialServerPort = initialPort + 100*portIncrement;
	        for(int i=0; i<clusterSize; i++){
				HttpSocket apiSocket = new HttpSocket(initialServerPort, clusterName);
				HttpSocketManager.init();
				HttpSocketManager.setMaxThreads(maxThreadsPerRequest);
				HttpSocketManager.executor.execute(apiSocket);
				System.out.println(clusterName + " server listenning on port: " + initialServerPort + ". Please check it sending a request to http://localhost:" + initialServerPort + "/ping");
				initialServerPort ++;
			}
	        portIncrement += 1;
	    }
	}

}
