package com.kiroule.vaadin.businessapp.ui.views;

import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.FlexBoxLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;

/**
 * A view frame that establishes app design guidelines. It consists of four parts:
 * <ul>
 * <li>Topmost {@link #setViewHeader(Component...) header}</li>
 * <li>Center {@link #setViewContent(Component...) content}</li>
 * <li>Center {@link #setViewDetails(Component...) details}</li>
 * <li>Bottom {@link #setViewFooter(Component...) footer}</li>
 * </ul>
 */
@CssImport("./styles/components/view-frame.css")
public class SplitViewFrame extends Composite<Div> implements HasStyle {

    private final String CLASS_NAME = "view-frame";

    private final Div header;

    private final FlexBoxLayout wrapper;
    private final Div content;
    private final Div details;

    private final Div footer;

    public enum Position {
        RIGHT,
        BOTTOM
    }

    public SplitViewFrame() {

        setClassName(this.CLASS_NAME);

        this.header = new Div();
        this.header.setClassName(this.CLASS_NAME + "__header");

        this.wrapper = new FlexBoxLayout();
        this.wrapper.setClassName(this.CLASS_NAME + "__wrapper");

        this.content = new Div();
        this.content.setClassName(this.CLASS_NAME + "__content");

        this.details = new Div();
        this.details.setClassName(this.CLASS_NAME + "__details");

        this.footer = new Div();
        this.footer.setClassName(this.CLASS_NAME + "__footer");

        this.wrapper.add(this.content, this.details);
        getContent().add(this.header, this.wrapper, this.footer);
    }

    /**
     * Sets the header slot's components.
     */
    public void setViewHeader(final Component... components) {

        this.header.removeAll();
        this.header.add(components);
    }

    /**
     * Sets the content slot's components.
     */
    public void setViewContent(final Component... components) {

        this.content.removeAll();
        this.content.add(components);
    }

    /**
     * Sets the detail slot's components.
     */
    public void setViewDetails(final Component... components) {

        this.details.removeAll();
        this.details.add(components);
    }

    public void setViewDetailsPosition(final Position position) {

        if (position.equals(Position.RIGHT)) {
            this.wrapper.setFlexDirection(FlexDirection.ROW);

        } else if (position.equals(Position.BOTTOM)) {
            this.wrapper.setFlexDirection(FlexDirection.COLUMN);
        }
    }

    /**
     * Sets the footer slot's components.
     */
    public void setViewFooter(final Component... components) {

        this.footer.removeAll();
        this.footer.add(components);
    }

    @Override
    protected void onAttach(final AttachEvent attachEvent) {

        super.onAttach(attachEvent);
        MainLayout.get().getAppBar().reset();
    }
}
