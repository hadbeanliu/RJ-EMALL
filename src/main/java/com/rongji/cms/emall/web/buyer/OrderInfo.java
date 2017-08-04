package com.rongji.cms.emall.web.buyer;

public class OrderInfo {
	
	private String addressId; // 收货地址
	private String remark; // 订单备注
	private String invoice; // 发票信息
	private String preOrderId; // 预下单ID
	private String carryId; // 配送方式
	private String cartId; // 购物车中记录ID
	
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getPreOrderId() {
		return preOrderId;
	}
	public void setPreOrderId(String preOrderId) {
		this.preOrderId = preOrderId;
	}
	public String getCarryId() {
		return carryId;
	}
	public void setCarryId(String carryId) {
		this.carryId = carryId;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((preOrderId == null) ? 0 : preOrderId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderInfo other = (OrderInfo) obj;
		if (preOrderId == null) {
			if (other.preOrderId != null)
				return false;
		} else if (!preOrderId.equals(other.preOrderId))
			return false;
		return true;
	}

}
