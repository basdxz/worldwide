package com.mumfrey.worldeditcui.render;

import com.mumfrey.worldeditcui.InitializationFactory;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import com.mumfrey.worldeditcui.render.selection.Selection;
import com.mumfrey.worldeditcui.render.selection.SelectionType;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

/**
 * @author Adam Mummery-Smith
 */
@RequiredArgsConstructor
public final class CUISelectionProvider implements InitializationFactory {
    private final WorldEditCUIController controller;

    private final Map<String, Constructor<? extends Selection>> selectionConstructors = new HashMap<>();

    @Override
    public void initialize() throws InitializationException {
        val types = SelectionType.values();
        for (val type : types) {
            val clazz = type.selectionClass();
            val clazzName = clazz.getName();
            val key = type.key();
            try {
                val ctor = clazz.getDeclaredConstructor(WorldEditCUIController.class);
                selectionConstructors.put(key, ctor);
                LOG.debug("Registered selection type: [{}] with class: [{}]", key, clazzName);
            } catch (Exception e) {
                LOG.error("Failed to find constructor for selection type: [{}] with class: [{}]", key, clazzName, e);
            }
        }
    }

    public Selection createSelection(String key) {
        val ctor = this.selectionConstructors.get(key);
        if (ctor == null) {
            LOG.warn("Unknown selection type: [{}]", key);
            return null;
        }

        try {
            return ctor.newInstance(this.controller);
        } catch (Exception e) {
            LOG.error("Failed to create new selection: [{}]", key, e);
            return null;
        }
    }
}
