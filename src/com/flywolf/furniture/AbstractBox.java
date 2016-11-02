package com.flywolf.furniture;

import java.util.ArrayList;
import java.util.List;

import com.flywolf.furniture.CommonStatic.BoxesType;
import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public abstract class AbstractBox implements Box {

	List<SettingsType> settingsTypeList = new ArrayList<SettingsType>();
	ArrayList<Detail> details = new ArrayList<Detail>();
	DetailSummary detailSummary;

	public ArrayList<Detail> getDetails() {
		return details;
	}

	public void createDetailSummary(DbWorker dbWorker) {
		detailSummary = new DetailSummary();
		detailSummary.x = dbWorker.x;
		detailSummary.y = dbWorker.y;
		detailSummary.z = dbWorker.z;
		detailSummary.name = dbWorker.name;
		detailSummary.type = dbWorker.type;
		detailSummary.z = dbWorker.z;
		detailSummary.quantity = dbWorker.quantity;
		detailSummary.shelfQuantity = dbWorker.shelfQuantity;
		detailSummary.boxSettings = dbWorker.boxSettings;
		detailSummary.edgeLong=getEdgeLong() * dbWorker.quantity;
		detailSummary.edgeCost=getMCost(getEdgeLong()
				* dbWorker.quantity,
				dbWorker.boxSettings
						.getSettings(SettingsType.EDGE_COST));
		detailSummary.dspSquare = getSpace("dsp") * dbWorker.quantity;
		detailSummary.dspSquare = getSpace("dsp") * dbWorker.quantity;
		detailSummary.dspCost = getM2Cost(getSpace("dsp") * dbWorker.quantity,
				dbWorker.boxSettings.getSettings(SettingsType.DSP_COST));
		detailSummary.furnitureCost = getCost(dbWorker.boxSettings
				.getSettings(SettingsType.FURNITURE_COST));
		
		detailSummary.workCost=dbWorker.boxSettings
				.getSettings(SettingsType.HOURS_MAKE)
				* getCost(dbWorker.boxSettings
						.getSettings(SettingsType.HOUR_COST));
		detailSummary.rentCost=getCost(dbWorker.boxSettings
				.getSettings(SettingsType.RENT_COST));
		detailSummary.dvpSquare=getSpace("dvp")
		* dbWorker.quantity;
		detailSummary.dvpCost=getM2Cost(getSpace("dvp")
				* dbWorker.quantity,
				dbWorker.boxSettings
						.getSettings(SettingsType.REAR_COST));
		detailSummary.totalCost=getMCost(getEdgeLong() * dbWorker.quantity,
				dbWorker.boxSettings.getSettings(SettingsType.EDGE_COST))
				+ getM2Cost(getSpace("dvp") * dbWorker.quantity,
						dbWorker.boxSettings
								.getSettings(SettingsType.REAR_COST))
				+ getM2Cost(getSpace("dsp") * dbWorker.quantity,
						dbWorker.boxSettings.getSettings(SettingsType.DSP_COST))
				+ getCost(dbWorker.boxSettings
						.getSettings(SettingsType.FURNITURE_COST))
				+ getCost(dbWorker.boxSettings
						.getSettings(SettingsType.RENT_COST))
				+ (dbWorker.boxSettings.getSettings(SettingsType.HOURS_MAKE) * getCost(dbWorker.boxSettings
						.getSettings(SettingsType.HOUR_COST)));
		
		// int dspSquare;
		// Double dspCost;
		//int dvpSquare;
		//Double dvpCost;
		// Double furnitureCost;
		//Double workCost;
		//Double rentCost;
		//Double totalCost;

	}

	public static double getMCost(int inv, int cost) {
		return (Double.valueOf(inv) / 1000) * (Double.valueOf(cost) / 100);
	}

	public static double getCost(int cost) {
		return (Double.valueOf(cost) / 100);
	}

	public static double getM2Cost(int inv, int cost) {
		return (Double.valueOf(inv) / 1000000) * (Double.valueOf(cost) / 100);
	}

	public void setDetails(ArrayList<Detail> details) {
		this.details = details;
	}

	public DetailSummary getDetailSummary() {
		return detailSummary;
	}

	public void setDetailSummary(DetailSummary detailSummary) {
		this.detailSummary = detailSummary;
	}

	@Override
	public int getDefaultX() {
		// TODO Auto-generated method stub
		return CommonStatic.defaultX;
	}

	@Override
	public int getDefaultY() {
		// TODO Auto-generated method stub
		return CommonStatic.defaultY;
	}

	@Override
	public int getDefaultZ() {
		// TODO Auto-generated method stub
		return CommonStatic.defaultZ;
	}

	@Override
	public int getDefaultRackQuantity() {
		// TODO Auto-generated method stub
		return CommonStatic.defaultRackQuantity;
	}

	@Override
	public boolean isProElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getEdgeLong() {
		int res = 0;
		for (Detail d : details) {
			res += ((d.getLengthEdge() * d.getLength()) + (d.getWidthEdge() * d
					.getWidth())) * d.getQuantity();

		}
		return res;
	}

	@Override
	public int getSpace(String material) {
		int res = 0;
		for (Detail d : details) {
			if (d.material.contentEquals(material))
				res += d.getLength() * d.getWidth() * d.getQuantity();
		}
		return res;
	}

	class DetailSummary {
		int x, y, z, quantity, shelfQuantity;
		String name;
		BoxesType type;
		long boxId;
		BoxSettings boxSettings;

	//	int getEdgeLong() {
	//		return (getEdgeLong() * quantity);
	//	}

		/*
		 * Double getEdgeCost() { return getMCost(getEdgeLong() quantity,
		 * boxSettings .getSettings(SettingsType.EDGE_COST)); }
		 */
		int edgeLong;
		Double edgeCost;
		int dspSquare;
		Double dspCost;
		int dvpSquare;
		Double dvpCost;
		Double furnitureCost;
		Double workCost;
		Double rentCost;
		Double totalCost;
	}

}
