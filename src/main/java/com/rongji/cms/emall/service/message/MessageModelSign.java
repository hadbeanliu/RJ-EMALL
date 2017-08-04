package com.rongji.cms.emall.service.message;


/**
 * 消息模板标识
 *
 * @since 2015-7-9
 * @author lxy
 *
 */
public class MessageModelSign {

	/**【给卖家】有新订单  @param order.sellerName卖家 order.orderNo订单号 */
	public static final String BUYER_NEW_ORDER = "buyerNewOrder";
	
	/**【给卖家】买家已付款 @param order.sellerName卖家 order.buyerName买家 order.orderNo订单号 */
	public static final String BUYER_ORDER_PAID = "buyerOrderPaid";
	
	/**【给卖家】买家已确认收货 @param order.sellerName卖家 order.buyerName买家 order.orderNo订单号  */
	public static final String BUYER_ORDER_CONFIRM = "buyerOrderConfirm";
	
	/**【给卖家】买家取消订单 @param order.sellerName卖家 order.buyerName买家 order.orderNo订单号  */
	public static final String BUYER_ORDER_CANCEL = "buyerOrderCancel";
	
	/**【给卖家】买家申请退款 @param order.sellerName卖家 order.buyerName买家 order.orderNo订单号  order.reason申请原因*/
	public static final String BUYER_APPLY_REFUND = "buyerApplyRefund";
	
	/**【给买家】有新订单 @param order.buyerName买家 order.orderNo订单号  */
	public static final String SELLER_NEW_ORDER = "sellerNewOrder";
	
	/**【给买家】卖家已发货 @param order.buyerName买家 order.orderNo订单号 order.storeName商铺 */
	public static final String SELLER_ORDER_SEND = "sellerOrderSend";
	
	/**【给买家】卖家调整订单价格 @param order.buyerName买家 order.orderNo订单号 order.storeName商铺  */
	public static final String SELLER_ORDER_MODPRICE = "sellerOrderModPrice";
	
	/**【给买家】卖家取消订单 @param order.buyerName买家 order.orderNo订单号 order.storeName商铺  */
	public static final String SELLER_ORDER_CANCEL = "sellerOrderCancel";
	
	/**【给买家】卖家拒绝退款 @param order.buyerName买家 order.orderNo订单号 order.storeName商铺  */
	public static final String SELLER_REFUSE_REFUND = "sellerRefuseRefund";
	
	/**【给买家】卖家同意退款 @param order.buyerName买家 order.orderNo订单号 order.storeName商铺  */
	public static final String SELLER_AGREE_REFUND = "sellerAgreeRefund";
	
	
}