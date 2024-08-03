package com.ventooth.worldwide.event;

import com.ventooth.worldwide.InitializationFactory;
import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.exceptions.InitializationException;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Mummery-Smith
 */
@RequiredArgsConstructor
public final class CUIEventDispatcher implements InitializationFactory {
    private final WorldEditCUIController controller;

    private final Map<String, Constructor<? extends CUIEvent>> eventConstructors = new HashMap<>();

    @Override
    public void initialize() throws InitializationException {
        val types = CUIEventType.values();
        for (val type : types) {
            val clazz = type.eventClass();
            val clazzName = clazz.getName();
            val key = type.key();
            try {
                val ctor = clazz.getDeclaredConstructor(WorldEditCUIController.class, String[].class);
                eventConstructors.put(key, ctor);
                Worldwide.LOG.debug("Registered event type: [{}] with class: [{}]", key, clazzName);
            } catch (Exception e) {
                Worldwide.LOG.error("Failed to find constructor for event type: [{}] with class: [{}]", key, clazz.getName(), e);
            }
        }
    }

    public void raiseEvent(CUIEventArgs args) {
        val type = args.type();

        val ctor = this.eventConstructors.get(type);
        if (ctor == null) {
            Worldwide.LOG.warn("Unknown event type: [{}]", type);
            return;
        }

        val params = args.params();
        final CUIEvent evt;
        try {
            evt = ctor.newInstance(this.controller, params);
            evt.prepare();
        } catch (Exception e) {
            Worldwide.LOG.error("Failed to create new event: [{}] with args: [{}]", type, Arrays.toString(params), e);
            return;
        }

        try {
            val response = evt.raise();
            if (response != null)
                handleEventResponse(response);
        } catch (Exception e) {
            Worldwide.LOG.error("Failed to raise event: [{}] with args: [{}]", type, Arrays.toString(params), e);
        }
    }

    private void handleEventResponse(String response) {
    }
}
