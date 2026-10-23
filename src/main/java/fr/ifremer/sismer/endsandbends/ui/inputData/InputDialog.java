/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EntriesDialog.java
 *
 * Created on 1 juin 2010, 17:36:50
 */
package fr.ifremer.sismer.endsandbends.ui.inputData;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;

import fr.ifremer.sismer.endsandbends.common.DefaultFolder;
import fr.ifremer.sismer.endsandbends.common.DefaultsHandler;
import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.enums.Operator;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.input.UkooaFileReader;
import fr.ifremer.sismer.endsandbends.mediation.input.CsvInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.IInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NetCdfInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NmeaInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NviInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NviUKOOAJTable;
import fr.ifremer.sismer.endsandbends.mediation.input.UkooaInputParamsObject;
import fr.ifremer.sismer.endsandbends.persistData.formatsParameters.DateVerifier;
import fr.ifremer.sismer.endsandbends.persistData.formatsParameters.TimeVerifier;
import fr.ifremer.sismer.endsandbends.ui.controller.IInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.InputDialogController;

/**
 * @author pclement
 */
public class InputDialog extends javax.swing.JDialog
{

    private final DefaultComboBoxModel filesTypesComboBoxModel =
        new DefaultComboBoxModel(InputFilesTypes.toArray());

    private final DefaultComboBoxModel delimiterUsedComboModel =
        new DefaultComboBoxModel(new String[] {Globals.SEMICOLON,
                Globals.COMMA, Globals.SPACE});

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

    private final DefaultComboBoxModel checkValidityOperatorComboModel =
        new DefaultComboBoxModel(Operator.toArray());

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

    IInputDialogController inputController;
    IInputParamsObject currentInputObject;

    /**
     * Objet utilis� pour la communication sur les param�tres de la fen�tre.
     */
    private static final String SIMPLEDATEFORMATMASK = Messages.getString(
        Messages.MESSAGES, "SIMPLEDATEFORMATMASK");
    private static final String SIMPLETIMEFORMATMASK = Messages.getString(
        Messages.MESSAGES, "SIMPLETIMEFORMATMASK");

