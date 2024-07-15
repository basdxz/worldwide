package com.mumfrey.worldeditcui;

import com.mumfrey.worldeditcui.config.CUIConfiguration;
import com.mumfrey.worldeditcui.event.CUIEventDispatcher;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import com.mumfrey.worldeditcui.render.CUISelectionProvider;
import com.mumfrey.worldeditcui.render.region.BaseRegion;
import com.mumfrey.worldeditcui.render.region.CuboidRegion;
import lombok.Getter;
import lombok.Setter;

/**
 * Main controller class. Uses a pseudo-JavaBeans paradigm. The only real
 * logic here is listener registration.
 * <p>
 * TODO: Preview mode
 * TODO: Command transactions
 * TODO: Add ability to flash selection
 *
 * @author yetanotherx
 */

@Getter
public final class WorldEditCUIController implements InitializationFactory {
    @Setter
    private BaseRegion selection;

    private CUIConfiguration configuration;
    private CUIEventDispatcher dispatcher;
    private CUISelectionProvider selectionProvider;

    @Override
    public void initialize() throws InitializationException {
        this.selection = new CuboidRegion(this);
        this.configuration = CUIConfiguration.create();
        this.dispatcher = new CUIEventDispatcher(this);
        this.selectionProvider = new CUISelectionProvider(this);

        this.selection.initialize();
        this.configuration.initialize();
        this.dispatcher.initialize();
        this.selectionProvider.initialize();
    }
}
