/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on Jul 9, 2013, 8:16:02 AM
 */
package mainApp;

import core.DBConnection;
import core.Exp;
import core.dayend.GUIDBBackup;
import core.dayend.GUIDayEnd;
import core.reminder.GUIReminder;
import core.system_transaction.TransactionType;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.DefaultDesktopManager;
import javax.swing.DesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import sp_report.ui.GUIComenDateReport;
import sp_report.ui.GUIComenReport;
import sp_report.ui.GUICustomerOnlyReport;
import sp_report.ui.GUICustomerReport;
import sp_report.ui.GUIItemFlow;
import sp_report.ui.GUIPaymentHistory;
import sp_report.ui.GUIStockReport;
import system.masters.bankmaster.GUIBankMaster;
import system.accounts.master.accountopeningbalance.GUIAccountsOpeningBalance;
import system.accounts.master.accountcreation.GUIAccountCreation;
import system.accounts.recpayreports.GUIBouncedCheques;
import system.accounts.recpayreports.GUIChequesRealised;
import system.accounts.recpayreports.GUIChequestobeRealised;
import system.accounts.recpayreports.GUIReceiptRegister;
import system.accounts.recpayreports.GUIPaymentRegister;
import system.accounts.transaction.receiptvoucher.onaccount.GUIRecOnAccount;
import system.accounts.transaction.assignment.GUIAssignment;
import system.accounts.transaction.creditnote.GUICredit;
import system.accounts.transaction.debitnote.GUIDebitNote;
import system.accounts.transaction.hp.GUIHPLoanCard;
import system.accounts.transaction.journalvoucher.GUIJournalVoucher;
import system.accounts.transaction.journalvoucher.cancel.GUIJournalVoucherCancel;
import system.accounts.transaction.paymentvoucher.againstinvoice.GUIPayAgainstInvoice;
import system.accounts.transaction.paymentvoucher.onaccount.GUIPayOnAccount;
import system.accounts.transaction.realization.Receipts.GUIRecRealizationReceipt;
import system.accounts.transaction.realization.paymennts.GUIRecRealizationPayment;
import system.accounts.transaction.receiptvoucher.againstinvoice.GUIRecAgainstInvoice;
import system.accounts.transaction.reports.Dr;
import system.accounts.transaction.reports.Cr;
import system.accounts.transaction.reports.GUIAccountBalance;
import system.accounts.transaction.reports.GUIAgeingAnalysis;
import system.accounts.transaction.reports.GUIArrearsRep;
import system.accounts.transaction.reports.GUIBalanceSheet;
import system.accounts.transaction.reports.GUIChartofAccount;
import system.accounts.transaction.reports.GUIJournalReport;
import system.accounts.transaction.reports.GUILedger;
import system.accounts.transaction.reports.GUIProfitnLoss;
import system.accounts.transaction.reports.GUIStatementofAccount;
import system.accounts.transaction.reports.GUITrialBalance;
import system.accounts.transaction.voucher_cancel.VoucherCancel;
import system.masters.area.GUIArea;
import system.inventory.master.salesrep.GUISalesRep;
import system.inventory.master.store.GUIStore;
import system.inventory.master.unit.GUIUnit;
import system.inventory.master.unitconversion.GUIUnitConversion;
import system.inventory.master.itemclassification.itemcategory.GUIItemCategory;
import system.inventory.master.itemclassification.itemgroup.GUIItemGroup;
import system.inventory.master.itemclassification.itemmaster.GUIItemMaster;
import system.inventory.masterreports.itemlist.GUIItemList;
import system.inventory.masterreports.PriceList.GUIPriceList;
import system.inventory.reports.Transfer.GUIIssueReport;
import system.inventory.reports.Transfer.GUIReciptReport;
import system.inventory.reports.Transfer.GUITransferIssuesReport;
import system.inventory.reports.Transfer.GUITransferReceiptReport;
import system.inventory.reports.Transfer.GUITransferSummaryReport;
import system.inventory.reports.purchas.GUIPOList;
import system.inventory.reports.purchas.GUIPendingPOList;
import system.inventory.reports.purchas.GUIPurchaseGeneralRegister;
import system.inventory.reports.purchas.GUIPurchaseRegister;
import system.inventory.reports.purchas.GUIPurchaseReturnRegister;
import system.inventory.reports.sales.GUIDelivaryOrderRegister;
import system.inventory.reports.sales.GUIItemInquery;
import system.inventory.reports.sales.GUIOutstandingInvoice;
import system.inventory.reports.sales.GUIPendingDOList;
import system.inventory.reports.sales.GUIQuotationrRegister;
import system.inventory.reports.sales.GUISalesRegister;
import system.inventory.reports.sales.GUISalesReturnRegister;
import system.inventory.reports.sales.GUISalesServiceRegister;
import system.inventory.reports.stock.GUIStockAdjustmentReport;
import system.inventory.reports.stock.GUIStockLedger;
import system.inventory.reports.stock.GUIStockPosition;
import system.inventory.reports.stock.GUIStockReportBatchWise;
import system.inventory.transaction.purchase.GUIPurchase;
import system.inventory.transaction.purchaseorder.GUIPurchaseOrder;
import system.inventory.transaction.sales.GUIDelivaryON;
import system.inventory.transaction.sales.Invoice.GUISalesInvoiceQO;
import system.inventory.transaction.sales.Quotion.GUISalesQuotation;
import system.inventory.transaction.sales.paymentplan.GUIInvPayPlan;
import system.inventory.transaction.sales.retern.GUISalesRetern;
import system.inventory.transaction.stock.pricelist.PriceList;
import system.inventory.transaction.stock.stock_adgesment.GUIStockAdjesment;
import system.inventory.transaction.stock.stocktransfer.GUIStockTransfer;
import system.masters.Deponent.GUIDeponent;
import system.masters.costcenter.GUICostCenter;
import system.masters.currency.GUICurrency;
import system.masters.customer.addnew.GUICustomer;
import system.masters.customer.search.GUISearchCustomer;
import system.masters.customergroup.GUICustomerGroup;
import system.masters.narration.GUINarration;
import system.masters.payterms.GUIPayTerm;
import system.masters.vendore.GUIVendor;
import system.masters.vendore.search.GUISearchVendor;

