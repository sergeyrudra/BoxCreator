package com.flywolf.furniture;

import java.util.List;

import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class Box25 extends AbstractBox {
	@Override
	public List<SettingsType> getSettingsTypeList() {
		// TODO Auto-generated method stub
		settingsTypeList.add(SettingsType.FACADE_CLEARANCE);
		settingsTypeList.add(SettingsType.REAR_DEEP);
		settingsTypeList.add(SettingsType.REAR_MARG);
		settingsTypeList.add(SettingsType.STRUT_WIDTH);
		settingsTypeList.add(SettingsType.RACK_DEEP);
		settingsTypeList.add(SettingsType.SOCLE_Y);
		return settingsTypeList;
	}

	public void create(DbWorker dbWorker) {		//ArrayList<Detail> details = new ArrayList<Detail>();
		int z = dbWorker.z - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_DEEP);
		int y = dbWorker.y-BoxSettings.getBoxSettings().getSettings(SettingsType.SOCLE_Y);
		Detail d;
		d = new Detail(CommonStatic.Details.BACK, dbWorker.x, y- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK), 1, 1, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// стенка
		// боковая
		details.add(d);
		d = new Detail(CommonStatic.Details.TOP, z , z, 1, 1, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK), "angle_cut_edge",z-dbWorker.x-(BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)*2));// крышка
		details.add(d);
		d = new Detail(CommonStatic.Details.BOTTOM, z- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) , z- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK), 0, 0, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK), "angle_cut_edge",z- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)-dbWorker.x);// дно
		details.add(d);
		
		d = new Detail(CommonStatic.Details.RACK, z- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) , z- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK), 0, 0, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK), "angle_cut_edge",z- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)-dbWorker.x+ BoxSettings.getBoxSettings().getSettings(SettingsType.RACK_DEEP));//полка
		details.add(d);

		d = new Detail(CommonStatic.Details.REAR, "dvp",dbWorker.z - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG),
				y - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG), 0, 0, 2,dbWorker.quantity);// задняя
		details.add(d);
		
		d = new Detail(CommonStatic.Details.FACADE, (((int) Math. round (Math.sqrt(2*Math.pow((dbWorker.z-dbWorker.x-BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)),2))))/2)- BoxSettings.getBoxSettings().getSettings(SettingsType.FACADE_CLEARANCE), y- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)
				- BoxSettings.getBoxSettings().getSettings(SettingsType.FACADE_CLEARANCE), 2, 2, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// фасад
		details.add(d);
		
		d = new Detail(CommonStatic.Details.STRUT, BoxSettings.getBoxSettings().getSettings(SettingsType.STRUT_WIDTH), y-(BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)*2), 0, 2,
				1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// распорка
		details.add(d);
		
		d = new Detail(CommonStatic.Details.SOCLE, (int) Math. round (Math.sqrt(2*Math.pow((dbWorker.z-dbWorker.x+ BoxSettings.getBoxSettings().getSettings(SettingsType.RACK_DEEP)-BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)),2)))- BoxSettings.getBoxSettings().getSettings(SettingsType.FACADE_CLEARANCE), BoxSettings.getBoxSettings().getSettings(SettingsType.SOCLE_Y), 1, 2,
				1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// цокль
		details.add(d);
		
	}
	@Override
	public int getDefaultX() {
		// TODO Auto-generated method stub
		return 500;
	}

	@Override
	public int getDefaultY() {
		// TODO Auto-generated method stub
		return 800;
	}

	@Override
	public int getDefaultZ() {
		// TODO Auto-generated method stub
		return 750;
	}	
}
