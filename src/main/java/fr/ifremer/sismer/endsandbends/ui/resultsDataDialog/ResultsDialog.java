/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ResultsDialog.java
 *
 * Created on 16 sept. 2010, 09:03:45
 */
package fr.ifremer.sismer.endsandbends.ui.resultsDataDialog;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import fr.ifremer.sismer.endsandbends.common.DefaultFolder;
import fr.ifremer.sismer.endsandbends.common.DefaultsHandler;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.enums.OutputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.mediation.output.AsciiColumnOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.AsciiOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.GmlOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.IOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.ShapeOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.WktOutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.controller.IOutputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.OutputDialogController;

/**
 * @author mbettich
 */
public class ResultsDialog extends javax.swing.JDialog
{

    private final DefaultComboBoxModel filesTypesComboBoxModel =
        new DefaultComboBoxModel(OutputFilesTypes.toArray());

    private final DefaultComboBoxModel dateFormatComboModel =
        new DefaultComboBoxModel(new String[] {
                Messages.getString(Messages.MESSAGES, "DATE_FORMAT_1"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_FORMAT_2"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_FORMAT_3"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_FORMAT_4")}); //$NON-NLS-1$ // NOI18N
    private final DefaultComboBoxModel timeFormatComboModel =
        new DefaultComboBoxModel(new String[] {
                Messages.getString(Messages.MESSAGES, "TIME_FORMAT_1"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "TIME_FORMAT_2"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "TIME_FORMAT_3"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "TIME_FORMAT_4")}); //$NON-NLS-1$ // NOI18N
    private final DefaultComboBoxModel dateTimeFormatComboModel =
        new DefaultComboBoxModel(new String[] {
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_1"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_2"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_3"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_4"),
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_5"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_6"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_7"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_8"),
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_9"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_10"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_11"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_12"),
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_13"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_14"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_15"), //$NON-NLS-1$ // NOI18N
                Messages.getString(Messages.MESSAGES, "DATE_TIME_FORMAT_16")}); //$NON-NLS-1$ // NOI18N
    private final DefaultComboBoxModel latitudeFormatComboModel =
        new DefaultComboBoxModel(new String[] {
                Messages.getString(Messages.MESSAGES, "LATITUDE_FORMAT_1"),
                Messages.getString(Messages.MESSAGES, "LATITUDE_FORMAT_2"),
                Messages.getString(Messages.MESSAGES, "LATITUDE_FORMAT_3"),
                Messages.getString(Messages.MESSAGES, "LATITUDE_FORMAT_4"),
                Messages.getString(Messages.MESSAGES, "LATITUDE_FORMAT_5"),
                Messages.getString(Messages.MESSAGES, "LATITUDE_FORMAT_6")});

    private final DefaultComboBoxModel longitudeFormatComboModel =
        new DefaultComboBoxModel(new String[] {
                Messages.getString(Messages.MESSAGES, "LONGITUDE_FORMAT_1"),
                Messages.getString(Messages.MESSAGES, "LONGITUDE_FORMAT_2"),
                Messages.getString(Messages.MESSAGES, "LONGITUDE_FORMAT_3"),
                Messages.getString(Messages.MESSAGES, "LONGITUDE_FORMAT_4"),
                Messages.getString(Messages.MESSAGES, "LONGITUDE_FORMAT_5"),
                Messages.getString(Messages.MESSAGES, "LONGITUDE_FORMAT_6")});

    private IOutputParamsObject currentOutputObject;

    private IOutputDialogController outputController;

    /** Creates new form ResultsDialog */
    public ResultsDialog(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        currentOutputObject = new AsciiOutputParamsObject();
        outputController = new OutputDialogController(this, null);
        initComponents();
        postInitComponents();
    }

