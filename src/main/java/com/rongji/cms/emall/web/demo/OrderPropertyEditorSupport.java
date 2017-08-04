package com.rongji.cms.emall.web.demo;

import java.beans.PropertyEditorSupport;

import com.rongji.cms.emall.vo.Order;

public class OrderPropertyEditorSupport extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		Order order = (Order) getValue();
		if (order != null) {
			return order.getId();
		}
		return "";
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		super.setValue(new Order(text));
	}
	
}
