package com.flywolf.furniture;

import java.util.List;

import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class Box22 extends AbstractBox {
	@Override
	public List<SettingsType> getSettingsTypeList() {
		// TODO Auto-generated method stub
		settingsTypeList.add(SettingsType.FACADE_CLEARANCE);
		settingsTypeList.add(SettingsType.REAR_DEEP);
		settingsTypeList.add(SettingsType.REAR_MARG);
		settingsTypeList.add(SettingsType.STRUT_WIDTH);
		settingsTypeList.add(SettingsType.RACK_DEEP);
		return settingsTypeList;
	}

	public void create(DbWorker dbWorker) {		//ArrayList<Detail> details = new ArrayList<Detail>();
		int z = dbWorker.z - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_DEEP)
				- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK);
		Detail d;
		d = new Detail(CommonStatic.Details.BACK, dbWorker.x, dbWorker.y, 2, 1, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// стенка
		// боковая
		details.add(d);
		
		d = new Detail(CommonStatic.Details.BOTTOM, z , z, 0, 0, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK), "angle_cut_edge",z-dbWorker.x);// дно
		details.add(d);
		
		d = new Detail(CommonStatic.Details.RACK, z , z, 0, 0, dbWorker.shelfQuantity,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK), "angle_cut_edge",z-dbWorker.x+ BoxSettings.getBoxSettings().getSettings(SettingsType.RACK_DEEP));//полка
		details.add(d);

		d = new Detail(CommonStatic.Details.REAR, "dvp",dbWorker.z - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG),
				dbWorker.y - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG), 0, 0, 2,dbWorker.quantity);// задняя
		details.add(d);
		
		d = new Detail(CommonStatic.Details.FACADE, (int) Math. round (Math.sqrt(2*Math.pow((dbWorker.z-dbWorker.x-BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)),2)))- BoxSettings.getBoxSettings().getSettings(SettingsType.FACADE_CLEARANCE), dbWorker.y
				- BoxSettings.getBoxSettings().getSettings(SettingsType.FACADE_CLEARANCE), 2, 2, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// фасад
		details.add(d);
		
		d = new Detail(CommonStatic.Details.STRUT, BoxSettings.getBoxSettings().getSettings(SettingsType.STRUT_WIDTH), dbWorker.y-(BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK)*2), 0, 2,
				1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// распорка
		details.add(d);
		
	}
	@Override
	public int getDefaultX() {
		// TODO Auto-generated method stub
		return 300;
	}

	@Override
	public int getDefaultY() {
		// TODO Auto-generated method stub
		return 800;
	}

	@Override
	public int getDefaultZ() {
		// TODO Auto-generated method stub
		return 550;
	}	
}
