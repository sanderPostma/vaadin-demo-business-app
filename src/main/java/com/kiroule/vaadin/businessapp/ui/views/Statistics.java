package com.kiroule.vaadin.businessapp.ui.views;

import com.kiroule.vaadin.businessapp.backend.Payment;
import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.DataSeriesItemWithRadius;
import com.kiroule.vaadin.businessapp.ui.components.FlexBoxLayout;
import com.kiroule.vaadin.businessapp.ui.components.ListItem;
import com.kiroule.vaadin.businessapp.ui.layout.size.Bottom;
import com.kiroule.vaadin.businessapp.ui.layout.size.Horizontal;
import com.kiroule.vaadin.businessapp.ui.layout.size.Left;
import com.kiroule.vaadin.businessapp.ui.layout.size.Right;
import com.kiroule.vaadin.businessapp.ui.layout.size.Top;
import com.kiroule.vaadin.businessapp.ui.layout.size.Uniform;
import com.kiroule.vaadin.businessapp.ui.util.FontSize;
import com.kiroule.vaadin.businessapp.ui.util.IconSize;
import com.kiroule.vaadin.businessapp.ui.util.LumoStyles;
import com.kiroule.vaadin.businessapp.ui.util.TextColor;
import com.kiroule.vaadin.businessapp.ui.util.UIUtils;
import com.kiroule.vaadin.businessapp.ui.util.css.BorderRadius;
import com.kiroule.vaadin.businessapp.ui.util.css.BoxSizing;
import com.kiroule.vaadin.businessapp.ui.util.css.Display;
import com.kiroule.vaadin.businessapp.ui.util.css.Position;
import com.kiroule.vaadin.businessapp.ui.util.css.Shadow;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.Background;
import com.vaadin.flow.component.charts.model.BackgroundShape;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Pane;
import com.vaadin.flow.component.charts.model.PlotOptionsSolidgauge;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@CssImport("./styles/views/statistics.css")
@PageTitle("Statistics")
@Route(value = "statistics",
        layout = MainLayout.class)
public class Statistics extends ViewFrame {

    private static final String CLASS_NAME = "statistics";
    public static final String MAX_WIDTH = "1024px";

    public Statistics() {

        setViewContent(createContent());
    }

    private Component createContent() {

        final Component payments = createPayments();
        final Component transactions = createTransactions();
        final Component docs = createDocs();

        final FlexBoxLayout content = new FlexBoxLayout(payments, transactions, docs);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createPayments() {

        final FlexBoxLayout payments = new FlexBoxLayout(createHeader(VaadinIcon.CREDIT_CARD, "Payments"), createPaymentsCharts());
        payments.setBoxSizing(BoxSizing.BORDER_BOX);
        payments.setDisplay(Display.BLOCK);
        payments.setMargin(Top.L);
        payments.setMaxWidth(MAX_WIDTH);
        payments.setPadding(Horizontal.RESPONSIVE_L);
        payments.setWidthFull();
        return payments;
    }

    private FlexBoxLayout createHeader(final VaadinIcon icon, final String title) {

        final FlexBoxLayout header = new FlexBoxLayout(UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, icon), UIUtils.createH3Label(title));
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(Bottom.L, Horizontal.RESPONSIVE_L);
        header.setSpacing(Right.L);
        return header;
    }

    private Component createPaymentsCharts() {

        final Row charts = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, charts);
        UIUtils.setBorderRadius(BorderRadius.S, charts);
        UIUtils.setShadow(Shadow.XS, charts);

        for (final Payment.Status status : Payment.Status.values()) {
            charts.add(createPaymentChart(status));
        }

