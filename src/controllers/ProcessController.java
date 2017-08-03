package controllers;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.HttpClient;

public class ProcessController extends MainController{
	private int backendServerPort = 8888;
	
	public void defaultMethod() {
		JSONObject finalResponse = new JSONObject();
		try{
			String socketName = (String) request.get("socketName");
			
			if(!request.get("uri").toString().contains(socketName)){
				setNotFoundResponse();
				return;
			}
			JSONObject parameters = request.getJSONObject("parameters");
			Long listenningPort = Long.valueOf(request.get("listeningPort").toString().substring(2));
			Thread.currentThread().sleep(Long.valueOf(listenningPort*listenningPort));
			finalResponse.put("delay", listenningPort*listenningPort);
			//-------------------------------------------------------------------
			//Seteo de respuesta
			System.out.println("Server: " + socketName + " - Response: " + finalResponse);
			setResponseBody(finalResponse);
			setResponseStatus(200);
			setResponseHeader("content-type", "application/json");
		}
		catch(Exception e){
			System.out.println("Exception " + e);
		}
	}
	
	public void index() {
		
		JSONObject finalResponse = new JSONObject();
		
		try{
			JSONObject parameters = request.getJSONObject("parameters");
			Long listenningPort = Long.valueOf(request.get("listeningPort").toString().substring(2));
			Thread.currentThread().sleep(Long.valueOf(listenningPort*listenningPort));
			finalResponse.put("delay", listenningPort*listenningPort);
			//-------------------------------------------------------------------
			//Seteo de respuesta
			setResponseBody(finalResponse);
			setResponseStatus(200);
			setResponseHeader("content-type", "application/json");
		}
		catch(Exception e){
			System.out.println("Exception " + e);
		}
		
	}
	
	private void setNotFoundResponse(){
		setResponseBody("{'message':'not found'}");
		setResponseStatus(404);
		setResponseHeader("content-type", "application/json");
		return;
	}
	
	private void setServiceFailedResponse(String message){
		setResponseBody("{'message':'" + message + "'}");
		setResponseStatus(500);
		setResponseHeader("content-type", "application/json");
		return;
	}
}
