package com.rongji.cms.emall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name= TablePrefix.NAME + "MY_BOND")
public class EmlMyBond implements Sid {
	
	@Id
	@Column(name="BOND_ID")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Comment("商品ID")
	private String goodsId;
	
	@Comment("用户ID")
	private String userId;
	
	@Comment("缴纳时间")
	private String submitTime;	
	
	@Comment("保证金金额")
	private float bondMonery;
	
	@Comment("释放时间")
    private Date releaseTime;
    
	@Comment("转移时间")
    private Date transferTime;
    
	@Comment("订单ID")
    private String orderId;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public float getBondMonery() {
		return bondMonery;
	}

	public void setBondMonery(float bondMonery) {
		this.bondMonery = bondMonery;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