/**
 *
 * @author dell
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        initOthers();
    }

    private void loadRem() {
        GUIReminder fc = GUIReminder.getGUIReminder();
        if (fc != null) {
            Toolkit t = Toolkit.getDefaultToolkit();
            fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, t.getScreenSize().width - 5, t.getScreenSize().height - 180);
            jDesktopPane1.add(fc);
            fc.setVisible(true);
        }
    }

    private void desktopPaneManager() {
        DesktopManager manager = new DefaultDesktopManager() {
            /**
             * This moves the <code>JComponent</code> and repaints the damaged
             * areas.
             */
            @Override
            public void setBoundsForFrame(JComponent f, int newX, int newY, int newWidth, int newHeight) {
                boolean didResize = (f.getWidth() != newWidth || f.getHeight() != newHeight);
                if (!inBounds((JInternalFrame) f, newX, newY, newWidth, newHeight)) {
                    return;
                }
                f.setBounds(newX, newY, newWidth, newHeight);
                if (didResize) {
                    f.validate();
                }
            }

            protected boolean inBounds(JInternalFrame f, int newX, int newY, int newWidth, int newHeight) {
                if (newX < 0 || newY < 0) {
                    return false;
                }
                if (newX + newWidth > f.getDesktopPane().getWidth()) {
                    return false;
                }
                return newY + newHeight <= f.getDesktopPane().getHeight();
            }

        };
        jDesktopPane1.setDesktopManager(manager);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    private void initOthers() {
        desktopPaneManager();
        this.setSize(800, 500);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(true);
        this.setIconImage(new ImageIcon("Balance.png").getImage());
//        registerKeys(p);
        setStatus();
        mainThread();
        loadRem();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pm = new javax.swing.JPopupMenu();
        Transaction = new javax.swing.JMenu();
        mnujournalvoucher1 = new javax.swing.JMenuItem();
        mnupayvoucherOA1 = new javax.swing.JMenuItem();
        mnupayvoucherAI1 = new javax.swing.JMenuItem();
        mnureceiptvoucherOA1 = new javax.swing.JMenuItem();
        mnureceiptvoucherAI1 = new javax.swing.JMenuItem();
        mnudebidnote1 = new javax.swing.JMenuItem();
        mnucreditnote1 = new javax.swing.JMenuItem();
        Inventory = new javax.swing.JMenu();
        mnuStore1 = new javax.swing.JMenuItem();
        mnuUnitofMeasurement1 = new javax.swing.JMenuItem();
        mnuItemGroup1 = new javax.swing.JMenuItem();
        mnuItemCategory1 = new javax.swing.JMenuItem();
        mnuItemMaster1 = new javax.swing.JMenuItem();
        Stock = new javax.swing.JMenu();
        mnuTransfer1 = new javax.swing.JMenuItem();
        mnuIssues1 = new javax.swing.JMenuItem();
        mnuReceipts1 = new javax.swing.JMenuItem();
        mnuSalesPrice1 = new javax.swing.JMenuItem();
        Purchase = new javax.swing.JMenu();
        mnuPurchaseOrder1 = new javax.swing.JMenuItem();
        mnuGRN1 = new javax.swing.JMenuItem();
        mnuPIGenerale1 = new javax.swing.JMenuItem();
        mnuPerchesReturns1 = new javax.swing.JMenuItem();
        Sales = new javax.swing.JMenu();
        mnuSalesQuotation1 = new javax.swing.JMenuItem();
        mnuDeliveryOrderNote1 = new javax.swing.JMenuItem();
        mnuSalesInvoiceAgainstDO1 = new javax.swing.JMenuItem();
        mnuSalesInvoice1 = new javax.swing.JMenuItem();
        mnuSalesInvoiceService1 = new javax.swing.JMenuItem();
        mnuSalesReturn1 = new javax.swing.JMenuItem();
        mnuPayPlan1 = new javax.swing.JMenuItem();
        Customer = new javax.swing.JMenu();
        mnunewcust1 = new javax.swing.JMenuItem();
        mnusearchcust1 = new javax.swing.JMenuItem();
        Vendore = new javax.swing.JMenu();
        mnunewven1 = new javax.swing.JMenuItem();
        mnusearchven1 = new javax.swing.JMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        txtlogo = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        cmdCustomers = new javax.swing.JButton();
        cmdVendors = new javax.swing.JButton();
        cmdTransactions = new javax.swing.JButton();
        cmdInventory = new javax.swing.JButton();
        cmdStock = new javax.swing.JButton();
        cmdPurchase = new javax.swing.JButton();
        cmdSales = new javax.swing.JButton();
        cmdCalculator2 = new javax.swing.JButton();
        cmdCalculator = new javax.swing.JButton();
        cmdLanguage = new javax.swing.JButton();
        cmdLanguage1 = new javax.swing.JButton();
        txtWorkingDate = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtuser = new javax.swing.JLabel();
        txtcompany = new javax.swing.JLabel();
        txtdate = new javax.swing.JLabel();
        ins = new javax.swing.JLabel();
        cps = new javax.swing.JLabel();
        txtLastUpdate = new javax.swing.JLabel();
        txtDB = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtdate1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuCreateUser = new javax.swing.JMenuItem();
        mnuToolBar = new javax.swing.JCheckBoxMenuItem();
        mnuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnucurrency = new javax.swing.JMenuItem();
        mnuarea = new javax.swing.JMenuItem();
        mnucostcenter = new javax.swing.JMenuItem();
        mnubankmaster = new javax.swing.JMenuItem();
        mnupaymentterms = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnucusgroup = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        mnunewcust = new javax.swing.JMenuItem();
        mnusearchcust = new javax.swing.JMenuItem();
        mnuvendor = new javax.swing.JMenuItem();
        mnunarration = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        mnuaccountcreation = new javax.swing.JMenuItem();
        mnuaccountopblance = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        mnujournalvoucher = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        mnupayvoucherOA = new javax.swing.JMenuItem();
        mnupayvoucherAI = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu14 = new javax.swing.JMenu();
        mnureceiptvoucherOA = new javax.swing.JMenuItem();
        mnureceiptvoucherAI = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        mnupayment = new javax.swing.JMenuItem();
        mnureciptDis = new javax.swing.JMenuItem();
        mnudebidnote = new javax.swing.JMenuItem();
        mnucreditnote = new javax.swing.JMenuItem();
        mnureconciliation = new javax.swing.JMenuItem();
        mnuassignments = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        mnuChartofaccounts = new javax.swing.JMenuItem();
        mnuACBalance = new javax.swing.JMenuItem();
        mnuJouinal = new javax.swing.JMenuItem();
        mnuCr = new javax.swing.JMenuItem();
        mnuDr = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuLedger = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuStatementofAccounts = new javax.swing.JMenuItem();
        mnuAgeingAnalysis = new javax.swing.JMenuItem();
        mnuTrialBalance = new javax.swing.JMenuItem();
        mnuProfitLoss = new javax.swing.JMenuItem();
        mnuBalanceSheet = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        mnuPaymentRegister = new javax.swing.JMenuItem();
        mnuReceiptRegister = new javax.swing.JMenuItem();
        mnuChequestobeRealisedCleared = new javax.swing.JMenuItem();
        mnuListofBouncedCheques = new javax.swing.JMenuItem();
        mnuChequesRealisedCleared = new javax.swing.JMenuItem();
        mnuBrowser = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu16 = new javax.swing.JMenu();
        mnuStore = new javax.swing.JMenuItem();
        mnuSalesRepresentatives = new javax.swing.JMenuItem();
        mnuUnitofMeasurement = new javax.swing.JMenuItem();
        mnuUnitConvesion = new javax.swing.JMenuItem();
        jMenu23 = new javax.swing.JMenu();
        mnuItemGroup = new javax.swing.JMenuItem();
        mnuItemCategory = new javax.swing.JMenuItem();
        mnuItemMaster = new javax.swing.JMenuItem();
        jMenu17 = new javax.swing.JMenu();
        jMenu24 = new javax.swing.JMenu();
        mnuPurchaseOrder = new javax.swing.JMenuItem();
        mnuGRN = new javax.swing.JMenuItem();
        mnuPIGenerale = new javax.swing.JMenuItem();
        mnuPerchesReturns = new javax.swing.JMenuItem();
        jMenu25 = new javax.swing.JMenu();
        mnuSalesQuotation = new javax.swing.JMenuItem();
        mnuDeliveryOrderNote = new javax.swing.JMenuItem();
        mnuSalesInvoiceAgainstDO = new javax.swing.JMenuItem();
        mnuSalesInvoice = new javax.swing.JMenuItem();
        mnuSalesInvoiceService = new javax.swing.JMenuItem();
        mnuSalesReturn = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu26 = new javax.swing.JMenu();
        jMenu27 = new javax.swing.JMenu();
        mnuTransfer = new javax.swing.JMenuItem();
        mnuIssues = new javax.swing.JMenuItem();
        mnuReceipts = new javax.swing.JMenuItem();
        mnuOpeningCostPrice = new javax.swing.JMenuItem();
        mnuStockOpeningBalance = new javax.swing.JMenuItem();
        mnuStockAdjustment = new javax.swing.JMenuItem();
        mnuSalesPrice = new javax.swing.JMenuItem();
        jMenu18 = new javax.swing.JMenu();
        jMenu28 = new javax.swing.JMenu();
        mnuLCCreation = new javax.swing.JMenuItem();
        mnuExpenses = new javax.swing.JMenuItem();
        mnuShipment = new javax.swing.JMenuItem();
        mnuTRSettlment = new javax.swing.JMenuItem();
        mnuLCInvoiceClose = new javax.swing.JMenuItem();
        jMenu29 = new javax.swing.JMenu();
        mnuLCAcceptance = new javax.swing.JMenuItem();
        mnuGoodsDelivery = new javax.swing.JMenuItem();
        jMenu19 = new javax.swing.JMenu();
        jMenu30 = new javax.swing.JMenu();
        mnuLCOpenRegister = new javax.swing.JMenuItem();
        mnuLCExpensesRegister = new javax.swing.JMenuItem();
        mnuLCShipmentRegister = new javax.swing.JMenuItem();
        mnuLCTRRegister = new javax.swing.JMenuItem();
        mnuLCCloseRegister = new javax.swing.JMenuItem();
        jMenu31 = new javax.swing.JMenu();
        mnuLCAcceptanceRegister = new javax.swing.JMenuItem();
        mnuGoodsDeliveryRegister = new javax.swing.JMenuItem();
        jMenu20 = new javax.swing.JMenu();
        mnuStockAgeingAnalysis = new javax.swing.JMenuItem();
        mnuSalesPriceAnalysis = new javax.swing.JMenuItem();
        mnuItemCostSalesPrice = new javax.swing.JMenuItem();
        mnuStockMargin = new javax.swing.JMenuItem();
        mnuIssueMargin = new javax.swing.JMenuItem();
        mnuInvoiceMargin = new javax.swing.JMenuItem();
        jMenu32 = new javax.swing.JMenu();
        mnuDetailed = new javax.swing.JMenuItem();
        mnuSummary = new javax.swing.JMenuItem();
        mnuSleepingItem = new javax.swing.JMenuItem();
        mnuItemQuantityLevel = new javax.swing.JMenuItem();
        mnuNegativeStockPosition = new javax.swing.JMenuItem();
        mnuSalesmanwiseAnalysis = new javax.swing.JMenuItem();
        mnuCustomerwiseSalesAnalysis = new javax.swing.JMenuItem();
        mnuCustomerMonthlySalesAnalysis = new javax.swing.JMenuItem();
        mnuRepresentativeMonthlySales = new javax.swing.JMenuItem();
        mnuRepresentativeMonthlyCollection = new javax.swing.JMenuItem();
        jMenu21 = new javax.swing.JMenu();
        jMenu33 = new javax.swing.JMenu();
        mnuItemEnquiry = new javax.swing.JMenuItem();
        mnuStockPosition = new javax.swing.JMenuItem();
        mnuStockLedger = new javax.swing.JMenuItem();
        jMenuItem86 = new javax.swing.JMenuItem();
        mnuStockAdjustmentReport = new javax.swing.JMenuItem();
        jMenu34 = new javax.swing.JMenu();
        mnuPurchaseOrderListing = new javax.swing.JMenuItem();
        mnuPendingPurchaseOrderListing = new javax.swing.JMenuItem();
        mnuPurchaseRegister = new javax.swing.JMenuItem();
        mnuPurchaseReturns = new javax.swing.JMenuItem();
        mnuPurchaseGeneralRegister = new javax.swing.JMenuItem();
        jMenu35 = new javax.swing.JMenu();
        mnuSalesQuotationRegister = new javax.swing.JMenuItem();
        mnuODRegister = new javax.swing.JMenuItem();
        mnuSalesRegister = new javax.swing.JMenuItem();
        mnuSalesReturns = new javax.swing.JMenuItem();
        mnuPendingODList = new javax.swing.JMenuItem();
        mnuOutstandingInvoices = new javax.swing.JMenuItem();
        mnuSalesServiceRegister = new javax.swing.JMenuItem();
        mnuInvoiceListLockedUnlocked = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu36 = new javax.swing.JMenu();
        mnuIssuesReport = new javax.swing.JMenuItem();
        mnuReceiptsReport = new javax.swing.JMenuItem();
        mnuStockTransferIssuesReport = new javax.swing.JMenuItem();
        mnuStockTransferReceiptsReport = new javax.swing.JMenuItem();
        mnuStockTransferSummary = new javax.swing.JMenuItem();
        jMenu22 = new javax.swing.JMenu();
        mnuItemMasterListing = new javax.swing.JMenuItem();
        mnuPriceList = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        mnuMonthlySalesAnalysis = new javax.swing.JMenuItem();
        mnuItemSalesAnalysisMonthly = new javax.swing.JMenuItem();
        mnuAgeingAnalysisGrp = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        mnuClearHistory = new javax.swing.JMenuItem();
        mnuChangeCompanyLogo = new javax.swing.JMenuItem();
        mnuYearEndProcess = new javax.swing.JMenuItem();
        mnuAlterTable = new javax.swing.JMenuItem();
        mnuReorganiseAccounts = new javax.swing.JMenuItem();
        mnuTransactionLockingDate = new javax.swing.JMenuItem();
        mnuChangeAccountName = new javax.swing.JMenuItem();
        mnuMaintenance = new javax.swing.JMenuItem();
        mnuDailyReporte = new javax.swing.JMenuItem();
        jMenu39 = new javax.swing.JMenu();
        mnuAbout = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        mnuREPCustomerInvoiceDet = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu38 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu40 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenu41 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();

        Transaction.setText("jMenu11");

        mnujournalvoucher1.setText("Journal Voucher");
        mnujournalvoucher1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnujournalvoucher1ActionPerformed(evt);
            }
        });
        Transaction.add(mnujournalvoucher1);

        mnupayvoucherOA1.setText("Payment Voucher (On Account)");
        mnupayvoucherOA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnupayvoucherOA1ActionPerformed(evt);
            }
        });
        Transaction.add(mnupayvoucherOA1);

        mnupayvoucherAI1.setText("Payment Voucher (Against Invoices)");
        mnupayvoucherAI1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnupayvoucherAI1ActionPerformed(evt);
            }
        });
        Transaction.add(mnupayvoucherAI1);

        mnureceiptvoucherOA1.setText("Receipt Voucher (On Account)");
        mnureceiptvoucherOA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnureceiptvoucherOA1ActionPerformed(evt);
            }
        });
        Transaction.add(mnureceiptvoucherOA1);

        mnureceiptvoucherAI1.setText("Receipt Voucher (Against Invoices)");
        mnureceiptvoucherAI1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnureceiptvoucherAI1ActionPerformed(evt);
            }
        });
        Transaction.add(mnureceiptvoucherAI1);

        mnudebidnote1.setText("Debit Note");
        mnudebidnote1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnudebidnote1ActionPerformed(evt);
            }
        });
        Transaction.add(mnudebidnote1);

        mnucreditnote1.setText("Credit Note");
        mnucreditnote1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnucreditnote1ActionPerformed(evt);
            }
        });
        Transaction.add(mnucreditnote1);

        Inventory.setText("jMenu12");

        mnuStore1.setText("Store");
        mnuStore1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStore1ActionPerformed(evt);
            }
        });
        Inventory.add(mnuStore1);

        mnuUnitofMeasurement1.setText("Unit of Measurement");
        mnuUnitofMeasurement1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUnitofMeasurement1ActionPerformed(evt);
            }
        });
        Inventory.add(mnuUnitofMeasurement1);

        mnuItemGroup1.setText("Item Group");
        mnuItemGroup1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemGroup1ActionPerformed(evt);
            }
        });
        Inventory.add(mnuItemGroup1);

        mnuItemCategory1.setText("Item Category");
        mnuItemCategory1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCategory1ActionPerformed(evt);
            }
        });
        Inventory.add(mnuItemCategory1);

        mnuItemMaster1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mnuItemMaster1.setText("Item Master");
        mnuItemMaster1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemMaster1ActionPerformed(evt);
            }
        });
        Inventory.add(mnuItemMaster1);

        Stock.setText("jMenu11");
        Stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StockActionPerformed(evt);
            }
        });

        mnuTransfer1.setText("Transfer");
        mnuTransfer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTransfer1ActionPerformed(evt);
            }
        });
        Stock.add(mnuTransfer1);

        mnuIssues1.setText("Issues");
        Stock.add(mnuIssues1);

        mnuReceipts1.setText("Receipts");
        Stock.add(mnuReceipts1);

        mnuSalesPrice1.setText("Sales Price");
        mnuSalesPrice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesPrice1ActionPerformed(evt);
            }
        });
        Stock.add(mnuSalesPrice1);

        Purchase.setText("jMenu11");

        mnuPurchaseOrder1.setText("Purchase Order");
        mnuPurchaseOrder1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPurchaseOrder1ActionPerformed(evt);
            }
        });
        Purchase.add(mnuPurchaseOrder1);

        mnuGRN1.setText("Purchase Invoice (GRN)");
        mnuGRN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGRN1ActionPerformed(evt);
            }
        });
        Purchase.add(mnuGRN1);

        mnuPIGenerale1.setText("Purchase Invoice (Generale)");
        Purchase.add(mnuPIGenerale1);

        mnuPerchesReturns1.setText("Purchase Returns");
        mnuPerchesReturns1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPerchesReturns1ActionPerformed(evt);
            }
        });
        Purchase.add(mnuPerchesReturns1);

        Sales.setText("jMenu11");

        mnuSalesQuotation1.setText("Sales Order");
        mnuSalesQuotation1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesQuotation1ActionPerformed(evt);
            }
        });
        Sales.add(mnuSalesQuotation1);

        mnuDeliveryOrderNote1.setText("Delivery Order Note");
        mnuDeliveryOrderNote1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDeliveryOrderNote1ActionPerformed(evt);
            }
        });
        Sales.add(mnuDeliveryOrderNote1);

        mnuSalesInvoiceAgainstDO1.setText("Sales Invoice Against DO");
        mnuSalesInvoiceAgainstDO1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesInvoiceAgainstDO1ActionPerformed(evt);
            }
        });
        Sales.add(mnuSalesInvoiceAgainstDO1);

        mnuSalesInvoice1.setText("Sales Invoice");
        Sales.add(mnuSalesInvoice1);

        mnuSalesInvoiceService1.setText("Sales Invoice (Service)");
        Sales.add(mnuSalesInvoiceService1);

        mnuSalesReturn1.setText("Sales Returns");
        mnuSalesReturn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesReturn1ActionPerformed(evt);
            }
        });
        Sales.add(mnuSalesReturn1);

        mnuPayPlan1.setText("Create Pay Plan");
        mnuPayPlan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPayPlan1ActionPerformed(evt);
            }
        });
        Sales.add(mnuPayPlan1);

        Customer.setText("jMenu12");

        mnunewcust1.setText("New Customer");
        mnunewcust1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnunewcust1ActionPerformed(evt);
            }
        });
        Customer.add(mnunewcust1);

        mnusearchcust1.setText("Search Customer");
        mnusearchcust1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnusearchcust1ActionPerformed(evt);
            }
        });
        Customer.add(mnusearchcust1);

        Vendore.setText("jMenu37");

        mnunewven1.setText("Add New Vendore");
        mnunewven1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnunewven1ActionPerformed(evt);
            }
        });
        Vendore.add(mnunewven1);

        mnusearchven1.setText("Search Vendore");
        mnusearchven1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnusearchven1ActionPerformed(evt);
            }
        });
        Vendore.add(mnusearchven1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RAP --- © RABID Technologies");
        setMinimumSize(new java.awt.Dimension(967, 511));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDesktopPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jDesktopPane1ComponentResized(evt);
            }
        });
        jDesktopPane1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDesktopPane1PropertyChange(evt);
            }
        });

        txtlogo.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        txtlogo.setForeground(new java.awt.Color(179, 0, 0));
        txtlogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtlogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/RAP-Logo.png"))); // NOI18N
        txtlogo.setMaximumSize(new java.awt.Dimension(340, 128));
        txtlogo.setMinimumSize(new java.awt.Dimension(340, 128));
        txtlogo.setPreferredSize(new java.awt.Dimension(340, 128));
        txtlogo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jDesktopPane1.add(txtlogo);
        txtlogo.setBounds(200, 90, 370, 130);
        jDesktopPane1.setLayer(txtlogo, -5);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        cmdCustomers.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdCustomers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/ManageUsers.png"))); // NOI18N
        cmdCustomers.setText("Customers");
        cmdCustomers.setToolTipText("Customers Creation");
        cmdCustomers.setFocusable(false);
        cmdCustomers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdCustomers.setMaximumSize(new java.awt.Dimension(93, 54));
        cmdCustomers.setMinimumSize(new java.awt.Dimension(93, 54));
        cmdCustomers.setPreferredSize(new java.awt.Dimension(93, 54));
        cmdCustomers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdCustomers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdCustomersMouseClicked(evt);
            }
        });
        cmdCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCustomersActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdCustomers);

        cmdVendors.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdVendors.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/Personnel.png"))); // NOI18N
        cmdVendors.setText("Vendors");
        cmdVendors.setToolTipText("Vendors Creation");
        cmdVendors.setFocusable(false);
        cmdVendors.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdVendors.setIconTextGap(1);
        cmdVendors.setMaximumSize(new java.awt.Dimension(93, 54));
        cmdVendors.setMinimumSize(new java.awt.Dimension(93, 54));
        cmdVendors.setPreferredSize(new java.awt.Dimension(93, 54));
        cmdVendors.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdVendors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdVendorsMouseClicked(evt);
            }
        });
        cmdVendors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdVendorsActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdVendors);

        cmdTransactions.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdTransactions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/Partnership1.png"))); // NOI18N
        cmdTransactions.setText("Transactions    ");
        cmdTransactions.setToolTipText("Transactions");
        cmdTransactions.setFocusable(false);
        cmdTransactions.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdTransactions.setIconTextGap(1);
        cmdTransactions.setMaximumSize(new java.awt.Dimension(93, 54));
        cmdTransactions.setMinimumSize(new java.awt.Dimension(93, 54));
        cmdTransactions.setPreferredSize(new java.awt.Dimension(93, 54));
        cmdTransactions.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdTransactions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdTransactionsMouseClicked(evt);
            }
        });
        cmdTransactions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTransactionsActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdTransactions);

        cmdInventory.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdInventory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/Entertainment.png"))); // NOI18N
        cmdInventory.setText("Inventory    ");
        cmdInventory.setToolTipText("Inventory & Units");
        cmdInventory.setFocusable(false);
        cmdInventory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdInventory.setIconTextGap(1);
        cmdInventory.setMaximumSize(new java.awt.Dimension(93, 54));
        cmdInventory.setMinimumSize(new java.awt.Dimension(93, 54));
        cmdInventory.setPreferredSize(new java.awt.Dimension(93, 54));
        cmdInventory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdInventoryMouseClicked(evt);
            }
        });
        jToolBar1.add(cmdInventory);

        cmdStock.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/Balance.png"))); // NOI18N
        cmdStock.setText("Stock      ");
        cmdStock.setToolTipText("Stock");
        cmdStock.setFocusable(false);
        cmdStock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdStock.setIconTextGap(1);
        cmdStock.setMaximumSize(new java.awt.Dimension(93, 54));
        cmdStock.setMinimumSize(new java.awt.Dimension(93, 54));
        cmdStock.setPreferredSize(new java.awt.Dimension(93, 54));
        cmdStock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdStockMouseClicked(evt);
            }
        });
        jToolBar1.add(cmdStock);

        cmdPurchase.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdPurchase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/Advertisements.png"))); // NOI18N
        cmdPurchase.setText("Purchase     ");
        cmdPurchase.setToolTipText("Purchase Invoice");
        cmdPurchase.setFocusable(false);
        cmdPurchase.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdPurchase.setIconTextGap(1);
        cmdPurchase.setMaximumSize(new java.awt.Dimension(93, 54));
        cmdPurchase.setMinimumSize(new java.awt.Dimension(93, 54));
        cmdPurchase.setPreferredSize(new java.awt.Dimension(93, 54));
        cmdPurchase.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdPurchaseMouseClicked(evt);
            }
        });
        cmdPurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPurchaseActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdPurchase);

        cmdSales.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdSales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/Accounting.png"))); // NOI18N
        cmdSales.setText("Sales      ");
        cmdSales.setToolTipText("Sales Invoice");
        cmdSales.setFocusable(false);
        cmdSales.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdSales.setIconTextGap(1);
        cmdSales.setMaximumSize(new java.awt.Dimension(93, 54));
        cmdSales.setMinimumSize(new java.awt.Dimension(93, 54));
        cmdSales.setPreferredSize(new java.awt.Dimension(93, 54));
        cmdSales.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdSalesMouseClicked(evt);
            }
        });
        jToolBar1.add(cmdSales);

        cmdCalculator2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdCalculator2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/hp.png"))); // NOI18N
        cmdCalculator2.setText("HP Loan Card");
        cmdCalculator2.setToolTipText("HP Loan Card");
        cmdCalculator2.setFocusable(false);
        cmdCalculator2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdCalculator2.setIconTextGap(1);
        cmdCalculator2.setMaximumSize(new java.awt.Dimension(93, 54));
        cmdCalculator2.setMinimumSize(new java.awt.Dimension(93, 54));
        cmdCalculator2.setPreferredSize(new java.awt.Dimension(93, 54));
        cmdCalculator2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdCalculator2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCalculator2ActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdCalculator2);

        cmdCalculator.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdCalculator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/DeviceControl.png"))); // NOI18N
        cmdCalculator.setText("Calculator");
        cmdCalculator.setToolTipText("Calculator");
        cmdCalculator.setFocusable(false);
        cmdCalculator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdCalculator.setIconTextGap(1);
        cmdCalculator.setMaximumSize(new java.awt.Dimension(79, 54));
        cmdCalculator.setMinimumSize(new java.awt.Dimension(79, 54));
        cmdCalculator.setPreferredSize(new java.awt.Dimension(79, 54));
        cmdCalculator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdCalculator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCalculatorActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdCalculator);

        cmdLanguage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdLanguage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/dayend.png"))); // NOI18N
        cmdLanguage.setText("Day End");
        cmdLanguage.setToolTipText("Day End");
        cmdLanguage.setFocusable(false);
        cmdLanguage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdLanguage.setIconTextGap(1);
        cmdLanguage.setMaximumSize(new java.awt.Dimension(79, 54));
        cmdLanguage.setMinimumSize(new java.awt.Dimension(79, 54));
        cmdLanguage.setPreferredSize(new java.awt.Dimension(79, 54));
        cmdLanguage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLanguageActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdLanguage);

        cmdLanguage1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmdLanguage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/48px-Gnome-video-x-generic.svg.png"))); // NOI18N
        cmdLanguage1.setText("Backup");
        cmdLanguage1.setToolTipText("Backup");
        cmdLanguage1.setFocusable(false);
        cmdLanguage1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdLanguage1.setIconTextGap(1);
        cmdLanguage1.setMaximumSize(new java.awt.Dimension(79, 54));
        cmdLanguage1.setMinimumSize(new java.awt.Dimension(79, 54));
        cmdLanguage1.setPreferredSize(new java.awt.Dimension(79, 54));
        cmdLanguage1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdLanguage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLanguage1ActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdLanguage1);

        txtWorkingDate.setBackground(new java.awt.Color(255, 255, 204));
        txtWorkingDate.setForeground(new java.awt.Color(153, 0, 0));
        txtWorkingDate.setText("Working Date");
        txtWorkingDate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtWorkingDate.setOpaque(true);
        txtWorkingDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtWorkingDateMouseClicked(evt);
            }
        });
        txtWorkingDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtWorkingDateKeyReleased(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 0, 102));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("EN");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel4.setOpaque(true);

        txtuser.setForeground(new java.awt.Color(51, 51, 51));
        txtuser.setText("User : ");
        txtuser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtcompany.setForeground(new java.awt.Color(51, 51, 51));
        txtcompany.setText("Company : ");
        txtcompany.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtdate.setText("16/07/2013");
        txtdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtdate.setMaximumSize(new java.awt.Dimension(72, 14));
        txtdate.setMinimumSize(new java.awt.Dimension(72, 14));
        txtdate.setPreferredSize(new java.awt.Dimension(72, 14));

        ins.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ins.setForeground(new java.awt.Color(64, 64, 64));
        ins.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ins.setText("INS");
        ins.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cps.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cps.setForeground(new java.awt.Color(64, 64, 64));
        cps.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cps.setText("CAPS");
        cps.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cps.setMaximumSize(new java.awt.Dimension(33, 14));
        cps.setMinimumSize(new java.awt.Dimension(33, 14));
        cps.setPreferredSize(new java.awt.Dimension(33, 14));

        txtLastUpdate.setForeground(new java.awt.Color(51, 51, 51));
        txtLastUpdate.setText("Last Update");
        txtLastUpdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtLastUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLastUpdateMouseClicked(evt);
            }
        });
        txtLastUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLastUpdateKeyReleased(evt);
            }
        });

        txtDB.setBackground(new java.awt.Color(255, 255, 204));
        txtDB.setForeground(new java.awt.Color(153, 0, 0));
        txtDB.setText("Database:");
        txtDB.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDB.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(0, 0, 102));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/32 x 32/48px-Gnome-appointment-soon.svg.png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        txtdate1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtdate1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtdate1.setMaximumSize(new java.awt.Dimension(72, 14));
        txtdate1.setMinimumSize(new java.awt.Dimension(72, 14));
        txtdate1.setPreferredSize(new java.awt.Dimension(72, 14));

        jMenu1.setText("File");

        mnuCreateUser.setText("Create User");
        jMenu1.add(mnuCreateUser);

        mnuToolBar.setSelected(true);
        mnuToolBar.setText("Tool Bar");
        mnuToolBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuToolBarActionPerformed(evt);
            }
        });
        jMenu1.add(mnuToolBar);

        mnuExit.setText("Exit");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnuExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(" Masters");

        mnucurrency.setText("Currency");
        mnucurrency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnucurrencyActionPerformed(evt);
            }
        });
        jMenu2.add(mnucurrency);

        mnuarea.setText("Area");
        mnuarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuareaActionPerformed(evt);
            }
        });
        jMenu2.add(mnuarea);

        mnucostcenter.setText("Cost Center");
        mnucostcenter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnucostcenterActionPerformed(evt);
            }
        });
        jMenu2.add(mnucostcenter);

        mnubankmaster.setText("Bank Master");
        mnubankmaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnubankmasterActionPerformed(evt);
            }
        });
        jMenu2.add(mnubankmaster);

        mnupaymentterms.setText("Payment Terms");
        mnupaymentterms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnupaymenttermsActionPerformed(evt);
            }
        });
        jMenu2.add(mnupaymentterms);
        jMenu2.add(jSeparator1);

        mnucusgroup.setText("Customer Group");
        mnucusgroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnucusgroupActionPerformed(evt);
            }
        });
        jMenu2.add(mnucusgroup);

        jMenu11.setText("Customer");

        mnunewcust.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnunewcust.setText("New Customer");
        mnunewcust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnunewcustActionPerformed(evt);
            }
        });
        jMenu11.add(mnunewcust);

        mnusearchcust.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        mnusearchcust.setText("Customer Serch");
        mnusearchcust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnusearchcustActionPerformed(evt);
            }
        });
        jMenu11.add(mnusearchcust);

        jMenu2.add(jMenu11);

        mnuvendor.setText("Vendor");
        mnuvendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuvendorActionPerformed(evt);
            }
        });
        jMenu2.add(mnuvendor);

        mnunarration.setText("Narration");
        mnunarration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnunarrationActionPerformed(evt);
            }
        });
        jMenu2.add(mnunarration);

        jMenuItem2.setText("Deponent");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Accounts");

        jMenu7.setText("Master");

        mnuaccountcreation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        mnuaccountcreation.setText("Accounts Creation");
        mnuaccountcreation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuaccountcreationActionPerformed(evt);
            }
        });
        jMenu7.add(mnuaccountcreation);

        mnuaccountopblance.setText("Accounts Opening Balance");
        mnuaccountopblance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuaccountopblanceActionPerformed(evt);
            }
        });
        jMenu7.add(mnuaccountopblance);

        jMenu3.add(jMenu7);

        jMenu8.setText("Transaction");

        mnujournalvoucher.setText("Journal Voucher");
        mnujournalvoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnujournalvoucherActionPerformed(evt);
            }
        });
        jMenu8.add(mnujournalvoucher);

        jMenu13.setText("Payment Voucher");

        mnupayvoucherOA.setText("Payment Voucher (On Account)");
        mnupayvoucherOA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnupayvoucherOAActionPerformed(evt);
            }
        });
        jMenu13.add(mnupayvoucherOA);

        mnupayvoucherAI.setText("Payment Voucher (Against Invoices)");
        mnupayvoucherAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnupayvoucherAIActionPerformed(evt);
            }
        });
        jMenu13.add(mnupayvoucherAI);

        jMenuItem14.setText("Payment Voucher Cancel");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem14);

        jMenu8.add(jMenu13);

        jMenu14.setText("Receipt Voucher");
        jMenu14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu14ActionPerformed(evt);
            }
        });

        mnureceiptvoucherOA.setText("Receipt Voucher (On Account)");
        mnureceiptvoucherOA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnureceiptvoucherOAActionPerformed(evt);
            }
        });
        jMenu14.add(mnureceiptvoucherOA);

        mnureceiptvoucherAI.setText("Receipt Voucher (Against Invoices)");
        mnureceiptvoucherAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnureceiptvoucherAIActionPerformed(evt);
            }
        });
        jMenu14.add(mnureceiptvoucherAI);

        jMenuItem15.setText("Receipt Voucher Cancel");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem15);

        jMenu8.add(jMenu14);

        jMenu15.setText("Realization");

        mnupayment.setText(" Payments");
        mnupayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnupaymentActionPerformed(evt);
            }
        });
        jMenu15.add(mnupayment);

        mnureciptDis.setText(" Recipts (Discounting)");
        mnureciptDis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnureciptDisActionPerformed(evt);
            }
        });
        jMenu15.add(mnureciptDis);

        jMenu8.add(jMenu15);

        mnudebidnote.setText("Debit Note");
        mnudebidnote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnudebidnoteActionPerformed(evt);
            }
        });
        jMenu8.add(mnudebidnote);

        mnucreditnote.setText("Credit Note");
        mnucreditnote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnucreditnoteActionPerformed(evt);
            }
        });
        jMenu8.add(mnucreditnote);

        mnureconciliation.setText("Reconciliation");
        mnureconciliation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnureconciliationActionPerformed(evt);
            }
        });
        jMenu8.add(mnureconciliation);

        mnuassignments.setText("Assignments");
        mnuassignments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuassignmentsActionPerformed(evt);
            }
        });
        jMenu8.add(mnuassignments);

        jMenu3.add(jMenu8);

        jMenu9.setText("Reports");

        mnuChartofaccounts.setText("Chart of Accounts");
        mnuChartofaccounts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuChartofaccountsActionPerformed(evt);
            }
        });
        jMenu9.add(mnuChartofaccounts);

        mnuACBalance.setText("A/C Balance");
        mnuACBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuACBalanceActionPerformed(evt);
            }
        });
        jMenu9.add(mnuACBalance);

        mnuJouinal.setText("Jouinal");
        mnuJouinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuJouinalActionPerformed(evt);
            }
        });
        jMenu9.add(mnuJouinal);

        mnuCr.setText("Trade Creditors");
        mnuCr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCrActionPerformed(evt);
            }
        });
        jMenu9.add(mnuCr);

        mnuDr.setText("Trade Debitors");
        mnuDr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDrActionPerformed(evt);
            }
        });
        jMenu9.add(mnuDr);
        jMenu9.add(jSeparator3);

        mnuLedger.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        mnuLedger.setText("Ledger");
        mnuLedger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLedgerActionPerformed(evt);
            }
        });
        jMenu9.add(mnuLedger);
        jMenu9.add(jSeparator2);

        mnuStatementofAccounts.setText("Statement of Accounts");
        mnuStatementofAccounts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStatementofAccountsActionPerformed(evt);
            }
        });
        jMenu9.add(mnuStatementofAccounts);

        mnuAgeingAnalysis.setText("Ageing Analysis");
        mnuAgeingAnalysis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAgeingAnalysisActionPerformed(evt);
            }
        });
        jMenu9.add(mnuAgeingAnalysis);

        mnuTrialBalance.setText("Trial Balance");
        mnuTrialBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTrialBalanceActionPerformed(evt);
            }
        });
        jMenu9.add(mnuTrialBalance);

        mnuProfitLoss.setText("Profit & Loss");
        mnuProfitLoss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuProfitLossActionPerformed(evt);
            }
        });
        jMenu9.add(mnuProfitLoss);

        mnuBalanceSheet.setText("Balance Sheet");
        mnuBalanceSheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBalanceSheetActionPerformed(evt);
            }
        });
        jMenu9.add(mnuBalanceSheet);

        jMenu3.add(jMenu9);

        jMenu10.setText("Receipt/Payment Reports");

        mnuPaymentRegister.setText("Payment Register");
        mnuPaymentRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPaymentRegisterActionPerformed(evt);
            }
        });
        jMenu10.add(mnuPaymentRegister);

        mnuReceiptRegister.setText("Receipt Register");
        mnuReceiptRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuReceiptRegisterActionPerformed(evt);
            }
        });
        jMenu10.add(mnuReceiptRegister);

        mnuChequestobeRealisedCleared.setText("Cheques to be Realised/Cleared");
        mnuChequestobeRealisedCleared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuChequestobeRealisedClearedActionPerformed(evt);
            }
        });
        jMenu10.add(mnuChequestobeRealisedCleared);

        mnuListofBouncedCheques.setText("List of Bounced Cheques(Payment/Receipt)");
        mnuListofBouncedCheques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuListofBouncedChequesActionPerformed(evt);
            }
        });
        jMenu10.add(mnuListofBouncedCheques);

        mnuChequesRealisedCleared.setText("Cheques Realised/Cleared");
        mnuChequesRealisedCleared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuChequesRealisedClearedActionPerformed(evt);
            }
        });
        jMenu10.add(mnuChequesRealisedCleared);

        jMenu3.add(jMenu10);

        mnuBrowser.setText("Browser");
        jMenu3.add(mnuBrowser);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Inventory");

        jMenu16.setText("Master");

        mnuStore.setText("Store");
        mnuStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStoreActionPerformed(evt);
            }
        });
        jMenu16.add(mnuStore);

        mnuSalesRepresentatives.setText("Sales Representatives");
        mnuSalesRepresentatives.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesRepresentativesActionPerformed(evt);
            }
        });
        jMenu16.add(mnuSalesRepresentatives);

        mnuUnitofMeasurement.setText("Unit of Measurement");
        mnuUnitofMeasurement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUnitofMeasurementActionPerformed(evt);
            }
        });
        jMenu16.add(mnuUnitofMeasurement);

        mnuUnitConvesion.setText("Unit Convesion");
        mnuUnitConvesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUnitConvesionActionPerformed(evt);
            }
        });
        jMenu16.add(mnuUnitConvesion);

        jMenu23.setText("Item Classification");

        mnuItemGroup.setText("Item Group");
        mnuItemGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemGroupActionPerformed(evt);
            }
        });
        jMenu23.add(mnuItemGroup);

        mnuItemCategory.setText("Item Category");
        mnuItemCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCategoryActionPerformed(evt);
            }
        });
        jMenu23.add(mnuItemCategory);

        mnuItemMaster.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mnuItemMaster.setText("Item Master");
        mnuItemMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemMasterActionPerformed(evt);
            }
        });
        jMenu23.add(mnuItemMaster);

        jMenu16.add(jMenu23);

        jMenu4.add(jMenu16);

        jMenu17.setText("Transaction");

        jMenu24.setText("Purchase");

        mnuPurchaseOrder.setText("Purchase Order");
        mnuPurchaseOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPurchaseOrderActionPerformed(evt);
            }
        });
        jMenu24.add(mnuPurchaseOrder);

        mnuGRN.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        mnuGRN.setText("Purchase Invoice (GRN)");
        mnuGRN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGRNActionPerformed(evt);
            }
        });
        jMenu24.add(mnuGRN);

        mnuPIGenerale.setText("Purchase Invoice (Generale)");
        jMenu24.add(mnuPIGenerale);

        mnuPerchesReturns.setText("Purchase Returns");
        jMenu24.add(mnuPerchesReturns);

        jMenu17.add(jMenu24);

        jMenu25.setText("Sales");

        mnuSalesQuotation.setText("Sales Order");
        mnuSalesQuotation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesQuotationActionPerformed(evt);
            }
        });
        jMenu25.add(mnuSalesQuotation);

        mnuDeliveryOrderNote.setText("Delivery Order Note");
        mnuDeliveryOrderNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDeliveryOrderNoteActionPerformed(evt);
            }
        });
        jMenu25.add(mnuDeliveryOrderNote);

        mnuSalesInvoiceAgainstDO.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuSalesInvoiceAgainstDO.setText("Sales Invoice Against DO");
        mnuSalesInvoiceAgainstDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesInvoiceAgainstDOActionPerformed(evt);
            }
        });
        jMenu25.add(mnuSalesInvoiceAgainstDO);

        mnuSalesInvoice.setText("Sales Invoice");
        jMenu25.add(mnuSalesInvoice);

        mnuSalesInvoiceService.setText("Sales Invoice (Service)");
        jMenu25.add(mnuSalesInvoiceService);

        mnuSalesReturn.setText("Sales Returns");
        jMenu25.add(mnuSalesReturn);

        jMenuItem1.setText("Create Pay Plan");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu25.add(jMenuItem1);

        jMenu17.add(jMenu25);

        jMenu26.setText("Stock");

        jMenu27.setText("Stock Transfer");

        mnuTransfer.setText("Transfer");
        mnuTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTransferActionPerformed(evt);
            }
        });
        jMenu27.add(mnuTransfer);

        mnuIssues.setText("Issues");
        jMenu27.add(mnuIssues);

        mnuReceipts.setText("Receipts");
        jMenu27.add(mnuReceipts);

        jMenu26.add(jMenu27);

        mnuOpeningCostPrice.setText("Opening Cost Price");
        jMenu26.add(mnuOpeningCostPrice);

        mnuStockOpeningBalance.setText("Stock Opening Balance");
        jMenu26.add(mnuStockOpeningBalance);

        mnuStockAdjustment.setText("Stock Adjustment");
        mnuStockAdjustment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockAdjustmentActionPerformed(evt);
            }
        });
        jMenu26.add(mnuStockAdjustment);

        mnuSalesPrice.setText("Sales Price");
        mnuSalesPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesPriceActionPerformed(evt);
            }
        });
        jMenu26.add(mnuSalesPrice);

        jMenu17.add(jMenu26);

        jMenu4.add(jMenu17);

        jMenu18.setText("Letter of Credit");

        jMenu28.setText("Import");

        mnuLCCreation.setText("LC Creation");
        jMenu28.add(mnuLCCreation);

        mnuExpenses.setText("Expenses");
        jMenu28.add(mnuExpenses);

        mnuShipment.setText("Shipment");
        jMenu28.add(mnuShipment);

        mnuTRSettlment.setText("T.R. Settlment");
        jMenu28.add(mnuTRSettlment);

        mnuLCInvoiceClose.setText("LC Invoice Close");
        jMenu28.add(mnuLCInvoiceClose);

        jMenu18.add(jMenu28);

        jMenu29.setText("Export");

        mnuLCAcceptance.setText("LC Acceptance");
        jMenu29.add(mnuLCAcceptance);

        mnuGoodsDelivery.setText("Goods Delivery");
        jMenu29.add(mnuGoodsDelivery);

        jMenu18.add(jMenu29);

        jMenu4.add(jMenu18);

        jMenu19.setText("LC Reports");

        jMenu30.setText("Import");

        mnuLCOpenRegister.setText("LC Open Register");
        jMenu30.add(mnuLCOpenRegister);

        mnuLCExpensesRegister.setText("LC Expenses Register");
        jMenu30.add(mnuLCExpensesRegister);

        mnuLCShipmentRegister.setText("LC Shipment Register");
        jMenu30.add(mnuLCShipmentRegister);

        mnuLCTRRegister.setText("LC T.R. Register");
        jMenu30.add(mnuLCTRRegister);

        mnuLCCloseRegister.setText("LC Close Register");
        jMenu30.add(mnuLCCloseRegister);

        jMenu19.add(jMenu30);

        jMenu31.setText("Export");

        mnuLCAcceptanceRegister.setText("LC Acceptance Register");
        jMenu31.add(mnuLCAcceptanceRegister);

        mnuGoodsDeliveryRegister.setText("Goods Delivery Register");
        jMenu31.add(mnuGoodsDeliveryRegister);

        jMenu19.add(jMenu31);

        jMenu4.add(jMenu19);

        jMenu20.setText("Analysis");

        mnuStockAgeingAnalysis.setText("Stock Ageing Analysis");
        jMenu20.add(mnuStockAgeingAnalysis);

        mnuSalesPriceAnalysis.setText("Sales Price Analysis");
        jMenu20.add(mnuSalesPriceAnalysis);

        mnuItemCostSalesPrice.setText("Item Cost/Sales Price");
        jMenu20.add(mnuItemCostSalesPrice);

        mnuStockMargin.setText("Stock Margin");
        jMenu20.add(mnuStockMargin);

        mnuIssueMargin.setText("Issue Margin");
        jMenu20.add(mnuIssueMargin);

        mnuInvoiceMargin.setText("Invoice Margin");
        jMenu20.add(mnuInvoiceMargin);

        jMenu32.setText("ABC Analysis");

        mnuDetailed.setText("Detailed");
        jMenu32.add(mnuDetailed);

        mnuSummary.setText("Summary");
        jMenu32.add(mnuSummary);

        jMenu20.add(jMenu32);

        mnuSleepingItem.setText("Sleeping Item");
        jMenu20.add(mnuSleepingItem);

        mnuItemQuantityLevel.setText("Item Quantity Level");
        jMenu20.add(mnuItemQuantityLevel);

        mnuNegativeStockPosition.setText("Negative Stock Position");
        jMenu20.add(mnuNegativeStockPosition);

        mnuSalesmanwiseAnalysis.setText("Salesman wise Analysis");
        jMenu20.add(mnuSalesmanwiseAnalysis);

        mnuCustomerwiseSalesAnalysis.setText("Customer wise Sales Analysis");
        jMenu20.add(mnuCustomerwiseSalesAnalysis);

        mnuCustomerMonthlySalesAnalysis.setText("Customer Monthly Sales Analysis");
        jMenu20.add(mnuCustomerMonthlySalesAnalysis);

        mnuRepresentativeMonthlySales.setText("Representative Monthly Sales");
        jMenu20.add(mnuRepresentativeMonthlySales);

        mnuRepresentativeMonthlyCollection.setText("Representative's Monthly Collection");
        jMenu20.add(mnuRepresentativeMonthlyCollection);

        jMenu4.add(jMenu20);

        jMenu21.setText("Reports");

        jMenu33.setText("Stock Reports");

        mnuItemEnquiry.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        mnuItemEnquiry.setText("Item Enquiry");
        mnuItemEnquiry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemEnquiryActionPerformed(evt);
            }
        });
        jMenu33.add(mnuItemEnquiry);

        mnuStockPosition.setText("Stock Position");
        mnuStockPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockPositionActionPerformed(evt);
            }
        });
        jMenu33.add(mnuStockPosition);

        mnuStockLedger.setText("Stock Ledger");
        mnuStockLedger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockLedgerActionPerformed(evt);
            }
        });
        jMenu33.add(mnuStockLedger);

        jMenuItem86.setText("Stock (Batch wise)");
        jMenuItem86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem86ActionPerformed(evt);
            }
        });
        jMenu33.add(jMenuItem86);

        mnuStockAdjustmentReport.setText("Stock Adjustment Report");
        mnuStockAdjustmentReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockAdjustmentReportActionPerformed(evt);
            }
        });
        jMenu33.add(mnuStockAdjustmentReport);

        jMenu21.add(jMenu33);

        jMenu34.setText("Purchase Report");

        mnuPurchaseOrderListing.setText("Purchase Order Listing ");
        mnuPurchaseOrderListing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPurchaseOrderListingActionPerformed(evt);
            }
        });
        jMenu34.add(mnuPurchaseOrderListing);

        mnuPendingPurchaseOrderListing.setText("Pending Purchase Order Listing");
        mnuPendingPurchaseOrderListing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPendingPurchaseOrderListingActionPerformed(evt);
            }
        });
        jMenu34.add(mnuPendingPurchaseOrderListing);

        mnuPurchaseRegister.setText("Purchase Register");
        mnuPurchaseRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPurchaseRegisterActionPerformed(evt);
            }
        });
        jMenu34.add(mnuPurchaseRegister);

        mnuPurchaseReturns.setText("Purchase Returns");
        mnuPurchaseReturns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPurchaseReturnsActionPerformed(evt);
            }
        });
        jMenu34.add(mnuPurchaseReturns);

        mnuPurchaseGeneralRegister.setText("Purchase (General) Register");
        mnuPurchaseGeneralRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPurchaseGeneralRegisterActionPerformed(evt);
            }
        });
        jMenu34.add(mnuPurchaseGeneralRegister);

        jMenu21.add(jMenu34);

        jMenu35.setText("Sales Reports");

        mnuSalesQuotationRegister.setText("Sales Quotation Register");
        mnuSalesQuotationRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesQuotationRegisterActionPerformed(evt);
            }
        });
        jMenu35.add(mnuSalesQuotationRegister);

        mnuODRegister.setText("DO Register");
        mnuODRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuODRegisterActionPerformed(evt);
            }
        });
        jMenu35.add(mnuODRegister);

        mnuSalesRegister.setText("Sales Register");
        mnuSalesRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesRegisterActionPerformed(evt);
            }
        });
        jMenu35.add(mnuSalesRegister);

        mnuSalesReturns.setText("Sales Returns");
        mnuSalesReturns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesReturnsActionPerformed(evt);
            }
        });
        jMenu35.add(mnuSalesReturns);

        mnuPendingODList.setText("Pending DO List");
        mnuPendingODList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPendingODListActionPerformed(evt);
            }
        });
        jMenu35.add(mnuPendingODList);

        mnuOutstandingInvoices.setText("Outstanding Invoices");
        mnuOutstandingInvoices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOutstandingInvoicesActionPerformed(evt);
            }
        });
        jMenu35.add(mnuOutstandingInvoices);

        mnuSalesServiceRegister.setText("Sales (Service) Register");
        mnuSalesServiceRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesServiceRegisterActionPerformed(evt);
            }
        });
        jMenu35.add(mnuSalesServiceRegister);

        mnuInvoiceListLockedUnlocked.setText("Invoice List (Locked/Unlocked)");
        mnuInvoiceListLockedUnlocked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInvoiceListLockedUnlockedActionPerformed(evt);
            }
        });
        jMenu35.add(mnuInvoiceListLockedUnlocked);

        jMenuItem3.setText("Daly Sales");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu35.add(jMenuItem3);

        jMenu21.add(jMenu35);

        jMenu36.setText("Transfer Reports");

        mnuIssuesReport.setText("Issues Report");
        mnuIssuesReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuIssuesReportActionPerformed(evt);
            }
        });
        jMenu36.add(mnuIssuesReport);

        mnuReceiptsReport.setText("Receipts Report");
        mnuReceiptsReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuReceiptsReportActionPerformed(evt);
            }
        });
        jMenu36.add(mnuReceiptsReport);

        mnuStockTransferIssuesReport.setText("Stock Transfer Issues Report");
        mnuStockTransferIssuesReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockTransferIssuesReportActionPerformed(evt);
            }
        });
        jMenu36.add(mnuStockTransferIssuesReport);

        mnuStockTransferReceiptsReport.setText("Stock Transfer Receipts Report");
        mnuStockTransferReceiptsReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockTransferReceiptsReportActionPerformed(evt);
            }
        });
        jMenu36.add(mnuStockTransferReceiptsReport);

        mnuStockTransferSummary.setText("Stock Transfer Summary");
        mnuStockTransferSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStockTransferSummaryActionPerformed(evt);
            }
        });
        jMenu36.add(mnuStockTransferSummary);

        jMenu21.add(jMenu36);

        jMenu4.add(jMenu21);

        jMenu22.setText("Master Reports");

        mnuItemMasterListing.setText("Item Master Listing");
        mnuItemMasterListing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemMasterListingActionPerformed(evt);
            }
        });
        jMenu22.add(mnuItemMasterListing);

        mnuPriceList.setText("Price List");
        mnuPriceList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPriceListActionPerformed(evt);
            }
        });
        jMenu22.add(mnuPriceList);

        jMenu4.add(jMenu22);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Graphs");

        mnuMonthlySalesAnalysis.setText("Monthly Sales Analysis");
        mnuMonthlySalesAnalysis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMonthlySalesAnalysisActionPerformed(evt);
            }
        });
        jMenu5.add(mnuMonthlySalesAnalysis);

        mnuItemSalesAnalysisMonthly.setText("Item Sales Analysis (Monthly)");
        jMenu5.add(mnuItemSalesAnalysisMonthly);

        mnuAgeingAnalysisGrp.setText("Ageing Analysis");
        jMenu5.add(mnuAgeingAnalysisGrp);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Utilities");

        mnuClearHistory.setText("Clear History");
        jMenu6.add(mnuClearHistory);

        mnuChangeCompanyLogo.setText("Change Company Logo");
        jMenu6.add(mnuChangeCompanyLogo);

        mnuYearEndProcess.setText("Year End Process");
        jMenu6.add(mnuYearEndProcess);

        mnuAlterTable.setText("Alter Table");
        jMenu6.add(mnuAlterTable);

        mnuReorganiseAccounts.setText("Reorganise Accounts");
        jMenu6.add(mnuReorganiseAccounts);

        mnuTransactionLockingDate.setText("Transaction Locking Date");
        jMenu6.add(mnuTransactionLockingDate);

        mnuChangeAccountName.setText("Change Account Name");
        jMenu6.add(mnuChangeAccountName);

        mnuMaintenance.setText("Maintenance");
        jMenu6.add(mnuMaintenance);

        mnuDailyReporte.setText("Daily Reporte");
        jMenu6.add(mnuDailyReporte);

        jMenuBar1.add(jMenu6);

        jMenu39.setText("Help");
        jMenu39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu39ActionPerformed(evt);
            }
        });

        mnuAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mnuAbout.setText("About");
        mnuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAboutActionPerformed(evt);
            }
        });
        jMenu39.add(mnuAbout);

        jMenuBar1.add(jMenu39);

        jMenu12.setText("Reports");

        mnuREPCustomerInvoiceDet.setText("Sales");

        jMenuItem9.setText("Sales Summery");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        mnuREPCustomerInvoiceDet.add(jMenuItem9);

        jMenuItem21.setText("Sales Summury (Sales Rep & Cost Center wise)");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        mnuREPCustomerInvoiceDet.add(jMenuItem21);

        jMenuItem18.setText("Sales History (Daily)");
        mnuREPCustomerInvoiceDet.add(jMenuItem18);

        jMenuItem17.setText("Sales History (Sales rep wise)");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        mnuREPCustomerInvoiceDet.add(jMenuItem17);

        jMenuItem19.setText("Sales History (Cost Center wise)");
        mnuREPCustomerInvoiceDet.add(jMenuItem19);

        jMenuItem20.setText("Sales History (Selected Category)");
        mnuREPCustomerInvoiceDet.add(jMenuItem20);

        jMenuItem13.setText("Sales Return");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        mnuREPCustomerInvoiceDet.add(jMenuItem13);

        jMenuItem22.setText("Sales Registry");
        mnuREPCustomerInvoiceDet.add(jMenuItem22);

        jMenu12.add(mnuREPCustomerInvoiceDet);

        jMenu38.setText("Purches");

        jMenuItem10.setText("GRN Summery");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu38.add(jMenuItem10);

        jMenu12.add(jMenu38);

        jMenu40.setText("Analysis");
        jMenu40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu40ActionPerformed(evt);
            }
        });

        jMenuItem5.setText("Item Flow Report");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu40.add(jMenuItem5);

        jMenuItem7.setText("Collection Summery");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu40.add(jMenuItem7);

        jMenuItem8.setText("Collection Det");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu40.add(jMenuItem8);

        jMenuItem12.setText("Stock Report");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu40.add(jMenuItem12);

        jMenuItem6.setText("Customer Invoice Det.");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu40.add(jMenuItem6);

        jMenuItem23.setText("Daily Sales Analysis");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu40.add(jMenuItem23);

        jMenuItem24.setText("Sales Analysis (Date Range)");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu40.add(jMenuItem24);

        jMenu12.add(jMenu40);

        jMenu41.setText("HP");

        jMenuItem4.setText("Client Payment History");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu41.add(jMenuItem4);

        jMenuItem11.setText("Client Arrears Report Summery");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu41.add(jMenuItem11);

        jMenuItem16.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem16.setText("Reminder");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu41.add(jMenuItem16);

        jMenu12.add(jMenu41);

        jMenuBar1.add(jMenu12);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcompany, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtWorkingDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLastUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ins, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cps, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtuser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtcompany, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtdate1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtLastUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtWorkingDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cps, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
    System.exit(0);
}//GEN-LAST:event_mnuExitActionPerformed

