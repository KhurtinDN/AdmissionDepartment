package ru.sgu.csit.admissiondepartment.gui.utils;

import java.awt.*;

/**
 * Date: 05.05.2010
 * Time: 14:12:37
 *
 * @author xx & hd
 */
public class GBConstraints extends GridBagConstraints {

    public GBConstraints(int gridX, int gridY) {
        this.gridx = gridX;
        this.gridy = gridY;
        this.insets = new Insets(3, 3, 3, 3);
        this.anchor = GBConstraints.WEST;
    }

    public GBConstraints(int gridX, int gridY, boolean isHorizontalFill) {
        this(gridX, gridY);
        if (isHorizontalFill) {
            this.setFill(GBConstraints.HORIZONTAL);
            this.weightx = 100;
        }
    }

    public GBConstraints(int gridX, int gridY, int gridWidth, int gridHeight) {
        this(gridX, gridY);
        this.gridwidth = gridWidth;
        this.gridheight = gridHeight;
    }

    public GBConstraints(int gridX, int gridY, int gridWidth, int gridHeight, boolean isHorizontalFill) {
        this(gridX, gridY, isHorizontalFill);
        this.gridwidth = gridWidth;
        this.gridheight = gridHeight;
    }

    public GBConstraints setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GBConstraints setFill(int fill) {
        this.fill = fill;
        return this;
    }

    public GBConstraints setWeight(double weightX, double weightY) {
        this.weightx = weightX;
        this.weighty = weightY;
        return this;
    }

    public GBConstraints setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GBConstraints setInsets(int distance) {
        return this.setInsets(distance, distance, distance, distance);
    }

    public GBConstraints setIPad(int iPadX, int iPadY) {
        this.ipadx = iPadX;
        this.ipady = iPadY;
        return this;
    }
}
