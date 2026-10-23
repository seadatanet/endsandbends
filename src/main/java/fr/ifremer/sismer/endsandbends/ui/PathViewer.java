package fr.ifremer.sismer.endsandbends.ui;

import javax.swing.JDialog;

/**
 * @author ATLANTIDE - ABT ViewerExport detin� � repr�senter, dans
 *         l'application, des pages HTML stock�e sur disque ou dans un cha�ne de
 *         caract�res. L'impl�mentation du viewer est capable d'afficher des
 *         images incluses dans les pages mais ne permet pas de naviguer par les
 *         liens hypertextes.
 */
public class PathViewer extends JDialog
{

// // Trac� � visualiser :
// private NavPathPoints _path = null;
// private GeoProjection _proj = null;
//
// // Contr�les :
// private final JPanel _ctrlPane = new DrawPane();
// private final JScrollPane _scrollPane = new JScrollPane(_ctrlPane);
// private final JButton _ctrlBtnQuit = new JButton();
// private final JButton _ctrlBtnZoomIn = new JButton();
// private final JButton _ctrlBtnZoomOut = new JButton();
// private final JButton _ctrlBtnZoomCursor = new JButton();
// private final JButton _ctrlBtnZoomBest = new JButton();
// private final JLabel _ctrlGridScale = new JLabel();
//
// // Curseur de s�lection d'un zoom :
// private Rubberband _rubber = null;
//
// /**
// * Constructeur
// */
// protected PathViewer(Frame owner, NavPathPoints path, GeoProjection proj)
// {
// // Classe de base (passer le owner) :
////        super(owner, Messages.getString(Messages.MESSAGES, "title_graph"), true); //$NON-NLS-1$
//        super(); //$NON-NLS-1$
//
// // Ajouter les �v�nements de cr�ation et de destruction :
// addWindowListener(new java.awt.event.WindowAdapter() {
// @Override
// public void windowClosing(java.awt.event.WindowEvent evt)
// {
// onClose();
// }
//
// @Override
// public void windowOpened(java.awt.event.WindowEvent evt)
// {
// onInitWindow();
// }
// });
//
// // Stocker le chemin :
// _path = path;
// _proj = proj;
//
// // Calcul de la grille :
// computeGrid();
//
// // Cr�ation des contr�les :
// initComponents();
//
// // Centrer la fen�tre :
// if (owner != null)
// {
// // R�cup�rer la taille de l'�cran :
// Toolkit tk = Toolkit.getDefaultToolkit();
// Dimension screenDim = tk.getScreenSize();
//
// // Positionner sur tout l'�cran - 1/10ieme :
// Dimension border =
// new Dimension(screenDim.width / 10, screenDim.height / 10);
// Dimension dim =
// new Dimension(screenDim.width - 2 * border.width,
// screenDim.height - 2 * border.height);
// setBounds(border.width, border.height, dim.width, dim.height);
// }
// }
//
// /**
// * Method initComponents : cr�ation des contr�les de la fen�tre principale
// */
// protected void initComponents()
// {
// // D'abord initialiser le Layout :
// getContentPane().setLayout(new GridBagLayout());
// java.awt.GridBagConstraints gridBagConstraints;
//
// // Cr�er le contr�le HTML dans un scrollpane :
// _scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
//
// // Ajouter le pane dans la fen�tre :
// gridBagConstraints = new GridBagConstraints();
// gridBagConstraints.gridx = 0;
// gridBagConstraints.gridy = 0;
// gridBagConstraints.gridheight = 1;
// gridBagConstraints.gridwidth = 6;
// gridBagConstraints.fill = GridBagConstraints.BOTH;
// gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
// gridBagConstraints.weightx = 0.8;
// gridBagConstraints.weighty = 0.8;
// getContentPane().add(_scrollPane, gridBagConstraints);
//
// // Ajouter les boutons :
// // Zoom in :
// _ctrlBtnZoomIn.setText(Messages.getString(Messages.MESSAGES,
//            "btn_zoomin")); //$NON-NLS-1$
// gridBagConstraints = new GridBagConstraints();
// gridBagConstraints.gridx = 0;
// gridBagConstraints.gridy = 1;
// gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
// gridBagConstraints.anchor = GridBagConstraints.WEST;
// _ctrlBtnZoomIn.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e)
// {
// onZoomIn();
// }
// });
// getContentPane().add(_ctrlBtnZoomIn, gridBagConstraints);
// // Zoom out :
// _ctrlBtnZoomOut.setText(Messages.getString(Messages.MESSAGES,
//            "btn_zoomout")); //$NON-NLS-1$
// gridBagConstraints = new GridBagConstraints();
// gridBagConstraints.gridx = 1;
// gridBagConstraints.gridy = 1;
// gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
// gridBagConstraints.anchor = GridBagConstraints.WEST;
// _ctrlBtnZoomOut.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e)
// {
// onZoomOut();
// }
// });
// getContentPane().add(_ctrlBtnZoomOut, gridBagConstraints);
// // Zoom par s�lection de rectangle :
// _ctrlBtnZoomCursor.setText(Messages.getString(Messages.MESSAGES,
//            "btn_zoomcursor")); //$NON-NLS-1$
// gridBagConstraints = new GridBagConstraints();
// gridBagConstraints.gridx = 2;
// gridBagConstraints.gridy = 1;
// gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
// gridBagConstraints.anchor = GridBagConstraints.WEST;
// _ctrlBtnZoomCursor.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e)
// {
// onZoomCursor();
// }
// });
// getContentPane().add(_ctrlBtnZoomCursor, gridBagConstraints);
// // Zoom par d�faut :
// _ctrlBtnZoomBest.setText(Messages.getString(Messages.MESSAGES,
//            "btn_zoombest")); //$NON-NLS-1$
// gridBagConstraints = new GridBagConstraints();
// gridBagConstraints.gridx = 3;
// gridBagConstraints.gridy = 1;
// gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
// gridBagConstraints.anchor = GridBagConstraints.WEST;
// _ctrlBtnZoomBest.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e)
// {
// onZoomBest();
// }
// });
// getContentPane().add(_ctrlBtnZoomBest, gridBagConstraints);
// // Quitter
//        _ctrlBtnQuit.setText(Messages.getString(Messages.MESSAGES, "btn_quit")); //$NON-NLS-1$
// gridBagConstraints = new GridBagConstraints();
// gridBagConstraints.gridx = 5;
// gridBagConstraints.gridy = 1;
// gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
// gridBagConstraints.anchor = GridBagConstraints.EAST;
// _ctrlBtnQuit.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e)
// {
// onQuit();
// }
// });
// getContentPane().add(_ctrlBtnQuit, gridBagConstraints);
//
// // Texte de l'�chelle :
// _ctrlGridScale.setText("Echelle : " + _gridScale + "�");
// gridBagConstraints = new GridBagConstraints();
// gridBagConstraints.gridx = 4;
// gridBagConstraints.gridy = 1;
// gridBagConstraints.fill = GridBagConstraints.BOTH;
// gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
// gridBagConstraints.weightx = 1.0;
// gridBagConstraints.weighty = 0.0;
// gridBagConstraints.anchor = GridBagConstraints.CENTER;
// _ctrlBtnQuit.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e)
// {
// onQuit();
// }
// });
// _ctrlGridScale.setFont(_ctrlGridScale.getFont().deriveFont(0));
// getContentPane().add(_ctrlGridScale, gridBagConstraints);
//
// // Compacter :
// pack();
// updateUI();
// }
//
// /**
// * Mise � jour de l'interface, grisage des boutons :
// */
// protected void updateUI()
// {
// _ctrlBtnZoomIn.setEnabled(_rubber == null);
// _ctrlBtnZoomOut.setEnabled(_rubber == null);
// _ctrlBtnZoomCursor.setSelected(_rubber != null);
// _ctrlBtnZoomBest.setEnabled(_rubber == null);
// }
//
// // // D�finition de l'espace Logic :
//
// // D�finition des bornes logiques :
// private double _logicMinX = 0.0;
// private double _logicMaxX = 0.0;
// private double _logicMinY = 0.0;
// private double _logicMaxY = 0.0;
//
// // D�finition du zoom entre Logic et Display (isomorphe)
// private double _scale = 1.0;
// private double _scrollX = 0.0;
// private double _scrollY = 0.0;
//
// // Transformation affine pour passer aux coordonn�es Display :
// private AffineTransform _mapLogicToDisplay = new AffineTransform();
//
// /**
// * Method setLogicBounds, fixe les limites de l'espace logique. Cette
// * m�thode ne recalcule pas la transformation entre l'espace Logic et
// * l'espace Display. Il faut pour cel� appeler la m�thode
// * updateLogicMapping().
// *
// * @param minX
// * @param maxX
// * @param minY
// * @param maxY
// * @see ifremer.sismer.endsandbends.ui.PathViewer#updateLogicMapping()
// */
// protected void setLogicBounds(double minX, double maxX, double minY,
// double maxY)
// {
// _logicMinX = minX;
// _logicMaxX = maxX;
// _logicMinY = minY;
// _logicMaxY = maxY;
// }
//
// /**
// * Calcul automatique de l'espace logique � partir du trac�.
// */
// protected void computeLogicBounds()
// {
// // Il faut parcourir tout le trac� pour trouver le min et la max :
// double xMin = Double.MAX_VALUE;
// double xMax = -Double.MAX_VALUE;
// double yMin = Double.MAX_VALUE;
// double yMax = -Double.MAX_VALUE;
// for (int index = 0; index < _path.getPointsCount(); index++)
// {
// Point2D pt = _path.getPoint(index);
// if (pt.getX() < xMin)
// xMin = pt.getX();
// if (pt.getX() > xMax)
// xMax = pt.getX();
// if (pt.getY() < yMin)
// yMin = pt.getY();
// if (pt.getY() > yMax)
// yMax = pt.getY();
// }
//
// // Faire la mise � jour :
// setLogicBounds(xMin, xMax, yMin, yMax);
// }
//
// /**
// * Changement d'�chelle, en gardant le point central centr�...
// *
// * @param scale
// */
// protected void setScale(double scale)
// {
// // R�cup�rer le point central en coordonn�es logiques :
// JViewport viewport = _scrollPane.getViewport();
// Point ptUpLeft = viewport.getViewPosition();
// Dimension dimView = viewport.getExtentSize();
// Point2D ptCenter =
// new Point2D.Double(ptUpLeft.getX() + dimView.getWidth() / 2.0,
// ptUpLeft.getY() + dimView.getHeight() / 2.0);
// pointD2L(ptCenter);
//
// // Changer l'�chelle :
// _scale = scale;
// updateLogicMapping();
//
// // Mises � jour :
// Dimension dim = getBestScreenSize();
// setScreenViewport(dim);
// _ctrlPane.setSize(dim);
// _ctrlPane.setMinimumSize(dim);
// _ctrlPane.setPreferredSize(dim);
// _ctrlPane.setMaximumSize(dim);
//
// // Repositionner le point central :
// pointL2D(ptCenter);
// ptUpLeft.setLocation(ptCenter.getX() - dimView.getWidth() / 2.0,
// ptCenter.getY() - dimView.getHeight() / 2.0);
// viewport.setViewPosition(ptUpLeft);
// }
//
// /**
// * Calcul d'une �chelle permettant de voir tout le dessin...
// */
// protected void computeBestScale()
// {
// double scaleX = _displayWidth / (_logicMaxX - _logicMinX);
// double scaleY = _displayHeight / (_logicMaxY - _logicMinY);
// _scale = StrictMath.min(scaleX, scaleY);
// if (_scale == 0.0)
// _scale = 1.0;
// _scrollX = 0;
// _scrollY = 0;
// }
//
// /**
// * Calcul un zoom � partir d'un rectangle en coordonn�es display
// *
// * @param x
// * point gauche du rectangle en Display
// * @param y
// * point haut du rectangle en Display
// * @param cx
// * largeur du rectangle en Display
// * @param cy
// * hauteur du rectangle en Display
// */
// protected void setZoom(double x, double y, double cx, double cy)
// {
// // Correction des coordonn�es :
// if (cx < 0)
// {
// x += cx;
// cx = StrictMath.abs(cx);
// }
// if (cy < 0)
// {
// y += cy;
// cy = StrictMath.abs(cy);
// }
//
// // R�cup�rer le point central en coordonn�es logiques :
// JViewport viewport = _scrollPane.getViewport();
// Point ptUpLeft = viewport.getViewPosition();
//
// // R�cup�rer la taille visible :
// Rectangle rectScreen = _scrollPane.getBounds();
// Dimension dimScreen = rectScreen.getSize();
//
// // Passer la dimension en coordonn�es logiques :
// double lx = xD2L(x);
// if (cy < 0)
// {
// y -= cy;
// cy = -cy;
// }
// double ly = yD2L(y);
// double clx = StrictMath.abs(cxD2L(cx));
// double cly = StrictMath.abs(cyD2L(cy));
//
// // Recalculer une �chelle :
// double scaleX = dimScreen.width / clx;
// double scaleY = dimScreen.height / cly;
// _scale = StrictMath.min(scaleX, scaleY);
// _scrollX = 0;
// _scrollY = 0;
// updateLogicMapping();
//
// // Mises � jour :
// Dimension dim = getBestScreenSize();
// setScreenViewport(dim);
// _ctrlPane.setSize(dim);
// _ctrlPane.setMinimumSize(dim);
// _ctrlPane.setPreferredSize(dim);
// _ctrlPane.setMaximumSize(dim);
//
// // Repositionner :
// Point2D ptUpLogic = pointL2DCreate(lx, ly);
// ptUpLeft.setLocation(ptUpLogic.getX(), ptUpLogic.getY());
// viewport.setViewPosition(ptUpLeft);
// }
//
// /**
// * Method updateLogicMapping, calcule la transformation affine qui permet de
// * passer de l'espace Logic � l'espace Display et inversement.
// */
// protected void updateLogicMapping()
// {
// double scaleX = _scale;
// double posX = -(_scrollX + _logicMinX) * _scale;
// double scaleY = -_scale;
// double posY = (_logicMaxY - _scrollY) * _scale;
// _mapLogicToDisplay =
// new AffineTransform(scaleX, 0, 0, scaleY, posX, posY);
// }
//
// // M�thodes de transformation dans le sens Logic -> Display :
// // A priori les transformations inverses ne sont pas n�cessaires
// // tant qu'on ne g�re pas d'interactions avec l'utilisateur.
//
// /**
// * Method xL2D, transformation d'une coordonn�es de l'axe X de l'espace
// * Logic � l'espace Display.
// *
// * @param x
// * abscisse dans l'espace Logic
// * @return double, abscisse dans l'espace Display
// */
// protected double xL2D(double x)
// {
// double[] src = new double[2];
// src[0] = x;
// src[1] = 0;
// _mapLogicToDisplay.transform(src, 0, src, 0, 1);
// return src[0];
// }
//
// /**
// * Method xD2L, transformation d'une coordonn�es de l'axe X de l'espace
// * Display � l'espace Logic.
// *
// * @param x
// * abscisse dans l'espace Display
// * @return double, abscisse dans l'espace Logic
// */
// protected double xD2L(double x)
// {
// double[] src = new double[2];
// src[0] = x;
// src[1] = 0;
// try
// {
// _mapLogicToDisplay.inverseTransform(src, 0, src, 0, 1);
// }
// catch (NoninvertibleTransformException nte)
// {
// nte.printStackTrace();
// }
// return src[0];
// }
//
// /**
// * Method yL2D, transformation d'une coordonn�es de l'axe Y de l'espace
// * Logic � l'espace Display.
// *
// * @param y
// * ordonn�e dans l'espace Logic
// * @return double, ordonn�e dans l'espace Display
// */
// protected double yL2D(double y)
// {
// double[] src = new double[2];
// src[0] = 0;
// src[1] = y;
// _mapLogicToDisplay.transform(src, 0, src, 0, 1);
// return src[1];
// }
//
// /**
// * Method yD2L, transformation d'une coordonn�es de l'axe Y de l'espace
// * Display � l'espace Logic.
// *
// * @param y
// * ordonn�e dans l'espace Display
// * @return double, ordonn�e dans l'espace Logic
// */
// protected double yD2L(double y)
// {
// double[] src = new double[2];
// src[0] = 0;
// src[1] = y;
// try
// {
// _mapLogicToDisplay.inverseTransform(src, 0, src, 0, 1);
// }
// catch (NoninvertibleTransformException nte)
// {
// nte.printStackTrace();
// }
// return src[1];
// }
//
// /**
// * Method cxL2D, transformation de la composante X d'une distance de
// * l'espace Logic � l'espace Display.
// *
// * @param cx
// * composante x d'un vecteur dans l'espace Logic
// * @return double, composante x dans l'espace Display
// */
// protected double cxL2D(double cx)
// {
// double[] src = new double[2];
// src[0] = cx;
// src[1] = 0;
// _mapLogicToDisplay.deltaTransform(src, 0, src, 0, 1);
// return src[0];
// }
//
// /**
// * Method cxD2L, transformation de la composante X d'une distance de
// * l'espace Display � l'espace Logic.
// *
// * @param cx
// * composante x d'un vecteur dans l'espace Display
// * @return double, composante x dans l'espace Logic
// */
// protected double cxD2L(double cx)
// {
// return cx / _scale;
// }
//
// /**
// * Method cyL2D, transformation de la composante Y d'une distance de
// * l'espace Logic � l'espace Display.
// *
// * @param cy
// * composante y d'un vecteur dans l'espace Logic
// * @return double, composante y dans l'espace Display
// */
// protected double cyL2D(double cy)
// {
// double[] src = new double[2];
// src[0] = 0;
// src[1] = cy;
// _mapLogicToDisplay.deltaTransform(src, 0, src, 0, 1);
// return src[1];
// }
//
// /**
// * Method cyD2L, transformation de la composante Y d'une distance de
// * l'espace Display � l'espace Logic.
// *
// * @param cy
// * composante y d'un vecteur dans l'espace Display
// * @return double, composante y dans l'espace Logic
// */
// protected double cyD2L(double cy)
// {
// return cy / _scale;
// }
//
// /**
// * Method pointL2D, conversion d'un point graphique de l'espace Logic �
// * l'espace Display
// *
// * @param point
// * point � transformer, modifi� par la m�thode
// */
// protected void pointL2D(Point2D point)
// {
// double[] src = new double[2];
// src[0] = point.getX();
// src[1] = point.getY();
// _mapLogicToDisplay.transform(src, 0, src, 0, 1);
// point.setLocation(src[0], src[1]);
// }
//
// /**
// * Method pointL2D, conversion d'un point graphique de l'espace Display �
// * l'espace Logic
// *
// * @param point
// * point � transformer, modifi� par la m�thode
// */
// protected void pointD2L(Point2D point)
// {
// double[] src = new double[2];
// src[0] = point.getX();
// src[1] = point.getY();
// try
// {
// _mapLogicToDisplay.inverseTransform(src, 0, src, 0, 1);
// }
// catch (NoninvertibleTransformException nte)
// {
// nte.printStackTrace();
// }
// point.setLocation(src[0], src[1]);
// }
//
// /**
// * Method pointL2DAssign, affectation d'un point graphique de l'espace
// * Display par des coordonn�es exprim�es dans l'espace Logic.
// *
// * @param point
// * point � transformer, modifi� par la m�thode, mais son contenu
// * initial n'est pas utilis�
// * @param x
// * abscisse du point dans l'espace Logic
// * @param y
// * ordonn�e du point dans l'espace Logic
// */
// protected void pointL2DAssign(Point2D point, double x, double y)
// {
// double[] src = new double[2];
// src[0] = x;
// src[1] = y;
// _mapLogicToDisplay.transform(src, 0, src, 0, 1);
// point.setLocation(src[0], src[1]);
// }
//
// /**
// * Method pointL2DCreate, cr�ation d'un point graphique de l'espace Display,
// * initialis� par des coordonn�es exprim�es dans l'espace Logic.
// *
// * @param x
// * abscisse du point dans l'espace Logic
// * @param y
// * ordonn�e du point dans l'espace Logic
// * @return Point2D, point renvoy�
// */
// protected Point2D pointL2DCreate(double x, double y)
// {
// double[] src = new double[2];
// src[0] = x;
// src[1] = y;
// _mapLogicToDisplay.transform(src, 0, src, 0, 1);
// return new Point2D.Double(src[0], src[1]);
// }
//
// /**
// * Method lineL2D, conversion d'un segment graphique de l'espace Logic �
// * l'espace Display.
// *
// * @param line
// * segment initialement exprim� dans l'espace Logic, converti par
// * la m�thode en coordonn�es Display
// */
// protected void lineL2D(Line2D line)
// {
// double[] src = new double[4];
// src[0] = line.getX1();
// src[1] = line.getY1();
// src[2] = line.getX2();
// src[3] = line.getY2();
// _mapLogicToDisplay.transform(src, 0, src, 0, 2);
// line.setLine(src[0], src[1], src[2], src[3]);
// }
//
// /**
// * Method lineL2DAssign, affectation d'une ligne de l'espace Display �
// * partir de deux points de l'espace Logic.
// *
// * @param line
// * ligne graphique renseign�e avec des coordonn�es de l'espace
// * Display.
// * @param x1
// * abscisse du premier point dans l'espace Logic
// * @param y1
// * ordonn�e du premier point dans l'espace Logic
// * @param x2
// * abscisse du deuxi�me point dans l'espace Logic
// * @param y2
// * ordonn�e du deuxi�me point dans l'espace Logic
// */
// protected void lineL2DAssign(Line2D line, double x1, double y1, double x2,
// double y2)
// {
// double[] src = new double[4];
// src[0] = x1;
// src[1] = y1;
// src[2] = x2;
// src[3] = y2;
// _mapLogicToDisplay.transform(src, 0, src, 0, 2);
// line.setLine(src[0], src[1], src[2], src[3]);
// }
//
// /**
// * Method lineL2DAssign, cr�ation d'une ligne de l'espace Display � partir
// * de deux points de l'espace Logic.
// *
// * @param x1
// * abscisse du premier point dans l'espace Logic
// * @param y1
// * ordonn�e du premier point dans l'espace Logic
// * @param x2
// * abscisse du deuxi�me point dans l'espace Logic
// * @param y2
// * ordonn�e du deuxi�me point dans l'espace Logic
// * @return Line2D, ligne renvoy�e
// */
// protected Line2D lineL2DCreate(double x1, double y1, double x2, double y2)
// {
// double[] src = new double[4];
// src[0] = x1;
// src[1] = y1;
// src[2] = x2;
// src[3] = y2;
// _mapLogicToDisplay.transform(src, 0, src, 0, 2);
// return new Line2D.Double(src[0], src[1], src[2], src[3]);
// }
//
// // // D�finition de l'espace Display :
// // Pour EndsAndBends, l'espace display est �quivalent � l'espace screen
// // toute la transformation se fait entre Logic et Display...
//
// // D�finition de la page :
// private double _displayWidth = 0.0;
// private double _displayHeight = 0.0;
//
// /**
// * Method setDisplaySize, fixe la taille de l'espace Display, cette taille
// * peut �tre vue comme la taille de la page sur laquelle on dessine.
// *
// * @param width
// * largeur de l'espace
// * @param height
// * hauteur de l'espace
// */
// protected void setDisplaySize(double width, double height)
// {
// _displayWidth = width;
// _displayHeight = height;
// }
//
// /**
// * Method setScreenViewport, cette m�thode permet au syst�me de calculer une
// * �chelle automatique si le mode est activ�.
// *
// * @param viewport
// */
// protected void setScreenViewport(Rectangle viewport)
// {
// setDisplaySize(viewport.width, viewport.height);
// }
//
// protected void setScreenViewport(Dimension dim)
// {
// setDisplaySize(dim.width, dim.height);
// }
//
// /**
// * Renvoie la taille r�elle de l'image, compte tenu du zoom (mais pas du
// * scrolling puisque c'est une taille...).
// *
// * @return Dimension
// */
// protected Dimension getBestScreenSize()
// {
// double width = StrictMath.abs(cxL2D(_logicMaxX - _logicMinX));
// double height = StrictMath.abs(cyL2D(_logicMaxY - _logicMinY));
// return new Dimension((int) StrictMath.round(width), (int) StrictMath
// .round(height));
// }
//
// /**
// * Method getDisplayMapping, calcul de la transformation pour passer de
// * l'espace Display � l'espace Screen. Dans cette application c'est
// *
// * @param viewport
// * rectangle disponible dans l'affichage
// * @return AffineTransform
// */
// protected AffineTransform getDisplayMapping(Rectangle viewport)
// {
// AffineTransform mapDisplayToScreen =
// new AffineTransform(1.0, 0, 0, 1.0, 0, 0);
// return mapDisplayToScreen;
// }
//
// // // Outils pour le dessin :
//
// // Trac� du fond :
// protected void drawBackground(Graphics2D graphics, Color color)
// {
// Color oldColor = graphics.getColor();
// Rectangle2D bounds =
// new Rectangle2D.Double(0, 0, _displayWidth, _displayHeight);
// graphics.setColor(color);
// graphics.fill(bounds);
// graphics.setColor(oldColor);
// }
//
// // // Panneau de dessin :
//
// /**
// * Method draw, dessin du spectre
// *
// * @param graphics
// */
// protected void draw(Graphics2D graphics)
// {
// // Fond blanc :
// // [TNU] variables non lues
// // Color oldBkColor = graphics.getBackground();
// // [/TNU]
// drawBackground(graphics, Color.white);
// graphics.setBackground(Color.white);
//
// // Faire un clipping :
// graphics.clip(new Rectangle2D.Double(0, 0, _displayWidth,
// _displayHeight));
//
// // Tracer les grilles :
// if (_gridLines != null)
// {
// // Dessiner en bleu :
// graphics.setColor(Color.blue);
// graphics.setStroke(new BasicStroke(1));
// Line2D line = new Line2D.Double();
// Point2D prevPoint = new Point2D.Double();
// Point2D point = new Point2D.Double();
// GridLine grid;
//
// // Parcours des lignes :
// for (int indLine = 0; indLine < _gridLines.length; indLine++)
// {
// grid = _gridLines[indLine];
// if ((grid == null) || (grid.x == null) || (grid.y == null)
// || (grid.x.length != grid.y.length))
// continue;
// prevPoint.setLocation(grid.x[0], grid.y[0]);
// for (int indPoint = 1; indPoint < grid.x.length; indPoint++)
// {
// point.setLocation(grid.x[indPoint], grid.y[indPoint]);
// lineL2DAssign(line, prevPoint.getX(), prevPoint.getY(),
// point.getX(), point.getY());
// graphics.draw(line);
// prevPoint.setLocation(point);
// }
// }
// }
//
// // V�rifier qu'il y a qqchose � tracer :
// if (_path == null)
// return;
//
// // R�cup�rer le mod�le s�lectionn� :
// int model = _path.getSelectedModel();
//
// // Parcours des points du mod�le :
// Point2D prevPoint = null;
// Point2D prevModelPoint = null;
// for (int i = 0; i < _path.getPointsCount(); i++)
// {
// // R�cup�rer l'info :
// Point2D point = _path.getPoint(i);
// int pointModel = _path.getModel(i);
//
// // Tracer le chemin de base :
// graphics.setColor(Color.black);
// graphics.setStroke(new BasicStroke(1));
// Line2D line = new Line2D.Double();
// if (prevPoint != null)
// {
// lineL2DAssign(line, prevPoint.getX(), prevPoint.getY(), point
// .getX(), point.getY());
// graphics.draw(line);
// }
// prevPoint = point;
//
// // Tracer le chemin optimis� :
// if ((pointModel > 0) && (pointModel <= model))
// {
// graphics.setColor(Color.red);
// if (prevModelPoint != null)
// {
// lineL2DAssign(line, prevModelPoint.getX(), prevModelPoint
// .getY(), point.getX(), point.getY());
// graphics.draw(line);
// }
// Point2D ptCross = pointL2DCreate(point.getX(), point.getY());
// line.setLine(ptCross.getX() - 3, ptCross.getY(),
// ptCross.getX() + 3, ptCross.getY());
// graphics.draw(line);
// line.setLine(ptCross.getX(), ptCross.getY() - 3,
// ptCross.getX(), ptCross.getY() + 3);
// graphics.draw(line);
// prevModelPoint = point;
// }
// }
// }
//
// /**
// * @author ATLANTIDE - ABT Composant de dessin pour ce graphisme
// */
// protected class DrawPane extends JPanel
// {
// public DrawPane()
// {
// setScreenViewport(getBounds());
// addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
// @Override
// public void ancestorResized(HierarchyEvent e)
// {
// onAncestorResized();
// }
// });
// addWindowListener(new java.awt.event.WindowAdapter() {
// @Override
// public void windowClosing(java.awt.event.WindowEvent evt)
// {
// onClose();
// }
//
// @Override
// public void windowOpened(java.awt.event.WindowEvent evt)
// {
// onInitWindow();
// }
// });
// }
//
// protected void onAncestorResized()
// {
// updateLogicMapping();
// Dimension dim = getBestScreenSize();
// setScreenViewport(dim);
// setSize(dim);
// setMinimumSize(dim);
// setPreferredSize(dim);
// setMaximumSize(dim);
// }
//
// protected void onInitWindow()
// {
// setScreenViewport(getBounds());
// computeLogicBounds();
// computeBestScale();
// updateLogicMapping();
// Dimension dim = getBestScreenSize();
// setScreenViewport(dim);
// setSize(dim);
// setMinimumSize(dim);
// setPreferredSize(dim);
// setMaximumSize(dim);
// onZoomBest();
// }
//
// protected void onClose()
// {
// if (_rubber != null)
// {
// _rubber.release();
// _rubber = null;
// }
// }
//
// @Override
// protected void paintComponent(Graphics g)
// {
// // Initialisation :
// super.paintComponent(g);
// Graphics2D graphics = (Graphics2D) g;
//
// // Initialiser une transformation affine pour faire le mapping :
// AffineTransform mapping = getDisplayMapping(getBounds());
// AffineTransform oldTransform = graphics.getTransform();
// graphics.transform(mapping);
//
// // Dessin :
// draw(graphics);
//
// // Remettre l'ancienne transformatin :
// graphics.setTransform(oldTransform);
// }
// }
//
// // // Grille :
//
// /**
// * Classe utilitaire pour stocker les lignes de grille
// */
// private static class GridLine
// {
// public String label = null;
// public boolean vertical = true;
// public double[] x = null;
// public double[] y = null;
//
// public GridLine()
// {
// }
// }
//
// // Donn�es de la grille :
// protected GridLine[] _gridLines = null;
// protected double _gridScale = 0.0;
//
// /**
// * Calcul d'une grille latitude / longitude en fonction du trac�.
// */
// protected void computeGrid()
// {
// // V�rification :
// if (_path == null)
// return;
//
// // Initialiser la projectin :
// _proj.initialize(_path.getLatitudes(), _path.getLongitudes());
//
// // R�cup�rer le min et max :
// NavPathPoints.Statistics statsLong = _path.getLongitudeStatistics();
// NavPathPoints.Statistics statsLat = _path.getLatitudeStatistics();
//
// // Calculer le pas de grille :
// double deltaLatitude =
// StrictMath.abs(statsLat.maxValue - statsLat.minValue);
// double deltaLongitude = statsLong.maxValue - statsLong.minValue;
// if (deltaLongitude <= 0)
// deltaLongitude = 360 - deltaLongitude;
// double nodeLatitude = 0.0;
// double nodeLongitude = 0.0;
// for (double delta = 10.0; (nodeLatitude == 0.0); delta /= 10.0)
// {
// if (deltaLatitude >= delta)
// nodeLatitude = delta / 10.0;
// else if (deltaLatitude >= delta / 3.0)
// nodeLatitude = delta / 20.0;
// }
// for (double delta = 10.0; (nodeLongitude == 0.0); delta /= 10.0)
// {
// if (deltaLongitude >= delta)
// nodeLongitude = delta / 10.0;
// else if (deltaLongitude >= delta / 3.0)
// nodeLongitude = delta / 20.0;
// }
// if (nodeLatitude > nodeLongitude)
// nodeLongitude = nodeLatitude;
// if (nodeLongitude > nodeLatitude)
// nodeLatitude = nodeLongitude;
// _gridScale = nodeLatitude;
//
// // Pas de dessin :
// double stepLatitude = nodeLatitude / 10.0;
// double startLatitude =
// StrictMath.floor(statsLat.minValue / stepLatitude) * stepLatitude;
// double firstLatitude =
// StrictMath.floor(statsLat.minValue / nodeLatitude) * nodeLatitude;
// int countLatitude =
// (int) (StrictMath.ceil(deltaLatitude / nodeLatitude) + 1);
// int countLatPoints =
// (int) (StrictMath.ceil(deltaLatitude / stepLatitude) + 10);
// double stepLongitude = nodeLongitude / 10.0;
// double startLongitude = 0;
// double firstLongitude = 0;
// if (statsLong.minValue < statsLong.maxValue)
// {
// startLongitude =
// StrictMath.floor(statsLong.minValue / stepLongitude)
// * stepLongitude;
// firstLongitude =
// StrictMath.floor(statsLong.minValue / nodeLongitude)
// * nodeLongitude;
// }
// else
// {
// startLongitude =
// StrictMath.floor(statsLong.maxValue / stepLongitude)
// * stepLongitude;
// firstLongitude =
// StrictMath.floor(statsLong.maxValue / nodeLongitude)
// * nodeLongitude;
// }
// int countLongitude =
// (int) (StrictMath.ceil(deltaLongitude / nodeLongitude) + 1);
// int countLongPoints =
// (int) (StrictMath.ceil(deltaLongitude / stepLongitude) + 10);
// double posLong, posLat;
//
// // Allouer :
// _gridLines = new GridLine[countLatitude + countLongitude];
// int indLine = 0;
//
// // Cr�er la grille longitude (verticale) :
// GridLine line;
// Point2D ptProj;
// for (int indLong = 0; indLong < countLongitude; indLong++)
// {
// posLong = firstLongitude + indLong * nodeLongitude;
// line = new GridLine();
// line.x = new double[countLatPoints];
// line.y = new double[countLatPoints];
// line.vertical = true;
// line.label = posLong + "�";
// for (int indLat = 0; indLat < countLatPoints; indLat++)
// {
// posLat = startLatitude + indLat * stepLatitude;
// try
// {
// ptProj = _proj.project(posLat, posLong);
// line.x[indLat] = ptProj.getX();
// line.y[indLat] = ptProj.getY();
// }
// catch (ProjectionException pe)
// {
// }
// }
// _gridLines[indLine++] = line;
// }
//
// // Cr�er la grille latitude (horizontale) :
// for (int indLat = 0; indLat < countLatitude; indLat++)
// {
// posLat = firstLatitude + indLat * nodeLatitude;
// line = new GridLine();
// line.x = new double[countLongPoints];
// line.y = new double[countLongPoints];
// line.vertical = false;
// line.label = posLat + "�";
// for (int indLong = 0; indLong < countLongPoints; indLong++)
// {
// posLong = startLongitude + indLong * stepLongitude;
// try
// {
// ptProj = _proj.project(posLat, posLong);
// line.x[indLong] = ptProj.getX();
// line.y[indLong] = ptProj.getY();
// }
// catch (ProjectionException pe)
// {
// }
// }
// _gridLines[indLine++] = line;
// }
// }
//
// // // Commandes :
//
// /**
// * Method onZoomIn.
// */
// protected void onZoomIn()
// {
// // Changer l'�chelle :
// setScale(_scale * 2.0);
//
// // Mise � jour de l'interface :
// updateUI();
// }
//
// /**
// * Method onZoomOut.
// */
// protected void onZoomOut()
// {
// // Changer l'�chelle :
// setScale(_scale / 2.0);
//
// // Mise � jour de l'interface :
// updateUI();
// }
//
// /**
// * Method onZoomCursor.
// */
// protected void onZoomCursor()
// {
// // S'il n'y a pas de rubber, en cr�er un :
// if (_rubber == null)
// {
// _rubber = new RectRubberband(_ctrlPane);
// _rubber.addJobStateListener(new RuberbandListener() {
// public void actionDone(int x, int y, int cx, int cy)
// {
// onZoomCursorEnded();
// }
// });
// }
// else
// {
// _rubber.release();
// _rubber = null;
// }
//
// // Mise � jour de l'interface :
// updateUI();
// }
//
// /**
// * Method onZoomCursorEnded.
// */
// protected void onZoomCursorEnded()
// {
// // S'il y a un mode en cours :
// if (_rubber != null)
// {
// // Mise � jour de l'�chelle :
// setZoom(_rubber.startX, _rubber.startY, _rubber.lastX
// - _rubber.startX, _rubber.lastY - _rubber.startY);
//
// // Virer le mode :
// _rubber.release();
// _rubber = null;
// }
//
// // Mise � jour de l'interface :
// updateUI();
// }
//
// /**
// * Method onZoomBest.
// */
// protected void onZoomBest()
// {
// // S'il n'y a pas de rubber, en cr�er un :
// setScreenViewport(_scrollPane.getBounds());
// computeBestScale();
// updateLogicMapping();
//
// // Mises � jour :
// Dimension dim = getBestScreenSize();
// setScreenViewport(dim);
// _ctrlPane.setSize(dim);
// _ctrlPane.setMinimumSize(dim);
// _ctrlPane.setPreferredSize(dim);
// _ctrlPane.setMaximumSize(dim);
//
// // Mise � jour de l'interface :
// updateUI();
// }
//
// /**
// * Method onInitWindow.
// */
// protected void onInitWindow()
// {
// }
//
// /**
// * Method onClose.
// */
// protected void onClose()
// {
// }
//
// /**
// * Method OnQuit : r�action � l'utilisation du bouton "Quitter", sortie de
// * la bo�te de dialogue.
// */
// protected void onQuit()
// {
// dispose();
// }
//
// /**
// * Method showFile, m�thode utilis�e pour afficher une page HTML stock�e
// * dans un fichier. La m�thode est modale : elle ne sort que lorsque
// * l'utilisateur appuie sur le bouton "Quitter".
// *
// * @param title
// * nom de la bo�te de dialogue.
// * @param filepath
// * chemin d'acc�s au fichier qui contient la page
// */
// public static void showPath(Component owner, NavPathPoints path,
// GeoProjection proj)
// {
// // Rechercher la frame :
// Component parent = owner;
// while ((parent != null) && !(parent instanceof Frame))
// parent = parent.getParent();
//
// // Cr�er la dialogue :
// PathViewer viewer = new PathViewer((Frame) parent, path, proj);
//
// // Montrer la dialogue :
// viewer.setVisible(true);
// }
}