private void mnuToolBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuToolBarActionPerformed
    toolAction();
}//GEN-LAST:event_mnuToolBarActionPerformed

private void cmdCustomersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCustomersMouseClicked
    custMenu(evt);
}//GEN-LAST:event_cmdCustomersMouseClicked

private void cmdCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCustomersActionPerformed
}//GEN-LAST:event_cmdCustomersActionPerformed

private void cmdTransactionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdTransactionsMouseClicked
    transMenu(evt);
}//GEN-LAST:event_cmdTransactionsMouseClicked

private void cmdTransactionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTransactionsActionPerformed
}//GEN-LAST:event_cmdTransactionsActionPerformed

private void mnujournalvoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnujournalvoucherActionPerformed
    loadJournalVoucher();
}//GEN-LAST:event_mnujournalvoucherActionPerformed

private void txtWorkingDateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtWorkingDateKeyReleased
}//GEN-LAST:event_txtWorkingDateKeyReleased

private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
}//GEN-LAST:event_formWindowActivated

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    setLogo();
}//GEN-LAST:event_formWindowOpened

private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    setLogo();
}//GEN-LAST:event_formComponentResized

private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
}//GEN-LAST:event_formWindowStateChanged

private void mnuProfitLossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProfitLossActionPerformed
    GUIProfitnLoss fc = new GUIProfitnLoss();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuProfitLossActionPerformed

