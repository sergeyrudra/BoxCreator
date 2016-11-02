package com.flywolf.furniture;

import java.util.List;

import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class Box9 extends AbstractBox {
	@Override
	public List<SettingsType> getSettingsTypeList() {
		// TODO Auto-generated method stub
		settingsTypeList.add(SettingsType.REAR_DEEP);
		settingsTypeList.add(SettingsType.REAR_MARG);
		return settingsTypeList;
	}

	public void create(DbWorker dbWorker) {		int z = dbWorker.z - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_DEEP);
		Detail d;
		d = new Detail(CommonStatic.Details.BACK, z, dbWorker.y, 2, 1, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// стенка
		// боковая
		details.add(d);
		d = new Detail(CommonStatic.Details.BOTTOM, dbWorker.x
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 2), z, 1, 0, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// дно
		details.add(d);
		d = new Detail(CommonStatic.Details.RACK, dbWorker.x
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 2), z, 1, 0,
				dbWorker.shelfQuantity,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// полка
		details.add(d);

		d = new Detail(CommonStatic.Details.REAR, "dvp", dbWorker.x - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG),
				dbWorker.y - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG), 0, 0, 1,dbWorker.quantity);// задняя
		details.add(d);
	}

}
