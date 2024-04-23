package ru.croc.javaschool2024.ui.methods;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.*;

/**
 * Абстрактный класс метода выявления фальсификаций.
 */
public abstract class Method {
    private final String description;
    private JFreeChart chart;

    /**
     * @param description Описание метода.
     */
    protected Method(String description) {
        this.description = description;
    }

    /**
     * @return Описание метода.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Дополнительная информация рассчитанная для данного метода.
     */
    public String getAdditionalInfo() {
        return "";
    }

    /**
     * @return График, который необходимо построить для данного метода.
     */
    protected abstract JFreeChart createChart();

    /**
     * Создает график, если он еще не создан.
     */
    private void defineChart() {
        if (chart == null) {
            chart = createChart();
        }
    }

    /**
     * @param width Ширина панели
     * @param height Высота панели
     * @return Панель с необходимым для данного метода графиком.
     */
    final public ChartPanel getChartPanel(int width, int height) {
        // Создаем график, если необходимо
        defineChart();
        ChartPanel panel = new ChartPanel(chart);
        // Делаем панель приблежаемой с помощью мышки.
        panel.setMouseZoomable(true);
        // Задаем размер
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }
}