private void cmdInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdInventoryMouseClicked
    inventsMenu(evt);
}//GEN-LAST:event_cmdInventoryMouseClicked

private void cmdStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdStockMouseClicked
    stockMenu(evt);
}//GEN-LAST:event_cmdStockMouseClicked

private void cmdPurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdPurchaseMouseClicked
    purchaseMenu(evt);
}//GEN-LAST:event_cmdPurchaseMouseClicked

private void cmdSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdSalesMouseClicked
    salesMenu(evt);
}//GEN-LAST:event_cmdSalesMouseClicked

private void cmdCalculatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCalculatorActionPerformed
    setCal();
}//GEN-LAST:event_cmdCalculatorActionPerformed

private void mnujournalvoucher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnujournalvoucher1ActionPerformed
    loadJournalVoucher();
}//GEN-LAST:event_mnujournalvoucher1ActionPerformed

private void mnureceiptvoucherOA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnureceiptvoucherOA1ActionPerformed
    loadRecVoucherOA();
}//GEN-LAST:event_mnureceiptvoucherOA1ActionPerformed

private void mnuPerchesReturns1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPerchesReturns1ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_mnuPerchesReturns1ActionPerformed

private void mnucurrencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnucurrencyActionPerformed
    GUICurrency fc = new GUICurrency();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - 205, jDesktopPane1.getHeight() / 2 - 77, 410, 155);
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnucurrencyActionPerformed

