package com.kiroule.vaadin.businessapp.ui.views;

import java.time.LocalDate;

import com.kiroule.vaadin.businessapp.backend.BankAccount;
import com.kiroule.vaadin.businessapp.backend.DummyData;
import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.FlexBoxLayout;
import com.kiroule.vaadin.businessapp.ui.components.ListItem;
import com.kiroule.vaadin.businessapp.ui.components.navigation.bar.AppBar;
import com.kiroule.vaadin.businessapp.ui.layout.size.Bottom;
import com.kiroule.vaadin.businessapp.ui.layout.size.Horizontal;
import com.kiroule.vaadin.businessapp.ui.layout.size.Top;
import com.kiroule.vaadin.businessapp.ui.layout.size.Vertical;
import com.kiroule.vaadin.businessapp.ui.util.BoxShadowBorders;
import com.kiroule.vaadin.businessapp.ui.util.LumoStyles;
import com.kiroule.vaadin.businessapp.ui.util.TextColor;
import com.kiroule.vaadin.businessapp.ui.util.UIUtils;
import com.kiroule.vaadin.businessapp.ui.util.css.BorderRadius;
import com.kiroule.vaadin.businessapp.ui.util.css.WhiteSpace;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Account Details")
@Route(value = "account-details",
        layout = MainLayout.class)
public class AccountDetails extends ViewFrame implements HasUrlParameter<Long> {

    public int RECENT_TRANSACTIONS = 4;

    private ListItem availability;
    private ListItem bankAccount;
    private ListItem updated;

    private BankAccount account;

    @Override
    public void setParameter(final BeforeEvent beforeEvent, final Long id) {

        setViewContent(createContent());
        this.account = DummyData.getBankAccount(id);
    }

    private Component createContent() {

        final FlexBoxLayout content = new FlexBoxLayout(createLogoSection(), createRecentTransactionsHeader(), createRecentTransactionsList(),
                createMonthlyOverviewHeader(), createMonthlyOverviewChart());
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        content.setMaxWidth("840px");
        return content;
    }

    private FlexBoxLayout createLogoSection() {

        final Image image = DummyData.getLogo();
        image.addClassName(LumoStyles.Margin.Horizontal.L);
        UIUtils.setBorderRadius(BorderRadius._50, image);
        image.setHeight("200px");
        image.setWidth("200px");

        this.availability = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), "", "Availability");
        this.availability.getPrimary().addClassName(LumoStyles.Heading.H2);
        this.availability.setDividerVisible(true);
        this.availability.setId("availability");
        this.availability.setReverse(true);

        this.bankAccount = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INSTITUTION), "", "");
        this.bankAccount.setDividerVisible(true);
        this.bankAccount.setId("bankAccount");
        this.bankAccount.setReverse(true);
        this.bankAccount.setWhiteSpace(WhiteSpace.PRE_LINE);

        this.updated = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Updated");
        this.updated.setReverse(true);

        final FlexBoxLayout listItems = new FlexBoxLayout(this.availability, this.bankAccount, this.updated);
        listItems.setFlexDirection(FlexDirection.COLUMN);

        final FlexBoxLayout section = new FlexBoxLayout(image, listItems);
        section.addClassName(BoxShadowBorders.BOTTOM);
        section.setAlignItems(FlexComponent.Alignment.CENTER);
        section.setFlex("1", listItems);
        section.setFlexWrap(FlexWrap.WRAP);
        section.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        section.setPadding(Bottom.L);
        return section;
    }

    private Component createRecentTransactionsHeader() {

        final Label title = UIUtils.createH3Label("Recent Transactions");

        final Button viewAll = UIUtils.createSmallButton("View All");
        viewAll.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));
        viewAll.addClassName(LumoStyles.Margin.Left.AUTO);

        final FlexBoxLayout header = new FlexBoxLayout(title, viewAll);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
        return header;
    }

    private Component createRecentTransactionsList() {

        final Div items = new Div();
        items.addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);

        for (int i = 0; i < this.RECENT_TRANSACTIONS; i++) {
            final Double amount = DummyData.getAmount();
            final Label amountLabel = UIUtils.createAmountLabel(amount);
            if (amount > 0) {
                UIUtils.setTextColor(TextColor.SUCCESS, amountLabel);
            } else {
                UIUtils.setTextColor(TextColor.ERROR, amountLabel);
            }
            final ListItem item = new ListItem(DummyData.getLogo(), DummyData.getCompany(), UIUtils.formatDate(LocalDate.now().minusDays(i)),
                    amountLabel);
            // Dividers for all but the last item
            item.setDividerVisible(i < this.RECENT_TRANSACTIONS - 1);
            items.add(item);
        }

        return items;
    }

    private Component createMonthlyOverviewHeader() {

        final Label header = UIUtils.createH3Label("Monthly Overview");
        header.addClassNames(LumoStyles.Margin.Vertical.L, LumoStyles.Margin.Responsive.Horizontal.L);
        return header;
    }

    private Component createMonthlyOverviewChart() {

        final Chart chart = new Chart(ChartType.COLUMN);

        final Configuration conf = chart.getConfiguration();
        conf.setTitle("");
        conf.getLegend().setEnabled(true);

        final XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Amount ($)");

        // Withdrawals and deposits
        final ListSeries withDrawals = new ListSeries("Withdrawals");
        final ListSeries deposits = new ListSeries("Deposits");

        for (int i = 0; i < 8; i++) {
            withDrawals.addData(DummyData.getRandomInt(5000, 10000));
            deposits.addData(DummyData.getRandomInt(5000, 10000));
        }

        conf.addSeries(withDrawals);
        conf.addSeries(deposits);

        final FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setHeight("400px");
        return card;
    }

    @Override
    protected void onAttach(final AttachEvent attachEvent) {

        super.onAttach(attachEvent);

        initAppBar();
        UI.getCurrent().getPage().setTitle(this.account.getOwner());

        this.availability.setPrimaryText(UIUtils.formatAmount(this.account.getAvailability()));
        this.bankAccount.setPrimaryText(this.account.getAccount());
        this.bankAccount.setSecondaryText(this.account.getBank());
        this.updated.setPrimaryText(UIUtils.formatDate(this.account.getUpdated()));
    }

    private AppBar initAppBar() {

        final AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate(Accounts.class));
        appBar.setTitle(this.account.getOwner());
        return appBar;
    }
}
