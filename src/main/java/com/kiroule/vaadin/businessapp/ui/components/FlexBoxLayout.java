package com.kiroule.vaadin.businessapp.ui.components;

import java.util.ArrayList;

import com.kiroule.vaadin.businessapp.ui.layout.size.Size;
import com.kiroule.vaadin.businessapp.ui.util.css.BorderRadius;
import com.kiroule.vaadin.businessapp.ui.util.css.BoxSizing;
import com.kiroule.vaadin.businessapp.ui.util.css.Display;
import com.kiroule.vaadin.businessapp.ui.util.css.Overflow;
import com.kiroule.vaadin.businessapp.ui.util.css.Position;
import com.kiroule.vaadin.businessapp.ui.util.css.Shadow;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.Lumo;

public class FlexBoxLayout extends FlexLayout {

    public static final String BACKGROUND_COLOR = "background-color";
    public static final String BORDER_RADIUS = "border-radius";
    public static final String BOX_SHADOW = "box-shadow";
    public static final String BOX_SIZING = "box-sizing";
    public static final String DISPLAY = "display";
    public static final String FLEX_DIRECTION = "flex-direction";
    public static final String FLEX_WRAP = "flex-wrap";
    public static final String MAX_WIDTH = "max-width";
    public static final String OVERFLOW = "overflow";
    public static final String POSITION = "position";

    private final ArrayList<Size> spacings;

    public FlexBoxLayout(final Component... components) {

        super(components);
        this.spacings = new ArrayList<>();
    }

    public void setBackgroundColor(final String value) {

        getStyle().set(BACKGROUND_COLOR, value);
    }

    public void setBackgroundColor(final String value, final String theme) {

        getStyle().set(BACKGROUND_COLOR, value);
        setTheme(theme);
    }

    public void removeBackgroundColor() {

        getStyle().remove(BACKGROUND_COLOR);
    }

    public void setBorderRadius(final BorderRadius radius) {

        getStyle().set(BORDER_RADIUS, radius.getValue());
    }

    public void removeBorderRadius() {

        getStyle().remove(BORDER_RADIUS);
    }

    public void setBoxSizing(final BoxSizing sizing) {

        getStyle().set(BOX_SIZING, sizing.getValue());
    }

    public void removeBoxSizing() {

        getStyle().remove(BOX_SIZING);
    }

    public void setDisplay(final Display display) {

        getStyle().set(DISPLAY, display.getValue());
    }

    public void removeDisplay() {

        getStyle().remove(DISPLAY);
    }

    public void setFlex(final String value, final Component... components) {

        for (final Component component : components) {
            component.getElement().getStyle().set("flex", value);
        }
    }

    public void setFlexBasis(final String value, final Component... components) {

        for (final Component component : components) {
            component.getElement().getStyle().set("flex-basis", value);
        }
    }

    public void removeFlexDirection() {

        getStyle().remove(FLEX_DIRECTION);
    }

    public void setFlexShrink(final String value, final Component... components) {

        for (final Component component : components) {
            component.getElement().getStyle().set("flex-shrink", value);
        }
    }

    public void removeFlexWrap() {

        getStyle().remove(FLEX_WRAP);
    }

    public void setMargin(final Size... sizes) {

        for (final Size size : sizes) {
            for (final String attribute : size.getMarginAttributes()) {
                getStyle().set(attribute, size.getVariable());
            }
        }
    }

    public void removeMargin() {

        getStyle().remove("margin");
        getStyle().remove("margin-bottom");
        getStyle().remove("margin-left");
        getStyle().remove("margin-right");
        getStyle().remove("margin-top");
    }

    @Override
    public void setMaxWidth(final String value) {

        getStyle().set(MAX_WIDTH, value);
    }

    public void removeMaxWidth() {

        getStyle().remove(MAX_WIDTH);
    }

    public void setOverflow(final Overflow overflow) {

        getStyle().set(OVERFLOW, overflow.getValue());
    }

    public void removeOverflow() {

        getStyle().remove(OVERFLOW);
    }

    public void setPadding(final Size... sizes) {

        removePadding();
        for (final Size size : sizes) {
            for (final String attribute : size.getPaddingAttributes()) {
                getStyle().set(attribute, size.getVariable());
            }
        }
    }

    public void removePadding() {

        getStyle().remove("padding");
        getStyle().remove("padding-bottom");
        getStyle().remove("padding-left");
        getStyle().remove("padding-right");
        getStyle().remove("padding-top");
    }

    public void setPosition(final Position position) {

        getStyle().set(POSITION, position.getValue());
    }

    public void removePosition() {

        getStyle().remove(POSITION);
    }

    public void setShadow(final Shadow shadow) {

        getStyle().set(BOX_SHADOW, shadow.getValue());
    }

    public void removeShadow() {

        getStyle().remove(BOX_SHADOW);
    }

    public void setSpacing(final Size... sizes) {

        // Remove old styles (if applicable)
        for (final Size spacing : this.spacings) {
            removeClassName(spacing.getSpacingClassName());
        }
        this.spacings.clear();

        // Add new
        for (final Size size : sizes) {
            addClassName(size.getSpacingClassName());
            this.spacings.add(size);
        }
    }

    public void setTheme(final String theme) {

        if (Lumo.DARK.equals(theme)) {
            getElement().setAttribute("theme", "dark");
        } else {
            getElement().removeAttribute("theme");
        }
    }
}