private void jDesktopPane1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDesktopPane1PropertyChange
}//GEN-LAST:event_jDesktopPane1PropertyChange

private void jDesktopPane1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDesktopPane1ComponentResized
    Component[] c = jDesktopPane1.getComponents();
    for (Component component : c) {
        component.setBounds(jDesktopPane1.getWidth() / 2 - component.getWidth() / 2, jDesktopPane1.getHeight() / 2 - component.getHeight() / 2, component.getWidth(), component.getHeight());
    }
}//GEN-LAST:event_jDesktopPane1ComponentResized

private void mnuareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuareaActionPerformed
    GUIArea fc = new GUIArea();
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuareaActionPerformed

private void mnucostcenterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnucostcenterActionPerformed
    GUICostCenter fc = new GUICostCenter();
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnucostcenterActionPerformed

private void mnubankmasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnubankmasterActionPerformed
    GUIBankMaster fc = new GUIBankMaster();
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnubankmasterActionPerformed

private void mnupaymenttermsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnupaymenttermsActionPerformed
    GUIPayTerm fc = new GUIPayTerm();
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnupaymenttermsActionPerformed

private void mnucusgroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnucusgroupActionPerformed
    GUICustomerGroup fc = new GUICustomerGroup();
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnucusgroupActionPerformed

private void mnunarrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnunarrationActionPerformed
    GUINarration fc = new GUINarration();
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnunarrationActionPerformed

private void mnunewcustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnunewcustActionPerformed
    loadNewCustomer();
}//GEN-LAST:event_mnunewcustActionPerformed

private void cmdVendorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdVendorsActionPerformed
}//GEN-LAST:event_cmdVendorsActionPerformed

private void mnuvendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuvendorActionPerformed
    loadNewVendore();
}//GEN-LAST:event_mnuvendorActionPerformed

private void mnuaccountcreationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuaccountcreationActionPerformed
    GUIAccountCreation fc = new GUIAccountCreation();
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuaccountcreationActionPerformed

private void mnuaccountopblanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuaccountopblanceActionPerformed
    GUIAccountsOpeningBalance fc = new GUIAccountsOpeningBalance();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuaccountopblanceActionPerformed

private void mnupayvoucherOAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnupayvoucherOAActionPerformed
    loadPayVoucherOA();
}//GEN-LAST:event_mnupayvoucherOAActionPerformed

private void mnupayvoucherOA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnupayvoucherOA1ActionPerformed
    loadPayVoucherOA();
}//GEN-LAST:event_mnupayvoucherOA1ActionPerformed

private void mnuMonthlySalesAnalysisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMonthlySalesAnalysisActionPerformed
}//GEN-LAST:event_mnuMonthlySalesAnalysisActionPerformed