    /**
     * Constructeur.
     */
    public InputDialog(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        inputController = new InputDialogController(this, null);
        currentInputObject = new NviInputParamsObject();
        initComponents();
        postInitComponents();

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

        buttonGroup1 = new javax.swing.ButtonGroup();
        fileTypeValue = new javax.swing.JComboBox();
        fileTypeLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        _inputFilePath = new javax.swing.JTextField();
        _chooseInputFileButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        startDate_label = new javax.swing.JLabel();
        endDate_label = new javax.swing.JLabel();
        startDate_flabel = new javax.swing.JLabel();
        endDate_flabel = new javax.swing.JLabel();
        startDate_ftlabel = new javax.swing.JLabel();
        endDate_ftlabel = new javax.swing.JLabel();
        isTimeFilteredEnabled = new javax.swing.JCheckBox();
        startDate_date = new javax.swing.JFormattedTextField();
        endDate_date = new javax.swing.JFormattedTextField();
        startDate_time = new javax.swing.JFormattedTextField();
        endDate_time = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        nviPanel = new javax.swing.JPanel();
        nviForceSplittingFileInProfiles = new javax.swing.JCheckBox();
        nviAddButton = new javax.swing.JButton();
        nviDeleteButton = new javax.swing.JButton();
        nviEditButton = new javax.swing.JButton();
        nviInsertButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        nviSegmentsTable =
            new NviUKOOAJTable(nviAddButton, nviDeleteButton, nviEditButton,
                nviInsertButton, this);
        netCDFPanel = new javax.swing.JPanel();
        joinDateTime1 = new javax.swing.JCheckBox();
        latitudeLabel1 = new javax.swing.JLabel();
        longitudeLabel1 = new javax.swing.JLabel();
        columnNameLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        dateLabel1 = new javax.swing.JLabel();
        dateVariableName = new javax.swing.JFormattedTextField();
        timeLabel1 = new javax.swing.JLabel();
        timeVariableName = new javax.swing.JFormattedTextField();
        jPanel6 = new javax.swing.JPanel();
        datetimeVariableName = new javax.swing.JFormattedTextField();
        dateTimeLabel = new javax.swing.JLabel();
        latitudeVariableName = new javax.swing.JTextField();
        longitudeVariableName = new javax.swing.JTextField();
        csvPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        formatLabel = new javax.swing.JLabel();
        joinDateTime = new javax.swing.JCheckBox();
        isCheckValidityEnabled = new javax.swing.JCheckBox();
        isDateTimeEnabled = new javax.swing.JCheckBox();
        latitudeLabel = new javax.swing.JLabel();
        longitudeLabel = new javax.swing.JLabel();
        columnNumberLabel = new javax.swing.JLabel();
        checkValidityoperatorCombo = new javax.swing.JComboBox();
        lastLine = new javax.swing.JFormattedTextField();
        checkValidityColumnNumber = new javax.swing.JFormattedTextField();
        validityValue = new javax.swing.JFormattedTextField();
        latitudeFormatColumnNumber = new javax.swing.JFormattedTextField();
        longitudeFormatColumnNumber = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        dateLabel = new javax.swing.JLabel();
        dateFormatColumnNumber = new javax.swing.JFormattedTextField();
        timeLabel = new javax.swing.JLabel();
        timeFormatColumnNumber = new javax.swing.JFormattedTextField();
        timeFormatCombo = new javax.swing.JComboBox();
        dateFormatCombo = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dateTimeColumnNumber = new javax.swing.JFormattedTextField();
        dateTimeFormat = new javax.swing.JComboBox();
        firstLine = new javax.swing.JFormattedTextField();
        longitudeFormat = new javax.swing.JComboBox();
        latitudeFormat = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        delimiterUsed = new javax.swing.JComboBox();
        nmeaPanel = new javax.swing.JPanel();
        lossOfAccuracyLabel = new javax.swing.JLabel();
        metersLabel = new javax.swing.JLabel();
        lossOfAccuracyField = new javax.swing.JFormattedTextField();
        ukooaPanel = new javax.swing.JPanel();
        ukooaForceSplittingFileInProfiles = new javax.swing.JCheckBox();
        ukooaAddButton = new javax.swing.JButton();
        ukooaDeleteButton = new javax.swing.JButton();
        ukooaEditButton = new javax.swing.JButton();
        ukooaInsertButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ukooaSegmentsTable =
            new NviUKOOAJTable(ukooaAddButton, ukooaDeleteButton,
                ukooaEditButton, ukooaInsertButton, this);
        jPanel_angularUnit = new javax.swing.JPanel();
        jRadioButton_degrees = new javax.swing.JRadioButton();
        jRadioButton_grades = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle =
            java.util.ResourceBundle
                .getBundle("fr.ifremer.sismer.endsandbends.resources.messages"); // NOI18N
        setTitle(bundle.getString("INPUT_DIALOG_TITLE")); // NOI18N
        setMinimumSize(new java.awt.Dimension(570, 690));
        getContentPane().setLayout(null);

        fileTypeValue.setModel(filesTypesComboBoxModel);
        fileTypeValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onFileTypeSelection(evt);
            }
        });
        getContentPane().add(fileTypeValue);
        fileTypeValue.setBounds(80, 20, 210, 20);

        fileTypeLabel.setText(Messages.getString(Messages.MESSAGES, "TYPE")); // NOI18N
        getContentPane().add(fileTypeLabel);
        fileTypeLabel.setBounds(20, 20, 50, 24);

        jButton1.setText(Messages.getString(Messages.MESSAGES, "CANCEL_LABEL")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onCancel(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(380, 620, 80, 23);

        jButton2.setText(bundle.getString("OK")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onOK(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(470, 620, 80, 23);
        getContentPane().add(_inputFilePath);
        _inputFilePath.setBounds(10, 570, 450, 20);

        _chooseInputFileButton
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/16/document-open.png"))); // NOI18N
        _chooseInputFileButton.setActionCommand(bundle.getString("CHOOSEFILE")); // NOI18N
        _chooseInputFileButton
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _chooseInputFile(evt);
                }
            });
        getContentPane().add(_chooseInputFileButton);
        _chooseInputFileButton.setBounds(460, 570, 24, 24);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle
            .getString("FILTER"))); // NOI18N
        jPanel2.setLayout(null);

        startDate_label.setText(bundle
            .getString("INPUT_DIALOG_START_DATE_LABEL")); // NOI18N
        jPanel2.add(startDate_label);
        startDate_label.setBounds(10, 60, 80, 20);

        endDate_label.setText(bundle.getString("INPUT_DIALOG_END_DATE_LABEL")); // NOI18N
        jPanel2.add(endDate_label);
        endDate_label.setBounds(10, 90, 80, 20);

        startDate_flabel.setText(bundle.getString("SIMPLEDATEFORMATMASK")); // NOI18N
        jPanel2.add(startDate_flabel);
        startDate_flabel.setBounds(210, 60, 90, 20);

        endDate_flabel.setText(bundle.getString("SIMPLEDATEFORMATMASK")); // NOI18N
        jPanel2.add(endDate_flabel);
        endDate_flabel.setBounds(210, 90, 90, 20);

        startDate_ftlabel.setText(bundle.getString("SIMPLETIMEFORMATMASK")); // NOI18N
        jPanel2.add(startDate_ftlabel);
        startDate_ftlabel.setBounds(450, 60, 80, 20);

        endDate_ftlabel.setText(bundle.getString("SIMPLETIMEFORMATMASK")); // NOI18N
        jPanel2.add(endDate_ftlabel);
        endDate_ftlabel.setBounds(450, 90, 80, 20);

        isTimeFilteredEnabled.setText(bundle
            .getString("SPECIFY_TEMPORAL_RANGE")); // NOI18N
        isTimeFilteredEnabled
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    isTimeFilteredEnabledStateChanged(evt);
                }
            });
        jPanel2.add(isTimeFilteredEnabled);
        isTimeFilteredEnabled.setBounds(10, 20, 280, 23);

        startDate_date.setInputVerifier(new DateVerifier(SIMPLEDATEFORMATMASK));
        jPanel2.add(startDate_date);
        startDate_date.setBounds(90, 60, 110, 20);

        endDate_date.setInputVerifier(new DateVerifier(SIMPLEDATEFORMATMASK));
        jPanel2.add(endDate_date);
        endDate_date.setBounds(90, 90, 110, 20);

        startDate_time.setInputVerifier(new TimeVerifier(SIMPLETIMEFORMATMASK));
        jPanel2.add(startDate_time);
        startDate_time.setBounds(300, 60, 140, 20);

        endDate_time.setInputVerifier(new TimeVerifier(SIMPLETIMEFORMATMASK));
        jPanel2.add(endDate_time);
        endDate_time.setBounds(300, 90, 140, 20);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 50, 540, 140);

        jPanel1.setLayout(new java.awt.CardLayout());

        nviPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        nviPanel.setLayout(null);

        nviForceSplittingFileInProfiles.setText(bundle
            .getString("INPUT_DIALOG_FORCE_SPLIT_LABEL")); // NOI18N
        nviForceSplittingFileInProfiles
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    nviForceSplittingFileInProfilesStateChanged(evt);
                }
            });
        nviPanel.add(nviForceSplittingFileInProfiles);
        nviForceSplittingFileInProfiles.setBounds(10, 20, 440, 23);

        nviAddButton.setText(bundle.getString("INPUT_DIALOG_SEGMENT_ADD")); // NOI18N
        nviPanel.add(nviAddButton);
        nviAddButton.setBounds(460, 70, 70, 23);

        nviDeleteButton
            .setText(bundle.getString("INPUT_DIALOG_SEGMENT_DELETE")); // NOI18N
        nviPanel.add(nviDeleteButton);
        nviDeleteButton.setBounds(460, 150, 70, 23);

        nviEditButton.setText(bundle.getString("INPUT_DIALOG_SEGMENT_EDIT")); // NOI18N
        nviPanel.add(nviEditButton);
        nviEditButton.setBounds(460, 190, 70, 23);

        nviInsertButton
            .setText(bundle.getString("INPUT_DIALOG_SEGMENT_INSERT")); // NOI18N
        nviPanel.add(nviInsertButton);
        nviInsertButton.setBounds(460, 110, 70, 23);

        nviSegmentsTable.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(nviSegmentsTable);

        nviPanel.add(jScrollPane1);
        jScrollPane1.setBounds(10, 60, 440, 260);

        jPanel1.add(nviPanel, "nviPanel");

        netCDFPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        netCDFPanel.setLayout(null);

        joinDateTime1.setText("Join date and time parameters");
        joinDateTime1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                joinDateTime1StateChanged(evt);
            }
        });
        netCDFPanel.add(joinDateTime1);
        joinDateTime1.setBounds(20, 30, 300, 23);

        latitudeLabel1.setText("Latitude");
        netCDFPanel.add(latitudeLabel1);
        latitudeLabel1.setBounds(10, 210, 70, 20);

        longitudeLabel1.setText("Longitude");
        netCDFPanel.add(longitudeLabel1);
        longitudeLabel1.setBounds(10, 240, 70, 20);

        columnNameLabel.setText(bundle
            .getString("INPUT_DIALOG_NETCDF_VARIABLE_NAME")); // NOI18N
        netCDFPanel.add(columnNameLabel);
        columnNameLabel.setBounds(90, 80, 110, 20);

        jPanel8.setLayout(new java.awt.CardLayout());

        jPanel7.setLayout(null);

        dateLabel1.setText("Date");
        jPanel7.add(dateLabel1);
        dateLabel1.setBounds(0, 10, 70, 20);

        dateVariableName.setText(bundle
            .getString("INPUT_DIALOG_NETCDF_DEFAULT_DATE_NAME")); // NOI18N
        jPanel7.add(dateVariableName);
        dateVariableName.setBounds(80, 10, 109, 20);

        timeLabel1.setText("Time");
        jPanel7.add(timeLabel1);
        timeLabel1.setBounds(0, 40, 70, 20);

        timeVariableName.setText(bundle
            .getString("INPUT_DIALOG_NETCDF_DEFAULT_TIME_NAME")); // NOI18N
        jPanel7.add(timeVariableName);
        timeVariableName.setBounds(80, 40, 109, 20);

        jPanel8.add(jPanel7, "dateTimeSplit");

        jPanel6.setLayout(null);

        datetimeVariableName.setText(bundle
            .getString("INPUT_DIALOG_NETCDF_DEFAULT_DATETIME_NAME")); // NOI18N
        jPanel6.add(datetimeVariableName);
        datetimeVariableName.setBounds(80, 10, 110, 20);

        dateTimeLabel.setText("Date & Time");
        jPanel6.add(dateTimeLabel);
        dateTimeLabel.setBounds(0, 10, 80, 20);

        jPanel8.add(jPanel6, "dateTimeJoin");

        netCDFPanel.add(jPanel8);
        jPanel8.setBounds(10, 100, 520, 80);

        latitudeVariableName.setText("latitude");
        netCDFPanel.add(latitudeVariableName);
        latitudeVariableName.setBounds(90, 210, 110, 20);

        longitudeVariableName.setText("longitude");
        netCDFPanel.add(longitudeVariableName);
        longitudeVariableName.setBounds(90, 240, 110, 20);

        jPanel1.add(netCDFPanel, "netCDFPanel");

        csvPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        csvPanel.setLayout(null);

        jLabel8.setText(bundle.getString("INPUT_DIALOG_CSV_FIRST_LINE")); // NOI18N
        csvPanel.add(jLabel8);
        jLabel8.setBounds(20, 10, 160, 20);

        jLabel10.setText(bundle.getString("INPUT_DIALOG_CSV_LAST_LINE")); // NOI18N
        csvPanel.add(jLabel10);
        jLabel10.setBounds(20, 40, 150, 20);

        formatLabel.setText(bundle.getString("INPUT_DIALOG_CSV_FORMAT_LABEL")); // NOI18N
        csvPanel.add(formatLabel);
        formatLabel.setBounds(250, 170, 170, 20);

        joinDateTime.setText(bundle.getString("INPUT_DIALOG_CSV_JOIN_DATE")); // NOI18N
        joinDateTime.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                joinDateTimeStateChanged(evt);
            }
        });
        csvPanel.add(joinDateTime);
        joinDateTime.setBounds(150, 110, 350, 23);

        isCheckValidityEnabled.setText(bundle
            .getString("INPUT_DIALOG_CSV_VALID_COLUMN")); // NOI18N
        isCheckValidityEnabled
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    isCheckValidityEnabledStateChanged(evt);
                }
            });
        csvPanel.add(isCheckValidityEnabled);
        isCheckValidityEnabled.setBounds(10, 80, 150, 23);

        isDateTimeEnabled.setText(bundle
            .getString("INPUT_DIALOG_CSV_IGNORE_DATE")); // NOI18N
        isDateTimeEnabled
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    isDateTimeEnabledStateChanged(evt);
                }
            });
        isDateTimeEnabled
            .addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt)
                {
                    isDateTimeEnabledPropertyChange(evt);
                }
            });
        csvPanel.add(isDateTimeEnabled);
        isDateTimeEnabled.setBounds(10, 110, 120, 23);

        latitudeLabel.setText(bundle
            .getString("INPUT_DIALOG_CSV_LATITUDE_LABEL")); // NOI18N
        csvPanel.add(latitudeLabel);
        latitudeLabel.setBounds(20, 280, 80, 20);

        longitudeLabel.setText(bundle
            .getString("INPUT_DIALOG_CSV_LONGITUDE_LABEL")); // NOI18N
        csvPanel.add(longitudeLabel);
        longitudeLabel.setBounds(20, 310, 80, 20);

        columnNumberLabel.setText(bundle
            .getString("INPUT_DIALOG_CSV_COLUMN_NUMBER_LABEL")); // NOI18N
        csvPanel.add(columnNumberLabel);
        columnNumberLabel.setBounds(100, 170, 120, 20);

        checkValidityoperatorCombo.setModel(checkValidityOperatorComboModel);
        csvPanel.add(checkValidityoperatorCombo);
        checkValidityoperatorCombo.setBounds(300, 80, 110, 20);

        lastLine
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        csvPanel.add(lastLine);
        lastLine.setBounds(180, 40, 100, 20);

        checkValidityColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        csvPanel.add(checkValidityColumnNumber);
        checkValidityColumnNumber.setBounds(180, 80, 100, 20);

        validityValue
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        csvPanel.add(validityValue);
        validityValue.setBounds(420, 80, 110, 20);

        latitudeFormatColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        csvPanel.add(latitudeFormatColumnNumber);
        latitudeFormatColumnNumber.setBounds(100, 280, 110, 20);

        longitudeFormatColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        csvPanel.add(longitudeFormatColumnNumber);
        longitudeFormatColumnNumber.setBounds(100, 310, 110, 20);

        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(500, 80));
        jPanel4.setLayout(null);

        dateLabel.setText(bundle.getString("INPUT_DIALOG_CSV_DATE_LABEL")); // NOI18N
        jPanel4.add(dateLabel);
        dateLabel.setBounds(10, 10, 70, 20);

        dateFormatColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        jPanel4.add(dateFormatColumnNumber);
        dateFormatColumnNumber.setBounds(90, 10, 110, 20);

        timeLabel.setText(bundle.getString("INPUT_DIALOG_CSV_TIME_LABEL")); // NOI18N
        jPanel4.add(timeLabel);
        timeLabel.setBounds(10, 40, 70, 20);

        timeFormatColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        jPanel4.add(timeFormatColumnNumber);
        timeFormatColumnNumber.setBounds(90, 40, 110, 20);

        timeFormatCombo.setEditable(true);
        timeFormatCombo.setModel(timeFormatComboModel);
        jPanel4.add(timeFormatCombo);
        timeFormatCombo.setBounds(240, 40, 270, 20);

        dateFormatCombo.setEditable(true);
        dateFormatCombo.setModel(dateFormatComboModel);
        jPanel4.add(dateFormatCombo);
        dateFormatCombo.setBounds(240, 10, 270, 20);

        jPanel3.add(jPanel4, "dateTimeSplit");

        jPanel5.setLayout(null);

        jLabel2.setText("Date & Time");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(0, 10, 90, 40);

        dateTimeColumnNumber
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        jPanel5.add(dateTimeColumnNumber);
        dateTimeColumnNumber.setBounds(90, 20, 110, 20);

        dateTimeFormat.setEditable(true);
        dateTimeFormat.setModel(dateTimeFormatComboModel);
        jPanel5.add(dateTimeFormat);
        dateTimeFormat.setBounds(240, 20, 270, 20);

        jPanel3.add(jPanel5, "dateTimeJoin");

        csvPanel.add(jPanel3);
        jPanel3.setBounds(10, 190, 520, 80);

        firstLine
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        csvPanel.add(firstLine);
        firstLine.setBounds(180, 10, 100, 20);

        longitudeFormat.setEditable(true);
        longitudeFormat.setModel(longitudeFormatComboModel);
        csvPanel.add(longitudeFormat);
        longitudeFormat.setBounds(250, 310, 270, 20);

        latitudeFormat.setEditable(true);
        latitudeFormat.setModel(latitudeFormatComboModel);
        csvPanel.add(latitudeFormat);
        latitudeFormat.setBounds(250, 280, 270, 20);

        jLabel22.setText(Messages.getString(Messages.MESSAGES,
            "COLUMNS_ASCII_TOKEN_SEPARATOR")); // NOI18N
        csvPanel.add(jLabel22);
        jLabel22.setBounds(100, 130, 150, 40);

        delimiterUsed.setEditable(true);
        delimiterUsed.setModel(delimiterUsedComboModel);
        csvPanel.add(delimiterUsed);
        delimiterUsed.setBounds(250, 140, 60, 20);

        jPanel1.add(csvPanel, "csvPanel");

        nmeaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        nmeaPanel.setLayout(null);

        lossOfAccuracyLabel.setText("Loss of horizontal accuracy limit (HDOP)");
        nmeaPanel.add(lossOfAccuracyLabel);
        lossOfAccuracyLabel.setBounds(10, 40, 200, 20);

        metersLabel.setText("meters");
        nmeaPanel.add(metersLabel);
        metersLabel.setBounds(460, 40, 70, 20);

        lossOfAccuracyField
            .setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                    new java.text.DecimalFormat("###############"))));
        nmeaPanel.add(lossOfAccuracyField);
        lossOfAccuracyField.setBounds(210, 40, 240, 20);

        jPanel1.add(nmeaPanel, "nmeaPanel");

        ukooaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        ukooaPanel.setLayout(null);

        ukooaForceSplittingFileInProfiles.setText(bundle
            .getString("INPUT_DIALOG_FORCE_SPLIT_LABEL")); // NOI18N
        ukooaForceSplittingFileInProfiles
            .addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt)
                {
                    ukooaForceSplittingFileInProfilesStateChanged(evt);
                }
            });
        ukooaPanel.add(ukooaForceSplittingFileInProfiles);
        ukooaForceSplittingFileInProfiles.setBounds(10, 60, 440, 23);

        ukooaAddButton.setText(bundle.getString("INPUT_DIALOG_SEGMENT_ADD")); // NOI18N
        ukooaAddButton.setEnabled(false);
        ukooaPanel.add(ukooaAddButton);
        ukooaAddButton.setBounds(460, 90, 70, 23);

        ukooaDeleteButton.setText(bundle
            .getString("INPUT_DIALOG_SEGMENT_DELETE")); // NOI18N
        ukooaDeleteButton.setEnabled(false);
        ukooaPanel.add(ukooaDeleteButton);
        ukooaDeleteButton.setBounds(460, 170, 70, 23);

        ukooaEditButton.setText(bundle.getString("INPUT_DIALOG_SEGMENT_EDIT")); // NOI18N
        ukooaEditButton.setEnabled(false);
        ukooaPanel.add(ukooaEditButton);
        ukooaEditButton.setBounds(460, 210, 70, 23);

        ukooaInsertButton.setText(bundle
            .getString("INPUT_DIALOG_SEGMENT_INSERT")); // NOI18N
        ukooaInsertButton.setEnabled(false);
        ukooaPanel.add(ukooaInsertButton);
        ukooaInsertButton.setBounds(460, 130, 70, 23);

        ukooaSegmentsTable.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(ukooaSegmentsTable);

        ukooaPanel.add(jScrollPane2);
        jScrollPane2.setBounds(10, 90, 440, 230);

        jPanel_angularUnit.setBorder(javax.swing.BorderFactory
            .createTitledBorder("Angular Unit"));
        jPanel_angularUnit.setLayout(null);

        buttonGroup1.add(jRadioButton_degrees);
        jRadioButton_degrees.setText("Degrees");
        jRadioButton_degrees.setEnabled(false);
        jPanel_angularUnit.add(jRadioButton_degrees);
        jRadioButton_degrees.setBounds(230, 10, 65, 23);

        buttonGroup1.add(jRadioButton_grades);
        jRadioButton_grades.setText("Grades");
        jRadioButton_grades.setEnabled(false);
        jPanel_angularUnit.add(jRadioButton_grades);
        jRadioButton_grades.setBounds(90, 10, 59, 23);

        ukooaPanel.add(jPanel_angularUnit);
        jPanel_angularUnit.setBounds(10, 10, 440, 40);

        jPanel1.add(ukooaPanel, "ukooaPanel");

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 200, 540, 340);

        jLabel3.setText(bundle.getString("INPUT_DIALOG_FILE_PATH_FIELD")); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 550, 450, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ukooaForceSplittingFileInProfilesStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_ukooaForceSplittingFileInProfilesStateChanged
        ukooaSegmentsTable.setEnabled(ukooaForceSplittingFileInProfiles
            .isSelected());
        ukooaAddButton.setEnabled(ukooaForceSplittingFileInProfiles
            .isSelected());
        ukooaDeleteButton.setEnabled(ukooaForceSplittingFileInProfiles
            .isSelected());
        ukooaEditButton.setEnabled(ukooaForceSplittingFileInProfiles
            .isSelected());
        ukooaInsertButton.setEnabled(ukooaForceSplittingFileInProfiles
            .isSelected());
    }// GEN-LAST:event_ukooaForceSplittingFileInProfilesStateChanged

    private void nviForceSplittingFileInProfilesStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_nviForceSplittingFileInProfilesStateChanged
        nviSegmentsTable.setEnabled(nviForceSplittingFileInProfiles
            .isSelected());
        nviAddButton.setEnabled(nviForceSplittingFileInProfiles.isSelected());
        nviDeleteButton
            .setEnabled(nviForceSplittingFileInProfiles.isSelected());
        nviEditButton.setEnabled(nviForceSplittingFileInProfiles.isSelected());
        nviInsertButton
            .setEnabled(nviForceSplittingFileInProfiles.isSelected());
    }// GEN-LAST:event_nviForceSplittingFileInProfilesStateChanged

    private void isDateTimeEnabledPropertyChange(
            java.beans.PropertyChangeEvent evt)
    {// GEN-FIRST:event_isDateTimeEnabledPropertyChange

    }// GEN-LAST:event_isDateTimeEnabledPropertyChange

    private void joinDateTime1StateChanged(javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_joinDateTime1StateChanged
        CardLayout cl = (CardLayout) (jPanel8.getLayout());
        if (joinDateTime1.isSelected())
        {
            cl.show(jPanel8, "dateTimeJoin");
            dateVariableName.setText(null);
            timeVariableName.setText(null);
        }
        else
        {
            cl.show(jPanel8, "dateTimeSplit");
            datetimeVariableName.setText(null);
        }
    }// GEN-LAST:event_joinDateTime1StateChanged

    private void joinDateTimeStateChanged(javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_joinDateTimeStateChanged
        CardLayout cl = (CardLayout) (jPanel3.getLayout());
        if (joinDateTime.isSelected())
        {
            cl.show(jPanel3, "dateTimeJoin");
            dateFormatColumnNumber.setText(null);
            timeFormatColumnNumber.setText(null);
            dateFormatCombo.setSelectedItem(null);
            timeFormatCombo.setSelectedItem(null);
        }
        else
        {
            cl.show(jPanel3, "dateTimeSplit");
            dateTimeColumnNumber.setText(null);
            dateTimeFormat.setSelectedItem(null);
        }
    }// GEN-LAST:event_joinDateTimeStateChanged

    private void isCheckValidityEnabledStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_isCheckValidityEnabledStateChanged
        checkValidityColumnNumber.setEnabled(isCheckValidityEnabled
            .isSelected());
        checkValidityoperatorCombo.setEnabled(isCheckValidityEnabled
            .isSelected());
        validityValue.setEnabled(isCheckValidityEnabled.isSelected());
    }// GEN-LAST:event_isCheckValidityEnabledStateChanged

    private void isDateTimeEnabledStateChanged(javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_isDateTimeEnabledStateChanged
        dateFormatColumnNumber.setEnabled(!isDateTimeEnabled.isSelected());
        dateFormatCombo.setEnabled(!isDateTimeEnabled.isSelected());
        dateLabel.setEnabled(!isDateTimeEnabled.isSelected());
        joinDateTime.setEnabled(!isDateTimeEnabled.isSelected());
        timeFormatColumnNumber.setEnabled(!isDateTimeEnabled.isSelected());
        timeFormatCombo.setEnabled(!isDateTimeEnabled.isSelected());
        timeLabel.setEnabled(!isDateTimeEnabled.isSelected());
        dateTimeColumnNumber.setEnabled(!isDateTimeEnabled.isSelected());
        dateTimeFormat.setEnabled(!isDateTimeEnabled.isSelected());
        jLabel2.setEnabled(!isDateTimeEnabled.isSelected());

        if (isTimeFilteredEnabled.isSelected())
        {
            isTimeFilteredEnabled.setSelected(!isDateTimeEnabled.isSelected());
            isTimeFilteredEnabledStateChanged(new ChangeEvent(evt));
            isTimeFilteredEnabled.setEnabled(!isDateTimeEnabled.isSelected());
        }
        if (!isTimeFilteredEnabled.isSelected())
        {
            isTimeFilteredEnabledStateChanged(new ChangeEvent(evt));
            isTimeFilteredEnabled.setEnabled(!isDateTimeEnabled.isSelected());
        }
    }// GEN-LAST:event_isDateTimeEnabledStateChanged

    private void isTimeFilteredEnabledStateChanged(
            javax.swing.event.ChangeEvent evt)
    {// GEN-FIRST:event_isTimeFilteredEnabledStateChange
        startDate_date.setEnabled(isTimeFilteredEnabled.isSelected());
        startDate_time.setEnabled(isTimeFilteredEnabled.isSelected());
        endDate_date.setEnabled(isTimeFilteredEnabled.isSelected());
        endDate_time.setEnabled(isTimeFilteredEnabled.isSelected());
        startDate_flabel.setEnabled(isTimeFilteredEnabled.isSelected());
        startDate_label.setEnabled(isTimeFilteredEnabled.isSelected());
        startDate_ftlabel.setEnabled(isTimeFilteredEnabled.isSelected());
        endDate_flabel.setEnabled(isTimeFilteredEnabled.isSelected());
        endDate_label.setEnabled(isTimeFilteredEnabled.isSelected());
        endDate_ftlabel.setEnabled(isTimeFilteredEnabled.isSelected());
        endDate_ftlabel.setEnabled(isTimeFilteredEnabled.isSelected());
    }// GEN-LAST:event_isTimeFilteredEnabledStateChanged

    private void onFileTypeSelection(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_onFileTypeSelection
        CardLayout cl = (CardLayout) (jPanel1.getLayout());
        if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "NETCDF_NVI_FORMAT")))
        {
            if (currentInputObject != null
                    && currentInputObject instanceof NviInputParamsObject)
            {
            }
            else
            {
                currentInputObject = new NviInputParamsObject();
            }
            cl.show(jPanel1, "nviPanel");
            isTimeFilteredEnabled.setEnabled(true);
        }
        else if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "GENERAL_NETCDF_FORMAT")))
        {

            if (currentInputObject != null
                    && currentInputObject instanceof NetCdfInputParamsObject)
            {
            }
            else
            {
                currentInputObject = new NetCdfInputParamsObject();

            }
            cl.show(jPanel1, "netCDFPanel");
            isTimeFilteredEnabled.setEnabled(true);
        }
        else if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "CSV_FORMAT")))
        {
            if (currentInputObject != null
                    && currentInputObject instanceof CsvInputParamsObject)
            {
            }
            else
            {
                currentInputObject = new CsvInputParamsObject();

            }
            cl.show(jPanel1, "csvPanel");
            isTimeFilteredEnabled.setEnabled(true);
        }
        else if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "NMEA_183_FORMAT")))
        {
            if (currentInputObject != null
                    && currentInputObject instanceof NmeaInputParamsObject)
            {
            }
            else
            {
                currentInputObject = new NmeaInputParamsObject();
            }
            cl.show(jPanel1, "nmeaPanel");
            isTimeFilteredEnabled.setEnabled(true);
        }
        else if (fileTypeValue.getSelectedItem().equals(
            Messages.getString(Messages.MESSAGES, "UKOOA_P1_90")))
        {
            if (currentInputObject != null
                    && currentInputObject instanceof UkooaInputParamsObject)
            {
            }
            else
            {
                currentInputObject = new UkooaInputParamsObject();
            }
            cl.show(jPanel1, "ukooaPanel");
            isTimeFilteredEnabled.setEnabled(false);
        }
    }// GEN-LAST:event_onFileTypeSelection

    private void onOK(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_onOK
        try
        {

            // Lors de l'appuie sur le bouton OK, on v�rifie les donn�es du
            // formulaire et on affiche si n�cessaires les erreurs.
            boolean result = inputController.validateForm();

            // En cas de probl�me, on affiche la liste des erreurs �
            // l'utilisateur.1
            if (result)
            {
                // On cr�e un file � partir du path de mani�re � ne pas avoir de
                // probl�mes de d�syncrho entre la s�lection depuis le
                // FileChooser
                // et le path.
                File selectedFile = new File(_inputFilePath.getText());
                currentInputObject.setSelectedFile(selectedFile);
                currentInputObject.updateFromInputDialog(this);

                if (inputController.getInputObject() == null
                        || currentInputObject
                            .isInputObjectChanged(inputController
                                .getInputObject()))
                {
                    inputController.saveInputFile();
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
        currentInputObject = null;
        dispose();
    }// GEN-LAST:event_onCancel

    private void _chooseInputFile(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__chooseInputFile
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(DefaultsHandler
            .getDefaultPath(DefaultFolder.INPUT_FOLDER)));

        int r = chooser.showOpenDialog(new JFrame());
        if (r == JFileChooser.APPROVE_OPTION)
        {
            String path = chooser.getSelectedFile().getPath();
            _inputFilePath.setText(path);
            UkooaFileReader fileReader = new UkooaFileReader(path);
            try
            {
                if (fileReader.testFileConformity() == 0)
                {
                    jRadioButton_degrees.setSelected(true);
                    jRadioButton_degrees.setEnabled(true);
                    jRadioButton_grades.setEnabled(true);
                }
                if (fileReader.testFileConformity() == 1)
                {
                    jRadioButton_degrees.setSelected(true);
                    jRadioButton_degrees.setEnabled(false);
                    jRadioButton_grades.setEnabled(false);
                }
                if (fileReader.testFileConformity() == -1)
                {
                    jRadioButton_grades.setSelected(true);
                    jRadioButton_degrees.setEnabled(false);
                    jRadioButton_grades.setEnabled(false);
                }
            }
            catch (EABException e)
            {
                // TODO
            }
        }
    }// GEN-LAST:event__chooseInputFile

    private void postInitComponents()
    {
        startDate_date.setEnabled(false);
        startDate_flabel.setEnabled(false);
        startDate_ftlabel.setEnabled(false);
        startDate_time.setEnabled(false);
        startDate_label.setEnabled(false);
        endDate_date.setEnabled(false);
        endDate_flabel.setEnabled(false);
        endDate_ftlabel.setEnabled(false);
        endDate_label.setEnabled(false);
        endDate_time.setEnabled(false);

        checkValidityColumnNumber.setEnabled(false);
        checkValidityoperatorCombo.setEnabled(false);
        validityValue.setEnabled(false);

        nviSegmentsTable.setEnabled(false);
        nviAddButton.setEnabled(false);
        nviDeleteButton.setEnabled(false);
        nviEditButton.setEnabled(false);
        nviInsertButton.setEnabled(false);
        ((NviUKOOAJTable) nviSegmentsTable).initNviUKOOAJTable();
        ((NviUKOOAJTable) ukooaSegmentsTable).initNviUKOOAJTable();

        // split en profile rendu impossible pour Ukooa mais on garde tous les
        // m�canisme mis en place au cas ou IFREMER changerai d'avis
        ukooaForceSplittingFileInProfiles.setEnabled(false);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                currentInputObject = null;
            }
        });

    }

    /**
     * Affichage de la fen�tre :
     */
    public IInputParamsObject showDialog()
    {
        // Ajuster la taille :
        pack();

        // Centrer la fen�tre :
        Rectangle rect = getOwner().getBounds();
        Dimension dim = getSize();
        setBounds(rect.x + ((rect.width - dim.width) / 2), rect.y
                + ((rect.height - dim.height) / 2), dim.width, dim.height);

        setVisible(true); // appel bloquant parce que la bo�te est modale...

        return currentInputObject;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _chooseInputFileButton;
    private javax.swing.JTextField _inputFilePath;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFormattedTextField checkValidityColumnNumber;
    private javax.swing.JComboBox checkValidityoperatorCombo;
    private javax.swing.JLabel columnNameLabel;
    private javax.swing.JLabel columnNumberLabel;
    private javax.swing.JPanel csvPanel;
    private javax.swing.JFormattedTextField dateFormatColumnNumber;
    private javax.swing.JComboBox dateFormatCombo;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JFormattedTextField dateTimeColumnNumber;
    private javax.swing.JComboBox dateTimeFormat;
    private javax.swing.JLabel dateTimeLabel;
    private javax.swing.JFormattedTextField dateVariableName;
    private javax.swing.JFormattedTextField datetimeVariableName;
    private javax.swing.JComboBox delimiterUsed;
    private javax.swing.JFormattedTextField endDate_date;
    private javax.swing.JLabel endDate_flabel;
    private javax.swing.JLabel endDate_ftlabel;
    private javax.swing.JLabel endDate_label;
    private javax.swing.JFormattedTextField endDate_time;
    private javax.swing.JLabel fileTypeLabel;
    private javax.swing.JComboBox fileTypeValue;
    private javax.swing.JFormattedTextField firstLine;
    private javax.swing.JLabel formatLabel;
    private javax.swing.JCheckBox isCheckValidityEnabled;
    private javax.swing.JCheckBox isDateTimeEnabled;
    private javax.swing.JCheckBox isTimeFilteredEnabled;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel_angularUnit;
    private javax.swing.JRadioButton jRadioButton_degrees;
    private javax.swing.JRadioButton jRadioButton_grades;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox joinDateTime;
    private javax.swing.JCheckBox joinDateTime1;
    private javax.swing.JFormattedTextField lastLine;
    private javax.swing.JComboBox latitudeFormat;
    private javax.swing.JFormattedTextField latitudeFormatColumnNumber;
    private javax.swing.JLabel latitudeLabel;
    private javax.swing.JLabel latitudeLabel1;
    private javax.swing.JTextField latitudeVariableName;
    private javax.swing.JComboBox longitudeFormat;
    private javax.swing.JFormattedTextField longitudeFormatColumnNumber;
    private javax.swing.JLabel longitudeLabel;
    private javax.swing.JLabel longitudeLabel1;
    private javax.swing.JTextField longitudeVariableName;
    private javax.swing.JFormattedTextField lossOfAccuracyField;
    private javax.swing.JLabel lossOfAccuracyLabel;
    private javax.swing.JLabel metersLabel;
    private javax.swing.JPanel netCDFPanel;
    private javax.swing.JPanel nmeaPanel;
    private javax.swing.JButton nviAddButton;
    private javax.swing.JButton nviDeleteButton;
    private javax.swing.JButton nviEditButton;
    private javax.swing.JCheckBox nviForceSplittingFileInProfiles;
    private javax.swing.JButton nviInsertButton;
    private javax.swing.JPanel nviPanel;
    private javax.swing.JTable nviSegmentsTable;
    private javax.swing.JFormattedTextField startDate_date;
    private javax.swing.JLabel startDate_flabel;
    private javax.swing.JLabel startDate_ftlabel;
    private javax.swing.JLabel startDate_label;
    private javax.swing.JFormattedTextField startDate_time;
    private javax.swing.JFormattedTextField timeFormatColumnNumber;
    private javax.swing.JComboBox timeFormatCombo;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel timeLabel1;
    private javax.swing.JFormattedTextField timeVariableName;
    private javax.swing.JButton ukooaAddButton;
    private javax.swing.JButton ukooaDeleteButton;
    private javax.swing.JButton ukooaEditButton;
    private javax.swing.JCheckBox ukooaForceSplittingFileInProfiles;
    private javax.swing.JButton ukooaInsertButton;
    private javax.swing.JPanel ukooaPanel;
    private javax.swing.JTable ukooaSegmentsTable;
    private javax.swing.JFormattedTextField validityValue;

    // End of variables declaration//GEN-END:variables

    public javax.swing.JComboBox getFileTypeValue()
    {
        return fileTypeValue;
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
     * Getter sur endDate_date.
     * 
     * @return the endDate_date
     */
    public javax.swing.JTextField getEndDate_date()
    {
        return endDate_date;
    }

    /**
     * Getter sur endDate_time.
     * 
     * @return the endDate_time
     */
    public javax.swing.JTextField getEndDate_time()
    {
        return endDate_time;
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
     * Getter sur isTimeFilteredEnabled.
     * 
     * @return the isTimeFilteredEnabled
     */
    public javax.swing.JCheckBox getIsTimeFilteredEnabled()
    {
        return isTimeFilteredEnabled;
    }

    /**
     * Getter sur nviForceSplittingFileInProfiles.
     * 
     * @return the nviForceSplittingFileInProfiles
     */
    public javax.swing.JCheckBox getNviForceSplittingFileInProfiles()
    {
        return nviForceSplittingFileInProfiles;
    }

    /**
     * Getter sur ukooaForceSplittingFileInProfiles.
     * 
     * @return the ukooaForceSplittingFileInProfiles
     */
    public javax.swing.JCheckBox getUkooaForceSplittingFileInProfiles()
    {
        return ukooaForceSplittingFileInProfiles;
    }

    /**
     * Getter sur startDate_date.
     * 
     * @return the startDate_date
     */
    public javax.swing.JFormattedTextField getStartDate_date()
    {
        return startDate_date;
    }

    /**
     * Getter sur startDate_time.
     * 
     * @return the startDate_time
     */
    public javax.swing.JFormattedTextField getStartDate_time()
    {
        return startDate_time;
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
     * Getter sur endDate_flabel.
     * 
     * @return the endDate_flabel
     */
    public javax.swing.JLabel getEndDate_flabel()
    {
        return endDate_flabel;
    }

    /**
     * Getter sur endDate_ftlabel.
     * 
     * @return the endDate_ftlabel
     */
    public javax.swing.JLabel getEndDate_ftlabel()
    {
        return endDate_ftlabel;
    }

    /**
     * Getter sur endDate_label.
     * 
     * @return the endDate_label
     */
    public javax.swing.JLabel getEndDate_label()
    {
        return endDate_label;
    }

    /**
     * Getter sur startDate_flabel.
     * 
     * @return the startDate_flabel
     */
    public javax.swing.JLabel getStartDate_flabel()
    {
        return startDate_flabel;
    }

    /**
     * Getter sur startDate_ftlabel.
     * 
     * @return the startDate_ftlabel
     */
    public javax.swing.JLabel getStartDate_ftlabel()
    {
        return startDate_ftlabel;
    }

    /**
     * Getter sur startDate_label.
     * 
     * @return the startDate_label
     */
    public javax.swing.JLabel getStartDate_label()
    {
        return startDate_label;
    }

    /**
     * Getter sur checkValidityColumnNumber.
     * 
     * @return the checkValidityColumnNumber
     */
    public javax.swing.JFormattedTextField getCheckValidityColumnNumber()
    {
        return checkValidityColumnNumber;
    }

    /**
     * Getter sur checkValidityoperatorCombo.
     * 
     * @return the checkValidityoperatorCombo
     */
    public javax.swing.JComboBox getCheckValidityoperatorCombo()
    {
        return checkValidityoperatorCombo;
    }

    /**
     * Getter sur csvPanel.
     * 
     * @return the csvPanel
     */
    public javax.swing.JPanel getCsvPanel()
    {
        return csvPanel;
    }

    /**
     * Getter sur dateFormatColumnNumber.
     * 
     * @return the dateFormatColumnNumber
     */
    public javax.swing.JFormattedTextField getDateFormatColumnNumber()
    {
        return dateFormatColumnNumber;
    }

    /**
     * Getter sur dateFormatCombo.
     * 
     * @return the dateFormatCombo
     */
    public javax.swing.JComboBox getDateFormatCombo()
    {
        return dateFormatCombo;
    }

    /**
     * Getter sur dateLabel.
     * 
     * @return the dateLabel
     */
    public javax.swing.JLabel getDateLabel()
    {
        return dateLabel;
    }

    /**
     * Getter sur firstLine.
     * 
     * @return the firstLine
     */
    public javax.swing.JFormattedTextField getFirstLine()
    {
        return firstLine;
    }

    /**
     * Getter sur isCheckValidityEnabled.
     * 
     * @return the isCheckValidityEnabled
     */
    public javax.swing.JCheckBox getIsCheckValidityEnabled()
    {
        return isCheckValidityEnabled;
    }

    /**
     * Getter sur isDateTimeEnabled.
     * 
     * @return the isDateTimeEnabled
     */
    public javax.swing.JCheckBox getIsDateTimeEnabled()
    {
        return isDateTimeEnabled;
    }

    /**
     * Getter sur joinDateTime.
     * 
     * @return the joinDateTime
     */
    public javax.swing.JCheckBox getJoinDateTime()
    {
        return joinDateTime;
    }

    /**
     * Getter sur lastLine.
     * 
     * @return the lastLine
     */
    public javax.swing.JTextField getLastLine()
    {
        return lastLine;
    }

    /**
     * Getter sur latitudeFormat.
     * 
     * @return the latitudeFormat
     */
    public JComboBox getLatitudeFormat()
    {
        return latitudeFormat;
    }

    /**
     * Getter sur latitudeFormatColumnNumber.
     * 
     * @return the latitudeFormatColumnNumber
     */
    public javax.swing.JFormattedTextField getLatitudeFormatColumnNumber()
    {
        return latitudeFormatColumnNumber;
    }

    /**
     * Getter sur longitudeFormat.
     * 
     * @return the longitudeFormat
     */
    public JComboBox getLongitudeFormat()
    {
        return longitudeFormat;
    }

    /**
     * Getter sur longitudeFormatColumnNumber.
     * 
     * @return the longitudeFormatColumnNumber
     */
    public javax.swing.JFormattedTextField getLongitudeFormatColumnNumber()
    {
        return longitudeFormatColumnNumber;
    }

    /**
     * Getter sur netCDFPanel.
     * 
     * @return the netCDFPanel
     */
    public javax.swing.JPanel getNetCDFPanel()
    {
        return netCDFPanel;
    }

    /**
     * Getter sur nmeaPanel.
     * 
     * @return the nmeaPanel
     */
    public javax.swing.JPanel getNmeaPanel()
    {
        return nmeaPanel;
    }

    /**
     * Getter sur nviPanel.
     * 
     * @return the nviPanel
     */
    public javax.swing.JPanel getNviPanel()
    {
        return nviPanel;
    }

    /**
     * Getter sur timeFormatColumnNumber.
     * 
     * @return the timeFormatColumnNumber
     */
    public javax.swing.JFormattedTextField getTimeFormatColumnNumber()
    {
        return timeFormatColumnNumber;
    }

    /**
     * Getter sur timeFormatCombo.
     * 
     * @return the timeFormatCombo
     */
    public javax.swing.JComboBox getTimeFormatCombo()
    {
        return timeFormatCombo;
    }

    /**
     * Getter sur timeLabel.
     * 
     * @return the timeLabel
     */
    public javax.swing.JLabel getTimeLabel()
    {
        return timeLabel;
    }

    /**
     * Getter sur validityValue.
     * 
     * @return the validityValue
     */
    public javax.swing.JFormattedTextField getValidityValue()
    {
        return validityValue;
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
     * Getter sur checkValidityOperatorComboModel.
     * 
     * @return the checkValidityOperatorComboModel
     */
    public DefaultComboBoxModel getCheckValidityOperatorComboModel()
    {
        return checkValidityOperatorComboModel;
    }

    /**
     * Getter sur nviSegmentsTable.
     * 
     * @return the nviSegmentsTable
     */
    public javax.swing.JTable getNviSegmentsTable()
    {
        return this.nviSegmentsTable;
    }

    /**
     * Getter sur ukooaSegmentsTable.
     * 
     * @return the ukooaSegmentsTable
     */
    public javax.swing.JTable getUkooaSegmentsTable()
    {
        return this.ukooaSegmentsTable;
    }

    /**
     * Getter sur ukooaSegmentsTable et nviSegmentsTable.
     * 
     * @return the ukooaSegmentsTable ou nviSegmentsTable
     */
// public javax.swing.JTable getISegmentsTable()
// {
// if (this.ukooaSegmentsTable.isEditing())
// return this.ukooaSegmentsTable;
// else if (this.nviSegmentsTable.isEditing())
// return this.nviSegmentsTable;
// return null;
// }

    /**
     * Getter sur dateTimeFormat.
     * 
     * @return the dateTimeFormat
     */
    public javax.swing.JComboBox getDateTimeFormat()
    {
        return dateTimeFormat;
    }

    /**
     * Getter sur dateTimeColumnNumber.
     * 
     * @return the dateTimeColumnNumber
     */
    public javax.swing.JFormattedTextField getDateTimeColumnNumber()
    {
        return dateTimeColumnNumber;
    }

    /**
     * Getter sur joinDateTime1.
     * 
     * @return the joinDateTime1
     */
    public javax.swing.JCheckBox getJoinDateTime1()
    {
        return joinDateTime1;
    }

    /**
     * Getter sur latitudeFormatColumnNumber1.
     * 
     * @return the latitudeFormatColumnNumber1
     */
    public javax.swing.JTextField getLatitudeVariableName()
    {
        return latitudeVariableName;
    }

    /**
     * Getter sur longitudeFormatColumnNumber1.
     * 
     * @return the longitudeFormatColumnNumber1
     */
    public javax.swing.JTextField getLongitudeVariableName()
    {
        return longitudeVariableName;
    }

    /**
     * Getter sur lossOfAccuracyField.
     * 
     * @return the lossOfAccuracyField
     */
    public javax.swing.JFormattedTextField getLossOfAccuracyField()
    {
        return lossOfAccuracyField;
    }

    /**
     * Getter sur timeFormatColumnNumber1.
     * 
     * @return the timeFormatColumnNumber1
     */
    public javax.swing.JFormattedTextField getTimeVariableName()
    {
        return timeVariableName;
    }

    /**
     * Getter sur currentInputObject.
     * 
     * @return the currentInputObject
     */
    public IInputParamsObject getCurrentInputObject()
    {
        return currentInputObject;
    }

    /**
     * Getter sur inputController.
     * 
     * @return the inputController
     */
    public IInputDialogController getInputController()
    {
        return inputController;
    }

    /**
     * Getter sur columnNameJoin.
     * 
     * @return the columnNameJoin
     */
    public javax.swing.JFormattedTextField getDateTimeVariableName()
    {
        return datetimeVariableName;
    }

    /**
     * Getter sur dateFormatColumnName.
     * 
     * @return the dateFormatColumnName
     */
    public javax.swing.JFormattedTextField getDateVariableName()
    {
        return dateVariableName;
    }

    /**
     * Getter sur latitudeFormatColumnName.
     * 
     * @return the latitudeFormatColumnName
     */
    public javax.swing.JTextField getLatitudeFormatColumnName()
    {
        return latitudeVariableName;
    }

    /**
     * Getter sur longitudeFormatColumnName.
     * 
     * @return the longitudeFormatColumnName
     */
    public javax.swing.JTextField getLongitudeFormatColumnName()
    {
        return longitudeVariableName;
    }

    /**
     * Getter sur timeFormatColumnName.
     * 
     * @return the timeFormatColumnName
     */
    public javax.swing.JFormattedTextField getTimeFormatColumnName()
    {
        return timeVariableName;
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
     * Getter sur jPanel3.
     * 
     * @return the jPanel3
     */
    public javax.swing.JPanel getjPanel3()
    {
        return jPanel3;
    }

    /**
     * Getter sur jPanel8.
     * 
     * @return the jPanel8
     */
    public javax.swing.JPanel getjPanel8()
    {
        return jPanel8;
    }

    /**
     * Setter sur currentInputObject.
     * 
     * @param currentInputObject
     *            the currentInputObject to set
     */
    public void setCurrentInputObject(IInputParamsObject currentInputObject)
    {
        this.currentInputObject = currentInputObject;
    }

    /**
     * Setter sur inputController.
     * 
     * @param inputController
     *            the inputController to set
     */
    public void setInputController(IInputDialogController inputController)
    {
        this.inputController = inputController;
    }

    /**
     * Getter sur delimiterUsed.
     * 
     * @return delimiterUsed
     */
    public JComboBox getDelimiterUsed()
    {
        return delimiterUsed;
    }

    /**
     * Getter sur jRadioButton_degrees.
     * 
     * @return the jRadioButton_degrees
     */
    public javax.swing.JRadioButton getjRadioButton_degrees()
    {
        return jRadioButton_degrees;
    }

    /**
     * Setter sur jRadioButton_degrees.
     * 
     * @param jRadioButton_degrees
     *            the jRadioButton_degrees to set
     */
    public void setjRadioButton_degrees(
            javax.swing.JRadioButton jRadioButton_degrees)
    {
        this.jRadioButton_degrees = jRadioButton_degrees;
    }

    /**
     * Getter sur jRadioButton_grades.
     * 
     * @return the jRadioButton_grades
     */
    public javax.swing.JRadioButton getjRadioButton_grades()
    {
        return jRadioButton_grades;
    }

    /**
     * Setter sur jRadioButton_grades.
     * 
     * @param jRadioButton_grades
     *            the jRadioButton_grades to set
     */
    public void setjRadioButton_grades(
            javax.swing.JRadioButton jRadioButton_grades)
    {
        this.jRadioButton_grades = jRadioButton_grades;
    }

}
