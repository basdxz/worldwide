package com.mumfrey.worldeditcui.event;

import com.mumfrey.worldeditcui.InitializationFactory;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

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
            val clazz = type.getEventClass();
            val clazzName = clazz.getName();
            val key = type.getKey();
            try {
                val ctor = clazz.getDeclaredConstructor(WorldEditCUIController.class, String[].class);
                eventConstructors.put(key, ctor);
                LOG.debug("Registered event type: [{}] with class: [{}]", key, clazzName);
            } catch (Exception e) {
                LOG.error("Failed to find constructor for event type: [{}] with class: [{}]", key, clazz.getName(), e);
            }
        }
    }

    public void raiseEvent(CUIEventArgs args) {
        val type = args.getType();

        val ctor = this.eventConstructors.get(type);
        if (ctor == null) {
            LOG.warn("Unknown event type: [{}]", type);
            return;
        }

        val params = args.getParams();
        final CUIEvent evt;
        try {
            evt = ctor.newInstance(this.controller, params);
            evt.prepare();
        } catch (Exception e) {
            LOG.error("Failed to create new event: [{}] with args: [{}]", type, Arrays.toString(params), e);
            return;
        }

        try {
            val response = evt.raise();
            if (response != null)
                handleEventResponse(response);
        } catch (Exception e) {
            LOG.error("Failed to raise event: [{}] with args: [{}]", type, Arrays.toString(params), e);
        }
    }

    private void handleEventResponse(String response) {
    }
}
