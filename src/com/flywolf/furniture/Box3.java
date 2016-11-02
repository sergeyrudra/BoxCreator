package com.flywolf.furniture;

import java.util.List;

import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class Box3 extends AbstractBox {
	@Override
	public List<SettingsType> getSettingsTypeList() {
		// TODO Auto-generated method stub
		settingsTypeList.add(SettingsType.FACADE_CLEARANCE);
		settingsTypeList.add(SettingsType.REAR_DEEP);
		settingsTypeList.add(SettingsType.REAR_MARG);
		settingsTypeList.add(SettingsType.RACK_DEEP);
	return settingsTypeList;
	}

	public void create(DbWorker dbWorker) {	
		int z = dbWorker.z - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_DEEP)
				- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK);
		Detail d;
		d = new Detail(CommonStatic.Details.BACK, z, dbWorker.y
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 2), 0, 1, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// стенка
																		// боковая
		details.add(d);
		d = new Detail(CommonStatic.Details.BOTTOM, dbWorker.x, z, 1, 2, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// дно
		details.add(d);
		d = new Detail(CommonStatic.Details.BACK_MIDDLE, z, dbWorker.y
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 2), 0, 1, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// стенка
																		// средняя
		details.add(d);
		d = new Detail(CommonStatic.Details.RACK,
				(dbWorker.x / 2)
						- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) + (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) / 2)), z - BoxSettings.getBoxSettings().getSettings(SettingsType.RACK_DEEP), 1, 0,
				2 * dbWorker.shelfQuantity,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// полка
		details.add(d);

		d = new Detail(CommonStatic.Details.REAR, "dvp", dbWorker.x - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG),
				dbWorker.y - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG), 0, 0, 1,dbWorker.quantity);// задняя
		details.add(d);
		
		d = new Detail(CommonStatic.Details.FACADE, (dbWorker.x / 2)
				- BoxSettings.getBoxSettings().getSettings(SettingsType.FACADE_CLEARANCE), dbWorker.y
				- BoxSettings.getBoxSettings().getSettings(SettingsType.FACADE_CLEARANCE), 2, 2, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// фасад
		details.add(d);
	}
}
