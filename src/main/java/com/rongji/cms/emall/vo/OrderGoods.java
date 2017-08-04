package com.rongji.cms.emall.vo;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.rongji.cms.emall.support.util.MoneyUtil;
import com.rongji.rjskeleton.support.id.Sid;

/**
 * 订单商品表
 * 
 * @author rjf
 *
 */
public class OrderGoods implements Sid {
	
	private String id;
	
	private Order order;
	
	
	/**
	 * 不使用对象的原因是：需要记录在下单时商品的信息
	 */
	@NotBlank
	private String goodsId;
	
	@NotBlank
	private String goodsTitle;
	
	@NotBlank
	private String goodsUrl;
	
	@NotBlank
	private Long goodsPrice;
	
	private String goodsImage;
	private String goodsDesc;
	
	
	private Long purchasePrice;
	
	
	private Integer purchaseQuantity;
	
	private Long discount; // 折扣金额
	private String discountTitle; // 折扣描述
	private String sku; // 购买规格
	private String skuId;
	
	
	private String createUser;
	
	
	private Date createTime;
	
	
	private Date modifyTime;
	
	
	private String rateId;
	
	
	private Date rateTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public Long getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Long goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Long getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Long purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Integer getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(Integer purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
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
	
	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public Date getRateTime() {
		return rateTime;
	}

	public void setRateTime(Date rateTime) {
		this.rateTime = rateTime;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public String getDiscountTitle() {
		return discountTitle;
	}

	public void setDiscountTitle(String discountTitle) {
		this.discountTitle = discountTitle;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	
	public String getGoodsPriceString() {
		return MoneyUtil.longToString(getGoodsPrice());
	}
	public String getPurchasePriceString() {
		return MoneyUtil.longToString(getPurchasePrice());
	}
	
	public String getTotalPrice() {
		return MoneyUtil.longToString(purchasePrice * purchaseQuantity);
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
		OrderGoods other = (OrderGoods) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
