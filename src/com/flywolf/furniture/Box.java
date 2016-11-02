package com.flywolf.furniture;

import java.util.ArrayList;
import java.util.List;

import com.flywolf.furniture.AbstractBox.DetailSummary;
import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;



public abstract interface Box {

	abstract void create(DbWorker dbWorker);
	abstract public List<SettingsType> getSettingsTypeList();
	abstract public int getDefaultX();
	abstract public int getDefaultY();
	abstract public int getDefaultZ();
	abstract public int getDefaultRackQuantity();
	abstract public boolean isProElement();
	abstract public void createDetailSummary(DbWorker dbWorker);
	
	abstract public ArrayList<Detail> getDetails();

	abstract public void setDetails(ArrayList<Detail> details);
	abstract public int getEdgeLong();
	abstract public int getSpace(String material);
	abstract public DetailSummary getDetailSummary();

}
