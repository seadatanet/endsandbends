/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainForm.java
 *
 * Created on Aug 27, 2010, 4:08:35 PM
 */

package fr.ifremer.sismer.endsandbends.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

import com.ago.xml.SAXParserEndsAndBends;

import fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine;
import fr.ifremer.sismer.endsandbends.algorithm.GaussKruegerProjection;
import fr.ifremer.sismer.endsandbends.algorithm.GeoProjection;
import fr.ifremer.sismer.endsandbends.algorithm.NullProjection;
import fr.ifremer.sismer.endsandbends.algorithm.OrthoDistanceToLine;
import fr.ifremer.sismer.endsandbends.algorithm.ProjectionException;
import fr.ifremer.sismer.endsandbends.algorithm.VerticalDistanceToLine;
import fr.ifremer.sismer.endsandbends.common.AppException;
import fr.ifremer.sismer.endsandbends.common.DefaultFolder;
import fr.ifremer.sismer.endsandbends.common.DefaultsHandler;
import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.StringTools;
import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.enums.OutputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.input.CsvNavPath;
import fr.ifremer.sismer.endsandbends.input.INavPath;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints;
import fr.ifremer.sismer.endsandbends.input.NcGenNavPath;
import fr.ifremer.sismer.endsandbends.input.NcNviNavPath;
import fr.ifremer.sismer.endsandbends.input.UkooaNavPath;
import fr.ifremer.sismer.endsandbends.mediation.input.CsvInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.IInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NetCdfInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NviInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.UkooaInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.AsciiColumnOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.AsciiOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.GmlOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.IOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.ShapeOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.WktOutputParamsObject;
import fr.ifremer.sismer.endsandbends.model.ComputationType;
import fr.ifremer.sismer.endsandbends.model.ReferenceTable;
import fr.ifremer.sismer.endsandbends.output.AsciiWriter;
import fr.ifremer.sismer.endsandbends.output.ColumnWriter;
import fr.ifremer.sismer.endsandbends.output.GMLFileWriter;
import fr.ifremer.sismer.endsandbends.output.IEndsAndBendsWriter;
import fr.ifremer.sismer.endsandbends.output.ShapeFileWriter;
import fr.ifremer.sismer.endsandbends.output.WKTFileWriter;
import fr.ifremer.sismer.endsandbends.ui.controller.AsciiColumnOutputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.AsciiOutputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.CsvInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.GmlOutputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.IInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.IOutputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.InputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.NetCdfInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.NmeaInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.NviInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.ShapeOutputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.UkooaInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.WktOutputDialogController;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * @author htaniou
 */
public class MainForm extends javax.swing.JFrame
{

    /**
     * logger
     */
    protected static final Logger myLogger = Logger.getLogger(MainForm.class);

    private static final long serialVersionUID = 4994271838726878743L;
    public static final String SIMPLEDATEFORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss.S";
    public static final String SIMPLEDATEFORMAT_DATE = Messages.getString(
        Messages.MESSAGES, "SIMPLEDATEFORMATMASK");
    public static final String SIMPLEDATEFORMAT_DATEISO = "yyyy-MM-dd";
    public static final String SIMPLEDATEFORMAT_TIME = "HH:mm:ss";
    // Donn�es :
    public NavigationData _data = new NavigationData();
    // Fen�tres de carto
    private PathViewerGen _segmentViewer;
    private PathViewerGen _surveyViewer;

