package ru.croc.javaschool2024.ui.methods;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.ui.Layer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.croc.javaschool2024.logic.Statistics;

import java.awt.*;

public class VarianceAnalysisMethod extends Method {
    /**
     * Проценты голосов, отданных за кандидата, на разных участках.
     */
    private final double[] candidateResults;

    public VarianceAnalysisMethod(double[] candidateResults) {
        super("""
                <html>
                Cравнение итогов голосования на близких по составу электората избирательных участках или территориях.
                Если электорат достаточно однородный, то при честном проведении голосования и подсчета голосов разброс не должен быть велик.
                Фальсификации же обычно осуществляются не по всем избирательным участкам, и это приводит к заметному увеличению разброса.
                </html>
                """
        );
        this.candidateResults = candidateResults;
    }

    private CategoryDataset createDataset() {
        var dataset = new DefaultCategoryDataset();
        for (int i = 0; i < candidateResults.length; ++i) {
            dataset.addValue(candidateResults[i], "result", "" + i);
        }
        return dataset;
    }

    /**
     * @return Информация о дисперсии и стандартном отклонении.
     */
    @Override
    public String getAdditionalInfo() {
        return String.format("""
                        <html>
                        Дисперсия: %.2f
                        <br>
                        Стандартное отклонение: %.2f
                        </html>""",
                Statistics.variance(candidateResults),
                Statistics.std(candidateResults));
    }

    @Override
    protected JFreeChart createChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "Избирательные участки",
                "Процент голосов, отданных за кандидата",
                createDataset(),
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        CategoryPlot plot = barChart.getCategoryPlot();
        // Меняем цвет заднего фона на белый
        plot.setBackgroundPaint(Color.white);

        // Добавляем горизонтальные линии сетки
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        // Убираем отступы по краям графика
        CategoryAxis axis = plot.getDomainAxis();
        axis.setLowerMargin(0.0);
        axis.setUpperMargin(0.0);

        // Добавляем линию, отображающую средний результат
        ValueMarker marker = new ValueMarker(Statistics.mean(candidateResults));  // position is the value on the axis
        marker.setPaint(Color.green);
        marker.setStroke(new BasicStroke(2));
        plot.addRangeMarker(marker, Layer.FOREGROUND);

        return barChart;
    }
}