private void mnupayvoucherAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnupayvoucherAIActionPerformed
    loadPayAgI();
}//GEN-LAST:event_mnupayvoucherAIActionPerformed

private void mnupayvoucherAI1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnupayvoucherAI1ActionPerformed
    loadPayAgI();
}//GEN-LAST:event_mnupayvoucherAI1ActionPerformed

private void mnureceiptvoucherOAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnureceiptvoucherOAActionPerformed
    loadRecVoucherOA();
}//GEN-LAST:event_mnureceiptvoucherOAActionPerformed

private void mnureceiptvoucherAI1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnureceiptvoucherAI1ActionPerformed
    loadRecAgI();
}//GEN-LAST:event_mnureceiptvoucherAI1ActionPerformed

private void mnureceiptvoucherAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnureceiptvoucherAIActionPerformed
    loadRecAgI();
}//GEN-LAST:event_mnureceiptvoucherAIActionPerformed

private void mnupaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnupaymentActionPerformed
    loadPayRealized();
}//GEN-LAST:event_mnupaymentActionPerformed

private void mnureciptDisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnureciptDisActionPerformed
    loadRecRealized();
}//GEN-LAST:event_mnureciptDisActionPerformed

private void mnudebidnoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnudebidnoteActionPerformed
    loadDebitNote();
}//GEN-LAST:event_mnudebidnoteActionPerformed

private void mnucreditnoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnucreditnoteActionPerformed
    loadCreditNote();
}//GEN-LAST:event_mnucreditnoteActionPerformed

private void mnudebidnote1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnudebidnote1ActionPerformed
    loadDebitNote();
}//GEN-LAST:event_mnudebidnote1ActionPerformed

private void mnucreditnote1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnucreditnote1ActionPerformed
    loadCreditNote();
}//GEN-LAST:event_mnucreditnote1ActionPerformed

private void mnureconciliationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnureconciliationActionPerformed
    loadReconciliation();
}//GEN-LAST:event_mnureconciliationActionPerformed

private void mnuassignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuassignmentsActionPerformed
    loadAssignment();
}//GEN-LAST:event_mnuassignmentsActionPerformed

private void mnuChartofaccountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuChartofaccountsActionPerformed
    GUIChartofAccount fc = new GUIChartofAccount();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuChartofaccountsActionPerformed

private void mnuACBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuACBalanceActionPerformed
    GUIAccountBalance fc = new GUIAccountBalance();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuACBalanceActionPerformed

private void mnuJouinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuJouinalActionPerformed
    GUIJournalReport fc = new GUIJournalReport();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuJouinalActionPerformed

private void mnuLedgerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLedgerActionPerformed
    GUILedger fc = new GUILedger();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuLedgerActionPerformed

private void mnuStatementofAccountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStatementofAccountsActionPerformed
    GUIStatementofAccount fc = new GUIStatementofAccount();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuStatementofAccountsActionPerformed

private void mnuAgeingAnalysisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAgeingAnalysisActionPerformed
    GUIAgeingAnalysis fc = new GUIAgeingAnalysis();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuAgeingAnalysisActionPerformed

private void mnuTrialBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTrialBalanceActionPerformed
    GUITrialBalance fc = new GUITrialBalance();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuTrialBalanceActionPerformed

private void mnuBalanceSheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBalanceSheetActionPerformed
    GUIBalanceSheet fc = new GUIBalanceSheet();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuBalanceSheetActionPerformed

private void mnuPaymentRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPaymentRegisterActionPerformed
    GUIReceiptRegister fc = new GUIReceiptRegister();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuPaymentRegisterActionPerformed

private void mnuReceiptRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuReceiptRegisterActionPerformed
    GUIPaymentRegister fc = new GUIPaymentRegister();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuReceiptRegisterActionPerformed

private void mnuChequestobeRealisedClearedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuChequestobeRealisedClearedActionPerformed
    GUIChequestobeRealised fc = new GUIChequestobeRealised();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuChequestobeRealisedClearedActionPerformed

private void mnuListofBouncedChequesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuListofBouncedChequesActionPerformed
    GUIBouncedCheques fc = new GUIBouncedCheques();
    loadUi(fc);
}//GEN-LAST:event_mnuListofBouncedChequesActionPerformed

private void mnuChequesRealisedClearedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuChequesRealisedClearedActionPerformed
    GUIChequesRealised fc = new GUIChequesRealised();
    loadUi(fc);
}//GEN-LAST:event_mnuChequesRealisedClearedActionPerformed

private void mnuStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStoreActionPerformed
    loadStore();
}//GEN-LAST:event_mnuStoreActionPerformed

private void mnuSalesRepresentativesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesRepresentativesActionPerformed
    loadSalesRep();
}//GEN-LAST:event_mnuSalesRepresentativesActionPerformed

private void mnuUnitofMeasurementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUnitofMeasurementActionPerformed
    loadUnit();
}//GEN-LAST:event_mnuUnitofMeasurementActionPerformed

private void mnuUnitConvesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUnitConvesionActionPerformed
    loadUnitCon();
}//GEN-LAST:event_mnuUnitConvesionActionPerformed

private void mnuStore1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStore1ActionPerformed
    loadStore();
}//GEN-LAST:event_mnuStore1ActionPerformed

private void mnuUnitofMeasurement1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUnitofMeasurement1ActionPerformed
    loadUnit();
}//GEN-LAST:event_mnuUnitofMeasurement1ActionPerformed

private void mnuItemGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemGroupActionPerformed
    loadItemGroup();
}//GEN-LAST:event_mnuItemGroupActionPerformed

private void mnuItemCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCategoryActionPerformed
    loadItemCategory();
}//GEN-LAST:event_mnuItemCategoryActionPerformed

private void mnuItemMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemMasterActionPerformed
    loadItemMaster();
}//GEN-LAST:event_mnuItemMasterActionPerformed

private void mnuItemGroup1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemGroup1ActionPerformed
    loadItemGroup();
}//GEN-LAST:event_mnuItemGroup1ActionPerformed

private void mnuItemCategory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCategory1ActionPerformed
    loadItemCategory();
}//GEN-LAST:event_mnuItemCategory1ActionPerformed

private void mnuItemMaster1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemMaster1ActionPerformed
    loadItemMaster();
}//GEN-LAST:event_mnuItemMaster1ActionPerformed

private void mnuPurchaseOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPurchaseOrderActionPerformed
    loadPurchaseOrder();
}//GEN-LAST:event_mnuPurchaseOrderActionPerformed

private void mnuDeliveryOrderNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDeliveryOrderNoteActionPerformed
    loadDelivaryON();
}//GEN-LAST:event_mnuDeliveryOrderNoteActionPerformed

private void mnuSalesInvoiceAgainstDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesInvoiceAgainstDOActionPerformed
    loadSalesInvoiceDO();
}//GEN-LAST:event_mnuSalesInvoiceAgainstDOActionPerformed

private void mnuPurchaseOrder1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPurchaseOrder1ActionPerformed
    loadPurchaseOrder();
}//GEN-LAST:event_mnuPurchaseOrder1ActionPerformed

private void mnuDeliveryOrderNote1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDeliveryOrderNote1ActionPerformed
    loadDelivaryON();
}//GEN-LAST:event_mnuDeliveryOrderNote1ActionPerformed

private void mnuSalesInvoiceAgainstDO1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesInvoiceAgainstDO1ActionPerformed
    loadSalesInvoiceDO();
}//GEN-LAST:event_mnuSalesInvoiceAgainstDO1ActionPerformed

private void mnuSalesQuotationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesQuotationActionPerformed
    loadSalesOrder();
}//GEN-LAST:event_mnuSalesQuotationActionPerformed

private void mnuSalesQuotation1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesQuotation1ActionPerformed
    loadSalesOrder();
}//GEN-LAST:event_mnuSalesQuotation1ActionPerformed

private void mnuSalesQuotationRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesQuotationRegisterActionPerformed
    loadSalesQuotationRegister();
}//GEN-LAST:event_mnuSalesQuotationRegisterActionPerformed

private void mnuODRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuODRegisterActionPerformed
    loadDORegister();
}//GEN-LAST:event_mnuODRegisterActionPerformed

private void mnuSalesRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesRegisterActionPerformed
    loadSalesRegister();
}//GEN-LAST:event_mnuSalesRegisterActionPerformed

private void mnuSalesReturnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesReturnsActionPerformed
    loadSalesReturnRegister();
}//GEN-LAST:event_mnuSalesReturnsActionPerformed

private void mnuPendingODListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPendingODListActionPerformed
    loadPendingDOListRegister();
}//GEN-LAST:event_mnuPendingODListActionPerformed

private void mnuOutstandingInvoicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOutstandingInvoicesActionPerformed
    loadOutstandingInvoiceRegister();
}//GEN-LAST:event_mnuOutstandingInvoicesActionPerformed

private void mnuSalesServiceRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesServiceRegisterActionPerformed
    loadSalesServiceRegister();
}//GEN-LAST:event_mnuSalesServiceRegisterActionPerformed

private void mnuPurchaseOrderListingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPurchaseOrderListingActionPerformed
    GUIPOList fc = new GUIPOList();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuPurchaseOrderListingActionPerformed

private void mnuPendingPurchaseOrderListingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPendingPurchaseOrderListingActionPerformed
    GUIPendingPOList fc = new GUIPendingPOList();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuPendingPurchaseOrderListingActionPerformed

private void mnuPurchaseRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPurchaseRegisterActionPerformed
    GUIPurchaseRegister fc = new GUIPurchaseRegister();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuPurchaseRegisterActionPerformed

private void mnuPurchaseReturnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPurchaseReturnsActionPerformed
    GUIPurchaseReturnRegister fc = new GUIPurchaseReturnRegister();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuPurchaseReturnsActionPerformed

private void mnuPurchaseGeneralRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPurchaseGeneralRegisterActionPerformed
    GUIPurchaseGeneralRegister fc = new GUIPurchaseGeneralRegister();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuPurchaseGeneralRegisterActionPerformed

private void mnuStockPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockPositionActionPerformed
    GUIStockPosition fc = new GUIStockPosition();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuStockPositionActionPerformed

private void mnuStockLedgerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockLedgerActionPerformed
    GUIStockLedger fc = new GUIStockLedger();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuStockLedgerActionPerformed

private void jMenuItem86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem86ActionPerformed
    GUIStockReportBatchWise fc = new GUIStockReportBatchWise();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_jMenuItem86ActionPerformed

private void mnuStockAdjustmentReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockAdjustmentReportActionPerformed
    GUIStockAdjustmentReport fc = new GUIStockAdjustmentReport();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuStockAdjustmentReportActionPerformed

private void mnuIssuesReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuIssuesReportActionPerformed
    GUIIssueReport fc = new GUIIssueReport();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuIssuesReportActionPerformed

private void mnuReceiptsReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuReceiptsReportActionPerformed
    GUIReciptReport fc = new GUIReciptReport();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuReceiptsReportActionPerformed

private void mnuStockTransferIssuesReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockTransferIssuesReportActionPerformed
    GUITransferIssuesReport fc = new GUITransferIssuesReport();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuStockTransferIssuesReportActionPerformed

private void mnuStockTransferReceiptsReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockTransferReceiptsReportActionPerformed
    GUITransferReceiptReport fc = new GUITransferReceiptReport();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuStockTransferReceiptsReportActionPerformed

private void mnuStockTransferSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockTransferSummaryActionPerformed
    GUITransferSummaryReport fc = new GUITransferSummaryReport();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuStockTransferSummaryActionPerformed

private void mnuItemMasterListingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemMasterListingActionPerformed
    GUIItemList fc = new GUIItemList();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuItemMasterListingActionPerformed

