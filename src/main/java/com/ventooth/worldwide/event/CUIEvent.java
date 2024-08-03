package com.ventooth.worldwide.event;

import com.google.common.base.Joiner;
import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.render.selection.Selection;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * Base event for CUI events, handles parameter validation and running the logic
 *
 * @author yetanotherx
 */
@RequiredArgsConstructor
public abstract class CUIEvent {
    protected final WorldEditCUIController controller;
    protected final String[] args;

    public abstract String raise();

    public abstract CUIEventType getEventType();

    protected final Selection selection() {
        val selection = controller.selection();
        if (selection == null)
            throw new IllegalStateException("Selection is null");
        return selection;
    }

    /**
     * Checks if the parameters match the required length.
     *
     * @return
     */
    public final boolean isValid() {
        val type = getEventType();

        val min = type.minParameters();
        val max = type.maxParameters();
        if (min == max) {
            return args.length == max;
        } else {
            return args.length <= max && args.length >= min;
        }
    }

    public final void prepare() {
        if (controller == null || args == null)
            throw new IllegalStateException("Controller and parameters must both be set");

        if (!isValid()) {
            val message = String.format("Invalid number of parameters. %s event requires %s parameters but received %s [%s]",
                                        getEventType(),
                                        getRequiredParameterString(),
                                        args.length,
                                        Joiner.on(", ").join(args));
            throw new IllegalArgumentException(message);
        }
    }

    private String getRequiredParameterString() {
        val type = getEventType();
        val min = type.minParameters();
        val max = type.maxParameters();

        if (min == max)
            return String.valueOf(max);
        return String.format("between %d and %d", min, max);
    }

    public final int getInt(int index) {
        return Integer.parseInt(this.args[index]);
    }

    public final double getDouble(int index) {
        return Double.parseDouble(this.args[index]);
    }

    public final String getString(int index) {
        return this.args[index];
    }
}
