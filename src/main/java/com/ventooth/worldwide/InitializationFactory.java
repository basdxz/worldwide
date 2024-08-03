package com.ventooth.worldwide;

import com.ventooth.worldwide.exceptions.InitializationException;

/**
 * Simple interface to trace what needs to be initialized at mod loading.
 * Uses a unique exception to know when to halt initialization and stop mod loading.
 *
 * @author yetanotherx
 */
public interface InitializationFactory {
    void initialize() throws InitializationException;
}
