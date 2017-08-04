package com.rongji.cms.emall.service.freight;

import java.util.List;

import com.rongji.cms.emall.entity.EmlFreightTemplate;
import com.rongji.cms.emall.vo.CarryMode;
import com.rongji.cms.emall.vo.FreightTemplate;
import com.rongji.cms.emall.vo.Order;
import com.rongji.rjskeleton.service.CommonService;

public interface FreightTemplateService extends CommonService<FreightTemplate, EmlFreightTemplate> {
	
	public FreightTemplate addFreightTemplate(FreightTemplate vo);
	
	public FreightTemplate updateFreightTemplate(FreightTemplate vo);
	
	public FreightTemplate getFreightTemp(String voId);
	
	
	/**
	 * 通过商家ID获取运费模版列表
	 * @param storeId 商家编号
	 * @return 运费模版列表
	 */
	public List<FreightTemplate> getFreightTemps(String storeId);
	
	//提交订单时传入订单里的件数和运费方式的ID,计算出运费以“分”为单位
	public int getFreightCarryFee(int piece, String carryId);
	
	
	/**
	 * 提交订单时传入订单和运费方式编号
	 * @param order 订单
	 * @param carryId 运费方式编号
	 * @return 计算出运费，以“分”为单位
	 */
	public int calculateFee(Order order, String carryId);
	
	/**
	 * 根据商家编号获取运送方式
	 * @param storeId 商家编号
	 * @return 运送方式列表
	 */
	public  List<CarryMode> getCarryMode(String storeId);
	
	/**
	 * 根据商家编号和地址编号获取运送方式
	 * @param storeId 商家编号
	 * @param addressId 地址编号
	 * @return 运送方式列表
	 */
	public  List<CarryMode> getCarryMode(String storeId,String addressId);
}
