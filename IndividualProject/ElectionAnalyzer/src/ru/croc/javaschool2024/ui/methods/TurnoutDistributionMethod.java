package ru.croc.javaschool2024.ui.methods;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;

import java.awt.*;

import static java.lang.Math.min;

public class TurnoutDistributionMethod extends Method{
    private final double[] turnout;
    private final int binsNumber;

    /**
     * @param turnout Явка в процентах по каждому из избирательных участков.
     */
    public TurnoutDistributionMethod(double[] turnout) {
        super("""
                <html>
                Метод заключается в построении графика, где по оси абсцисс откладывается явка,\s
                а по оси ординат – число избирательных участков,\s
                на которых явка укладывается в соответствующий однопроцентный интервал.\s
                Метод может быть использован для территорий, содержащих значительное число участков\s
                (две-три тысячи и более), для меньших территорий приходится использовать двух– или даже\s
                пятипроцентные интервалы, но точность и наглядность в этом случае значительно ниже.
                <br>
                По мнению исследователей, использующих данный метод,\s
                на относительно однородных территориях при отсутствии административного воздействия получающиеся\s
                кривые распределения должны быть близки к гауссовым, то есть иметь один «горб» и\s
                быть достаточно симметричными.
                </html>
                """);
        this.turnout = turnout;
        // Рассчитываем количество столбцов в гистограмме в зависимости от количества данных.
        this.binsNumber = min(100, turnout.length / 20 + 1);
    }

    /**
     * @return Количество столбцов гистограммы.
     */
    public int getBinsNumber() {
        return binsNumber;
    }

    private HistogramDataset createDataset() {
        var dataset = new HistogramDataset();
        dataset.addSeries("turnout", turnout, binsNumber );
        return dataset;
    }

    @Override
    protected JFreeChart createChart() {
        JFreeChart histogram = ChartFactory.createHistogram(
                "",
                "Явка",
                "Количество избирательных участков",
                createDataset());
        XYPlot plot = histogram.getXYPlot();
        // Меняем цвет заднего фона на белый
        plot.setBackgroundPaint(Color.white);

        // Добавляем горизонтальные линии сетки
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        return histogram;
    }
}
