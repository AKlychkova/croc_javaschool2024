package ru.croc.javaschool2024.ui;

import org.jfree.chart.ChartPanel;
import ru.croc.javaschool2024.ui.methods.Method;

import javax.swing.*;
import java.awt.*;

/**
 * Класс определяющий одну вкладку.
 */
public class Tab {
    private JPanel tabPanel;
    private JScrollPane scrollPane;
    private JLabel labelInfo;
    private JLabel labelDescription;

    /**
     * @param method      Метод выявления фальсификаций.
     * @param graph_width Ширина графа.
     */
    public Tab(Method method, int graph_width) {
        labelDescription.setText(method.getDescription());
        labelInfo.setText(method.getAdditionalInfo());
        ChartPanel chartPanel = method.getChartPanel(graph_width, scrollPane.getHeight());
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        scrollPane.setViewportView(chartPanel);
    }

    /**
     * @param method Метод выявления фальсификаций.
     */
    public Tab(Method method) {
        labelDescription.setText(method.getDescription());
        labelInfo.setText(method.getAdditionalInfo());
        ChartPanel chartPanel = method.getChartPanel(scrollPane.getWidth(), scrollPane.getHeight());
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        scrollPane.setViewportView(chartPanel);
    }

    /**
     * @return Панель вкладки
     */
    public JPanel getTabPanel() {
        return tabPanel;
    }
}
