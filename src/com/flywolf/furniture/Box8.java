package com.flywolf.furniture;

import java.util.List;

import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class Box8 extends AbstractBox {
	@Override
	public List<SettingsType> getSettingsTypeList() {
		// TODO Auto-generated method stub
		settingsTypeList.add(SettingsType.SOCLE_Y);
		settingsTypeList.add(SettingsType.TABLE_LEDGE_X);
		settingsTypeList.add(SettingsType.TABLE_LEDGE_Z);
		settingsTypeList.add(SettingsType.COMPUTER_X);
		return settingsTypeList;
	}

	public void create(DbWorker dbWorker) {
		int z = dbWorker.z;// - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_DEEP);
		Detail d;
		d = new Detail(CommonStatic.Details.BACK,
				z - (BoxSettings.getBoxSettings().getSettings(SettingsType.TABLE_LEDGE_Z)* 2), dbWorker.y
						- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK), 0, 1, 3,dbWorker.quantity, BoxSettings.getBoxSettings()
						.getSettings(SettingsType.EDGE_THICK));// стенка
																		// боковая
		details.add(d);
		d = new Detail(CommonStatic.Details.TOP, dbWorker.x, z, 2, 2, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// крышка
		details.add(d);
		d = new Detail(CommonStatic.Details.RACK, BoxSettings.getBoxSettings().getSettings(SettingsType.COMPUTER_X),
				z - (BoxSettings.getBoxSettings().getSettings(SettingsType.TABLE_LEDGE_Z) * 2), 1, 0,
				dbWorker.shelfQuantity,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// полка
		details.add(d);
		d = new Detail(CommonStatic.Details.REAR, dbWorker.x - (BoxSettings.getBoxSettings().getSettings(SettingsType.TABLE_LEDGE_X) * 2)
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 3) - BoxSettings.getBoxSettings().getSettings(SettingsType.COMPUTER_X),
				dbWorker.y / 2, 1, 0, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// задняя
		details.add(d);
		d = new Detail(CommonStatic.Details.SOCLE, BoxSettings.getBoxSettings().getSettings(SettingsType.COMPUTER_X),
				BoxSettings.getBoxSettings().getSettings(SettingsType.SOCLE_Y), 0, 0, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// цоколь
		details.add(d);
	}
	@Override
	public boolean isProElement() {
		// TODO Auto-generated method stub
		return false;
	}	
	@Override
	public int getDefaultX() {
		// TODO Auto-generated method stub
		return 1200;
	}

	@Override
	public int getDefaultY() {
		// TODO Auto-generated method stub
		return 750;
	}

	@Override
	public int getDefaultZ() {
		// TODO Auto-generated method stub
		return 600;
	}	
}
