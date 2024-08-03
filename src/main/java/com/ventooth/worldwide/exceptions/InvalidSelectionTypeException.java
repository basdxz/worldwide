package com.ventooth.worldwide.exceptions;

import com.ventooth.worldwide.render.selection.SelectionType;

public final class InvalidSelectionTypeException extends RuntimeException {
    public InvalidSelectionTypeException(SelectionType type, String eventName) {
        super(String.format("Selection event %s is not supported for selection type %s", eventName, type));
    }
}
