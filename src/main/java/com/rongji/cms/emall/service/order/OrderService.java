package com.rongji.cms.emall.service.order;

import java.util.List;
import java.util.Map;

import com.rongji.cms.emall.entity.EmlOrder;
import com.rongji.cms.emall.vo.Logistics;
import com.rongji.cms.emall.vo.Order;
import com.rongji.cms.emall.vo.OrderGoods;
import com.rongji.rjskeleton.service.CommonService;
import com.rongji.rjskeleton.web.controller.datatables.PageEntity;
import com.rongji.rjskeleton.web.controller.datatables.request.DatatablesMetadata;

/**
 * 订单业务逻辑
 *
 * @since 2015-6-3
 * @author rjf
 *
 */
public interface OrderService extends CommonService<Order, EmlOrder> {
	
	/**
	 * 根据订单编号获取订单带条件过滤查询分页
	 * 
	 * @param orderNo 订单编号 非空
	 * @param metadata 分页查询信息 非空
	 * @return 如果orderNo或者metadata为{@literal null}，则返回null
	 */
	public PageEntity<Order> getByDatatablesByUser(String userId, DatatablesMetadata metadata);
	
	/**
	 * @param storeId 商店ID 非空
	 * @param metadata 分页查询信息 非空
	 * @return 如果orderNo或者metadata为{@literal null}，则返回null
	 */
	public PageEntity<Order> getByDatatablesByStore(String storeId, DatatablesMetadata metadata);
	
	/**
	 * 保存订单为预订单，既生成订单信息等待用户确认订单。
	 * <p>
	 * 预订单存在15分钟的时效性，失效后用户需重新下单
	 * 
	 * @param order 订单
	 * @return 预订单Id
	 */
	public String savePreOrder(Order order);
	
	/**
	 * 获取预订单
	 * <p>
	 * 预订单存在15分钟的时效性，失效后用户需重新下单
	 * 
	 * @param preOrderId 预订单
	 * @return 不存在或者超过15分钟则返回{@literal null}
	 */
	public Order getPreOrder(String preOrderId);
	
	/**
	 * 创建从商品信息中订单信息
	 * 
	 * @param userId 创建订单人员
	 * @param ogds 商品信息
	 * @return 返回订单列表
	 * @throws EmallException 用户不存在、订单商品信息不合法时
	 */
	public List<Order> createPreOrders(String userId, OrderGoods... ogds);
	
	/**
	 * 添加订单，用户待支付
	 * 
	 * @param userId 下单用户
	 * @param goods 选择的商品，需是同一商家的商品
	 * @return 订单
	 */
	public Order addOrder(String userId, List<OrderGoods> goods);
	
	/**
	 * 添加订单，用户待支付
	 * 
	 * @param userId 下单用户
	 * @param order 订单信息
	 * @return 订单
	 */
	public Order addOrder(String userId, Order order);
	
	/**
	 * 根据订单ID获取订单信息，包含商品等附加信息
	 * 
	 * @param orderId 订单ID
	 * @return 订单
	 */
	public Order getOrder(String orderId);
	
	/**
	 * 根据订单编号（对外友好的）获取订单信息，包含商品等附加信息
	 * 
	 * @param orderNo 订单编号
	 * @return 订单
	 */
	public Order getOrderByNo(String orderNo);
	
	/**
	 * 根据订单的支付编号（对应具体的支付平台编号）获取订单信息，包含商品等附加信息
	 * 
	 * @param tradeNo 支付编号
	 * @return 订单
	 */
	public Order getOrderByTradeNo(String tradeNo);
	
//	/**
//	 * 生成支付信息，提供出可用的支付方式
//	 * <p>
//	 * <code>userId</code>和下单<code>orderNo</code>需为同一人
//	 * 
//	 * @param userId 下单用户
//	 * @param orderNo 订单编号
//	 * @return 支付代码
//	 */
//	public String showPay(String userId, String orderNo);
	
//	/**
//	 * 进行支付操作
//	 * <p>
//	 * <code>userId</code>和下单<code>orderNo</code>需为同一人
//	 * 
//	 * @param userId 下单用户
//	 * @param orderNo 订单编号
//	 * @return 如果为{@literal null}则表示支付成功，否则为出错信息
//	 */
//	public String doPay(String userId, String orderNo);
	
	/**
	 * 发货
	 * <p>
	 * 
	 * @param userId 发货操作人员
	 * @param orderId 订单ID
	 * @param logistics 物流信息
	 */
	public void sendGoods(String userId, String orderId, Logistics logistics);
	
	/**
	 * 确认收货
	 * <p>
	 * <code>userId</code>和下单<code>orderId</code>需为同一人
	 * 
	 * @param userId
	 * @param orderId
	 */
	public void confirmGoods(String userId, String orderId);
	
	/**
	 * 进行店铺商品评论
	 * 
	 * @param userId 评论用户
	 * @param orderNo 订单编号
	 * @param commentId 订单评论ID, 关联外部
	 * @param goodsCommentId 商品评论ID集合, 关联外部
	 */
	public void doComment(String userId, String orderNo, String commentId, Map<String, String> goodsCommentId);

	/**
	 * 卖家修改订单价格
	 * 
	 * @param orderId 订单 
	 * @param cutPrice 减价优惠
	 * @param logisticsFee 运费调整
	 */
	public void editPrice(String userId, String orderId, long cutPrice, long logisticsFee);
	
	/**
	 * 订单退款成功
	 * 
	 * @param refundId 退款ID
	 * @param money 金额
	 */
	public void refundSuccess(String refundId, long momey);
}
