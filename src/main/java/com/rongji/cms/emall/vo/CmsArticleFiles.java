package com.rongji.cms.emall.vo;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.rongji.dfish.cache.services.Module;

/**
 * 行为计数json数据
 * 
 * @author zouhaoning
 * 
 */
public class CmsArticleFiles extends Module {

	@Expose	private String wuFileId;
	@Expose	private String raw;
	@Expose	private String fileId;
	@Expose	private String name;
	@Expose	private String thumbnail_url;
	@Expose	private String type;
	@Expose	private String description;

	@Expose	private String url;
	@Expose	private Date uploadTime;
	@Expose	private String address;

	@Expose	private String tags;
	@Expose	private String friend;
	@Expose	private String model;//器材

	@Expose	private String focalLength;//焦距
	@Expose	private String shutterSpeedValue; //快门
	@Expose	private String apertureValue;//光圈
	
	@Expose	private String make;//品牌	
	@Expose	private String	exposureBiasValue ; //曝光补偿
	@Expose	private String isoSpeedRatings ; //ISO
	@Expose private String lensModel;     //镜头
	@Expose private String lat;//纬度
	@Expose private String lng;//经度

	
	public String getLensModel() {
		return lensModel;
	}
	public void setLensModel(String lensModel) {
		this.lensModel = lensModel;
	}
	public String getWuFileId() {
		return wuFileId;
	}
	public void setWuFileId(String wuFileId) {
		this.wuFileId = wuFileId;
	}
	public String getRaw() {
		return raw;
	}
	public void setRaw(String raw) {
		this.raw = raw;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getThumbnail_url() {
		return thumbnail_url;
	}
	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getFriend() {
		return friend;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getFocalLength() {
		return focalLength;
	}
	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
	}
	public String getShutterSpeedValue() {
		return shutterSpeedValue;
	}
	public void setShutterSpeedValue(String shutterSpeedValue) {
		this.shutterSpeedValue = shutterSpeedValue;
	}
	public String getApertureValue() {
		return apertureValue;
	}
	public void setApertureValue(String apertureValue) {
		this.apertureValue = apertureValue;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getExposureBiasValue() {
		return exposureBiasValue;
	}
	public void setExposureBiasValue(String exposureBiasValue) {
		this.exposureBiasValue = exposureBiasValue;
	}
	public String getIsoSpeedRatings() {
		return isoSpeedRatings;
	}
	public void setIsoSpeedRatings(String isoSpeedRatings) {
		this.isoSpeedRatings = isoSpeedRatings;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}	
}