        return charts;
    }

    private Component createPaymentChart(final Payment.Status status) {

        int value;

        switch (status) {
        case PENDING:
            value = 24;
            break;

        case SUBMITTED:
            value = 40;
            break;

        case CONFIRMED:
            value = 32;
            break;

        default:
            value = 4;
            break;
        }

        final FlexBoxLayout textContainer = new FlexBoxLayout(UIUtils.createH2Label(Integer.toString(value)), UIUtils.createLabel(FontSize.S, "%"));
        textContainer.setAlignItems(FlexComponent.Alignment.BASELINE);
        textContainer.setPosition(Position.ABSOLUTE);
        textContainer.setSpacing(Right.XS);

        final Chart chart = createProgressChart(status, value);

        final FlexBoxLayout chartContainer = new FlexBoxLayout(chart, textContainer);
        chartContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        chartContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        chartContainer.setPosition(Position.RELATIVE);
        chartContainer.setHeight("120px");
        chartContainer.setWidth("120px");

        final FlexBoxLayout paymentChart = new FlexBoxLayout(new Label(status.getName()), chartContainer);
        paymentChart.addClassName(CLASS_NAME + "__payment-chart");
        paymentChart.setAlignItems(FlexComponent.Alignment.CENTER);
        paymentChart.setFlexDirection(FlexDirection.COLUMN);
        paymentChart.setPadding(Bottom.S, Top.M);
        return paymentChart;
    }

    private Chart createProgressChart(final Payment.Status status, final int value) {

        final Chart chart = new Chart();
        chart.addClassName(status.getName().toLowerCase());
        chart.setSizeFull();

        final Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.SOLIDGAUGE);
        configuration.setTitle("");
        configuration.getTooltip().setEnabled(false);

        configuration.getyAxis().setMin(0);
        configuration.getyAxis().setMax(100);
        configuration.getyAxis().getLabels().setEnabled(false);

        final PlotOptionsSolidgauge opt = new PlotOptionsSolidgauge();
        opt.getDataLabels().setEnabled(false);
        configuration.setPlotOptions(opt);

        final DataSeriesItemWithRadius point = new DataSeriesItemWithRadius();
        point.setY(value);
        point.setInnerRadius("100%");
        point.setRadius("110%");
        configuration.setSeries(new DataSeries(point));

        final Pane pane = configuration.getPane();
        pane.setStartAngle(0);
        pane.setEndAngle(360);

        final Background background = new Background();
        background.setShape(BackgroundShape.ARC);
        background.setInnerRadius("100%");
        background.setOuterRadius("110%");
        pane.setBackground(background);

        return chart;
    }

    private Component createTransactions() {

        final FlexBoxLayout transactions = new FlexBoxLayout(createHeader(VaadinIcon.MONEY_EXCHANGE, "Transactions"), createAreaChart());
        transactions.setBoxSizing(BoxSizing.BORDER_BOX);
        transactions.setDisplay(Display.BLOCK);
        transactions.setMargin(Top.XL);
        transactions.setMaxWidth(MAX_WIDTH);
        transactions.setPadding(Horizontal.RESPONSIVE_L);
        transactions.setWidthFull();
        return transactions;
    }

    private Component createAreaChart() {

        final Chart chart = new Chart(ChartType.AREASPLINE);

        final Configuration conf = chart.getConfiguration();
        conf.setTitle("2019");
        conf.getLegend().setEnabled(false);

        final XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Number of Processed Transactions");

        conf.addSeries(new ListSeries(220, 240, 400, 360, 420, 640, 580, 800, 600, 580, 740, 800));

        final FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createDocs() {

        final Component reports = createReports();
        final Component logs = createLogs();

        final Row docs = new Row(reports, logs);
        docs.addClassName(LumoStyles.Margin.Top.XL);
        UIUtils.setMaxWidth(MAX_WIDTH, docs);
        docs.setWidthFull();

        return docs;
    }

    private Component createReports() {

        final FlexBoxLayout header = createHeader(VaadinIcon.RECORDS, "Reports");

        final Tabs tabs = new Tabs();
        for (final String label : new String[] { "All", "Archive", "Workflows", "Support" }) {
            tabs.add(new Tab(label));
        }

        final Div items = new Div(
                new ListItem(UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.CHART), "Weekly Report", "Generated Oct 5, 2018",
                        createInfoButton()),
                new ListItem(UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.SITEMAP), "Payment Workflows",
                        "Last modified Oct 24, 2018", createInfoButton()));
        items.addClassNames(LumoStyles.Padding.Vertical.S);

        final Div card = new Div(tabs, items);
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
        UIUtils.setBorderRadius(BorderRadius.S, card);
        UIUtils.setShadow(Shadow.XS, card);

        final FlexBoxLayout reports = new FlexBoxLayout(header, card);
        reports.addClassName(CLASS_NAME + "__reports");
        reports.setFlexDirection(FlexDirection.COLUMN);
        reports.setPadding(Bottom.XL, Left.RESPONSIVE_L);
        return reports;
    }

    private Component createLogs() {

        final FlexBoxLayout header = createHeader(VaadinIcon.EDIT, "Logs");

        final Tabs tabs = new Tabs();
        for (final String label : new String[] { "All", "Transfer", "Security", "Change" }) {
            tabs.add(new Tab(label));
        }

        final Div items = new Div(
                new ListItem(UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.EXCHANGE), "Transfers (October)", "Generated Oct 31, 2018",
                        createInfoButton()),
                new ListItem(UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.SHIELD), "Security Log", "Updated 16:31 CET",
                        createInfoButton()));
        items.addClassNames(LumoStyles.Padding.Vertical.S);

        final Div card = new Div(tabs, items);
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
        UIUtils.setBorderRadius(BorderRadius.S, card);
        UIUtils.setShadow(Shadow.XS, card);

        final FlexBoxLayout logs = new FlexBoxLayout(header, card);
        logs.addClassName(CLASS_NAME + "__logs");
        logs.setFlexDirection(FlexDirection.COLUMN);
        logs.setPadding(Bottom.XL, Right.RESPONSIVE_L);
        return logs;
    }

    private Button createInfoButton() {

        final Button infoButton = UIUtils.createSmallButton(VaadinIcon.INFO);
        infoButton.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));
        return infoButton;
    }
}
