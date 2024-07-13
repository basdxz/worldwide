package com.mumfrey.worldeditcui.render;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.mumfrey.worldeditcui.InitializationFactory;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import com.mumfrey.worldeditcui.render.region.BaseRegion;
import com.mumfrey.worldeditcui.render.region.RegionType;

/**
 *
 * @author Adam Mummery-Smith
 */
public class CUISelectionProvider implements InitializationFactory
{
	private Map<String, Constructor<? extends BaseRegion>> regionConstructors = new HashMap<String, Constructor<? extends BaseRegion>>();

	private WorldEditCUIController controller;

	public CUISelectionProvider(WorldEditCUIController controller)
	{
		this.controller = controller;
	}

	@Override
	public void initialize() throws InitializationException
	{
		for (RegionType regionType : RegionType.values())
		{
			try
			{
				Class<? extends BaseRegion> eventClass = regionType.getRegionClass();
				Constructor<? extends BaseRegion> ctor = eventClass.getDeclaredConstructor(WorldEditCUIController.class);

				this.regionConstructors.put(regionType.getKey(), ctor);
			}
			catch (NoSuchMethodException ex)
			{
				this.controller.getDebugger().debug("Error getting constructor for region type " + regionType.getKey());
			}
		}
	}

	public BaseRegion createSelection(String key)
	{
		try
		{
			Constructor<? extends BaseRegion> regionCtor = this.regionConstructors.get(key);
			BaseRegion region = regionCtor.newInstance(this.controller);
			return region;
		}
		catch (NullPointerException ex)
		{
			this.controller.getDebugger().debug("No such selection type " + key);
		}
		catch (Exception ex)
		{
			this.controller.getDebugger().debug("Error creation " + key + " selection: " + ex.getClass().getSimpleName() + " " + ex.getMessage());
		}

		return null;
	}
}
