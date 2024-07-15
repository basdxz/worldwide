package com.mumfrey.worldeditcui.render;

import com.mumfrey.worldeditcui.InitializationFactory;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import com.mumfrey.worldeditcui.render.region.BaseRegion;
import com.mumfrey.worldeditcui.render.region.RegionType;
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
public final class CUISelectionProvider implements InitializationFactory {
    private final WorldEditCUIController controller;

    private final Map<String, Constructor<? extends BaseRegion>> regionConstructors = new HashMap<>();

    @Override
    public void initialize() throws InitializationException {
        val types = RegionType.values();
        for (val type : types) {
            val clazz = type.getRegionClass();
            val clazzName = clazz.getName();
            val key = type.getKey();
            try {
                val ctor = clazz.getDeclaredConstructor(WorldEditCUIController.class);
                regionConstructors.put(key, ctor);
                LOG.debug("Registered selection type: [{}] with class: [{}]", key, clazzName);
            } catch (Exception e) {
                LOG.error("Failed to find constructor for region type: [{}] with class: [{}]", key, clazzName, e);
            }
        }
    }

    public BaseRegion createSelection(String key) {
        val ctor = this.regionConstructors.get(key);
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
