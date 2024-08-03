package com.ventooth.worldwide;

import com.ventooth.worldwide.event.CUIEventDispatcher;
import com.ventooth.worldwide.exceptions.InitializationException;
import com.ventooth.worldwide.render.CUISelectionProvider;
import com.ventooth.worldwide.render.selection.CuboidSelection;
import com.ventooth.worldwide.render.selection.Selection;
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
    private Selection selection;

    private CUIEventDispatcher dispatcher;
    private CUISelectionProvider selectionProvider;

    @Override
    public void initialize() throws InitializationException {
        this.selection = new CuboidSelection(this);
        this.dispatcher = new CUIEventDispatcher(this);
        this.selectionProvider = new CUISelectionProvider(this);

        this.selection.initialize();
        this.dispatcher.initialize();
        this.selectionProvider.initialize();
    }
}