    private String _inputFileName;

// public NavPathPoints[] _data._paths = null;
// private GeoProjection _data._projection = GeoProjection.getDefault();
// private DistanceToLine _data._distance = DistanceToLine.getDefault();
// public long _timeStarted = 0;
// public long _timeEnded = 0;
// public double _minLongitude = 0.0;
// public double _maxLongitude = 0.0;
// public double _minLatitude = 0.0;
// public double _maxLatitude = 0.0;
// public boolean _data._isDateIgnored = false;
    /** Creates new form MainForm */
    public MainForm()
    {
        try
        {
            // Install the look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException ex)
        {
            myLogger.error(ex.getMessage());
        }
        catch (InstantiationException ex)
        {
            myLogger.error(ex.getMessage());
        }
        catch (IllegalAccessException ex)
        {
            myLogger.error(ex.getMessage());
        }
        catch (UnsupportedLookAndFeelException ex)
        {
            myLogger.error(ex.getMessage());
        }

        initComponents();
        onAbout();
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
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        _surveyProcessPanel = new javax.swing.JPanel();
        _btnComputeModelsOptimDistance = new javax.swing.JButton();
        _btnComputeModels = new javax.swing.JButton();
        _segmentProcessPanel = new javax.swing.JPanel();
        _btnDefaultComputeSegment = new javax.swing.JButton();
        _btnUserComputeSegment = new javax.swing.JButton();
        _lblUserComputeSegment = new javax.swing.JLabel();
        _lblDefaultComputeSegment = new javax.swing.JLabel();
        _lblSelectTableLine = new javax.swing.JLabel();
        _lblComputeModels = new javax.swing.JLabel();
        _lblComputeModelsOptimError = new javax.swing.JLabel();
        _lblViewSurvey = new javax.swing.JLabel();
        _btnComputeModelsOptimError = new javax.swing.JButton();
        _lblComputeModelsOptimDistance = new javax.swing.JLabel();
        _btnViewSurvey = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        _btnOpenSourceFile = new javax.swing.JButton();
        _lblOpenSourceFile = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        _btnWriteResultFile = new javax.swing.JButton();
        _lblWriteResultFile = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        _labelOptions = new javax.swing.JLabel();
        _btnOptions = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        _tabReference = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        _tablePaths = new EABTable();
        _tablePaths.getSelectionModel()
            .addListSelectionListener(_listenerTable);
        jLabel5 = new javax.swing.JLabel();
        _menuMain = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ends & Bends");
        setMinimumSize(new java.awt.Dimension(780, 585));
        getContentPane().setLayout(null);

        java.util.ResourceBundle bundle =
            java.util.ResourceBundle
                .getBundle("fr.ifremer.sismer.endsandbends.resources.messages"); // NOI18N
        _surveyProcessPanel.setBorder(javax.swing.BorderFactory
            .createTitledBorder(bundle.getString("MAINFORM_SURVEY_LABEL"))); // NOI18N
        _surveyProcessPanel.setLayout(null);

        _btnComputeModelsOptimDistance
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/run-build-configure.png"))); // NOI18N
        _btnComputeModelsOptimDistance.setActionCommand("jButton1");
        _btnComputeModelsOptimDistance
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _btnComputeModelsOptimDistanceActionPerformed(evt);
                }
            });
        _surveyProcessPanel.add(_btnComputeModelsOptimDistance);
        _btnComputeModelsOptimDistance.setBounds(20, 120, 48, 48);

        _btnComputeModels.setIcon(new javax.swing.ImageIcon(getClass()
            .getResource(
                "/fr/ifremer/sismer/endsandbends/resources/icons/run-build.png"))); // NOI18N
        _btnComputeModels.setActionCommand("jButton1");
        _btnComputeModels
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _btnComputeModelsActionPerformed(evt);
                }
            });
        _surveyProcessPanel.add(_btnComputeModels);
        _btnComputeModels.setBounds(20, 70, 48, 48);

        _segmentProcessPanel.setBorder(javax.swing.BorderFactory
            .createTitledBorder(bundle.getString("MAINFORM_SEGMENT_LABEL"))); // NOI18N
        _segmentProcessPanel.setLayout(null);

        _btnDefaultComputeSegment.setIcon(new javax.swing.ImageIcon(getClass()
            .getResource(
                "/fr/ifremer/sismer/endsandbends/resources/icons/run-build.png"))); // NOI18N
        _btnDefaultComputeSegment.setActionCommand("jButton1");
        _btnDefaultComputeSegment
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _btnDefaultComputeSegmentActionPerformed(evt);
                }
            });
        _segmentProcessPanel.add(_btnDefaultComputeSegment);
        _btnDefaultComputeSegment.setBounds(10, 20, 48, 48);

        _btnUserComputeSegment
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/run-build-configure.png"))); // NOI18N
        _btnUserComputeSegment.setActionCommand("jButton1");
        _btnUserComputeSegment
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _btnUserComputeSegmentActionPerformed(evt);
                }
            });
        _segmentProcessPanel.add(_btnUserComputeSegment);
        _btnUserComputeSegment.setBounds(10, 70, 48, 48);

        _lblUserComputeSegment.setText(bundle
            .getString("MAINFORM_SEGMENT_CUSTOM_LABEL")); // NOI18N
        _lblUserComputeSegment
            .addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    _lblUserComputeSegmentMouseClicked(evt);
                }
            });
        _segmentProcessPanel.add(_lblUserComputeSegment);
        _lblUserComputeSegment.setBounds(70, 90, 150, 14);

        _lblDefaultComputeSegment.setText(bundle
            .getString("MAINFORM_SEGMENT_DEFAULT_LABEL")); // NOI18N
        _lblDefaultComputeSegment
            .addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    _lblDefaultComputeSegmentMouseClicked(evt);
                }
            });
        _segmentProcessPanel.add(_lblDefaultComputeSegment);
        _lblDefaultComputeSegment.setBounds(70, 40, 150, 14);

        _lblSelectTableLine.setFont(new java.awt.Font("DejaVu Sans", 1, 11));
        _lblSelectTableLine.setText(bundle
            .getString("MAINFORM_SELECT_SEGMENT_LABEL")); // NOI18N
        _segmentProcessPanel.add(_lblSelectTableLine);
        _lblSelectTableLine.setBounds(67, 24, 160, 90);

        _surveyProcessPanel.add(_segmentProcessPanel);
        _segmentProcessPanel.setBounds(10, 230, 240, 130);

        _lblComputeModels.setText(bundle
            .getString("MAINFORM_DEFAULT_COMPUTATION_LABEL")); // NOI18N
        _lblComputeModels.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                _lblComputeModelsMouseClicked(evt);
            }
        });
        _surveyProcessPanel.add(_lblComputeModels);
        _lblComputeModels.setBounds(80, 90, 170, 14);

        _lblComputeModelsOptimError.setText(bundle
            .getString("MAINFORM_ERROR_OPTIM_LABEL")); // NOI18N
        _lblComputeModelsOptimError
            .addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    _lblComputeModelsOptimErrorMouseClicked(evt);
                }
            });
        _surveyProcessPanel.add(_lblComputeModelsOptimError);
        _lblComputeModelsOptimError.setBounds(80, 190, 170, 14);

        _lblViewSurvey.setText(bundle.getString("MAINFORM_SHOW_SURVEY_LABEL")); // NOI18N
        _lblViewSurvey.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                _lblViewSurveyMouseClicked(evt);
            }
        });
        _surveyProcessPanel.add(_lblViewSurvey);
        _lblViewSurvey.setBounds(80, 40, 170, 14);

        _btnComputeModelsOptimError
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/run-build-clean.png"))); // NOI18N
        _btnComputeModelsOptimError
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _btnComputeModelsOptimErrorActionPerformed(evt);
                }
            });
        _surveyProcessPanel.add(_btnComputeModelsOptimError);
        _btnComputeModelsOptimError.setBounds(20, 170, 48, 48);

        _lblComputeModelsOptimDistance.setText(bundle
            .getString("MAINFORM_FARTHEST_POINT_LABEL")); // NOI18N
        _lblComputeModelsOptimDistance
            .addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    _lblComputeModelsOptimDistanceMouseClicked(evt);
                }
            });
        _surveyProcessPanel.add(_lblComputeModelsOptimDistance);
        _lblComputeModelsOptimDistance.setBounds(80, 140, 170, 14);

        _btnViewSurvey
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/office-chart-line-stacked.png"))); // NOI18N
        _btnViewSurvey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                _btnViewSurveyActionPerformed(evt);
            }
        });
        _surveyProcessPanel.add(_btnViewSurvey);
        _btnViewSurvey.setBounds(20, 20, 48, 48);

        getContentPane().add(_surveyProcessPanel);
        _surveyProcessPanel.setBounds(10, 80, 260, 370);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle
            .getString("MAINFORM_SOURCE_LABEL"))); // NOI18N
        jPanel1.setLayout(null);

        _btnOpenSourceFile
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/document-open.png"))); // NOI18N
        _btnOpenSourceFile.setActionCommand("jButton1");
        _btnOpenSourceFile
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _btnOpenSourceFileActionPerformed(evt);
                }
            });
        jPanel1.add(_btnOpenSourceFile);
        _btnOpenSourceFile.setBounds(20, 20, 48, 48);

        _lblOpenSourceFile.setText(bundle.getString("MAINFORM_OPEN_LABEL")); // NOI18N
        _lblOpenSourceFile.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                _lblOpenSourceFileMouseClicked(evt);
            }
        });
        jPanel1.add(_lblOpenSourceFile);
        _lblOpenSourceFile.setBounds(80, 40, 160, 14);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 0, 260, 80);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle
            .getString("MAINFORM_TARGET_LABEL"))); // NOI18N
        jPanel2.setLayout(null);

        _btnWriteResultFile
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/document-edit.png"))); // NOI18N
        _btnWriteResultFile.setActionCommand("jButton1");
        _btnWriteResultFile
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    _btnWriteResultFileActionPerformed(evt);
                }
            });
        jPanel2.add(_btnWriteResultFile);
        _btnWriteResultFile.setBounds(20, 20, 48, 48);

        _lblWriteResultFile.setText(bundle.getString("MAINFORM_WRITE_LABEL")); // NOI18N
        _lblWriteResultFile.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                _lblWriteResultFileMouseClicked(evt);
            }
        });
        jPanel2.add(_lblWriteResultFile);
        _lblWriteResultFile.setBounds(80, 40, 160, 14);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 450, 260, 80);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle
            .getString("MAINFORM_OPTIONS_LABEL"))); // NOI18N
        jPanel3.setLayout(null);

        _labelOptions.setText("Options:");
        jPanel3.add(_labelOptions);
        _labelOptions.setBounds(10, 13, 400, 60);

        _btnOptions
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/preferences-system.png"))); // NOI18N
        _btnOptions.setToolTipText(bundle.getString("btn_options")); // NOI18N
        _btnOptions.setActionCommand("jButton1");
        _btnOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                _btnOptionsActionPerformed(evt);
            }
        });
        jPanel3.add(_btnOptions);
        _btnOptions.setBounds(420, 20, 48, 48);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(280, 0, 480, 80);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle
            .getString("MAINFORM_SEGMENT_TABLE_LABEL"))); // NOI18N
        jPanel4.setLayout(null);

        _tabReference.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] { {null, null, null, null},
                    {null, null, null, null}, {null, null, null, null}},
            new String[] {"Optimization", "Model", "Point Nb", "Error"}) {
            Class[] types = new Class[] {java.lang.String.class,
                    java.lang.String.class, java.lang.String.class,
                    java.lang.String.class};

            @Override
            public Class getColumnClass(int columnIndex)
            {
                return types[columnIndex];
            }
        });
        _tabReference
            .setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jScrollPane2.setViewportView(_tabReference);
        _tabReference.getColumnModel().getColumn(0).setPreferredWidth(40);
        _tabReference.getColumnModel().getColumn(1).setPreferredWidth(20);
        _tabReference.getColumnModel().getColumn(2).setPreferredWidth(10);
        _tabReference.getColumnModel().getColumn(3).setPreferredWidth(100);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(10, 360, 460, 82);

        _tablePaths.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {

            }, new String[] {"N\u00b0", "Point Nb", "Model", "Point Nb", "Error"}) {
            Class[] types = new Class[] {java.lang.String.class,
                    java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class};

            @Override
            public Class getColumnClass(int columnIndex)
            {
                return types[columnIndex];
            }
        });
        _tablePaths
            .setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jScrollPane1.setViewportView(_tablePaths);
        _tablePaths.getColumnModel().getColumn(0).setPreferredWidth(10);
        _tablePaths.getColumnModel().getColumn(1).setPreferredWidth(30);
        _tablePaths.getColumnModel().getColumn(2).setPreferredWidth(45);
        _tablePaths.getColumnModel().getColumn(3).setPreferredWidth(30);
        _tablePaths.getColumnModel().getColumn(4).setPreferredWidth(120);
        TableColumn modelColumn = _tablePaths.getColumnModel().getColumn(2);
        JComboBox comboBox = new JComboBox();
        comboBox.setFont(comboBox.getFont().deriveFont(0));
        modelColumn.setCellEditor(new DefaultCellEditor(comboBox));

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(10, 20, 460, 310);

        jLabel5.setFont(new java.awt.Font("DejaVu Sans", 1, 11));
        jLabel5.setText(bundle.getString("REFERENCE_TABLE_TITLE")); // NOI18N
        jPanel4.add(jLabel5);
        jLabel5.setBounds(10, 340, 460, 15);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(280, 80, 480, 450);

        _menuMain.setMinimumSize(new java.awt.Dimension(800, 500));

        fileMenu.setText(bundle.getString("MAINFORM_MENU_FILE")); // NOI18N

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/16/dialog-close.png"))); // NOI18N
        exitMenuItem.setText(bundle.getString("MAINFORM_MENU_EXIT")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        _menuMain.add(fileMenu);

        helpMenu.setText(bundle.getString("MAINFORM_MENU_HELP")); // NOI18N

        contentsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_F1, 0));
        contentsMenuItem
            .setIcon(new javax.swing.ImageIcon(
                getClass()
                    .getResource(
                        "/fr/ifremer/sismer/endsandbends/resources/icons/16/help-contents.png"))); // NOI18N
        contentsMenuItem.setText(bundle.getString("MAINFORM_MENU_CONTENTS")); // NOI18N
        contentsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                contentsMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource(
            "/fr/ifremer/sismer/endsandbends/resources/icons/16/help-about.png"))); // NOI18N
        aboutMenuItem.setText(bundle.getString("MAINFORM_MENU_ABOUT")); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        _menuMain.add(helpMenu);

        setJMenuBar(_menuMain);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _lblOpenSourceFileMouseClicked(java.awt.event.MouseEvent evt)
    {// GEN-FIRST:event__lblOpenSourceFileMouseClicked
        _btnOpenSourceFileActionPerformed(null);
    }// GEN-LAST:event__lblOpenSourceFileMouseClicked

    private void _lblViewSurveyMouseClicked(java.awt.event.MouseEvent evt)
    {// GEN-FIRST:event__lblViewSurveyMouseClicked
        _btnViewSurveyActionPerformed(null);
    }// GEN-LAST:event__lblViewSurveyMouseClicked

    private void _lblComputeModelsMouseClicked(java.awt.event.MouseEvent evt)
    {// GEN-FIRST:event__lblComputeModelsMouseClicked
        _btnComputeModelsActionPerformed(null);
    }// GEN-LAST:event__lblComputeModelsMouseClicked

    private void _lblComputeModelsOptimDistanceMouseClicked(
            java.awt.event.MouseEvent evt)
    {// GEN-FIRST:event__lblComputeModelsOptimDistanceMouseClicked
        _btnComputeModelsOptimDistanceActionPerformed(null);
    }// GEN-LAST:event__lblComputeModelsOptimDistanceMouseClicked

    private void _lblComputeModelsOptimErrorMouseClicked(
            java.awt.event.MouseEvent evt)
    {// GEN-FIRST:event__lblComputeModelsOptimErrorMouseClicked
        _btnComputeModelsOptimErrorActionPerformed(null);
    }// GEN-LAST:event__lblComputeModelsOptimErrorMouseClicked

    private void _lblDefaultComputeSegmentMouseClicked(
            java.awt.event.MouseEvent evt)
    {// GEN-FIRST:event__lblDefaultComputeSegmentMouseClicked
        _btnDefaultComputeSegmentActionPerformed(null);
    }// GEN-LAST:event__lblDefaultComputeSegmentMouseClicked

    private void _lblUserComputeSegmentMouseClicked(
            java.awt.event.MouseEvent evt)
    {// GEN-FIRST:event__lblUserComputeSegmentMouseClicked
        _btnUserComputeSegmentActionPerformed(null);
    }// GEN-LAST:event__lblUserComputeSegmentMouseClicked

    private void _lblWriteResultFileMouseClicked(java.awt.event.MouseEvent evt)
    {// GEN-FIRST:event__lblWriteResultFileMouseClicked
        _btnWriteResultFileActionPerformed(null);
    }// GEN-LAST:event__lblWriteResultFileMouseClicked

    private void _btnComputeModelsActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnComputeModelsActionPerformed
     // TODO add your handling code here:
        try
        {
            onComputeModels();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnComputeModelsActionPerformed

    private void _btnComputeModelsOptimErrorActionPerformed(
            java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnComputeModelsOptimErrorActionPerformed
     // TODO add your handling code here:
        try
        {
            onComputeModelsOptimError();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnComputeModelsOptimErrorActionPerformed

    private void _btnViewSurveyActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnViewSurveyActionPerformed
        try
        {
            onViewSurvey();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnViewSurveyActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_exitMenuItemActionPerformed
        try
        {
            onQuit();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event_exitMenuItemActionPerformed

    private void _btnOpenSourceFileActionPerformed(
            java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnOpenSourceFileActionPerformed
        try
        {
            onOpenSourceFile();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnOpenSourceFileActionPerformed

    private void _btnOptionsActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnOptionsActionPerformed
        try
        {
            onOptions();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnOptionsActionPerformed

    private void _btnComputeModelsOptimDistanceActionPerformed(
            java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnComputeModelsOptimDistanceActionPerformed
        try
        {
            onComputeModelsOptimDistance();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnComputeModelsOptimDistanceActionPerformed

    private void _btnDefaultComputeSegmentActionPerformed(
            java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnDefaultComputeSegmentActionPerformed
        try
        {
            onDefaultComputeSegment();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnDefaultComputeSegmentActionPerformed

    private void _btnUserComputeSegmentActionPerformed(
            java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnUserComputeSegmentActionPerformed
        try
        {
            onUserComputeSegment();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnUserComputeSegmentActionPerformed

    private void _btnWriteResultFileActionPerformed(
            java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event__btnWriteResultFileActionPerformed
        try
        {
            onWriteResultFile();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event__btnWriteResultFileActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_aboutMenuItemActionPerformed
        try
        {
            onAbout();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event_aboutMenuItemActionPerformed

    private void contentsMenuItemActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_contentsMenuItemActionPerformed
        try
        {
            onHelp();
        }
        catch (Throwable t)
        {
            handleUncaugthException(t);
        }
    }// GEN-LAST:event_contentsMenuItemActionPerformed

    private void menuParametersActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_menuParametersActionPerformed
// try
// {
// onOpenFormatsParameters();
// }
// catch (Throwable t)
// {
// handleUncaugthException(t);
// }
    }// GEN-LAST:event_menuParametersActionPerformed

// /**
// * @param args
// * the command line arguments
// */
// public static void main(String args[])
// {
// java.awt.EventQueue.invokeLater(new Runnable() {
// public void run()
// {
// new MainForm().setVisible(true);
// }
// });
// }

    private final ListSelectionListener _listenerTable =
        new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e)
            {
                try
                {
                    if (e.getValueIsAdjusting())
                    {
                        return;
                    }
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                    onListSelChanged(lsm);
                }
                catch (Throwable t)
                {
                    handleUncaugthException(t);
                }
            }
        };

    /**
     * Gestion des exceptions non intercept�es
     * 
     * @param t
     */
    protected void handleUncaugthException(Throwable t)
    {
        t.printStackTrace();

        StringBuilder message = new StringBuilder(Globals.UNCAUGHT_EXCEPTION);

        message.append(StringTools.getStackTrace(t));

        JOptionPane.showMessageDialog(this, message.toString(),
            Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
            JOptionPane.ERROR_MESSAGE);

        // Fin de la progression :
        setCursor(Cursor.getDefaultCursor());
        setEnabled(true);

        // Mise � jour de la table :
        updateContents(false);

        updateUI();
    }

    /**
     * Mise � jour du contenu de la table...
     */
    private void updateContents(boolean computed)
    {
        ((EABTable) _tablePaths).resetGlobalModel(computed);
        ((EABTable) _tablePaths).updateUI(_data._paths);
    }

    /**
     * Method updateUI, mise � jour de l'interface (grisage des boutons et
     * remplissage de la description de l'action)
     */
    private void updateUI()
    {
        // R�cup�ration d'�tat :
        // rowsSelected : nb de ligne de segment s�lectionn�es
        int rowsSelected = 0;
        // pointsSelected : nb de points s�lectionn�s
        int pointsSelected = 0;
        // totalSelected : la ligne de total est s�lectionn�e
        boolean totalSelected = false;
        int[] rows = _tablePaths.getSelectedRows();
        for (int index = 0; index < rows.length; index++)
        {
            if (rows[index] < _data._paths.length)
            {
                rowsSelected++;
                pointsSelected += _data._paths[rows[index]].getPointsCount();
            }
            else if (rows[index] == _data._paths.length)
            {
                totalSelected = true;
            }
        }

        // Actions globales :
        boolean computed = (_data._paths != null);

        _btnOpenSourceFile.setEnabled(true);
        _lblOpenSourceFile.setEnabled(true);

        _btnViewSurvey.setEnabled(computed);
        _lblViewSurvey.setEnabled(computed);

        _btnComputeModels.setEnabled(computed);
        _lblComputeModels.setEnabled(computed);

        _btnComputeModelsOptimDistance.setEnabled(computed);
        _lblComputeModelsOptimDistance.setEnabled(computed);

        _btnComputeModelsOptimError.setEnabled(computed);
        _lblComputeModelsOptimError.setEnabled(computed);

        _btnWriteResultFile.setEnabled(computed);
        _lblWriteResultFile.setEnabled(computed);

        // Cas particulier du bouton "Show segment"
        boolean canShowSegment =
            computed && (rowsSelected > 0) && !totalSelected;
// _btnViewPath.setEnabled(canShowSegment);
// _lblShowSegment.setVisible(canShowSegment);

        // Boutons sur les trac�s :
        boolean selection =
            computed && (rowsSelected == 1) && (pointsSelected > 2)
                    && !totalSelected;
// _btnViewPath.setEnabled(selection);
        _btnUserComputeSegment.setEnabled(selection);
        _lblUserComputeSegment.setEnabled(selection);

        _btnDefaultComputeSegment.setEnabled(selection);
        _lblDefaultComputeSegment.setEnabled(selection);

        // Libell�s
        _lblSelectTableLine.setVisible(!selection);
        _lblDefaultComputeSegment.setVisible(selection);
        _lblUserComputeSegment.setVisible(selection);

        // Texte des options :

        // [TNU]Creating a new java.lang.String object using the no-argument
        // constructor wastes memory because the object so created will be
        // functionally indistinguishable from the empty string constant "".
        // Java guarantees that identical string constants will be represented
        // by the same String object. Therefore, you should just use the empty
        // string constant directly.
        // [PERF] utilisation d'un StringBuilder

// String options = new String();
// options = "Projection : ";

        String gap = "";
        if (NcNviNavPath.getGapType() == OptionConstants.GAP_TYPE_TIME)
        {
            gap = String.valueOf(NcNviNavPath.getGapTimeInMinuts() + " min");
        }
        else
        {
            gap = String.valueOf(NcNviNavPath.getGapDistance() + " m");
        }
        _labelOptions
            .setText(Globals.OPTIONS_SUMMARY_TEMPLATE.format(new String[] {gap,
                    String.valueOf(NavPathPoints.get_pathLimit()),
                    _data._projection.getName(), _data._distance.getName(),
                    String.valueOf(GaussKruegerProjection.getLimitDistance()),
                    String.valueOf(GaussKruegerProjection.getCuttingDistance())}));

// StringBuilder options = new StringBuilder("Projection : ");
//
// if (_data._projection != null)
// {
// options.append(_data._projection.getName());
// }
// else
// {
// options.append("<null>");
// }
//
// options.append(", distance : ");
//
// if (_data._distance != null)
// {
// options.append(_data._distance.getName());
// }
// else
// {
// options.append("<null>");
// }
//
// _labelOptions.setText(options.toString());
// [/TNU]

        Set<NavPathPoints> segmentsToHighlight = new HashSet<NavPathPoints>();

        if (null != _segmentViewer && _segmentViewer.isVisible())
        {
            int[] rowIndexes = _tablePaths.getSelectedRows();

            if (null != rowIndexes && rowIndexes.length > 0)
            {
                List<NavPathPoints> pathList =
                    new ArrayList<NavPathPoints>(rowIndexes.length);
                List<String> segmentNumber =
                    new ArrayList<String>(rowIndexes.length);

                for (int index : rowIndexes)
                {
                    if (index < _data._paths.length)
                    {
                        NavPathPoints path = _data._paths[index];
                        pathList.add(path);
                        segmentNumber.add(String.valueOf(_tablePaths
                            .getValueAt(index, 0)));

                        segmentsToHighlight.add(path);
                    }
                }

                _segmentViewer.reinit(pathList,
                    computeViewerTitle(segmentNumber), _data._projection,
                    _data._globalProjection);
            }
        }

        if (null != _data._paths && null != _surveyViewer
                && _surveyViewer.isVisible())
        {
            List<NavPathPoints> pathList =
                new ArrayList<NavPathPoints>(_data._paths.length);

            for (NavPathPoints path : _data._paths)
            {
                pathList.add(path);
            }

            _surveyViewer.reinit(pathList, segmentsToHighlight, null,
                _data._projection, _data._globalProjection);
        }
    }

    /**
     * Method onInitWindow, initialisation de la fen�tre.
     */
    public void onInitWindow()
    {
        // Charger les defauts :
        DefaultsHandler.loadDefaults();
        initOptions();

        // Mettre � jour l'affichage :
        updateUI();
    }

    private void handleInputFileTypeAndInitDialog(String format,
            InputDialog dlg, SAXParserEndsAndBends inputParser)
    {
        IInputDialogController controller;
        if (InputFilesTypes.CSV_FORMAT.getInputFileType().equals(format))
        {
            controller =
                new CsvInputDialogController(dlg,
                    inputParser.getInputParamsObject());
            controller.initializeDialog();
        }
        else if (InputFilesTypes.NETCDF_NVI_FORMAT.getInputFileType().equals(
            format))
        {
            controller =
                new NviInputDialogController(dlg,
                    inputParser.getInputParamsObject());
            controller.initializeDialog();
        }
        else if (InputFilesTypes.GENERAL_NETCDF_FORMAT.getInputFileType()
            .equals(format))
        {
            controller =
                new NetCdfInputDialogController(dlg,
                    inputParser.getInputParamsObject());
            controller.initializeDialog();
        }
        else if (InputFilesTypes.NMEA_183_FORMAT.getInputFileType().equals(
            format))
        {
            controller =
                new NmeaInputDialogController(dlg,
                    inputParser.getInputParamsObject());
            controller.initializeDialog();
        }
        else if (InputFilesTypes.UKOOA_P1_90.getInputFileType().equals(format))
        {
            controller =
                new UkooaInputDialogController(dlg,
                    inputParser.getInputParamsObject());
            controller.initializeDialog();
        }

    }

    /**
     * Method onOpenSourceFile.
     */
    private void onOpenSourceFile()
    {
        // Vider :
        _data._paths = null;
        _data._isDateIgnored = false;

        // [TNU] Code explicitly invokes garbage collection. Except for
        // specific use in benchmarking, this is very dubious.
        // System.gc();
        // [/TNU]

        // on ferme les fen�tres de carto

        _tablePaths.clearSelection();
        disposePathViewer(_segmentViewer);
        disposePathViewer(_surveyViewer);

        // INFO CLT Interface de demande de renseignements sur le fichier
        // d'entr�e.
        InputDialog dlg;
        IInputParamsObject inputDialogParameters = null;

        int n =
            JOptionPane.showConfirmDialog(this,
                Globals.LOAD_PARAMETER_FILE_QUESTION,
                Globals.LOAD_PARAMETER_FILE_TITLE, JOptionPane.YES_NO_OPTION);

        if (JOptionPane.OK_OPTION == n)
        {// INFO CLT Interface de demande de renseignements sur le fichier

            // Create a file chooser
            final JFileChooser fc = new JFileChooser();

            fc.setCurrentDirectory(new File(DefaultsHandler
                .getDefaultPath(DefaultFolder.INPUT_PARAMETER_FOLDER)));

            // In response to a button click:
            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File chosenFile = fc.getSelectedFile();

                DefaultsHandler.setDefaultPath(
                    DefaultFolder.INPUT_PARAMETER_FOLDER,
                    chosenFile.getParent());

                SAXParserEndsAndBends inputParser =
                    new SAXParserEndsAndBends(chosenFile);
                try
                {
                    inputParser.parseDocument();
                    // d'entr�e.
                    dlg = new InputDialog(this, true);
                    InputFilesTypes format =
                        inputParser.getInputParamsObject().getFormat();
                    handleInputFileTypeAndInitDialog(format.getInputFileType(),
                        dlg, inputParser);
                    inputDialogParameters = dlg.showDialog();
                }
                catch (EABException e)
                {
                    e.printStackTrace();

                    JOptionPane.showMessageDialog(this, e.getMessage(),
                        Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        else if (JOptionPane.NO_OPTION == n)
        {
            // d'entr�e.
            dlg = new InputDialog(this, true);
            dlg.setInputController(new InputDialogController(dlg, null));
            inputDialogParameters = dlg.showDialog();
        }

        if (null != inputDialogParameters)
        {

            // D�but de progression :
            ProgressMonitor progress =
                new ProgressMonitor(this, Messages.getString(Messages.MESSAGES,
                    "prog_loadfile"), "", 0, 4); //$NON-NLS-1$
            Cursor oldone = getCursor();
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            setEnabled(false);

            // Protection pour sortir correctement dans tous les cas
            // d'erreur...
            try
            {
                // R�cup�rer le fichier :
                File file = inputDialogParameters.getSelectedFile();

                if (null != file)
                {
                    _inputFileName = file.getName();

                    DefaultsHandler.setDefaultPath(DefaultFolder.INPUT_FOLDER,
                        file.getParent());

                    // Cr�er un chemin unique avec tous les points :
                    // et passage des param�tres de configuration

                    INavPath path = null;

                    InputFilesTypes inputFileType =
                        inputDialogParameters.getFormat();

                    if (InputFilesTypes.NETCDF_NVI_FORMAT.equals(inputFileType))
                    {
                        if (((NviInputParamsObject) inputDialogParameters)
                            .isSplitFileEnabled())
                        {
                            path =
                                new NcNviNavPath(
                                    ((NviInputParamsObject) inputDialogParameters)
                                        .getDateSegmentList());
                        }
                        else
                        {
                            path = new NcNviNavPath();
                        }
                    }
                    if (InputFilesTypes.UKOOA_P1_90.equals(inputFileType)
                            || InputFilesTypes.UKOOA_P1_90 == null)
                    {
                        if (((UkooaInputParamsObject) inputDialogParameters)
                            .isSplitFileEnabled())
                        {
                            path =
                                new UkooaNavPath(
                                    ((UkooaInputParamsObject) inputDialogParameters)
                                        .getDateSegmentList(),
                                    ((UkooaInputParamsObject) inputDialogParameters)
                                        .isDegreeTheAngularUnit());
                        }
                        else
                        {
                            path =
                                new UkooaNavPath(
                                    ((UkooaInputParamsObject) inputDialogParameters)
                                        .isDegreeTheAngularUnit());
                        }
                    }
                    else if (InputFilesTypes.NMEA_183_FORMAT
                        .equals(inputFileType))
                    {
                        // TODO � impl�menter
                        // path = new NcNviNavPath();
                    }
                    else if (InputFilesTypes.GENERAL_NETCDF_FORMAT
                        .equals(inputFileType))
                    {
                        path =
                            new NcGenNavPath(
                                (NetCdfInputParamsObject) inputDialogParameters);
                    }
                    else if (InputFilesTypes.CSV_FORMAT.equals(inputFileType))
                    {
                        _data._isDateIgnored =
                            ((CsvInputParamsObject) inputDialogParameters)
                                .isDateTimeEnabled();
                        path =
                            new CsvNavPath(
                                (CsvInputParamsObject) inputDialogParameters);
                    }

                    if (null != path
                            && !path.loadNavFile(file.getAbsolutePath()))
                    {
                        throw new AppException(Messages.getString(
                            Messages.MESSAGES, "error_readfile")); //$NON-NLS-1$
                    }

                    if (inputDialogParameters.isTimeFilterEnabled())
                    {
                        path.filterDates(inputDialogParameters.getStartDate(),
                            inputDialogParameters.getEndDate());
                    }

                    _data._timeStarted = path.getTimeStarted();
                    _data._timeEnded = path.getTimeEnded();
                    _data._minLongitude = path.getMinLongitude();
                    _data._maxLongitude = path.getMaxLongitude();
                    _data._minLatitude = path.getMinLatitude();
                    _data._maxLatitude = path.getMaxLatitude();
                    progress.setProgress(1);
                    if (progress.isCanceled())
                    {
                        throw new AppException(Messages.getString(
                            Messages.MESSAGES, "error_canceled")); //$NON-NLS-1$
                    }

                    // Calcul min et max des positions :
                    // NavPath.Statistics statsLat =
                    // path.getLatitudeStatistics();
                    // NavPath.Statistics statsLong =
                    // path.getLongitudeStatistics();
                    // _minLatitude = statsLat.minValue;
                    // _maxLatitude = statsLat.maxValue;
                    // _minLongitude = statsLong.minValue; // west
                    // _maxLongitude = statsLong.maxValue; // east

                    // on verifie qu'on essaie pas de decouper en temps un
// fichier sans donnees temporelles
                    boolean isFileCompatibleWithGapType =
                        path.checkGapTypeCompatibilityWithFile();

                    if (!isFileCompatibleWithGapType)
                    {
                        // pour evol mail19/09/2012 (non confirm�e)
                        // remplacer throw new ... par ligne ci-dessous
// path.setGapToDistance();

                        throw new AppException(Messages.getString(
                            Messages.MESSAGES,
                            "error_fileCompatibilityWithGapType")); //$NON-NLS-1$

                    }
                    // D�composition en trac�s continus :
                    NavPathPoints[] paths = path.splitIntoContinuousPaths();
                    if (paths == null)
                    {
                        throw new AppException(Messages.getString(
                            Messages.MESSAGES, "error_splitpath")); //$NON-NLS-1$
                    }
                    progress.setProgress(2);
                    if (progress.isCanceled())
                    {
                        throw new AppException(Messages.getString(
                            Messages.MESSAGES, "error_canceled")); //$NON-NLS-1$
                    }
                    INavPath copyPath = path;
                    path = null;

                    // Tester et �ventuellement red�compos� en trac�s pouvant
                    // �tre projet�s :
                    for (int index = 0; index < paths.length; index++)
                    {
                        NavPathPoints[] pathsproj =
                            paths[index].splitProjection(_data._projection);
                        if (pathsproj == null)
                        {
                            throw new AppException(Messages.getString(
                                Messages.MESSAGES, "error_readfile")); //$NON-NLS-1$
                        }
                        if (pathsproj.length > 1)

                        {
                            NavPathPoints[] pathsnew =
                                new NavPathPoints[paths.length
                                        + pathsproj.length - 1];
                            for (int i = 0; i < index; i++)
                            {
                                pathsnew[i] = paths[i];
                            }
                            for (int i = 0; i < pathsproj.length; i++)
                            {
                                pathsnew[index + i] = pathsproj[i];
                            }
                            for (int i = index + 1; i < paths.length; i++)
                            {
                                pathsnew[pathsproj.length + i - 1] = paths[i];
                            }
                            paths = pathsnew;
                            index += pathsproj.length - 1; // il y a un ++ qui
                            // va
                            // encore passer...
                        }
                        else
                        {
                            paths[index] = pathsproj[0];
                        }
                        if (progress.isCanceled())
                        {
                            throw new AppException(Messages.getString(
                                Messages.MESSAGES, "error_canceled")); //$NON-NLS-1$
                        }
                    }

                    // Log
                    int cptNav = 0;
                    myLogger
                        .info("\n-------------------\n split summary for file "
                                + _inputFileName + "\n-------------------\n");

                    for (NavPathPoints nav : paths)
                    {

                        myLogger.info("seg. no " + cptNav + " "
                                + paths[cptNav].getNbPoints() + " pts "
                                + paths[cptNav].getStartType().toString()
                                + " - " + paths[cptNav].getEndType().toString()
                                + " - " + "LON: "
                                + paths[cptNav].getLongitude(0) + " - "
                                + "LAT: " + paths[cptNav].getLatitude(0));

                        cptNav++;
                    }

                    progress.setProgress(3);

                    // projection globale
                    projectAllSegmentsInGlobalProj(paths, copyPath, progress);

                    // projection locale pour chaque segment
                    projectAllSegmentsInLocaleProj(paths, progress);

                    progress.setProgress(4);

                    // Stocker :
                    _data._paths = paths;
                }
            }
            catch (EABException ue)
            {
                ue.printStackTrace();

                JOptionPane.showMessageDialog(this, ue.getMessage(),
                    Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                    JOptionPane.ERROR_MESSAGE);
                _data._paths = null;
            }
            catch (AppException ue)
            {
                ue.printStackTrace();

                JOptionPane.showMessageDialog(this, ue.getMessage(),
                    Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                    JOptionPane.ERROR_MESSAGE);
                _data._paths = null;
            }

            // Fin de la progression :
            setCursor(oldone);
            setEnabled(true);
            progress.close();
        }
        else
        {
            _inputFileName = null;
        }

        // Mise � jour de la table :
        updateContents(false);

        // Mettre � jour l'affichage :
        updateUI();

        ReferenceTable.getInstance().resetReferences(_tabReference);

        updateTitle();
    }

    private void projectAllSegmentsInGlobalProj(NavPathPoints[] paths,
            INavPath copyPath, ProgressMonitor progress) throws AppException
    {
        /**
         * PROJECTION SELON LA PROJECTION GLOBALE (car la premi�re carte ouverte
         * est la carte globale)
         */
        // init projection globale
        _data._globalProjection.initialize(copyPath.getLatitudes(),
            copyPath.getLongitudes());

        // Faire la projection :
        for (int index = 0; index < paths.length; index++)
        {
            // Protection des erreurs de la projection :
            try
            {
                if (paths[index] != null)
                {
                    // projection
                    paths[index]
                        .projectInGlobalProjection(_data._globalProjection);
                }
                else
                {
                    throw new AppException(Messages.getString(
                        Messages.MESSAGES, "error_reading_file")); //$NON-NLS-1$
                }
            }
            catch (ProjectionException pe)
            {
                pe.printStackTrace();
                JOptionPane
                    .showMessageDialog(
                        this,
                        "Trac\u00E9 n\u00B0" + index + " : " + pe.getMessage(), Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
            }

            // Test de l'annulation :
            if (progress.isCanceled())
            {
                throw new AppException(Messages.getString(Messages.MESSAGES,
                    "error_canceled")); //$NON-NLS-1$
            }
        }
        if (((GaussKruegerProjection) _data._globalProjection)
            .isDistLimitExceeded())
        {
            JOptionPane
                .showMessageDialog(
                    this,
                    Messages.getString(Messages.MESSAGES,
                        "info_gausskrueger_5grad"), Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void projectAllSegmentsInLocaleProj(NavPathPoints[] paths,
            ProgressMonitor progress) throws AppException
    {
        /**
         * PROJECTION SELON LA PROJECTION Locale
         */
        // init projection Locale

        // Faire la projection :
        for (int index = 0; index < paths.length; index++)
        {
            // Protection des erreurs de la projection :
            try
            {
                if (paths[index] != null)
                {
                    _data._projection.initialize(paths[index].getLatitudes(),
                        paths[index].getLongitudes());

                    // projection
                    paths[index].projectInLocaleProjection(_data._projection);
                }
                else
                {
                    throw new AppException(Messages.getString(
                        Messages.MESSAGES, "error_reading_file")); //$NON-NLS-1$
                }
            }
            catch (ProjectionException pe)
            {
                pe.printStackTrace();
                JOptionPane
                    .showMessageDialog(
                        this,
                        "Trac\u00E9 n\u00B0" + index + " : " + pe.getMessage(), Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
            }
            if (((GaussKruegerProjection) _data._projection)
                .isDistLimitExceeded())
            {

                throw new ProjectionException("Trac\u00E9 n\u00B0"
                        + index
                        + " : "
                        + Messages.getString(Messages.MESSAGES,
                            "error_gauskrueger_5grad")); //$NON-NLS-1$
            }
            // Test de l'annulation :
            if (progress.isCanceled())
            {
                throw new AppException(Messages.getString(Messages.MESSAGES,
                    "error_canceled")); //$NON-NLS-1$
            }
        }
    }

    /**
     * @param segmentViewer
     */
    private void disposePathViewer(PathViewerGen viewer)
    {
        if (null != viewer)
        {
            viewer.dispose();
            viewer = null;
        }
    }

    /**
     * Mise � jour du titre de la fen�tre principale
     */
    private void updateTitle()
    {
        String title;

        if (null == _inputFileName)
        {
            title = Globals.MAIN_TITLE;
        }
        else
        {
            title =
                Globals.MAIN_TITLE_INPUT.format(new String[] {_inputFileName});
        }

        setTitle(title);
    }

    /**
     * Method onComputeModels.
     */
    private void onComputeModels()
    {
        // Parcourir les trac�s :
        if ((_data._paths != null) && (_data._paths.length > 0))
        {
            // Initialiser la progression :
            ProgressMonitor progress =
                new ProgressMonitor(this, Messages.getString(Messages.MESSAGES,
                    "prog_compute"), "", 0, _data._paths.length); //$NON-NLS-1$

            // Protection pour sortir correctement dans tous les cas
            // d'erreur...
            try
            {

                // Traitement d'un profil :
                for (int i = 0; i < _data._paths.length; i++)
                {
                    // Calculer :
                    _data._paths[i].compute(_data._distance);

                    // Progression :
                    progress.setProgress(i + 1);
                    if (progress.isCanceled())
                    {
                        throw new AppException(Messages.getString(
                            Messages.MESSAGES, "error_canceled")); //$NON-NLS-1$
                    }
                }

            }
            catch (AppException ue)
            {
                JOptionPane.showMessageDialog(this, ue.getMessage(),
                    Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                    JOptionPane.ERROR_MESSAGE);
            }

            // Fin de traitement :
            progress.close();
        }

        // Mise � jour de la table :
        updateContents(true);

        // Mettre � jour l'affichage :
        updateUI();

        // Mise � jour de la table de r�f�rence
        ReferenceTable.getInstance().updateReferenceTable(_tabReference,
            _tablePaths, ComputationType.DEFAULT);
    }

    /**
     * Method onComputeModelsOptimDistance.
     */
    private void onComputeModelsOptimDistance()
    {
        // Calculer
        NavPathPoints.computeAcrossPathsOnDistance(_data._distance,
            _data._paths, NavPathPoints.get_pathLimit());

        // Mise � jour de la table :
        updateContents(true);

        // Mettre � jour l'affichage :
        updateUI();

        // Mise � jour de la table de r�f�rence
        ReferenceTable.getInstance().updateReferenceTable(_tabReference,
            _tablePaths, ComputationType.FARTHEST);
    }

    /**
     * Method onComputeModelsOptimError.
     */
    private void onComputeModelsOptimError()
    {
        // Calculer
        NavPathPoints.computeAcrossPathsOnError(_data._distance, _data._paths,
            NavPathPoints.get_pathLimit());

        // Mise � jour de la table :
        updateContents(true);

        // Mettre � jour l'affichage :
        updateUI();

        // Mise � jour de la table de r�f�rence
        ReferenceTable.getInstance().updateReferenceTable(_tabReference,
            _tablePaths, ComputationType.ERROR);
    }

    private void handleOutputFileTypeAndInitDialog(String format,
            ResultsDialog dlg, SAXParserEndsAndBends outputParser)
    {
        IOutputDialogController controller;
        if (OutputFilesTypes.ASCII_IFREMER_FORMAT.getOutputFileType().equals(
            format))
        {
            controller =
                new AsciiOutputDialogController(dlg,
                    outputParser.getOutputParamsObject());
            controller.initializeDialog();
        }
        else if (OutputFilesTypes.WKT_FORMAT.getOutputFileType().equals(format))
        {
            controller =
                new WktOutputDialogController(dlg,
                    outputParser.getOutputParamsObject());
            controller.initializeDialog();
        }
        else if (OutputFilesTypes.COLUMNS_ASCII_FORMAT.getOutputFileType()
            .equals(format))
        {
            controller =
                new AsciiColumnOutputDialogController(dlg,
                    outputParser.getOutputParamsObject());
            controller.initializeDialog();
        }
        else if (OutputFilesTypes.GML_FORMAT.getOutputFileType().equals(format))
        {
            controller =
                new GmlOutputDialogController(dlg,
                    outputParser.getOutputParamsObject());
            controller.initializeDialog();
        }
        else if (OutputFilesTypes.SHAPE_FORMAT.getOutputFileType().equals(
            format))
        {
            controller =
                new ShapeOutputDialogController(dlg,
                    outputParser.getOutputParamsObject());
            controller.initializeDialog();
        }

    }

    /**
     * Method onWriteResultFile.
     */
    private void onWriteResultFile()
    {
        if (null != _data._paths)
        {
            try
            {
                // INFO CLT Interface de demande de renseignements sur le
                // fichier
                // d'entr�e.
                int n =
                    JOptionPane.showConfirmDialog(this,
                        Globals.LOAD_PARAMETER_FILE_QUESTION,
                        Globals.LOAD_PARAMETER_FILE_TITLE,
                        JOptionPane.YES_NO_OPTION);
                ResultsDialog dlg;
                IOutputParamsObject resultsParams = null;
                if (JOptionPane.OK_OPTION == n)
                {// INFO CLT Interface de demande de renseignements sur le
                 // fichier

                    // Create a file chooser
                    final JFileChooser fc = new JFileChooser();
                    fc.setCurrentDirectory(new File(DefaultsHandler
                        .getDefaultPath(DefaultFolder.OUTPUT_PARAMETER_FOLDER)));

                    // In response to a button click:
                    int returnVal = fc.showOpenDialog(this);

                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        File chosenFile = fc.getSelectedFile();

                        DefaultsHandler.setDefaultPath(
                            DefaultFolder.OUTPUT_PARAMETER_FOLDER,
                            chosenFile.getParent());

                        SAXParserEndsAndBends outputParser =
                            new SAXParserEndsAndBends(chosenFile);
                        outputParser.parseDocument();
                        // d'entr�e.
                        dlg = new ResultsDialog(this, true);
                        String format =
                            outputParser.getOutputParamsObject().getFormat()
                                .getOutputFileType();
                        handleOutputFileTypeAndInitDialog(format, dlg,
                            outputParser);
                        resultsParams = dlg.showDialog();
                    }

                }
                else
                {
                    // d'entr�e.
                    dlg = new ResultsDialog(this, true);
                    resultsParams = dlg.showDialog();
                }
                if (null != resultsParams)
                {
                    // V�rifications :
                    if (_data._paths == null || _data._paths.length == 0)
                    {
                        return;
                    }
                    int totalPoints = 0;
                    int optimPoints = 0;
                    NavPathPoints path = null;
                    int model;
                    for (int index = 0; index < _data._paths.length; index++)
                    {
                        path = _data._paths[index];
                        model = path.getSelectedModel();
                        totalPoints += path.getPointsCount();
                        if (model > 0)
                        {
                            optimPoints += path.getModelCount(model);
                        }
                        else
                        {
                            optimPoints += path.getPointsCount();
                        }
                    }
                    /*if (optimPoints > NavPathPoints.__pathLimit)
                    {
                        int choice =
                            JOptionPane.showConfirmDialog(this,
                                Messages.getString(Messages.MESSAGES,
                                    "warning_toomuchpoints"), Messages //$NON-NLS-1$
                                    .getString(Messages.MESSAGES, "warning_title"), //$NON-NLS-1$
                                JOptionPane.YES_NO_OPTION);
                        if (choice != JOptionPane.YES_OPTION)
                        {
                            return;
                        }
                    }*/

                    OutputFilesTypes fileType = resultsParams.getFormat();
                    if (fileType != null)
                    {
                        File outputFile = resultsParams.getSelectedFile();

                        DefaultsHandler
                            .setDefaultPath(DefaultFolder.OUTPUT_FOLDER,
                                outputFile.getParent());

                        switch (fileType)
                        {
                            case ASCII_IFREMER_FORMAT:
                                // R�cup�ration des pr�f�rences sur le format.
                                IEndsAndBendsWriter writer =
                                    new AsciiWriter(this, _data,
                                        (AsciiOutputParamsObject) resultsParams);
                                writer.writeFile(path, optimPoints);
                            break;
                            case COLUMNS_ASCII_FORMAT:
                                IEndsAndBendsWriter columnWriter =
                                    new ColumnWriter(
                                        this,
                                        _data,
                                        (AsciiColumnOutputParamsObject) resultsParams);
                                columnWriter.writeFile(path, optimPoints);
                            break;
                            case GML_FORMAT:
                                IEndsAndBendsWriter gmlWriter =
                                    new GMLFileWriter(this, _data,
                                        (GmlOutputParamsObject) resultsParams);
                                gmlWriter.writeFile(path, optimPoints);
                            break;
                            case WKT_FORMAT:
                                IEndsAndBendsWriter wktWriter =
                                    new WKTFileWriter(this, _data,
                                        (WktOutputParamsObject) resultsParams);
                                wktWriter.writeFile(path, optimPoints);
                            break;
                            case SHAPE_FORMAT:
                                IEndsAndBendsWriter shapeWriter =
                                    new ShapeFileWriter(this, _data,
                                        (ShapeOutputParamsObject) resultsParams);
                                shapeWriter.writeFile(path, optimPoints);
                            break;
                            default:
                            break;
                        }
                    }

                }
                // Mettre � jour l'affichage :
                updateUI();
            }
            catch (EABException e)
            {
                e.printStackTrace();

                JOptionPane.showMessageDialog(this, e.getMessage(),
                    Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Method onListSelChanged.
     * 
     * @param lsm
     */
    private void onListSelChanged(ListSelectionModel lsm)
    {
        if (_tablePaths.getSelectedRows().length > 0)
        {
            if (null == _segmentViewer || !_segmentViewer.isVisible())
            {
                // Cr�er la dialogue :
                _segmentViewer =
                    new PathViewerGen(this, null,
                        new ArrayList<NavPathPoints>(), _data._projection,
                        _data._globalProjection);
            }
        }
        else
        {
            disposePathViewer(_segmentViewer);
        }

        updateUI();

        ReferenceTable.getInstance().updateUI(_tabReference, _tablePaths);
    }

    /**
     * Method onViewPath.
     */
    private void onViewSegment()
    {
        int[] rowIndexes = _tablePaths.getSelectedRows();

        if (null != rowIndexes && rowIndexes.length > 0)
        {
            List<NavPathPoints> pathList =
                new ArrayList<NavPathPoints>(rowIndexes.length);
            List<String> segmentNumber =
                new ArrayList<String>(rowIndexes.length);

            for (int index : rowIndexes)
            {
                if (index < _data._paths.length)
                {
                    pathList.add(_data._paths[index]);
                    segmentNumber.add(String.valueOf(_tablePaths.getValueAt(
                        index, 0)));
                }
            }

            String viewerTitle = computeViewerTitle(segmentNumber);

            if (null == _segmentViewer || !_segmentViewer.isVisible())
            {
                // Cr�er la dialogue :
                _segmentViewer =
                    new PathViewerGen(this, viewerTitle, pathList,
                        _data._projection, _data._globalProjection);
            }
            else
            {
                _segmentViewer.reinit(pathList, viewerTitle, _data._projection,
                    null);
            }
        }
    }

    /**
     * Cr�ation du titre de la fen�tre de carto � partir des segments
     * s�lectionn�s
     * 
     * @param segmentNumber
     * @return
     */
    private String computeViewerTitle(List<String> segmentNumber)
    {
        StringBuilder buffer =
            new StringBuilder(Messages.getString(Messages.MESSAGES,
                "PATH_VIEWER_TITLE_SEGMENT")); //$NON-NLS-1$

        buffer.append(" : ");

        int size = segmentNumber.size();
        int sizeMinusOne = size - 1;

        for (int i = 0; i < size; i++)
        {
            buffer.append(segmentNumber.get(i));

            if (i < sizeMinusOne)
            {
                buffer.append(", ");
            }
        }

        return buffer.toString();
    }

    /**
     * Method onViewSurvey.
     */
    private void onViewSurvey()
    {
        if (null != _data && null != _data._paths)
        {
            List<NavPathPoints> segmentsToDraw =
                new ArrayList<NavPathPoints>(_data._paths.length);

            int[] selectedRows = _tablePaths.getSelectedRows();

            Set<NavPathPoints> segmentsTohighlight;
            if (null != selectedRows && selectedRows.length > 0)
            {
                segmentsTohighlight = new HashSet<NavPathPoints>();

                for (int row : selectedRows)
                {
                    if (row < _data._paths.length)
                    {
                        segmentsTohighlight.add(_data._paths[row]);
                    }
                }
            }
            else
            {
                segmentsTohighlight = null;
            }

            for (NavPathPoints path : _data._paths)
            {
                segmentsToDraw.add(path);
            }

            if (null == _surveyViewer || !_surveyViewer.isVisible())
            {
                // Cr�er la dialogue :
                _surveyViewer =
                    new PathViewerGen(this,
                        Messages.getString(Messages.MESSAGES,
                            "PATH_VIEWER_TITLE_SURVEY"), //$NON-NLS-1$
                        segmentsToDraw, segmentsTohighlight, _data._projection,
                        _data._globalProjection);
            }
            else
            {
                _surveyViewer.reinit(segmentsToDraw, segmentsTohighlight, null,
                    _data._projection, _data._globalProjection);
            }
        }
    }

    /**
     * Method onUserComputeSegment.
     */
    private void onUserComputeSegment()
    {
        int rowSelected = _tablePaths.getSelectedRow();
        if ((rowSelected >= 0) && (rowSelected < _data._paths.length))
        {
            NavPathPoints path = _data._paths[rowSelected];
            if (path.getPointsCount() >= 3)
            {
                int model = path.getSelectedModel();
                int points = 0;
                if (model > 0)
                {
                    points = path.getModelCount(model);
                }
                else
                {
                    points = path.getPointsCount();
                }
                DialogUserCompute dlg = new DialogUserCompute(this, points);
                if (dlg.showDialog())
                {
                    // Calculer :
                    points = dlg.getPointsLimit();
                    path.computeUser(_data._distance, points);

                    // Mise � jour de la table :
                    updateContents(true);

                    // Mettre � jour l'affichage :
                    updateUI();
                }
            }
            else
            {
                JOptionPane
                    .showMessageDialog(this, Messages.getString(
                        Messages.MESSAGES, "not_enough_points"), Messages //$NON-NLS-1$
                        .getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Method onDefaultComputeSegment.
     */
    private void onDefaultComputeSegment()
    {
        int rowSelected = _tablePaths.getSelectedRow();
        if ((rowSelected >= 0) && (rowSelected < _data._paths.length))
        {
            // R�cup�rer le segment :
            NavPathPoints path = _data._paths[rowSelected];
            if (path.getPointsCount() >= 3)
            {
                // Traitement d'un profil :
                path.compute(_data._distance);

                // Mise � jour de la table :
                updateContents(true);

                // Mettre � jour l'affichage :
                updateUI();
            }
            else
            {
                JOptionPane
                    .showMessageDialog(this, Messages.getString(
                        Messages.MESSAGES, "not_enough_points"), Messages //$NON-NLS-1$
                        .getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Method onOptions.
     */
    private void onOptions()
    {
        // Cr�er les pages :
        OptionSheet props =
            new OptionSheet(this, Messages.getString(Messages.MESSAGES,
                "title_options")); //$NON-NLS-1$

        // FIXME0 CLT En cours de d�placement vers formatsParameters.
// OptionOutput optOutput =
// new OptionOutput(_userName, _userComment, _campagnNumber,
// _campagnName, _campagnBoat);
// props.addPane(Messages
//            .getString(Messages.MESSAGES, "tab_option_output"), optOutput); //$NON-NLS-1$
        OptionConstants optConsts =
            new OptionConstants(NcNviNavPath.getGapTimeInMinuts(),
                NcNviNavPath.getGapDistance(), NcNviNavPath.getGapType(),
                NavPathPoints.get_pathLimit());
        props
            .addPane(
                Messages.getString(Messages.MESSAGES, "tab_option_constants"), optConsts); //$NON-NLS-1$
        OptionProjection optProj =
            new OptionProjection(_data._projection, _data._distance);
        props
            .addPane(
                Messages.getString(Messages.MESSAGES, "tab_option_projection"), optProj); //$NON-NLS-1$
        // On regroupe les onglets projection et distance. Ils passent dans
        // projection.
// OptionDistance optDist = new OptionDistance(_data._distance);
// props.addPane(Messages.getString(Messages.MESSAGES,
//            "tab_option_data._distance"), optDist); //$NON-NLS-1$
        OptionGKProjection optGKProjection = new OptionGKProjection();
        props.addPane(
            Messages.getString(Messages.MESSAGES, "tab_option_gkproj"), //$NON-NLS-1$
            optGKProjection);

// FormatsPanel optionFormats = new FormatsPanel();
// props.addPane(Messages.getString(Messages.MESSAGES,
//            "tab_option_formats"), optionFormats); //$NON-NLS-1$

        // Afficher la bo�te et pereniser l'action si l'utilisateur
        // a choisi de sauvegarder :
        if (props.showOptions())
        {
            // Projection et distance, qui changent les donn�es en m�moire
            if (!_data._projection.getClass().equals(
                optProj.getProjection().getClass())
                    || (NcNviNavPath.getGapTimeInMinuts() != optConsts
                        .getTimeLimit())
                    || (NcNviNavPath.getGapDistance() != optConsts
                        .getDistanceLimit())
                    || (NcNviNavPath.getGapType() != optConsts.getGapType())
                    || (GaussKruegerProjection.getLimitDistance() != optGKProjection
                        .getDistLimit())
                    || (GaussKruegerProjection.getCuttingDistance() != optGKProjection
                        .getDistCut()))
            {
                int choice = JOptionPane.YES_OPTION;
                if (_data._paths != null)
                {
                    choice =
                        JOptionPane.showConfirmDialog(this, Messages.getString(
                            Messages.MESSAGES, "warning_option_changes"), //$NON-NLS-1$
                            Messages.getString(Messages.MESSAGES,
                                "warning_title"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$
                }
                if (choice == JOptionPane.YES_OPTION)
                {
                    // Vider :
                    _data._paths = null;

                    // Mise � jour :
                    updateContents(false);
                    updateUI();

                    // Changer les outils :
                    _data._projection = optProj.getProjection();
                    NcNviNavPath.setGapTime((int) optConsts.getTimeLimit());
                    NcNviNavPath.setGapDistance((int) optConsts
                        .getDistanceLimit());
                    NcNviNavPath.setGapType(optConsts.getGapType());
                    GaussKruegerProjection.setLimitDistance(optGKProjection
                        .getDistLimit());
                    GaussKruegerProjection.setCuttingDistance(optGKProjection
                        .getDistCut());
                }
            }

            // Distance :
            _data._distance = optProj.getDistance();

            // Autres limites :
            NavPathPoints.set_pathLimit(optConsts.getPointsLimit());

            // FIXME0 CLT En cours de d�placement vers formatsParameters.
            // Informations sur l'utilisateur :
// _userName = optOutput.getUserName();
// _userComment = optOutput.getUserComment();
// _campagnNumber = optOutput.getCampagnNumber();
// _campagnName = optOutput.getCampagnName();
// _campagnBoat = optOutput.getCampagnBoat();
        }

        // Mettre � jour l'affichage :
        updateUI();
    }

    /**
     * Method onAbout.
     */
    private void onAbout()
    {
        DialogAbout dlg = new DialogAbout(this);
        dlg.showDialog();

    }

    /**
     * Method onHelp.
     */
    private void onHelp()
    {
        try
        {
            java.awt.Desktop
                .getDesktop()
                .open(
                    new File(
                        "help/EndsAndBends_v2.2.0_User_Manual.pdf"));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            myLogger.error("error when trying to open Ends&Bends User Manual");
        }
// if (Locale.getDefault().equals(Locale.FRENCH))
// {
// HtmlBrowser.showPage("file:./help_FR/index.html");
// }
// else
// {
// HtmlBrowser.showPage("file:./help/index.html");
// }
    }

    /**
     * Method onQuit.
     */
    private void onQuit()
    {
        try
        {
            onClose();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        dispose();
        System.exit(0);
    }

    /**
     * Method onClose.
     */
    protected void onClose()
    {
        // Sauvegarder les d�faut :
        saveOptions();
        DefaultsHandler.saveDefaults();
    }

    /**
     * Chargement des options sauvegard�es :
     */
    private void initOptions()
    {
        // Projection :
        String projection = DefaultsHandler.getDefault("projection", "");
        if ((projection == null) || (projection.length() == 0))
        {
            _data._projection = GeoProjection.getDefault(false);
            _data._globalProjection = GeoProjection.getDefault(true);
        }
        else if (projection.equals(Messages.getString(Messages.MESSAGES,
            "proj_null_name"))) //$NON-NLS-1$
        {
            _data._projection = new NullProjection();
            _data._globalProjection = new NullProjection();
        }
        else if (projection.equals(Messages.getString(Messages.MESSAGES,
            "proj_gk_name"))) //$NON-NLS-1$
        {
            _data._projection = new GaussKruegerProjection(false);
            _data._globalProjection = new GaussKruegerProjection(true);
        }

        // Distance :
        String distance = DefaultsHandler.getDefault("distance", "");
        if ((distance == null) || (distance.length() == 0))
        {
            _data._distance = DistanceToLine.getDefault();
        }
        else if (distance.equals(Messages.getString(Messages.MESSAGES,
            "dist_vert_name"))) //$NON-NLS-1$
        {
            _data._distance = new VerticalDistanceToLine();
        }
        else if (distance.equals(Messages.getString(Messages.MESSAGES,
            "dist_ortho_name"))) //$NON-NLS-1$
        {
            _data._distance = new OrthoDistanceToLine();
        }

        // Info globale :
// _userName = DefaultsHandler.getDefault("userName", "");
// _userComment = DefaultsHandler.getDefault("userComment", "");
// _campagnNumber = DefaultsHandler.getDefault("campagnNumber", "");
// _campagnName = DefaultsHandler.getDefault("campagnName", "");
// _campagnBoat = DefaultsHandler.getDefault("campagnBoat", "");
    }

    /**
     * Sauvegarde des options choisies...
     */
    private void saveOptions()
    {
        if (_data._projection != null)
        {
            DefaultsHandler.setDefault("projection",
                _data._projection.getName());
        }
        if (_data._distance != null)
        {
            DefaultsHandler.setDefault("distance", _data._distance.getName());
        }
// DefaultsHandler.setDefault("userName", _userName);
// DefaultsHandler.setDefault("userComment", _userComment);
// DefaultsHandler.setDefault("campagnNumber", _campagnNumber);
// DefaultsHandler.setDefault("campagnName", _campagnName);
// DefaultsHandler.setDefault("campagnBoat", _campagnBoat);
    }

    /**
     * Method showMain : utilis�e quand la fen�tre est principale, elle quitte
     * l'application a sa fermeture...
     */
    public void showMain()
    {
        // Ajouter un listener pour quitter l'appli quand la fen�tre se ferme :
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                onQuit();
            }

            @Override
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                onInitWindow();
            }
        });

        // Centrer la fen�tre :
        Dimension dimScreen = getToolkit().getScreenSize();
        Dimension dimWindow = getSize();
        Rectangle rect = new Rectangle();
        rect.x = (dimScreen.width - dimWindow.width) / 2;
        rect.y = (dimScreen.height - dimWindow.height) / 2;
        rect.width = dimWindow.width;
        rect.height = dimWindow.height;
        setBounds(rect);

        updateUI();

        // Montrer :
        // [TNU] remplacement d'une methode Deprecated
// show();
        setVisible(true);
        // [/TNU]
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _btnComputeModels;
    private javax.swing.JButton _btnComputeModelsOptimDistance;
    private javax.swing.JButton _btnComputeModelsOptimError;
    private javax.swing.JButton _btnDefaultComputeSegment;
    private javax.swing.JButton _btnOpenSourceFile;
    private javax.swing.JButton _btnOptions;
    private javax.swing.JButton _btnUserComputeSegment;
    private javax.swing.JButton _btnViewSurvey;
    private javax.swing.JButton _btnWriteResultFile;
    private javax.swing.JLabel _labelOptions;
    private javax.swing.JLabel _lblComputeModels;
    private javax.swing.JLabel _lblComputeModelsOptimDistance;
    private javax.swing.JLabel _lblComputeModelsOptimError;
    private javax.swing.JLabel _lblDefaultComputeSegment;
    private javax.swing.JLabel _lblOpenSourceFile;
    private javax.swing.JLabel _lblSelectTableLine;
    private javax.swing.JLabel _lblUserComputeSegment;
    private javax.swing.JLabel _lblViewSurvey;
    private javax.swing.JLabel _lblWriteResultFile;
    private javax.swing.JMenuBar _menuMain;
    private javax.swing.JPanel _segmentProcessPanel;
    private javax.swing.JPanel _surveyProcessPanel;
    private javax.swing.JTable _tabReference;
    private javax.swing.JTable _tablePaths;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}