    private void postInitComponents()
    {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                currentOutputObject = null;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        fileTypeValue = new javax.swing.JComboBox();
        fileTypeLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        _inputFilePath = new javax.swing.JTextField();
        _chooseInputFileButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        _IfremerAsciiPanel = new javax.swing.JPanel();
        _ifremerAsciiOperatorNameLabel = new javax.swing.JLabel();
        _ifremerAsciiCommentLabel = new javax.swing.JLabel();
        _ifremerAsciiCampainIdentifierLabel = new javax.swing.JLabel();
        _ifremerAsciiCampainTitleLabel = new javax.swing.JLabel();
        _ifremerAsciiBoatNameLabel = new javax.swing.JLabel();
        asciiTypeSurveyRadioButton = new javax.swing.JRadioButton();
        asciiTypeDataRadioButton = new javax.swing.JRadioButton();
        writeSeconds = new javax.swing.JCheckBox();
        _ifremerAsciiCampainIdentifierValue =
            new javax.swing.JFormattedTextField();
        _ifremerAsciiCommentValue = new javax.swing.JFormattedTextField();
        _ifremerAsciiCampainTitleValue = new javax.swing.JFormattedTextField();
        _ifremerAsciiBoatNameValue = new javax.swing.JFormattedTextField();
        _ifremerAsciiOperatorNameValue = new javax.swing.JFormattedTextField();
        _columnsAsciiPanel = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        _columnsAsciiTokenizer = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        _columnsAsciiColumnNumberLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        _columnsAsciiLatitudeColumnNumber =
            new javax.swing.JFormattedTextField();
        _columnsAsciiLongitudeColumnNumber =
            new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        _columnsAsciiDateLabel = new javax.swing.JLabel();
        _columnsAsciiDateColumnNumber = new javax.swing.JFormattedTextField();
        _columnsAsciiTimeLabel = new javax.swing.JLabel();
        _columnsAsciiTimeColumnNumber = new javax.swing.JFormattedTextField();
        _columnsAsciiTimeFormats = new javax.swing.JComboBox();
        _columnsAsciiDateFormats = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        _columnsAsciiDateTimeLabel = new javax.swing.JLabel();
        _columnsAsciiDatetimeColumnNumber =
            new javax.swing.JFormattedTextField();
        _columnsAsciiDatetimeFormats = new javax.swing.JComboBox();
        _columnsAsciiGroupSeparateDateTimeFormatsCheckBox =
            new javax.swing.JCheckBox();
        TimeToApply = new javax.swing.JSpinner();
        startEndIndicatorColumnNumber = new javax.swing.JFormattedTextField();
        startEndIndicatorLabel = new javax.swing.JLabel();
        startIndicatorField = new javax.swing.JTextField();
        endIndicatorField = new javax.swing.JTextField();
        startIndicatorLabel = new javax.swing.JLabel();
        endIndicatorLabel = new javax.swing.JLabel();
        _columnsAsciiLatitudeFormat = new javax.swing.JComboBox();
        _columnsAsciiLongitudeFormat = new javax.swing.JComboBox();
        _WKTPanel = new javax.swing.JPanel();
        _wktResolutionLabel = new javax.swing.JLabel();
        _wktResolutionValue = new javax.swing.JSpinner();
        _shapePanel = new javax.swing.JPanel();
        _gmlPanel = new javax.swing.JPanel();
        _gmlResolutionLabel = new javax.swing.JLabel();
        _insertIntoMikadoFileRadio = new javax.swing.JRadioButton();
        _generateGmlFileRadio = new javax.swing.JRadioButton();
        _gmlResolutionValue = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle =
            java.util.ResourceBundle
                .getBundle("fr.ifremer.sismer.endsandbends.resources.messages"); // NOI18N
        setTitle(bundle.getString("OUTPUT_DIALOG_TITLE")); // NOI18N
        setMinimumSize(new java.awt.Dimension(605, 730));
        getContentPane().setLayout(null);

        fileTypeValue.setModel(filesTypesComboBoxModel);
        fileTypeValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                fileTypeValueonFileTypeSelection(evt);
            }
        });
        getContentPane().add(fileTypeValue);
        fileTypeValue.setBounds(90, 20, 230, 20);

        fileTypeLabel.setText(Messages.getString(Messages.MESSAGES, "TYPE")); // NOI18N
        getContentPane().add(fileTypeLabel);
        fileTypeLabel.setBounds(10, 20, 80, 14);

        jButton1.setText(Messages.getString(Messages.MESSAGES, "CANCEL_LABEL")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onCancel(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(410, 580, 80, 23);

        jButton2.setText(bundle.getString("OK")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onOK(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(500, 580, 80, 23);
        getContentPane().add(_inputFilePath);
        _inputFilePath.setBounds(20, 540, 469, 20);

        _chooseInputFileButton
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/16/document-save-as.png"))); // NOI18N
        _chooseInputFileButton.setActionCommand(bundle.getString("CHOOSEFILE")); // NOI18N
        _chooseInputFileButton
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _chooseInputFileButton_chooseInputFile(evt);
                }
            });
        getContentPane().add(_chooseInputFileButton);
        _chooseInputFileButton.setBounds(490, 540, 24, 24);

        jPanel1.setLayout(new java.awt.CardLayout());

        _IfremerAsciiPanel.setBorder(javax.swing.BorderFactory
            .createTitledBorder(""));
        _IfremerAsciiPanel.setLayout(null);

        _ifremerAsciiOperatorNameLabel.setText(Messages.getString(
            Messages.MESSAGES, "IFREMER_ASCII_OPERATOR_NAME_LABEL")); // NOI18N
        _IfremerAsciiPanel.add(_ifremerAsciiOperatorNameLabel);
        _ifremerAsciiOperatorNameLabel.setBounds(18, 85, 180, 14);

        _ifremerAsciiCommentLabel.setText(Messages.getString(Messages.MESSAGES,
            "IFREMER_ASCII_COMMENT_LABEL")); // NOI18N
        _IfremerAsciiPanel.add(_ifremerAsciiCommentLabel);
        _ifremerAsciiCommentLabel.setBounds(20, 124, 180, 20);

        _ifremerAsciiCampainIdentifierLabel.setText(Messages.getString(
            Messages.MESSAGES, "IFREMER_ASCII_CAMPAIN_IDENT_LABEL")); // NOI18N
        _IfremerAsciiPanel.add(_ifremerAsciiCampainIdentifierLabel);
        _ifremerAsciiCampainIdentifierLabel.setBounds(18, 163, 180, 20);
        _ifremerAsciiCampainIdentifierLabel.getAccessibleContext()
            .setAccessibleName("");

        _ifremerAsciiCampainTitleLabel.setText(Messages.getString(
            Messages.MESSAGES, "IFREMER_ASCII_CAMPAIN_TITLE_LABEL")); // NOI18N
        _IfremerAsciiPanel.add(_ifremerAsciiCampainTitleLabel);
        _ifremerAsciiCampainTitleLabel.setBounds(18, 211, 180, 14);

        _ifremerAsciiBoatNameLabel.setText(Messages.getString(
            Messages.MESSAGES, "IFREMER_ASCII_BOAT_NAME_LABEL")); // NOI18N
        _IfremerAsciiPanel.add(_ifremerAsciiBoatNameLabel);
        _ifremerAsciiBoatNameLabel.setBounds(18, 253, 180, 14);

        asciiTypeSurveyRadioButton.setSelected(true);
        asciiTypeSurveyRadioButton.setText("Survey");
        asciiTypeSurveyRadioButton
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    asciiTypeSurveyRadioButtonStateChanged(evt);
                }
            });
        _IfremerAsciiPanel.add(asciiTypeSurveyRadioButton);
        asciiTypeSurveyRadioButton.setBounds(18, 22, 130, 23);

        asciiTypeDataRadioButton.setText("Data");
        asciiTypeDataRadioButton
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    asciiTypeDataRadioButtonStateChanged(evt);
                }
            });
        _IfremerAsciiPanel.add(asciiTypeDataRadioButton);
        asciiTypeDataRadioButton.setBounds(180, 20, 170, 23);

        writeSeconds.setText("Write seconds");
        _IfremerAsciiPanel.add(writeSeconds);
        writeSeconds.setBounds(359, 22, 205, 23);

        try
        {
            _ifremerAsciiCampainIdentifierValue
                .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                    new javax.swing.text.MaskFormatter("********")));
        }
        catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }
        _IfremerAsciiPanel.add(_ifremerAsciiCampainIdentifierValue);
        _ifremerAsciiCampainIdentifierValue.setBounds(200, 160, 140, 20);

        try
        {
            _ifremerAsciiCommentValue
                .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                    new javax.swing.text.MaskFormatter("********")));
        }
        catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }
        _IfremerAsciiPanel.add(_ifremerAsciiCommentValue);
        _ifremerAsciiCommentValue.setBounds(200, 120, 140, 20);

        try
        {
            _ifremerAsciiCampainTitleValue
                .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                    new javax.swing.text.MaskFormatter(
                        "*************************")));
        }
        catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }
        _IfremerAsciiPanel.add(_ifremerAsciiCampainTitleValue);
        _ifremerAsciiCampainTitleValue.setBounds(200, 210, 140, 20);

        try
        {
            _ifremerAsciiBoatNameValue
                .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                    new javax.swing.text.MaskFormatter(
                        "*************************")));
        }
        catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }
        _IfremerAsciiPanel.add(_ifremerAsciiBoatNameValue);
        _ifremerAsciiBoatNameValue.setBounds(200, 250, 140, 20);

        try
        {
            _ifremerAsciiOperatorNameValue
                .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                    new javax.swing.text.MaskFormatter("********************")));
        }
        catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }
        _IfremerAsciiPanel.add(_ifremerAsciiOperatorNameValue);
        _ifremerAsciiOperatorNameValue.setBounds(200, 80, 140, 20);

        jPanel1.add(_IfremerAsciiPanel, "_IfremerAsciiPanel");

        _columnsAsciiPanel.setBorder(javax.swing.BorderFactory
            .createTitledBorder(""));
        _columnsAsciiPanel.setLayout(null);

        jLabel22.setText(Messages.getString(Messages.MESSAGES,
            "COLUMNS_ASCII_TOKEN_SEPARATOR")); // NOI18N
        _columnsAsciiPanel.add(jLabel22);
        jLabel22.setBounds(20, 330, 120, 30);
        _columnsAsciiPanel.add(_columnsAsciiTokenizer);
        _columnsAsciiTokenizer.setBounds(150, 330, 110, 20);

        jLabel28.setText(Messages.getString(Messages.MESSAGES,
            "COLUMNS_ASCII_LONGITUDE_LABEL")); // NOI18N
        _columnsAsciiPanel.add(jLabel28);
        jLabel28.setBounds(20, 234, 110, 30);

        jLabel29.setText(Messages.getString(Messages.MESSAGES,
            "COLUMNS_ASCII_LATITUDE_LABEL")); // NOI18N
        _columnsAsciiPanel.add(jLabel29);
        jLabel29.setBounds(20, 214, 120, 20);

        jLabel32.setText(Messages.getString(Messages.MESSAGES,
            "COLUMNS_ASCII_COLUMN_FORMAT_LABEL")); // NOI18N
        _columnsAsciiPanel.add(jLabel32);
        jLabel32.setBounds(300, 100, 200, 30);

        _columnsAsciiColumnNumberLabel.setText(Messages.getString(
            Messages.MESSAGES, "COLUMNS_ASCII_COLUMN_NUMBER_LABEL")); // NOI18N
        _columnsAsciiPanel.add(_columnsAsciiColumnNumberLabel);
        _columnsAsciiColumnNumberLabel.setBounds(150, 100, 150, 30);

        jLabel2.setText("Time to apply");
        _columnsAsciiPanel.add(jLabel2);
        jLabel2.setBounds(10, 15, 110, 30);

        _columnsAsciiLatitudeColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        _columnsAsciiPanel.add(_columnsAsciiLatitudeColumnNumber);
        _columnsAsciiLatitudeColumnNumber.setBounds(150, 210, 110, 20);

        _columnsAsciiLongitudeColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        _columnsAsciiPanel.add(_columnsAsciiLongitudeColumnNumber);
        _columnsAsciiLongitudeColumnNumber.setBounds(150, 240, 110, 20);

        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(500, 80));
        jPanel4.setLayout(null);

        _columnsAsciiDateLabel.setText("Date");
        jPanel4.add(_columnsAsciiDateLabel);
        _columnsAsciiDateLabel.setBounds(10, 10, 90, 20);

        _columnsAsciiDateColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        jPanel4.add(_columnsAsciiDateColumnNumber);
        _columnsAsciiDateColumnNumber.setBounds(140, 10, 110, 20);

        _columnsAsciiTimeLabel.setText("Time");
        jPanel4.add(_columnsAsciiTimeLabel);
        _columnsAsciiTimeLabel.setBounds(10, 40, 110, 20);

        _columnsAsciiTimeColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        jPanel4.add(_columnsAsciiTimeColumnNumber);
        _columnsAsciiTimeColumnNumber.setBounds(140, 40, 110, 20);

        _columnsAsciiTimeFormats.setEditable(true);
        _columnsAsciiTimeFormats.setModel(timeFormatComboModel);
        jPanel4.add(_columnsAsciiTimeFormats);
        _columnsAsciiTimeFormats.setBounds(280, 40, 250, 20);

        _columnsAsciiDateFormats.setEditable(true);
        _columnsAsciiDateFormats.setModel(dateFormatComboModel);
        jPanel4.add(_columnsAsciiDateFormats);
        _columnsAsciiDateFormats.setBounds(280, 10, 250, 20);

        jPanel3.add(jPanel4, "dateTimeSplit");

        jPanel5.setLayout(null);

        _columnsAsciiDateTimeLabel.setText("Date & Time");
        jPanel5.add(_columnsAsciiDateTimeLabel);
        _columnsAsciiDateTimeLabel.setBounds(10, 20, 120, 20);

        _columnsAsciiDatetimeColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        jPanel5.add(_columnsAsciiDatetimeColumnNumber);
        _columnsAsciiDatetimeColumnNumber.setBounds(140, 20, 110, 20);

        _columnsAsciiDatetimeFormats.setEditable(true);
        _columnsAsciiDatetimeFormats.setModel(dateTimeFormatComboModel);
        jPanel5.add(_columnsAsciiDatetimeFormats);
        _columnsAsciiDatetimeFormats.setBounds(280, 20, 250, 20);

        jPanel3.add(jPanel5, "dateTimeJoin");

        _columnsAsciiPanel.add(jPanel3);
        jPanel3.setBounds(10, 135, 550, 70);

        _columnsAsciiGroupSeparateDateTimeFormatsCheckBox.setText(Messages
            .getString(Messages.MESSAGES,
                "COLUMNS_ASCII_JOINT_SPLIT_DATE_AND_TIME_LABEL")); // NOI18N
        _columnsAsciiGroupSeparateDateTimeFormatsCheckBox
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    _columnsAsciiGroupSeparateDateTimeFormatsCheckBoxStateChanged(evt);
                }
            });
        _columnsAsciiPanel
            .add(_columnsAsciiGroupSeparateDateTimeFormatsCheckBox);
        _columnsAsciiGroupSeparateDateTimeFormatsCheckBox.setBounds(10, 70,
            330, 23);

        TimeToApply.setModel(new javax.swing.SpinnerNumberModel(0, -12, 12, 1));
        _columnsAsciiPanel.add(TimeToApply);
        TimeToApply.setBounds(120, 20, 110, 20);

        startEndIndicatorColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("##############"))));
        _columnsAsciiPanel.add(startEndIndicatorColumnNumber);
        startEndIndicatorColumnNumber.setBounds(150, 290, 110, 20);

        startEndIndicatorLabel.setText("Start/End Indicator");
        _columnsAsciiPanel.add(startEndIndicatorLabel);
        startEndIndicatorLabel.setBounds(20, 290, 120, 20);
        startEndIndicatorLabel.getAccessibleContext().setAccessibleName(
            Messages.getString(Messages.MESSAGES, "START_END_INDICATOR_LABEL"));

        _columnsAsciiPanel.add(startIndicatorField);
        startIndicatorField.setBounds(290, 290, 110, 20);
        _columnsAsciiPanel.add(endIndicatorField);
        endIndicatorField.setBounds(420, 290, 120, 20);

        startIndicatorLabel.setText("Start Indicator");
        _columnsAsciiPanel.add(startIndicatorLabel);
        startIndicatorLabel.setBounds(290, 270, 110, 20);
        startIndicatorLabel.getAccessibleContext().setAccessibleName(
            Messages.getString(Messages.MESSAGES, "START_INDICATOR_LABEL"));

        endIndicatorLabel.setText("End Indicator");
        _columnsAsciiPanel.add(endIndicatorLabel);
        endIndicatorLabel.setBounds(420, 270, 120, 20);
        endIndicatorLabel.getAccessibleContext().setAccessibleName(
            Messages.getString(Messages.MESSAGES, "END_INDICATOR_LABEL"));

        _columnsAsciiLatitudeFormat.setEditable(true);
        _columnsAsciiLatitudeFormat.setModel(latitudeFormatComboModel);
        _columnsAsciiPanel.add(_columnsAsciiLatitudeFormat);
        _columnsAsciiLatitudeFormat.setBounds(290, 210, 250, 20);

        _columnsAsciiLongitudeFormat.setEditable(true);
        _columnsAsciiLongitudeFormat.setModel(longitudeFormatComboModel);
        _columnsAsciiPanel.add(_columnsAsciiLongitudeFormat);
        _columnsAsciiLongitudeFormat.setBounds(290, 240, 250, 20);

        jPanel1.add(_columnsAsciiPanel, "_columnsAsciiPanel");

        _WKTPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        _wktResolutionLabel.setText(Messages.getString(Messages.MESSAGES,
            "WKT_RESOLUTION_LABEL")); // NOI18N

        javax.swing.GroupLayout _WKTPanelLayout =
            new javax.swing.GroupLayout(_WKTPanel);
        _WKTPanel.setLayout(_WKTPanelLayout);
        _WKTPanelLayout.setHorizontalGroup(_WKTPanelLayout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            _WKTPanelLayout.createSequentialGroup().addGap(18, 18, 18)
                .addComponent(_wktResolutionLabel).addGap(18, 18, 18)
                .addComponent(_wktResolutionValue,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 145,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(
                    263, Short.MAX_VALUE)));
        _WKTPanelLayout.setVerticalGroup(_WKTPanelLayout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            _WKTPanelLayout.createSequentialGroup().addContainerGap().addGroup(
                _WKTPanelLayout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                    _wktResolutionLabel).addComponent(_wktResolutionValue,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(
                393, Short.MAX_VALUE)));

        jPanel1.add(_WKTPanel, "_WKTPanel");

        _shapePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout _shapePanelLayout =
            new javax.swing.GroupLayout(_shapePanel);
        _shapePanel.setLayout(_shapePanelLayout);
        _shapePanelLayout.setHorizontalGroup(_shapePanelLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE));
        _shapePanelLayout.setVerticalGroup(_shapePanelLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE));

        jPanel1.add(_shapePanel, "_shapePanel");

        _gmlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        _gmlResolutionLabel.setText(Messages.getString(Messages.MESSAGES,
            "GML_RESOLUTION_LABEL")); // NOI18N

        _insertIntoMikadoFileRadio.setText(Messages.getString(
            Messages.MESSAGES,
            "RESULTS_DIALOG_GML_INSERT_INTO_MIKADO_FILE_LABEL")); // NOI18N
        _insertIntoMikadoFileRadio
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    _insertIntoMikadoFileRadioStateChanged(evt);
                }
            });

        _generateGmlFileRadio.setSelected(true);
        _generateGmlFileRadio.setText(Messages.getString(Messages.MESSAGES,
            "RESULTS_DIALOG_GML_GENERATE_GML_FILE_LABEL")); // NOI18N
        _generateGmlFileRadio
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    _generateGmlFileRadioStateChanged(evt);
                }
            });

        _gmlResolutionValue.setModel(new javax.swing.SpinnerNumberModel(0, 0,
            7, 1));

        javax.swing.GroupLayout _gmlPanelLayout =
            new javax.swing.GroupLayout(_gmlPanel);
        _gmlPanel.setLayout(_gmlPanelLayout);
        _gmlPanelLayout.setHorizontalGroup(_gmlPanelLayout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            _gmlPanelLayout.createSequentialGroup().addContainerGap().addGroup(
                _gmlPanelLayout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                    _gmlPanelLayout.createSequentialGroup().addComponent(
                        _gmlResolutionLabel,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 132,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_gmlResolutionValue,
                            javax.swing.GroupLayout.PREFERRED_SIZE, 147,
                            javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(
                    _gmlPanelLayout.createSequentialGroup().addComponent(
                        _generateGmlFileRadio,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 190,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18,
                        18).addComponent(_insertIntoMikadoFileRadio,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 243,
                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(93, Short.MAX_VALUE)));
        _gmlPanelLayout.setVerticalGroup(_gmlPanelLayout.createParallelGroup(
            javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            _gmlPanelLayout.createSequentialGroup().addContainerGap().addGroup(
                _gmlPanelLayout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                    _gmlResolutionLabel).addComponent(_gmlResolutionValue,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18)
                .addGroup(
                    _gmlPanelLayout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(_generateGmlFileRadio).addComponent(
                            _insertIntoMikadoFileRadio)).addContainerGap(352,
                    Short.MAX_VALUE)));

        jPanel1.add(_gmlPanel, "_gmlPanel");

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 60, 570, 440);

        jLabel3.setText("Choose file for results");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 520, 190, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void asciiTypeSurveyRadioButtonStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_asciiTypeSurveyRadioButtonStateChanged
        asciiTypeDataRadioButton.setSelected(!asciiTypeSurveyRadioButton
            .isSelected());
    }// GEN-LAST:event_asciiTypeSurveyRadioButtonStateChanged

    private void asciiTypeDataRadioButtonStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_asciiTypeDataRadioButtonStateChanged
        asciiTypeSurveyRadioButton.setSelected(!asciiTypeDataRadioButton
            .isSelected());
    }// GEN-LAST:event_asciiTypeDataRadioButtonStateChanged

    private void _generateGmlFileRadioStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event__generateGmlFileRadioStateChanged
        _insertIntoMikadoFileRadio.setSelected(!_generateGmlFileRadio
            .isSelected());
    }// GEN-LAST:event__generateGmlFileRadioStateChanged

    private void _insertIntoMikadoFileRadioStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event__insertIntoMikadoFileRadioStateChanged
        _generateGmlFileRadio.setSelected(!_insertIntoMikadoFileRadio
            .isSelected());
    }// GEN-LAST:event__insertIntoMikadoFileRadioStateChanged

    private void _columnsAsciiGroupSeparateDateTimeFormatsCheckBoxStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event__columnsAsciiGroupSeparateDateTimeFormatsCheckBoxStateChanged
        CardLayout cl = (CardLayout) (jPanel3.getLayout());
        if (_columnsAsciiGroupSeparateDateTimeFormatsCheckBox.isSelected())
        {
            cl.show(jPanel3, "dateTimeJoin");
        }
        else
        {
            cl.show(jPanel3, "dateTimeSplit");
        }
    }// GEN-LAST:event__columnsAsciiGroupSeparateDateTimeFormatsCheckBoxStateChanged

    private void fileTypeValueonFileTypeSelection(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_fileTypeValueonFileTypeSelection
        CardLayout cl = (CardLayout) (jPanel1.getLayout());
        if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "IFREMER_ASCII_FORMAT")))
        {
            if (currentOutputObject != null
                    && currentOutputObject instanceof AsciiOutputParamsObject)
            {
            }
            else
            {
                currentOutputObject = new AsciiOutputParamsObject();
            }
            cl.show(jPanel1, "_IfremerAsciiPanel");
        }
        else if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "GML_FORMAT")))
        {
            if (currentOutputObject != null
                    && currentOutputObject instanceof GmlOutputParamsObject)
            {
            }
            else
            {
                currentOutputObject = new GmlOutputParamsObject();
            }
            cl.show(jPanel1, "_gmlPanel");
        }
        else if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "WKT_FORMAT")))
        {
            if (currentOutputObject != null
                    && currentOutputObject instanceof WktOutputParamsObject)
            {
            }
            else
            {
                currentOutputObject = new WktOutputParamsObject();
            }
            cl.show(jPanel1, "_WKTPanel");
        }
        else if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "COLUMNS_ASCII_FORMAT")))
        {
            if (currentOutputObject != null
                    && currentOutputObject instanceof AsciiColumnOutputParamsObject)
            {
            }
            else
            {
                currentOutputObject = new AsciiColumnOutputParamsObject();
            }
            cl.show(jPanel1, "_columnsAsciiPanel");
        }
        else if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "SHAPE_FORMAT")))
        {
            if (currentOutputObject != null
                    && currentOutputObject instanceof ShapeOutputParamsObject)
            {
// currentOutputObject.reset();
            }
            else
            {
                currentOutputObject = new ShapeOutputParamsObject();
            }
            cl.show(jPanel1, "_shapePanel");
        }
    }// GEN-LAST:event_fileTypeValueonFileTypeSelection

    private void _chooseInputFileButton_chooseInputFile(
            java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__chooseInputFileButton_chooseInputFile
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(DefaultsHandler
            .getDefaultPath(DefaultFolder.OUTPUT_FOLDER)));

        int r = chooser.showOpenDialog(new JFrame());
        if (r == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = chooser.getSelectedFile();
            String path = selectedFile.getPath();
            _inputFilePath.setText(path);
            // _dialogParametersResult.setSelectedFile(selectedFile);
            currentOutputObject.setSelectedFile(selectedFile);
        }
    }// GEN-LAST:event__chooseInputFileButton_chooseInputFile

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner TimeToApply;
    private javax.swing.JPanel _IfremerAsciiPanel;
    private javax.swing.JPanel _WKTPanel;
    private javax.swing.JButton _chooseInputFileButton;
    private javax.swing.JLabel _columnsAsciiColumnNumberLabel;
    private javax.swing.JFormattedTextField _columnsAsciiDateColumnNumber;
    private javax.swing.JComboBox _columnsAsciiDateFormats;
    private javax.swing.JLabel _columnsAsciiDateLabel;
    private javax.swing.JLabel _columnsAsciiDateTimeLabel;
    private javax.swing.JFormattedTextField _columnsAsciiDatetimeColumnNumber;
    private javax.swing.JComboBox _columnsAsciiDatetimeFormats;
    private javax.swing.JCheckBox _columnsAsciiGroupSeparateDateTimeFormatsCheckBox;
    private javax.swing.JFormattedTextField _columnsAsciiLatitudeColumnNumber;
    private javax.swing.JComboBox _columnsAsciiLatitudeFormat;
    private javax.swing.JFormattedTextField _columnsAsciiLongitudeColumnNumber;
    private javax.swing.JComboBox _columnsAsciiLongitudeFormat;
    private javax.swing.JPanel _columnsAsciiPanel;
    private javax.swing.JFormattedTextField _columnsAsciiTimeColumnNumber;
    private javax.swing.JComboBox _columnsAsciiTimeFormats;
    private javax.swing.JLabel _columnsAsciiTimeLabel;
    private javax.swing.JTextField _columnsAsciiTokenizer;
    private javax.swing.JRadioButton _generateGmlFileRadio;
    private javax.swing.JPanel _gmlPanel;
    private javax.swing.JLabel _gmlResolutionLabel;
    private javax.swing.JSpinner _gmlResolutionValue;
    private javax.swing.JLabel _ifremerAsciiBoatNameLabel;
    private javax.swing.JFormattedTextField _ifremerAsciiBoatNameValue;
    private javax.swing.JLabel _ifremerAsciiCampainIdentifierLabel;
    private javax.swing.JFormattedTextField _ifremerAsciiCampainIdentifierValue;
    private javax.swing.JLabel _ifremerAsciiCampainTitleLabel;
    private javax.swing.JFormattedTextField _ifremerAsciiCampainTitleValue;
    private javax.swing.JLabel _ifremerAsciiCommentLabel;
    private javax.swing.JFormattedTextField _ifremerAsciiCommentValue;
    private javax.swing.JLabel _ifremerAsciiOperatorNameLabel;
    private javax.swing.JFormattedTextField _ifremerAsciiOperatorNameValue;
    private javax.swing.JTextField _inputFilePath;
    private javax.swing.JRadioButton _insertIntoMikadoFileRadio;
    private javax.swing.JPanel _shapePanel;
    private javax.swing.JLabel _wktResolutionLabel;
    private javax.swing.JSpinner _wktResolutionValue;
    private javax.swing.JRadioButton asciiTypeDataRadioButton;
    private javax.swing.JRadioButton asciiTypeSurveyRadioButton;
    private javax.swing.JTextField endIndicatorField;
    private javax.swing.JLabel endIndicatorLabel;
    private javax.swing.JLabel fileTypeLabel;
    private javax.swing.JComboBox fileTypeValue;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JFormattedTextField startEndIndicatorColumnNumber;
    private javax.swing.JLabel startEndIndicatorLabel;
    private javax.swing.JTextField startIndicatorField;
    private javax.swing.JLabel startIndicatorLabel;
    private javax.swing.JCheckBox writeSeconds;

    // End of variables declaration//GEN-END:variables

    /**
     * Affichage de la fen�tre :
     */
    public IOutputParamsObject showDialog()
    {
        // Ajuster la taille :
        pack();

        // Centrer la fen�tre :
        Rectangle rect = getOwner().getBounds();
        Dimension dim = getSize();
        setBounds(rect.x + ((rect.width - dim.width) / 2), rect.y
                + ((rect.height - dim.height) / 2), dim.width, dim.height);

        setVisible(true); // appel bloquant parce que la bo�te est modale...

        return currentOutputObject;
    }

    /**
     * Getter sur _IfremerAsciiPanel.
     * 
     * @return the ifremerAsciiPanel
     */
    public javax.swing.JPanel getIfremerAsciiPanel()
    {
        return _IfremerAsciiPanel;
    }

    /**
     * Getter sur _WKTPanel.
     * 
     * @return the wKTPanel
     */
    public javax.swing.JPanel getWKTPanel()
    {
        return _WKTPanel;
    }

    /**
     * Getter sur _chooseInputFileButton.
     * 
     * @return the chooseInputFileButton
     */
    public javax.swing.JButton getChooseInputFileButton()
    {
        return _chooseInputFileButton;
    }

    /**
     * Getter sur _columnsAsciiColumnNumberLabel.
     * 
     * @return the columnsAsciiColumnNumberLabel
     */
    public javax.swing.JLabel getColumnsAsciiColumnNumberLabel()
    {
        return _columnsAsciiColumnNumberLabel;
    }

    /**
     * Getter sur _columnsAsciiDateColumnNumber.
     * 
     * @return the columnsAsciiDateColumnNumber
     */
    public javax.swing.JFormattedTextField getColumnsAsciiDateColumnNumber()
    {
        return _columnsAsciiDateColumnNumber;
    }

    /**
     * Getter sur _columnsAsciiDateFormats.
     * 
     * @return the columnsAsciiDateFormats
     */
    public javax.swing.JComboBox getColumnsAsciiDateFormats()
    {
        return _columnsAsciiDateFormats;
    }

    /**
     * Getter sur _columnsAsciiDateLabel.
     * 
     * @return the columnsAsciiDateLabel
     */
    public javax.swing.JLabel getColumnsAsciiDateLabel()
    {
        return _columnsAsciiDateLabel;
    }

    /**
     * Getter sur _columnsAsciiDateTimeLabel.
     * 
     * @return the columnsAsciiDateTimeLabel
     */
    public javax.swing.JLabel getColumnsAsciiDateTimeLabel()
    {
        return _columnsAsciiDateTimeLabel;
    }

    /**
     * Getter sur _columnsAsciiDatetimeColumnNumber.
     * 
     * @return the columnsAsciiDatetimeColumnNumber
     */
    public javax.swing.JFormattedTextField getColumnsAsciiDatetimeColumnNumber()
    {
        return _columnsAsciiDatetimeColumnNumber;
    }

    /**
     * Getter sur _columnsAsciiDatetimeFormats.
     * 
     * @return the columnsAsciiDatetimeFormats
     */
    public javax.swing.JComboBox getColumnsAsciiDatetimeFormats()
    {
        return _columnsAsciiDatetimeFormats;
    }

    /**
     * Getter sur _columnsAsciiGroupSeparateDateTimeFormatsCheckBox.
     * 
     * @return the columnsAsciiGroupSeparateDateTimeFormatsCheckBox
     */
    public javax.swing.JCheckBox getColumnsAsciiGroupSeparateDateTimeFormatsCheckBox()
    {
        return _columnsAsciiGroupSeparateDateTimeFormatsCheckBox;
    }

    /**
     * Getter sur _columnsAsciiLatitudeColumnNumber.
     * 
     * @return the columnsAsciiLatitudeColumnNumber
     */
    public javax.swing.JFormattedTextField getColumnsAsciiLatitudeColumnNumber()
    {
        return _columnsAsciiLatitudeColumnNumber;
    }

    /**
     * Getter sur _columnsAsciiLatitudeFormat.
     * 
     * @return the columnsAsciiLatitudeFormat
     */
    public JComboBox getColumnsAsciiLatitudeFormat()
    {
        return _columnsAsciiLatitudeFormat;
    }

    /**
     * Getter sur _columnsAsciiLongitudeColumnNumber.
     * 
     * @return the columnsAsciiLongitudeColumnNumber
     */
    public javax.swing.JFormattedTextField getColumnsAsciiLongitudeColumnNumber()
    {
        return _columnsAsciiLongitudeColumnNumber;
    }

    /**
     * Getter sur _columnsAsciiLongitudeFormat.
     * 
     * @return the columnsAsciiLongitudeFormat
     */
    public JComboBox getColumnsAsciiLongitudeFormat()
    {
        return _columnsAsciiLongitudeFormat;
    }

    /**
     * Getter sur _columnsAsciiPanel.
     * 
     * @return the columnsAsciiPanel
     */
    public javax.swing.JPanel getColumnsAsciiPanel()
    {
        return _columnsAsciiPanel;
    }

    /**
     * Getter sur _columnsAsciiTimeColumnNumber.
     * 
     * @return the columnsAsciiTimeColumnNumber
     */
    public javax.swing.JFormattedTextField getColumnsAsciiTimeColumnNumber()
    {
        return _columnsAsciiTimeColumnNumber;
    }

    /**
     * Getter sur _columnsAsciiTimeFormats.
     * 
     * @return the columnsAsciiTimeFormats
     */
    public javax.swing.JComboBox getColumnsAsciiTimeFormats()
    {
        return _columnsAsciiTimeFormats;
    }

    /**
     * Getter sur _columnsAsciiTimeLabel.
     * 
     * @return the columnsAsciiTimeLabel
     */
    public javax.swing.JLabel getColumnsAsciiTimeLabel()
    {
        return _columnsAsciiTimeLabel;
    }

    /**
     * Getter sur _columnsAsciiTokenizer.
     * 
     * @return the columnsAsciiTokenizer
     */
    public javax.swing.JTextField getColumnsAsciiTokenizer()
    {
        return _columnsAsciiTokenizer;
    }

    /**
     * Getter sur _generateGmlFileRadio.
     * 
     * @return the generateGmlFileRadio
     */
    public javax.swing.JRadioButton getGenerateGmlFileRadio()
    {
        return _generateGmlFileRadio;
    }

    /**
     * Getter sur _gmlPanel.
     * 
     * @return the gmlPanel
     */
    public javax.swing.JPanel getGmlPanel()
    {
        return _gmlPanel;
    }

    /**
     * Getter sur _gmlResolutionLabel.
     * 
     * @return the gmlResolutionLabel
     */
    public javax.swing.JLabel getGmlResolutionLabel()
    {
        return _gmlResolutionLabel;
    }

    /**
     * Getter sur _gmlResolutionValue.
     * 
     * @return the gmlResolutionValue
     */
    public javax.swing.JSpinner getGmlResolutionValue()
    {
        return _gmlResolutionValue;
    }

    /**
     * Getter sur _ifremerAsciiBoatNameLabel.
     * 
     * @return the ifremerAsciiBoatNameLabel
     */
    public javax.swing.JLabel getIfremerAsciiBoatNameLabel()
    {
        return _ifremerAsciiBoatNameLabel;
    }

    /**
     * Getter sur _ifremerAsciiBoatNameValue.
     * 
     * @return the ifremerAsciiBoatNameValue
     */
    public javax.swing.JFormattedTextField getIfremerAsciiBoatNameValue()
    {
        return _ifremerAsciiBoatNameValue;
    }

    /**
     * Getter sur _ifremerAsciiCampainIdentifierLabel.
     * 
     * @return the ifremerAsciiCampainIdentifierLabel
     */
    public javax.swing.JLabel getIfremerAsciiCampainIdentifierLabel()
    {
        return _ifremerAsciiCampainIdentifierLabel;
    }

    /**
     * Getter sur _ifremerAsciiCampainIdentifierValue.
     * 
     * @return the ifremerAsciiCampainIdentifierValue
     */
    public javax.swing.JFormattedTextField getIfremerAsciiCampainIdentifierValue()
    {
        return _ifremerAsciiCampainIdentifierValue;
    }

    /**
     * Getter sur _ifremerAsciiCampainTitleLabel.
     * 
     * @return the ifremerAsciiCampainTitleLabel
     */
    public javax.swing.JLabel getIfremerAsciiCampainTitleLabel()
    {
        return _ifremerAsciiCampainTitleLabel;
    }

    /**
     * Getter sur _ifremerAsciiCampainTitleValue.
     * 
     * @return the ifremerAsciiCampainTitleValue
     */
    public javax.swing.JFormattedTextField getIfremerAsciiCampainTitleValue()
    {
        return _ifremerAsciiCampainTitleValue;
    }

    /**
     * Getter sur _ifremerAsciiCommentLabel.
     * 
     * @return the ifremerAsciiCommentLabel
     */
    public javax.swing.JLabel getIfremerAsciiCommentLabel()
    {
        return _ifremerAsciiCommentLabel;
    }

    /**
     * Getter sur _ifremerAsciiCommentValue.
     * 
     * @return the ifremerAsciiCommentValue
     */
    public javax.swing.JFormattedTextField getIfremerAsciiCommentValue()
    {
        return _ifremerAsciiCommentValue;
    }

    /**
     * Getter sur _ifremerAsciiOperatorNameLabel.
     * 
     * @return the ifremerAsciiOperatorNameLabel
     */
    public javax.swing.JLabel getIfremerAsciiOperatorNameLabel()
    {
        return _ifremerAsciiOperatorNameLabel;
    }

    /**
     * Getter sur _ifremerAsciiOperatorNameValue.
     * 
     * @return the ifremerAsciiOperatorNameValue
     */
    public javax.swing.JFormattedTextField getIfremerAsciiOperatorNameValue()
    {
        return _ifremerAsciiOperatorNameValue;
    }

    /**
     * Getter sur _inputFilePath.
     * 
     * @return the inputFilePath
     */
    public javax.swing.JTextField getInputFilePath()
    {
        return _inputFilePath;
    }

    /**
     * Getter sur _insertIntoMikadoFileRadio.
     * 
     * @return the insertIntoMikadoFileRadio
     */
    public javax.swing.JRadioButton getInsertIntoMikadoFileRadio()
    {
        return _insertIntoMikadoFileRadio;
    }

    /**
     * Getter sur _shapePanel.
     * 
     * @return the shapePanel
     */
    public javax.swing.JPanel getShapePanel()
    {
        return _shapePanel;
    }

    /**
     * Getter sur _wktResolutionLabel.
     * 
     * @return the wktResolutionLabel
     */
    public javax.swing.JLabel getWktResolutionLabel()
    {
        return _wktResolutionLabel;
    }

    /**
     * Getter sur _wktResolutionValue.
     * 
     * @return the wktResolutionValue
     */
    public javax.swing.JSpinner getWktResolutionValue()
    {
        return _wktResolutionValue;
    }

    /**
     * Getter sur fileTypeLabel.
     * 
     * @return the fileTypeLabel
     */
    public javax.swing.JLabel getFileTypeLabel()
    {
        return fileTypeLabel;
    }

    /**
     * Getter sur fileTypeValue.
     * 
     * @return the fileTypeValue
     */
    public javax.swing.JComboBox getFileTypeValue()
    {
        return fileTypeValue;
    }

    /**
     * Getter sur filesTypesComboBoxModel.
     * 
     * @return the filesTypesComboBoxModel
     */
    public DefaultComboBoxModel getFilesTypesComboBoxModel()
    {
        return filesTypesComboBoxModel;
    }

    /**
     * Getter sur dateFormatComboModel.
     * 
     * @return the dateFormatComboModel
     */
    public DefaultComboBoxModel getDateFormatComboModel()
    {
        return dateFormatComboModel;
    }

    /**
     * Getter sur timeFormatComboModel.
     * 
     * @return the timeFormatComboModel
     */
    public DefaultComboBoxModel getTimeFormatComboModel()
    {
        return timeFormatComboModel;
    }

    /**
     * Getter sur dateTimeFormatComboModel.
     * 
     * @return the dateTimeFormatComboModel
     */
    public DefaultComboBoxModel getDateTimeFormatComboModel()
    {
        return dateTimeFormatComboModel;
    }

    /**
     * Getter sur asciiTypeDataRadioButton.
     * 
     * @return the asciiTypeDataRadioButton
     */
    public javax.swing.JRadioButton getAsciiTypeDataRadioButton()
    {
        return asciiTypeDataRadioButton;
    }

    /**
     * Getter sur asciiTypeSurveyRadioButton.
     * 
     * @return the asciiTypeSurveyRadioButton
     */
    public javax.swing.JRadioButton getAsciiTypeSurveyRadioButton()
    {
        return asciiTypeSurveyRadioButton;
    }

    private void onOK(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_onOK

        try
        {
            // Lors de l'appuie sur le bouton OK, on v�rifie les donn�es du
            // formulaire et on affiche si n�cessaires les erreurs.
            boolean result = outputController.validateForm();

            // En cas de probl�me, on affiche la liste des erreurs �
            // l'utilisateur.
            if (result)
            {
                // On cr�e un file � partir du path de mani�re � ne pas avoir de
                // probl�mes de d�syncrho entre la s�lection depuis le
                // FileChooser
                // et le path.
                File selectedFile = new File(_inputFilePath.getText());
                currentOutputObject.setSelectedFile(selectedFile);
                currentOutputObject.updateFromResultDialog(this);

                if (outputController.getOutputObject() == null
                        || currentOutputObject
                            .isOutputObjectChanged(outputController
                                .getOutputObject()))
                {
                    outputController.saveOutputFile();
                }
                dispose();
            }
        }
        catch (EABException e)
        {
            e.displayFatalError(this);
        }

    }// GEN-LAST:event_onOK

    private void onCancel(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_onCancel
        currentOutputObject = null;
        dispose();
    }// GEN-LAST:event_onCancel

    /**
     * Getter sur currentOutputObject.
     * 
     * @return the currentOutputObject
     */
    public IOutputParamsObject getCurrentOutputObject()
    {
        return currentOutputObject;
    }

    /**
     * Setter sur currentOutputObject.
     * 
     * @param currentOutputObject
     *            the currentOutputObject to set
     */
    public void setCurrentOutputObject(IOutputParamsObject currentOutputObject)
    {
        this.currentOutputObject = currentOutputObject;
    }

    /**
     * Getter sur TimeToApply.
     * 
     * @return the timeToApply
     */
    public javax.swing.JSpinner getTimeToApply()
    {
        return TimeToApply;
    }

    /**
     * Getter sur outputController.
     * 
     * @return the outputController
     */
    public IOutputDialogController getOutputController()
    {
        return outputController;
    }

    /**
     * Setter sur outputController.
     * 
     * @param outputController
     *            the outputController to set
     */
    public void setOutputController(IOutputDialogController outputController)
    {
        this.outputController = outputController;
    }

    /**
     * Getter sur jPanel3.
     * 
     * @return the jPanel3
     */
    public javax.swing.JPanel getjPanel3()
    {
        return jPanel3;
    }

    /**
     * Getter sur writeSeconds.
     * 
     * @return the writeSeconds
     */
    public javax.swing.JCheckBox getWriteSeconds()
    {
        return writeSeconds;
    }

    /**
     * Getter sur endIndicatorField.
     * 
     * @return the endIndicatorField
     */
    public javax.swing.JTextField getEndIndicatorField()
    {
        return endIndicatorField;
    }

    /**
     * Getter sur startEndIndicatorColumnNumber.
     * 
     * @return the startEndIndicatorColumnNumber
     */
    public javax.swing.JFormattedTextField getStartEndIndicatorColumnNumber()
    {
        return startEndIndicatorColumnNumber;
    }

    /**
     * Getter sur startIndicatorField.
     * 
     * @return the startIndicatorField
     */
    public javax.swing.JTextField getStartIndicatorField()
    {
        return startIndicatorField;
    }

}
