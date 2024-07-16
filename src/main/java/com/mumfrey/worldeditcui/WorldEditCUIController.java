package com.mumfrey.worldeditcui;

import com.mumfrey.worldeditcui.config.CUIConfiguration;
import com.mumfrey.worldeditcui.event.CUIEventDispatcher;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import com.mumfrey.worldeditcui.render.CUISelectionProvider;
import com.mumfrey.worldeditcui.render.selection.CuboidSelection;
import com.mumfrey.worldeditcui.render.selection.SelectionBase;
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
    private SelectionBase selection;

    private CUIConfiguration configuration;
    private CUIEventDispatcher dispatcher;
    private CUISelectionProvider selectionProvider;

    @Override
    public void initialize() throws InitializationException {
        this.selection = new CuboidSelection(this);
        this.configuration = CUIConfiguration.create();
        this.dispatcher = new CUIEventDispatcher(this);
        this.selectionProvider = new CUISelectionProvider(this);

        this.selection.initialize();
        this.configuration.initialize();
        this.dispatcher.initialize();
        this.selectionProvider.initialize();
    }
}
