package com.avekvist.pacman.core.graphics;

import javax.swing.*;

public class Window {
    private String title;
    private int width;
    private int height;
    private JFrame frame;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        frame = new JFrame();

        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public JFrame getFrame() {
        return frame;
    }
}
