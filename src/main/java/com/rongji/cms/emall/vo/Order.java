package com.rongji.cms.emall.vo;

import java.util.Date;
import java.util.List;

import com.rongji.cms.emall.support.util.MoneyUtil;
import com.rongji.rjskeleton.support.id.Sid;

public class Order implements Sid {
	
	private String id;
	
	
	private String orderNo;
	
	
	private Long price;
	
	private String remark;
	
//	private PayServiceProvider payServiceProvider;
	
	private String payCode;
	
	
	private Logistics logistics;
	private Long logisticsFee;
	
	
	private String status;
	
	
	private Store store;
	
	
	private String createUser;
	
	private Date createTime;
	
	private Date modifyTime;
	
	private Date dealTime;
	
	private Date paymentTime;
	
	private Date rateTime;
	
	private String confirmUser;
	
	private Date storeConfirmTime;
	
	private Date doneTime;
	
	
	private Address address;
	private String tradeStatus;
	private String tradeNo;
	
	private List<OrderGoods> goods;
	private User user;
	private String statusDesc;
	
	private String preOrderId; // 提交订单前的信息
	private String carryId; // 配送方式
	
	private boolean inCart; // 是否从购物车过来的订单
	private Refund refund; // 退款信息
	
	public Order(){}
	public Order(String id) {
		this.id = id;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo == null ? id : orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getPrice() {
		return price == null ? 0 : price;
	}
	
	/**
	 * 返回格式{@literal 105.01}
	 * @return
	 */
	public String getPriceString() {
		return getMoneyString(price);
//		return String.valueOf(price / 100.0);
	}
	
	/**
	 * 获取订单总金额 {@literal 105.01}格式
	 * <p>
	 * 包含运费
	 * 
	 * @return
	 */
	public String getPriceAndFee() {
		return getMoneyString(getPrice() + getLogisticsFee());
	}
	
	public void setPrice(Long price) {
		this.price = price;
	}

//	public PayServiceProvider getPayServiceProvider() {
//		return payServiceProvider;
//	}
//
//	public void setPayServiceProvider(PayServiceProvider payServiceProvider) {
//		this.payServiceProvider = payServiceProvider;
//	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public Logistics getLogistics() {
		return logistics;
	}

	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Store getStore() {
		return store;
	}
	
	public void setStore(Store store) {
		this.store = store;
	}
	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public Date getStoreConfirmTime() {
		return storeConfirmTime;
	}

	public void setStoreConfirmTime(Date storeConfirmTime) {
		this.storeConfirmTime = storeConfirmTime;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<OrderGoods> getGoods() {
		return goods;
	}
	public void setGoods(List<OrderGoods> goods) {
		this.goods = goods;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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
	public Long getLogisticsFee() {
		return logisticsFee == null ? 0 : logisticsFee;
	}
	public Date getRateTime() {
		return rateTime;
	}
	public void setRateTime(Date rateTime) {
		this.rateTime = rateTime;
	}
	public String getLogisticsFeeString() {
		return getMoneyString(getLogisticsFee());
	}
	public void setLogisticsFee(Long logisticsFee) {
		this.logisticsFee = logisticsFee;
	}
	public boolean isInCart() {
		return inCart;
	}
	public void setInCart(boolean inCart) {
		this.inCart = inCart;
	}
	public Refund getRefund() {
		return refund;
	}
	public void setRefund(Refund refund) {
		this.refund = refund;
	}
	public String getOrderTitle() {
		if (getGoods() == null) {
			return null;
		}
		String t = getGoods().get(0).getGoodsTitle();
		if ( getGoods().size() > 1 ) {
			t += " 等多件";
		}
		
		return t == null || t.trim().isEmpty() ? "" : t;
	}
	
	public String getOrderBody() {
		if (getGoods() == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if ( getGoods().size() > 1 ) {
			for (OrderGoods goods : getGoods()) {
				sb.append(goods.getGoodsTitle()).append(" ").append(goods.getGoodsUrl());
			}
			
		} else {
			OrderGoods goods = getGoods().get(0);
			String desc = goods.getGoodsDesc() == null ? goods.getGoodsTitle() : goods.getGoodsDesc();
			sb.append(desc == null ? "" : desc);
		}
		
		return sb.length() == 0 ? "" : sb.toString();
	}
	
	public String getOrderUrl() {
		if (getGoods() == null) {
			return null;
		}
		if ( getGoods().size() > 1 ) {
			return "";
		}
		OrderGoods goods = getGoods().get(0);
		return goods.getGoodsUrl() == null || goods.getGoodsUrl().trim().isEmpty() ? "" : goods.getGoodsUrl();
	}

	/**
	 * 返回科学计数的金额格式
	 * 
	 * @param money 具体金额
	 * @return 返回格式{@literal 105.01}
	 */
	public static String getMoneyString(Long money) {
		if (money == null) {
			return "0.00";
		}
		return MoneyUtil.longToString(money);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
