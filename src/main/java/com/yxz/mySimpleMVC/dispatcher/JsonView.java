package com.yxz.mySimpleMVC.dispatcher;

/**
 * @author Yu
 * json视图
 */
public class JsonView implements View {

	private Object jsonObject;

	public JsonView(Object jsonObject) {
		this.jsonObject = jsonObject;
	}

	public Object getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(Object jsonObject) {
		this.jsonObject = jsonObject;
	}
	
}
