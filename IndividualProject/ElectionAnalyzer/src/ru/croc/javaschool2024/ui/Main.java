package ru.croc.javaschool2024.ui;

import ru.croc.javaschool2024.logic.PollingStationsData;
import ru.croc.javaschool2024.logic.Reader;
import ru.croc.javaschool2024.ui.methods.SobyaninSukhovolskyMethod;
import ru.croc.javaschool2024.ui.methods.TurnoutDistributionMethod;
import ru.croc.javaschool2024.ui.methods.VarianceAnalysisMethod;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends JFrame {
    private PollingStationsData data;

    private JPanel mainPanel;
    private JTextField tfPath;
    private JTabbedPane tpMethod;
    private JButton buttonOk;
    private JTabbedPane tpVarianceAnalysis;
    private JTabbedPane tpSS;
    private JPanel pTurnoutDistribution;
    private final ChangeListener tooSmallDataset = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent event) {
            if (tpMethod.getSelectedIndex() == 2) {
                JOptionPane.showMessageDialog(
                        null,
                        "Слишком маленькое количество данных об участах. Данный метод не будет объективным."
                );
            }
        }
    };

    public Main() {
        super("Анализатор выборов");   // Задаем заголовок окна
        setContentPane(mainPanel);          // Размещаем на окне панель
        setSize(1000, 750);   // Задаем изначальные размеры окна
        setMinimumSize(new Dimension(1000, 750)); // Минимальные размеры окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);    // Выходим из приложения по кнопке закрыть
        setVisible(true);

        // Размещаем окно в центре экрана
        setLocationRelativeTo(null);

        // Добавляем слушатель кнопке
        buttonOk.addActionListener(event -> {
            // Читаем путь из текстового поля
            Path path = Paths.get(tfPath.getText());
            try {
                // Пробуем прочитать файл
                data = Reader.readPollingStationsInfo(path);
                // Обновляем вкладки
                updateTabs();
            } catch (IOException e) {
                // Выводим сообщение об ошибке
                JOptionPane.showMessageDialog(null, "Не получается открыть файл");
            }
        });
    }

    /**
     * Обновление вкладок
     */
    private void updateTabs() {
        tpVarianceAnalysis.removeAll();
        tpSS.removeAll();
        tpMethod.removeChangeListener(tooSmallDataset);
        pTurnoutDistribution.removeAll();

        addVarianceAnalysisTabs();
        addSSTabs();
        addTurnoutDistribution();
    }

    /**
     * Добавляем распределение явки
     */
    private void addTurnoutDistribution() {
        var turnoutDistribution = new TurnoutDistributionMethod(data.getTurnoutData());
        // Если столбцов в гистограмме слишком мало, предупреждаем пользователя об необъетивности данного метода.
        if (turnoutDistribution.getBinsNumber() < 10) {
            if (tpMethod.getSelectedIndex() == 2) {
                JOptionPane.showMessageDialog(
                        null,
                        "Слишком маленькое количество данных об участах. Данный метод не будет объективным."
                );
            }
            tpMethod.addChangeListener(tooSmallDataset);
        }
        JPanel tabPanel = new Tab(turnoutDistribution).getTabPanel();
        pTurnoutDistribution.add(tabPanel);
    }

    /**
     * Добавляем дисперсионный анализ
     */
    private void addVarianceAnalysisTabs() {
        String[] names = data.getCandidatesNames();
        for (int i = 0; i < names.length; ++i) {
            var varAnalysis = new VarianceAnalysisMethod(data.getCandidatePercentsData(i));
            JPanel tabPanel = new Tab(varAnalysis, (int) (data.getStationsNumber() * 3.5)).getTabPanel();
            tpVarianceAnalysis.addTab(names[i], tabPanel);
        }
    }

    /**
     * Добавляем метод Собянина – Суховольского
     */
    private void addSSTabs() {
        String[] names = data.getCandidatesNames();
        for (int i = 0; i < names.length; ++i) {
            var ss = new SobyaninSukhovolskyMethod(
                    data.getTurnoutData(),
                    data.getCandidatePercentOfTotalData(i),
                    data.getCandidatePercentsData(i));
            JPanel tabPanel = new Tab(ss).getTabPanel();
            tpSS.addTab(names[i], tabPanel);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
