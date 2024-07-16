package com.mumfrey.worldeditcui.exceptions;

import com.mumfrey.worldeditcui.render.selection.SelectionType;

public final class InvalidSelectionTypeException extends RuntimeException {
    public InvalidSelectionTypeException(SelectionType type, String eventName) {
        super(String.format("Selection event %s is not supported for selection type %s", eventName, type));
    }
}
