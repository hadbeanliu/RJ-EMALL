package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

/**
 * 订单商品表
 * 
 * @author rjf
 *
 */
@Entity
@Table(name= TablePrefix.NAME + "ORDER_GOODS")
public class EmlOrderGoods implements Sid {
	
	@Id
	@Column
	private String id;
	
	@ManyToOne  @JoinColumn(name="ORDER_ID")
	private EmlOrder order;
	
	
	/**
	 * 不使用对象的原因是：需要记录在下单时商品的信息
	 */
	@Column(nullable=false, length=32)
	@Comment("商品ID")
	private String goodsId;
	
	
	@Column(nullable=false, length=100)
	@Comment("商品名称")
	private String goodsTitle;
	
	@Column(nullable=false, length=100)
	@Comment("商品链接")
	private String goodsUrl;
	
	@Column(length=100)
	@Comment("商品图片")
	private String goodsImage;
	
	@Column(length=500)
	@Comment("商品描述")
	private String goodsDesc;
	
	
	@Column(nullable=false, length=50)
	@Comment("商品单价")
	private Long goodsPrice;
	
	
	@Column(nullable=false, length=50)
	@Comment("购买价格")
	private Long purchasePrice;
	
	
	@Column(nullable=false)
	@Comment("购买数量")
	private Integer purchaseQuantity;
	
	
//	@Column(nullable=false)
//	@Comment("创建人员")
//	private String createUser;
	
	
	@Column(nullable=false)
	@Comment("创建时间")
	private Date createTime;
	
	@Column
	@Comment("修改时间")
	private Date modifyTime;
	
	@Column
	@Comment("点评ID")
	private String rateId;
	
	@Column
	@Comment("点评时间")
	private Date rateTime;
	
	
	@Column
	@Comment("折扣金额")
	private Long discount;
	
	@Column
	@Comment("折扣描述")
	private String discountTitle;
	
	@Column
	@Comment("购买商品的规格")
	private String skuId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EmlOrder getOrder() {
		return order;
	}

	public void setOrder(EmlOrder order) {
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

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}


	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
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

//	public String getCreateUser() {
//		return createUser;
//	}
//
//	public void setCreateUser(String createUser) {
//		this.createUser = createUser;
//	}

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

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
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
		EmlOrderGoods other = (EmlOrderGoods) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
