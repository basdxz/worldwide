package com.mumfrey.worldeditcui.event;

import com.google.common.base.Joiner;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.region.BaseRegion;
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

    protected final BaseRegion getSelection() {
        val selection = controller.getSelection();
        if (selection == null)
            throw new IllegalStateException("Selection is null");
        return selection;
    }

    public final String getEventName() {
        return getEventType().getName();
    }

    /**
     * Checks if the parameters match the required length.
     *
     * @return
     */
    public final boolean isValid() {
        val type = getEventType();

        val min = type.getMinParameters();
        val max = type.getMaxParameters();
        if (min == max) {
            return this.args.length == max;
        } else {
            return this.args.length <= max && this.args.length >= min;
        }
    }

    public final void prepare() {
        if (this.controller == null || this.args == null) {
            throw new IllegalStateException("Controller and parameters must both be set.");
        }

        if (!this.isValid()) {
            String message = String.format("Invalid number of parameters. %s event requires %s parameters but received %s [%s]",
                                           this.getEventName(),
                                           this.getRequiredParameterString(),
                                           this.args.length,
                                           Joiner.on(", ").join(this.args));
            throw new IllegalArgumentException(message);
        }
    }

    private String getRequiredParameterString() {
        val type = getEventType();
        val min = type.getMinParameters();
        val max = type.getMaxParameters();

        if (min == max)
            return String.valueOf(max);
        return String.format("between %d and %d", min, max);
    }

    public final int getInt(int index) {
        return (int) Float.parseFloat(this.args[index]);
    }

    public final double getDouble(int index) {
        return Double.parseDouble(this.args[index]);
    }

    public final String getString(int index) {
        return this.args[index];
    }
}
