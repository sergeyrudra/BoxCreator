package com.flywolf.furniture;

import java.util.List;

import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class Box14 extends AbstractBox {
	@Override
	public List<SettingsType> getSettingsTypeList() {
		// TODO Auto-generated method stub
		settingsTypeList.add(SettingsType.SOCLE_Y);
		settingsTypeList.add(SettingsType.CLOSET_DEEP);
		settingsTypeList.add(SettingsType.LEFT_PART_X);
		settingsTypeList.add(SettingsType.REAR_DEEP);
		settingsTypeList.add(SettingsType.REAR_MARG);
		settingsTypeList.add(SettingsType.STRUT_WIDTH);
		settingsTypeList.add(SettingsType.MEZZANINE_Y);
		
	return settingsTypeList;
	}

	public void create(DbWorker dbWorker) {	//	ArrayList<Detail> details = new ArrayList<Detail>();
		int z = dbWorker.z - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_DEEP);
		Detail d;
		d = new Detail(CommonStatic.Details.BACK, z, dbWorker.y- BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK), 1, 1, 2,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// стенка
		details.add(d);

		d = new Detail(CommonStatic.Details.BACK_MIDDLE, z-BoxSettings.getBoxSettings().getSettings(SettingsType.CLOSET_DEEP), dbWorker.y-BoxSettings.getBoxSettings().getSettings(SettingsType.SOCLE_Y)- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 3)-BoxSettings.getBoxSettings().getSettings(SettingsType.MEZZANINE_Y), 0, 1, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// стенка
		details.add(d);

		d = new Detail(CommonStatic.Details.TOP, dbWorker.x, z
				, 1, 2, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// верх
		details.add(d);

		d = new Detail(CommonStatic.Details.BOTTOM, dbWorker.x
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 2), z, 1, 0, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// дно
		details.add(d);

		d = new Detail(CommonStatic.Details.RACK, BoxSettings.getBoxSettings().getSettings(SettingsType.LEFT_PART_X), z-BoxSettings.getBoxSettings().getSettings(SettingsType.CLOSET_DEEP), 1, 0,
				dbWorker.shelfQuantity,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// полка левая
		details.add(d);

		d = new Detail(CommonStatic.Details.RACK, (dbWorker.x-BoxSettings.getBoxSettings().getSettings(SettingsType.LEFT_PART_X))
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 3), z-BoxSettings.getBoxSettings().getSettings(SettingsType.CLOSET_DEEP), 1, 0,
				1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// полка гардероба
		details.add(d);

		d = new Detail(CommonStatic.Details.RACK, dbWorker.x-(BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 2), z-BoxSettings.getBoxSettings().getSettings(SettingsType.CLOSET_DEEP), 1, 0,
				1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// полка антресоли
		details.add(d);

		d = new Detail(CommonStatic.Details.STRUT, (dbWorker.x-BoxSettings.getBoxSettings().getSettings(SettingsType.LEFT_PART_X))
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 3), BoxSettings.getBoxSettings().getSettings(SettingsType.STRUT_WIDTH), 2, 0,
				1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// распорка
		details.add(d);

		d = new Detail(CommonStatic.Details.REAR, "dvp", dbWorker.x - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG),
				dbWorker.y - BoxSettings.getBoxSettings().getSettings(SettingsType.REAR_MARG) - BoxSettings.getBoxSettings().getSettings(SettingsType.SOCLE_Y), 0,
				0, 1,dbWorker.quantity);// задняя
		details.add(d);

		d = new Detail(CommonStatic.Details.SOCLE, dbWorker.x
				- (BoxSettings.getBoxSettings().getSettings(SettingsType.DSP_THICK) * 2),
				BoxSettings.getBoxSettings().getSettings(SettingsType.SOCLE_Y), 0, 0, 1,dbWorker.quantity, BoxSettings.getBoxSettings()
				.getSettings(SettingsType.EDGE_THICK));// цоколь
		details.add(d);
		//return details;
		
	}

	@Override
	public int getDefaultX() {
		// TODO Auto-generated method stub
		return 1600;
	}

	@Override
	public int getDefaultY() {
		// TODO Auto-generated method stub
		return 2100;
	}

	@Override
	public int getDefaultZ() {
		// TODO Auto-generated method stub
		return 600;
	}

	@Override
	public int getDefaultRackQuantity() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public boolean isProElement() {
		// TODO Auto-generated method stub
		return true;
	}

}
