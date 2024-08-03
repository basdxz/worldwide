package com.ventooth.worldwide.exceptions;

import lombok.experimental.StandardException;

/**
 * Special exception that only gets called during initialization
 * Throwing this halts the loading of the mod
 *
 * @author yetanotherx
 */
@StandardException
public final class InitializationException extends Exception {
}
