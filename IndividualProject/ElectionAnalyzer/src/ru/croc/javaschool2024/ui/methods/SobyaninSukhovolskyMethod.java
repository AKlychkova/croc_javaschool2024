package ru.croc.javaschool2024.ui.methods;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.Layer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ru.croc.javaschool2024.logic.PairedRegression;
import ru.croc.javaschool2024.logic.Statistics;

import java.awt.*;

public class SobyaninSukhovolskyMethod extends Method {
    private final double[] turnout;
    private final double[] candidateResultsOfTotal;
    private final double[] candidateResultsOfValid;
    private final PairedRegression regression;

    /**
     * @param turnout Явка в процентах по каждому из избирательных участков.
     * @param candidateResultsOfTotal Результаты кандидата в процентах от общего числа избирателей по каждому из избирательных участков.
     * @param candidateResultsOfValid Результаты кандидата в процентах от количества действительных бюллетений по каждому из участков.
     */
    public SobyaninSukhovolskyMethod(double[] turnout, double[] candidateResultsOfTotal, double[] candidateResultsOfValid) {
        super("""
                <html>
                Если результаты кандидатов на отдельных избирательных участках выражать в процентах от списочного числа избирателей,s
                тангенс угла наклона которой равен среднему проценту голосов от числа проголосовавших избирателей.
                то зависимость таких результатов от показателя явки должна выражаться прямой, исходящей из начала координат,s
                <br>
                Тангенс угла наклона кривой на графиках Собянина – Суховольского назвается «поддержкой дополнительных избирателей» (ПДИ)s
                и в качестве критерия отклонения от «нормы» может использоваться разность между ПДИ и результатом кандидата относительно числа проголосовавших избирателей.
                <br>
                Второй параметр регрессионного уравнения (отрезок, отсекаемый регрессионной прямой на оси ординат) назвали смещением регрессионной линии (СРЛ).s
                В идеальном случае значение СРЛ должно быть нулевым (то есть регрессионная линия должна попадать в начало координат),s
                «нормальными» можно считать итоги голосования, при которых значение СРЛ по модулю не превышает 0,1 (или 10 %).s
                Превышение этого порога свидетельствует об аномалиях.
                <br>
                При способе Собянина – Суховольского в «нормальном» случае коэффициент корреляции явки и результатов будет близок к единице
                </html>
                """);

        // Проверяем, что количество избирательных участков совпадает во всех предоставленных данных.
        if (turnout.length != candidateResultsOfTotal.length
                || candidateResultsOfTotal.length != candidateResultsOfValid.length) {
            throw new IllegalArgumentException("Несогласование количества избирательных участков");
        }

        this.turnout = turnout;
        this.candidateResultsOfTotal = candidateResultsOfTotal;
        this.candidateResultsOfValid = candidateResultsOfValid;
        this.regression = new PairedRegression(turnout, candidateResultsOfTotal);
    }

    private XYDataset createDataset() {
        var series = new XYSeries("data");
        for (int i = 0; i < candidateResultsOfTotal.length; ++i) {
            series.add(turnout[i], candidateResultsOfTotal[i]);
        }

        // Добавляем регрессионную линию
        XYSeries trend = new XYSeries("trend");
        trend.add(0, regression.predict(0));
        trend.add(100, regression.predict(100));

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(trend);

        return dataset;
    }

    @Override
    protected JFreeChart createChart() {
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "",
                "Явка",
                "Результат кандидата в процентах от списочного числа избирателей",
                createDataset(),
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        XYPlot plot = scatterPlot.getXYPlot();
        // Меняем цвет заднего фона на белый
        plot.setBackgroundPaint(Color.white);

        // Добавляем горизонтальные линии сетки
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        // Добавляем вертикальные линии сетки
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        // Добавляем линии осей
        ValueMarker marker = new ValueMarker(0);
        marker.setPaint(Color.BLACK);
        marker.setStroke(new BasicStroke(2));
        plot.addRangeMarker(marker,Layer.BACKGROUND);
        plot.addDomainMarker(marker, Layer.BACKGROUND);

        // Отрисовываем регрессионную линию
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        r.setSeriesStroke(1,new BasicStroke(2));
        r.setSeriesLinesVisible(1, true);
        r.setSeriesShapesVisible(1, false);
        return scatterPlot;
    }

    /**
     * @return Информация о ПДИ, СРЛ и корреляции.
     */
    @Override
    public String getAdditionalInfo() {
        double pdi = regression.getBeta();
        double srl = regression.getAlpha();
        double result = Statistics.mean(candidateResultsOfValid);
        double corr = Statistics.corr(candidateResultsOfTotal, turnout);
        return String.format("""
                        <html>
                        ПДИ: %.2f
                        <br>
                        Cредний процент голосов от числа проголосовавших избирателей: %.2f
                        <br>
                        Разность между ПДИ и средним процентом голосов от числа проголосовавших избирателей: %.2f
                        <br>
                        СРЛ: %.2f
                        <br>
                        Корреляция: %.2f
                        </html>""",
                pdi,
                result,
                pdi - result,
                srl,
                corr
        );
    }
}
