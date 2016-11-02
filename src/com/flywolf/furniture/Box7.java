package com.flywolf.furniture;

import java.util.List;

import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class Box7 extends AbstractBox {
	@Override
	public List<SettingsType> getSettingsTypeList() {
		// TODO Auto-generated method stub
		settingsTypeList.add(SettingsType.TABLE_LEDGE_X);
		settingsTypeList.add(SettingsType.TABLE_LEDGE_Z);
		return settingsTypeList;
	}

	public void create(DbWorker dbWorker) {		Detail d;
		d = new Detail(CommonStatic.Details.BACK,
				dbWorker.z - (BoxSettings.getBoxSettings().getSettings(SettingsType.TABLE_LEDGE_Z) * 2), dbWorker.y
						- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK), 0, 1, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
						.getSettings(SettingsType.EDGE_THICK));// стенка
																		// боковая
		details.add(d);
		d = new Detail(CommonStatic.Details.TOP, dbWorker.x, dbWorker.z, 2, 2, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// крышка
		details.add(d);

		d = new Detail(CommonStatic.Details.REAR, dbWorker.x - (BoxSettings.getBoxSettings().getSettings(SettingsType.TABLE_LEDGE_X) * 2)
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 2), dbWorker.y / 2, 1,
				0, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// задняя
		details.add(d);

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
