package com.mumfrey.worldeditcui.render;

import com.mumfrey.worldeditcui.config.Colour;
import lombok.val;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

import static org.lwjgl.opengl.GL11.GL_GEQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;

/**
 * Stores colour data for each type of line.
 * <p>
 * Each line has a normal line, and a hidden line.
 * The normal line has an alpha value of 0.8f, and
 * the hidden line has an alpha value of 0.2f. They
 * both have a thickness of 3.0f.
 *
 * @author yetanotherx
 * @author lahwran
 */
public enum LineStyles implements Iterable<LineStyle> {
    // @formatter:off
	CUBOIDBOX      ("colour.cuboidedge"    , new Colour("#CC3333CC")),
	CUBOIDGRID     ("colour.cuboidgrid"    , new Colour("#CC4C4CCC")),
	CUBOIDPOINT1   ("colour.cuboidpoint1"  , new Colour("#33CC33CC")),
	CUBOIDPOINT2   ("colour.cuboidpoint2"  , new Colour("#3333CCCC")),
	POLYGRID       ("colour.polygrid"      , new Colour("#CC3333CC")),
	POLYBOX        ("colour.polyedge"      , new Colour("#CC4C4CCC")),
	POLYPOINT      ("colour.polypoint"     , new Colour("#33CCCCCC")),
	ELLIPSOIDGRID  ("colour.ellipsoidgrid" , new Colour("#CC4C4CCC")),
	ELLIPSOIDCENTER("colour.ellipsoidpoint", new Colour("#CCCC33CC")),
	CYLINDERGRID   ("colour.cylindergrid"  , new Colour("#CC3333CC")),
	CYLINDERBOX    ("colour.cylinderedge"  , new Colour("#CC4C4CCC")),
	CYLINDERCENTER ("colour.cylinderpoint" , new Colour("#CC33CCCC")),
	// @formatter:on
    ;

    private String displayName;

    private Colour defaultColour;
    private Colour colour;

    private LineStyle normal;
    private LineStyle hidden;

    private LineStyles(String displayName, Colour colour) {
        this.displayName = displayName;
        this.colour = colour;
        this.defaultColour = new Colour().copyFrom(colour);
    }

    @NotNull
    @Override
    public Iterator<LineStyle> iterator() {
        val list = new ArrayList<LineStyle>();
        list.add(hidden);
        list.add(normal);
        return list.iterator();
    }

    public String getDisplayName() {
        return I18n.format(this.displayName);
    }

    public void setColour(Colour colour) {
        this.colour = colour;
        this.updateColour();
    }

    public void updateColour() {
        this.normal = new LineStyle(3.0f, this.colour.red(), this.colour.green(), this.colour.blue(), this.colour.alpha(), GL_LESS);
        this.hidden = new LineStyle(3.0f, this.colour.red(), this.colour.green(), this.colour.blue(), this.colour.alpha() * 0.25F, GL_GEQUAL);
    }

    public void setDefaultColour() {
        this.colour.copyFrom(this.defaultColour);
        this.updateColour();
    }

    public void setColourIntRGBA(int argb) {
        int rgba = ((argb << 8) & 0xFFFFFF00) | (((argb & 0xFF000000) >> 24) & 0xFF);
        this.colour.setHex(Integer.toHexString(rgba));
        this.updateColour();
    }

    public int getColourIntARGB() {
        return this.colour.getIntARGB();
    }
}
