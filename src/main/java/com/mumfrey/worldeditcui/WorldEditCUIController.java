package com.mumfrey.worldeditcui;

import com.mumfrey.worldeditcui.config.CUIConfiguration;
import com.mumfrey.worldeditcui.event.CUIEventDispatcher;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import com.mumfrey.worldeditcui.render.CUISelectionProvider;
import com.mumfrey.worldeditcui.render.region.BaseRegion;
import com.mumfrey.worldeditcui.render.region.CuboidRegion;

/**
 * Main controller class. Uses a pseudo-JavaBeans paradigm. The only real
 * logic here is listener registration.
 *
 * TODO: Preview mode
 * TODO: Command transactions
 * TODO: Add ability to flash selection
 *
 * @author yetanotherx
 */
public class WorldEditCUIController
{
	private BaseRegion selection;
	private CUIConfiguration configuration;
	private CUIEventDispatcher dispatcher;
	private CUISelectionProvider selectionProvider;

	public void initialize()
	{
		this.selection = new CuboidRegion(this);
		this.configuration = CUIConfiguration.create();
		this.dispatcher = new CUIEventDispatcher(this);
		this.selectionProvider = new CUISelectionProvider(this);

		try
		{
			this.selection.initialize();
			this.configuration.initialize();
			this.dispatcher.initialize();
			this.selectionProvider.initialize();
		}
		catch (InitializationException e)
		{
			e.printStackTrace();
			return;
		}
	}

	public CUIEventDispatcher getDispatcher()
	{
		return this.dispatcher;
	}

	public CUISelectionProvider getSelectionProvider()
	{
		return this.selectionProvider;
	}

	public CUIConfiguration getConfiguration()
	{
		return this.configuration;
	}

	public BaseRegion getSelection()
	{
		return this.selection;
	}

	public void setSelection(BaseRegion selection)
	{
		this.selection = selection;
	}
}