private void mnuPriceListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPriceListActionPerformed
    GUIPriceList fc = new GUIPriceList();
    SwingUtilities.updateComponentTreeUI(fc);
    fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
    jDesktopPane1.add(fc);
    fc.setVisible(true);
}//GEN-LAST:event_mnuPriceListActionPerformed

    private void mnuGRNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGRNActionPerformed
        loadPurches();
    }//GEN-LAST:event_mnuGRNActionPerformed

    private void cmdPurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPurchaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdPurchaseActionPerformed

    private void mnuGRN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGRN1ActionPerformed
        loadPurches();
    }//GEN-LAST:event_mnuGRN1ActionPerformed

    private void mnuPayPlan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPayPlan1ActionPerformed
        new GUIInvPayPlan(null, true).setVisible(true);
    }//GEN-LAST:event_mnuPayPlan1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new GUIInvPayPlan(null, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mnuTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTransferActionPerformed
        GUIStockTransfer fc = new GUIStockTransfer();
        SwingUtilities.updateComponentTreeUI(fc);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }//GEN-LAST:event_mnuTransferActionPerformed

    private void mnuTransfer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTransfer1ActionPerformed
        GUIStockTransfer fc = new GUIStockTransfer();
        SwingUtilities.updateComponentTreeUI(fc);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }//GEN-LAST:event_mnuTransfer1ActionPerformed

    private void txtWorkingDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtWorkingDateMouseClicked

    }//GEN-LAST:event_txtWorkingDateMouseClicked

    private void mnuSalesPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesPriceActionPerformed
        try {
            new PriceList().setVisible(true);
        } catch (Exception ex) {
            Exp.Handle(ex);
        }
    }//GEN-LAST:event_mnuSalesPriceActionPerformed

    private void StockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StockActionPerformed

    }//GEN-LAST:event_StockActionPerformed

    private void mnuSalesPrice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesPrice1ActionPerformed
        try {
            new PriceList().setVisible(true);
        } catch (Exception ex) {
            Exp.Handle(ex);
        }
    }//GEN-LAST:event_mnuSalesPrice1ActionPerformed

    private void mnunewcust1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnunewcust1ActionPerformed
        loadNewCustomer();
    }//GEN-LAST:event_mnunewcust1ActionPerformed

    private void mnusearchcustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnusearchcustActionPerformed
        loadSearchCustomer();
    }//GEN-LAST:event_mnusearchcustActionPerformed

    private void mnusearchcust1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnusearchcust1ActionPerformed
        loadSearchCustomer();
    }//GEN-LAST:event_mnusearchcust1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        loadDepo();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mnuCrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCrActionPerformed
        try {
            new Cr().setVisible(true);
        } catch (Exception ex) {
            Exp.Handle(ex);
        }
    }//GEN-LAST:event_mnuCrActionPerformed

    private void mnuDrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDrActionPerformed
        try {
            new Dr().setVisible(true);
        } catch (Exception ex) {
            Exp.Handle(ex);
        }
    }//GEN-LAST:event_mnuDrActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        GUISalesRegister fc = new GUISalesRegister();
        SwingUtilities.updateComponentTreeUI(fc);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void mnuInvoiceListLockedUnlockedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInvoiceListLockedUnlockedActionPerformed
        GUISalesRegister fc = new GUISalesRegister();
        SwingUtilities.updateComponentTreeUI(fc);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }//GEN-LAST:event_mnuInvoiceListLockedUnlockedActionPerformed

    private void mnuSalesReturn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesReturn1ActionPerformed
        loadSalesReturn();
    }//GEN-LAST:event_mnuSalesReturn1ActionPerformed

    private void txtLastUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLastUpdateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastUpdateMouseClicked

    private void txtLastUpdateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLastUpdateKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastUpdateKeyReleased

    private void cmdLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLanguageActionPerformed
        doDayEnd();
    }//GEN-LAST:event_cmdLanguageActionPerformed

    private void mnuItemEnquiryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemEnquiryActionPerformed
        doInquery();
    }//GEN-LAST:event_mnuItemEnquiryActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        repPaymentHistory();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu40ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        repItemFlow();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        repCustomerInvoiceDet();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        repCollectionSum();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        repCollectionDet();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        repSalesSum();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        repGRNSum();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        repClientArrearsReportSum();
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void mnuStockAdjustmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStockAdjustmentActionPerformed
        loadStockAdjustment();
    }//GEN-LAST:event_mnuStockAdjustmentActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        repStockReport();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        repSalesReturnReport();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void cmdLanguage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLanguage1ActionPerformed
        new GUIDBBackup(this, true).setVisible(true);
    }//GEN-LAST:event_cmdLanguage1ActionPerformed

    private void cmdVendorsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdVendorsMouseClicked
        venMenu(evt);
    }//GEN-LAST:event_cmdVendorsMouseClicked

    private void mnunewven1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnunewven1ActionPerformed
        loadNewVendore();
    }//GEN-LAST:event_mnunewven1ActionPerformed

    private void mnusearchven1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnusearchven1ActionPerformed
        loadSearchVendore();
    }//GEN-LAST:event_mnusearchven1ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        loadRem();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jMenu39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu39ActionPerformed

    private void mnuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAboutActionPerformed
    }//GEN-LAST:event_mnuAboutActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        loadJvcancel(TransactionType.VOUCHER);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenu14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu14ActionPerformed

    }//GEN-LAST:event_jMenu14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        loadJvcancel(TransactionType.RECEIPT);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void cmdCalculator2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCalculator2ActionPerformed
        loadHPLoanCard();
    }//GEN-LAST:event_cmdCalculator2ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        GUIArrearsRep fc = new GUIArrearsRep();
        SwingUtilities.updateComponentTreeUI(fc);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        repSalesSummerySrepCC();
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        repDailySalesAnalysis();
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        repSalesAnalysis();
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Customer;
    private javax.swing.JMenu Inventory;
    private javax.swing.JMenu Purchase;
    private javax.swing.JMenu Sales;
    private javax.swing.JMenu Stock;
    private javax.swing.JMenu Transaction;
    private javax.swing.JMenu Vendore;
    private javax.swing.JButton cmdCalculator;
    private javax.swing.JButton cmdCalculator2;
    private javax.swing.JButton cmdCustomers;
    private javax.swing.JButton cmdInventory;
    private javax.swing.JButton cmdLanguage;
    private javax.swing.JButton cmdLanguage1;
    private javax.swing.JButton cmdPurchase;
    private javax.swing.JButton cmdSales;
    private javax.swing.JButton cmdStock;
    private javax.swing.JButton cmdTransactions;
    private javax.swing.JButton cmdVendors;
    public javax.swing.JLabel cps;
    private javax.swing.JLabel ins;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu18;
    private javax.swing.JMenu jMenu19;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu20;
    private javax.swing.JMenu jMenu21;
    private javax.swing.JMenu jMenu22;
    private javax.swing.JMenu jMenu23;
    private javax.swing.JMenu jMenu24;
    private javax.swing.JMenu jMenu25;
    private javax.swing.JMenu jMenu26;
    private javax.swing.JMenu jMenu27;
    private javax.swing.JMenu jMenu28;
    private javax.swing.JMenu jMenu29;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu30;
    private javax.swing.JMenu jMenu31;
    private javax.swing.JMenu jMenu32;
    private javax.swing.JMenu jMenu33;
    private javax.swing.JMenu jMenu34;
    private javax.swing.JMenu jMenu35;
    private javax.swing.JMenu jMenu36;
    private javax.swing.JMenu jMenu38;
    private javax.swing.JMenu jMenu39;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu40;
    private javax.swing.JMenu jMenu41;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem86;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    public javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem mnuACBalance;
    private javax.swing.JMenuItem mnuAbout;
    private javax.swing.JMenuItem mnuAgeingAnalysis;
    private javax.swing.JMenuItem mnuAgeingAnalysisGrp;
    private javax.swing.JMenuItem mnuAlterTable;
    private javax.swing.JMenuItem mnuBalanceSheet;
    private javax.swing.JMenuItem mnuBrowser;
    private javax.swing.JMenuItem mnuChangeAccountName;
    private javax.swing.JMenuItem mnuChangeCompanyLogo;
    private javax.swing.JMenuItem mnuChartofaccounts;
    private javax.swing.JMenuItem mnuChequesRealisedCleared;
    private javax.swing.JMenuItem mnuChequestobeRealisedCleared;
    private javax.swing.JMenuItem mnuClearHistory;
    private javax.swing.JMenuItem mnuCr;
    private javax.swing.JMenuItem mnuCreateUser;
    private javax.swing.JMenuItem mnuCustomerMonthlySalesAnalysis;
    private javax.swing.JMenuItem mnuCustomerwiseSalesAnalysis;
    private javax.swing.JMenuItem mnuDailyReporte;
    private javax.swing.JMenuItem mnuDeliveryOrderNote;
    private javax.swing.JMenuItem mnuDeliveryOrderNote1;
    private javax.swing.JMenuItem mnuDetailed;
    private javax.swing.JMenuItem mnuDr;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuExpenses;
    private javax.swing.JMenuItem mnuGRN;
    private javax.swing.JMenuItem mnuGRN1;
    private javax.swing.JMenuItem mnuGoodsDelivery;
    private javax.swing.JMenuItem mnuGoodsDeliveryRegister;
    private javax.swing.JMenuItem mnuInvoiceListLockedUnlocked;
    private javax.swing.JMenuItem mnuInvoiceMargin;
    private javax.swing.JMenuItem mnuIssueMargin;
    private javax.swing.JMenuItem mnuIssues;
    private javax.swing.JMenuItem mnuIssues1;
    private javax.swing.JMenuItem mnuIssuesReport;
    private javax.swing.JMenuItem mnuItemCategory;
    private javax.swing.JMenuItem mnuItemCategory1;
    private javax.swing.JMenuItem mnuItemCostSalesPrice;
    private javax.swing.JMenuItem mnuItemEnquiry;
    private javax.swing.JMenuItem mnuItemGroup;
    private javax.swing.JMenuItem mnuItemGroup1;
    private javax.swing.JMenuItem mnuItemMaster;
    private javax.swing.JMenuItem mnuItemMaster1;
    private javax.swing.JMenuItem mnuItemMasterListing;
    private javax.swing.JMenuItem mnuItemQuantityLevel;
    private javax.swing.JMenuItem mnuItemSalesAnalysisMonthly;
    private javax.swing.JMenuItem mnuJouinal;
    private javax.swing.JMenuItem mnuLCAcceptance;
    private javax.swing.JMenuItem mnuLCAcceptanceRegister;
    private javax.swing.JMenuItem mnuLCCloseRegister;
    private javax.swing.JMenuItem mnuLCCreation;
    private javax.swing.JMenuItem mnuLCExpensesRegister;
    private javax.swing.JMenuItem mnuLCInvoiceClose;
    private javax.swing.JMenuItem mnuLCOpenRegister;
    private javax.swing.JMenuItem mnuLCShipmentRegister;
    private javax.swing.JMenuItem mnuLCTRRegister;
    private javax.swing.JMenuItem mnuLedger;
    private javax.swing.JMenuItem mnuListofBouncedCheques;
    private javax.swing.JMenuItem mnuMaintenance;
    private javax.swing.JMenuItem mnuMonthlySalesAnalysis;
    private javax.swing.JMenuItem mnuNegativeStockPosition;
    private javax.swing.JMenuItem mnuODRegister;
    private javax.swing.JMenuItem mnuOpeningCostPrice;
    private javax.swing.JMenuItem mnuOutstandingInvoices;
    private javax.swing.JMenuItem mnuPIGenerale;
    private javax.swing.JMenuItem mnuPIGenerale1;
    private javax.swing.JMenuItem mnuPayPlan1;
    private javax.swing.JMenuItem mnuPaymentRegister;
    private javax.swing.JMenuItem mnuPendingODList;
    private javax.swing.JMenuItem mnuPendingPurchaseOrderListing;
    private javax.swing.JMenuItem mnuPerchesReturns;
    private javax.swing.JMenuItem mnuPerchesReturns1;
    private javax.swing.JMenuItem mnuPriceList;
    private javax.swing.JMenuItem mnuProfitLoss;
    private javax.swing.JMenuItem mnuPurchaseGeneralRegister;
    private javax.swing.JMenuItem mnuPurchaseOrder;
    private javax.swing.JMenuItem mnuPurchaseOrder1;
    private javax.swing.JMenuItem mnuPurchaseOrderListing;
    private javax.swing.JMenuItem mnuPurchaseRegister;
    private javax.swing.JMenuItem mnuPurchaseReturns;
    private javax.swing.JMenu mnuREPCustomerInvoiceDet;
    private javax.swing.JMenuItem mnuReceiptRegister;
    private javax.swing.JMenuItem mnuReceipts;
    private javax.swing.JMenuItem mnuReceipts1;
    private javax.swing.JMenuItem mnuReceiptsReport;
    private javax.swing.JMenuItem mnuReorganiseAccounts;
    private javax.swing.JMenuItem mnuRepresentativeMonthlyCollection;
    private javax.swing.JMenuItem mnuRepresentativeMonthlySales;
    private javax.swing.JMenuItem mnuSalesInvoice;
    private javax.swing.JMenuItem mnuSalesInvoice1;
    private javax.swing.JMenuItem mnuSalesInvoiceAgainstDO;
    private javax.swing.JMenuItem mnuSalesInvoiceAgainstDO1;
    private javax.swing.JMenuItem mnuSalesInvoiceService;
    private javax.swing.JMenuItem mnuSalesInvoiceService1;
    private javax.swing.JMenuItem mnuSalesPrice;
    private javax.swing.JMenuItem mnuSalesPrice1;
    private javax.swing.JMenuItem mnuSalesPriceAnalysis;
    private javax.swing.JMenuItem mnuSalesQuotation;
    private javax.swing.JMenuItem mnuSalesQuotation1;
    private javax.swing.JMenuItem mnuSalesQuotationRegister;
    private javax.swing.JMenuItem mnuSalesRegister;
    private javax.swing.JMenuItem mnuSalesRepresentatives;
    private javax.swing.JMenuItem mnuSalesReturn;
    private javax.swing.JMenuItem mnuSalesReturn1;
    private javax.swing.JMenuItem mnuSalesReturns;
    private javax.swing.JMenuItem mnuSalesServiceRegister;
    private javax.swing.JMenuItem mnuSalesmanwiseAnalysis;
    private javax.swing.JMenuItem mnuShipment;
    private javax.swing.JMenuItem mnuSleepingItem;
    private javax.swing.JMenuItem mnuStatementofAccounts;
    private javax.swing.JMenuItem mnuStockAdjustment;
    private javax.swing.JMenuItem mnuStockAdjustmentReport;
    private javax.swing.JMenuItem mnuStockAgeingAnalysis;
    private javax.swing.JMenuItem mnuStockLedger;
    private javax.swing.JMenuItem mnuStockMargin;
    private javax.swing.JMenuItem mnuStockOpeningBalance;
    private javax.swing.JMenuItem mnuStockPosition;
    private javax.swing.JMenuItem mnuStockTransferIssuesReport;
    private javax.swing.JMenuItem mnuStockTransferReceiptsReport;
    private javax.swing.JMenuItem mnuStockTransferSummary;
    private javax.swing.JMenuItem mnuStore;
    private javax.swing.JMenuItem mnuStore1;
    private javax.swing.JMenuItem mnuSummary;
    private javax.swing.JMenuItem mnuTRSettlment;
    private javax.swing.JCheckBoxMenuItem mnuToolBar;
    private javax.swing.JMenuItem mnuTransactionLockingDate;
    private javax.swing.JMenuItem mnuTransfer;
    private javax.swing.JMenuItem mnuTransfer1;
    private javax.swing.JMenuItem mnuTrialBalance;
    private javax.swing.JMenuItem mnuUnitConvesion;
    private javax.swing.JMenuItem mnuUnitofMeasurement;
    private javax.swing.JMenuItem mnuUnitofMeasurement1;
    private javax.swing.JMenuItem mnuYearEndProcess;
    private javax.swing.JMenuItem mnuaccountcreation;
    private javax.swing.JMenuItem mnuaccountopblance;
    private javax.swing.JMenuItem mnuarea;
    private javax.swing.JMenuItem mnuassignments;
    private javax.swing.JMenuItem mnubankmaster;
    private javax.swing.JMenuItem mnucostcenter;
    private javax.swing.JMenuItem mnucreditnote;
    private javax.swing.JMenuItem mnucreditnote1;
    private javax.swing.JMenuItem mnucurrency;
    private javax.swing.JMenuItem mnucusgroup;
    private javax.swing.JMenuItem mnudebidnote;
    private javax.swing.JMenuItem mnudebidnote1;
    private javax.swing.JMenuItem mnujournalvoucher;
    private javax.swing.JMenuItem mnujournalvoucher1;
    private javax.swing.JMenuItem mnunarration;
    private javax.swing.JMenuItem mnunewcust;
    private javax.swing.JMenuItem mnunewcust1;
    private javax.swing.JMenuItem mnunewven1;
    private javax.swing.JMenuItem mnupayment;
    private javax.swing.JMenuItem mnupaymentterms;
    private javax.swing.JMenuItem mnupayvoucherAI;
    private javax.swing.JMenuItem mnupayvoucherAI1;
    private javax.swing.JMenuItem mnupayvoucherOA;
    private javax.swing.JMenuItem mnupayvoucherOA1;
    private javax.swing.JMenuItem mnureceiptvoucherAI;
    private javax.swing.JMenuItem mnureceiptvoucherAI1;
    private javax.swing.JMenuItem mnureceiptvoucherOA;
    private javax.swing.JMenuItem mnureceiptvoucherOA1;
    private javax.swing.JMenuItem mnureciptDis;
    private javax.swing.JMenuItem mnureconciliation;
    private javax.swing.JMenuItem mnusearchcust;
    private javax.swing.JMenuItem mnusearchcust1;
    private javax.swing.JMenuItem mnusearchven1;
    private javax.swing.JMenuItem mnuvendor;
    private javax.swing.JPopupMenu pm;
    private javax.swing.JLabel txtDB;
    private javax.swing.JLabel txtLastUpdate;
    private javax.swing.JLabel txtWorkingDate;
    private javax.swing.JLabel txtcompany;
    private javax.swing.JLabel txtdate;
    private javax.swing.JLabel txtdate1;
    private javax.swing.JLabel txtlogo;
    private javax.swing.JLabel txtuser;
    // End of variables declaration//GEN-END:variables
    // private GUIConsole guiConsole;
    //   private Toolkit tk = Toolkit.getDefaultToolkit();
    public static int f = 0;
    public static String val = "";

    private void toolAction() {
        if (mnuToolBar.isSelected()) {
            jToolBar1.setVisible(true);
        } else {
            jToolBar1.setVisible(!true);
        }
    }

    private void transMenu(MouseEvent evt) {
        pm.removeAll();
        pm.add(mnujournalvoucher1);
        pm.add(mnupayvoucherOA1);
        pm.add(mnupayvoucherAI1);
        pm.add(mnureceiptvoucherOA1);
        pm.add(mnureceiptvoucherAI1);
        pm.add(mnudebidnote1);
        pm.add(mnucreditnote1);
        pm.show(evt.getComponent(), 0, 54);
    }

    private void custMenu(MouseEvent evt) {

        pm.removeAll();
        pm.add(mnunewcust1);
        pm.add(mnusearchcust1);

        pm.show(evt.getComponent(), 0, 54);
    }

    private void venMenu(MouseEvent evt) {

        pm.removeAll();
        pm.add(mnunewven1);
        pm.add(mnusearchven1);

        pm.show(evt.getComponent(), 0, 54);
    }

    private void inventsMenu(MouseEvent evt) {
        pm.removeAll();

        pm.add(mnuStore1);
        pm.add(mnuUnitofMeasurement1);
        pm.add(mnuItemGroup1);
        pm.add(mnuItemCategory1);
        pm.add(mnuItemMaster1);
        pm.show(evt.getComponent(), 0, 54);
    }

    private void stockMenu(MouseEvent evt) {
        pm.removeAll();
        pm.add(mnuTransfer1);
        pm.add(mnuIssues1);
        pm.add(mnuReceipts1);
        pm.add(mnuSalesPrice1);
        pm.show(evt.getComponent(), 0, 54);
    }

    private void purchaseMenu(MouseEvent evt) {
        pm.removeAll();
        pm.add(mnuPurchaseOrder1);
        pm.add(mnuGRN1);
        pm.add(mnuPIGenerale1);
        pm.add(mnuPerchesReturns1);
        pm.show(evt.getComponent(), 0, 54);
    }

    private void salesMenu(MouseEvent evt) {
        pm.removeAll();
        pm.add(mnuSalesQuotation1);
        pm.add(mnuDeliveryOrderNote1);  
        pm.add(mnuSalesInvoiceAgainstDO1);
        pm.add(mnuSalesInvoice1);
        pm.add(mnuSalesInvoiceService1);
        pm.add(mnuSalesReturn1);
        pm.add(mnuPayPlan1);
        pm.show(evt.getComponent(), 0, 54);
    }

    private void setCal() {
        try {
            Process pr = Runtime.getRuntime().exec("calc.exe");
        } catch (IOException e) {
            Exp.Handle(e);
        }
    }

    private void loadJournalVoucher() {
        GUIJournalVoucher fc = new GUIJournalVoucher();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadPayVoucherOA() {
        GUIPayOnAccount fc = new GUIPayOnAccount();
        loadUi(fc);
    }

    private void loadPayAgI() {
        GUIPayAgainstInvoice fc = new GUIPayAgainstInvoice();
        loadUi(fc);
    }

    private void loadRecVoucherOA() {
        GUIRecOnAccount fc = new GUIRecOnAccount();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadRecAgI() {
        GUIRecAgainstInvoice fc = new GUIRecAgainstInvoice();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadRecAgI(String a) {
        GUIRecAgainstInvoice fc = new GUIRecAgainstInvoice(a);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadPayRealized() {
        GUIRecRealizationPayment fc = new GUIRecRealizationPayment();
        fc.setBounds(
                jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2,
                jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2,
                fc.getWidth(),
                fc.getHeight());

        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadRecRealized() {
        GUIRecRealizationReceipt fc = new GUIRecRealizationReceipt();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadDebitNote() {
        GUIDebitNote fc = new GUIDebitNote();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadCreditNote() {
        GUICredit fc = new GUICredit();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadReconciliation() {
//        GUIReconciliation fc = new GUIReconciliation();
//        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
//        jDesktopPane1.add(fc);
//        fc.setVisible(true);
    }

    private void loadAssignment() {
        GUIAssignment fc = new GUIAssignment();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    /*
     * ------------- Vendore ----------------
     */
    public void loadNewVendore() {
        GUIVendor fc = new GUIVendor();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    public void loadSearchVendore() {
        GUISearchVendor fc = new GUISearchVendor();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    public void loadJvcancel(String transactionType) {
        VoucherCancel fc = new VoucherCancel(transactionType);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    /*
     * ------------- Customer ----------------
     */
    public void loadNewCustomer() {
        GUICustomer fc = new GUICustomer();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    public void loadCustomer(String s) {
        GUICustomer fc = new GUICustomer(s);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSearchCustomer() {
        GUISearchCustomer fc = new GUISearchCustomer();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadDepo() {
        GUIDeponent fc = new GUIDeponent();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    /*
     * ------------- Inventory ----------------
     */
    private void loadStore() {
        GUIStore fc = new GUIStore();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesRep() {
        GUISalesRep fc = new GUISalesRep();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadUnit() {
        GUIUnit fc = new GUIUnit();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadUnitCon() {
        GUIUnitConversion fc = new GUIUnitConversion();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadItemGroup() {
        GUIItemGroup fc = new GUIItemGroup();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadItemCategory() {
        GUIItemCategory fc = new GUIItemCategory();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadItemMaster() {
        GUIItemMaster fc = new GUIItemMaster();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadPurchaseOrder() {
        GUIPurchaseOrder fc = new GUIPurchaseOrder();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadDelivaryON() {
        GUIDelivaryON fc = new GUIDelivaryON();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadPurches() {
        GUIPurchase fc = new GUIPurchase();
        SwingUtilities.updateComponentTreeUI(fc);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadPurches(String val) {
        GUIPurchase fc = new GUIPurchase(val);
        SwingUtilities.updateComponentTreeUI(fc);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesInvoiceDO() {
        GUISalesInvoiceQO fc = new GUISalesInvoiceQO();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesInvoiceDOS(String s) {
        GUISalesInvoiceQO fc = new GUISalesInvoiceQO(s);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesInvoiceDOS(String s, String ss) {
        GUISalesInvoiceQO fc = new GUISalesInvoiceQO(s, ss);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesOrder() {
        GUISalesQuotation fc = new GUISalesQuotation();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesQuotationRegister() {
        GUIQuotationrRegister fc = new GUIQuotationrRegister();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadDORegister() {
        GUIDelivaryOrderRegister fc = new GUIDelivaryOrderRegister();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesRegister() {
        GUISalesRegister fc = new GUISalesRegister();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void doInquery() {
        GUIItemInquery fc = new GUIItemInquery();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesReturnRegister() {
        GUISalesReturnRegister fc = new GUISalesReturnRegister();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadPendingDOListRegister() {
        GUIPendingDOList fc = new GUIPendingDOList();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadOutstandingInvoiceRegister() {
        GUIOutstandingInvoice fc = new GUIOutstandingInvoice();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesServiceRegister() {
        GUISalesServiceRegister fc = new GUISalesServiceRegister();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadSalesReturn() {
        GUISalesRetern fc = new GUISalesRetern();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void loadStockAdjustment() {
        GUIStockAdjesment fc = new GUIStockAdjesment();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    //HP Loan Card
    private void loadHPLoanCard() {
        GUIHPLoanCard fc = new GUIHPLoanCard();
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void setCaps(boolean caps) {
        if (caps) {
            cps.setForeground(Color.BLACK);
        } else {
            cps.setForeground(Color.DARK_GRAY);
        }
    }

    private void setStatus() {
        txtuser.setText("   User : " + accountpackage.AccountPackage.user);
        txtcompany.setText("   Company : " + accountpackage.AccountPackage.company);
        txtLastUpdate.setText("    Last Update : " + accountpackage.AccountPackage.lastUpdate() + "    ");
        txtWorkingDate.setText("    Working Date : " + accountpackage.AccountPackage.company.getWorkingDate() + "    ");
        txtDB.setText("    Database : " + DBConnection.db + "    ");
        txtdate.setText("    " + core.Locals.currentDate_F1() + "    ");
    }

    private void setLogo() {
        int x = jDesktopPane1.getWidth();
        int y = jDesktopPane1.getHeight();

        //x = (x / 2) - 220;
        //y = (y / 2) - 60;
        txtlogo.setBounds(jDesktopPane1.getWidth() / 2 - txtlogo.getWidth() / 2, jDesktopPane1.getHeight() / 2 - txtlogo.getHeight() / 2, txtlogo.getWidth(), txtlogo.getHeight());
        txtlogo.setVisible(true);
    }

    private void mainThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        switch (f) {
                            case 1:
                                loadCustomer(val);
                                val = "";
                                f = 0;
                                break;
                            case 2:
                                loadSalesInvoiceDOS(val);
                                val = "";
                                f = 0;
                                break;
                            case 3:
                                loadDepo();
                                val = "";
                                f = 0;
                                break;
                            case 4:
                                loadSalesInvoiceDOS(val, "invoNo");
                                val = "";
                                f = 0;
                                break;
                            case 5:
                                loadRecAgI(val);
                                val = "";
                                f = 0;
                                break;
                            case 6:
                                repCustomerInvoiceDet();
                                val = "";
                                f = 0;
                                break;
                            case 7:
                                loadPurches(val);
                                val = "";
                                f = 0;
                                break;
                        }

                    } catch (Exception ex) {
                        Exp.Handle(ex);
                    }
                }
            }
        }).start();
    }

    private void doDayEnd() {
        GUIDayEnd dayEnd = new GUIDayEnd(this, true);
        dayEnd.setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="SP Report">    
    private void repPaymentHistory() {
        GUICustomerOnlyReport fc = new GUICustomerOnlyReport("REPCustomerHistory.jasper", "Collection Det Report");
        loadUi(fc);
    }

    private void repItemFlow() {
        GUIItemFlow fc = new GUIItemFlow();
        loadUi(fc);
    }

    private void repCollectionDet() {
        GUIComenReport fc = new GUIComenReport("REPCollectionDet.jasper", "Collection Det Report");
        loadUi(fc);
    }

    private void repCollectionSum() {
        GUIComenReport fc = new GUIComenReport("REPCollectionSum.jasper", "Collection Sum Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repSalesSum() {
        GUIComenReport fc = new GUIComenReport("REPSalesSum.jasper", "Sales Sum Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repGRNSum() {
        GUIComenReport fc = new GUIComenReport("REPGRNSum.jasper", "GRN Sum Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repStockReport() {
        GUIStockReport fc = new GUIStockReport("REPStockReport.jasper", "Stock Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repSalesReturnReport() {
        GUIComenReport fc = new GUIComenReport("REPSalesReturnHistory.jasper", "Sales Return Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repClientArrearsReportSum() {
        GUIComenDateReport fc = new GUIComenDateReport("REPCustomerArrearsSum.jasper", "Customer Arrears Sum Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repCustomerInvoiceDet() {
        GUICustomerReport fc = new GUICustomerReport("REPCustomerInvoiceDet.jasper", "Customer Invoice Det Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repSalesSummerySrepCC() {
        GUIComenReport fc = new GUIComenReport("REPSalesCCWise.jasper", "Sales Summery (Sales rep & Cost Center wise) Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repDailySalesAnalysis() {
        GUIComenDateReport fc = new GUIComenDateReport("REPDailySalesAnalysis.jasper", "Daily Sales Analysis Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    private void repSalesAnalysis() {
        GUIComenReport fc = new GUIComenReport("REPSalesAnalysis.jasper", "Sales Analysis Report");
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);
    }

    //</editor-fold>
    private void loadUi(JInternalFrame fc) {
        SwingUtilities.updateComponentTreeUI(fc);
        fc.setBounds(jDesktopPane1.getWidth() / 2 - fc.getWidth() / 2, jDesktopPane1.getHeight() / 2 - fc.getHeight() / 2, fc.getWidth(), fc.getHeight());
        jDesktopPane1.add(fc);
        fc.setVisible(true);

    }
}
