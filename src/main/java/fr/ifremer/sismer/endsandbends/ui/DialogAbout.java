package fr.ifremer.sismer.endsandbends.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.ResourceLoader;

/**
 * @author ATLANTIDE - ABT Bo�te de dialogue utilis�e pour le calcul personalis�
 */
public class DialogAbout extends javax.swing.JDialog
{
    // Contr�les :
    private final JEditorPane _ctrlText = new JEditorPane("text/html",
        "<HTML></HTML>");
    private final JScrollPane _scrollPane = new JScrollPane(_ctrlText);

    private final JButton _ctrlBtnQuit = new JButton();

    /**
     * Constructor for DialogUserCompute.
     */
    public DialogAbout(Frame parent)
    {
        super(parent, Messages.getString(Messages.MESSAGES, "title_dlg_about"), //$NON-NLS-1$
            true);
        initComponents();
    }

    /**
     * Chargement d'une page qui se trouve dans les ressources
     * 
     * @param path
     *            chemin d'acc�s � la page...
     * @return String
     */
    private String getResourcePage(String pagename)
    {
        String valueToReturn = ""; //$NON-NLS-1$

        String path =
            new StringBuilder("/fr/ifremer/sismer/endsandbends/resources/")
                .append(pagename).toString();
        InputStream stream = ResourceLoader.getStream(this, path);
        InputStreamReader reader = null;
        StringWriter writer = null;
        char[] buffer = new char[256];
        try
        {
            reader = new InputStreamReader(stream);
            writer = new StringWriter();
            while (reader.ready())
            {
                int size = reader.read(buffer);
                writer.write(buffer, 0, size);
            }

            valueToReturn = writer.toString();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != reader)
                {
                    reader.close();
                }

                if (null != writer)
                {
                    writer.close();
                }
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return valueToReturn;
    }

    /**
     * Cr�ation des contr�les
     */
    private void initComponents()
    {
        // D'abord initialiser le Layout :
        getContentPane().setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 600));
        java.awt.GridBagConstraints gridBagConstraints;

        // Récupérer la page "about" et paramétrer le contrôle :
        _ctrlText.setMargin(new Insets(5, 5, 5, 5));
        _ctrlText.setEditable(false);
        _ctrlText.setContentType("text/html");
        try {
            URL aboutURL = ResourceLoader.getURL(this, "/fr/ifremer/sismer/endsandbends/resources/about.htm");
            if (aboutURL != null) {
                _ctrlText.setPage(aboutURL);
            } else {
                _ctrlText.setText(getResourcePage("about.htm"));
            }
        } catch (Exception e) {
            _ctrlText.setText(getResourcePage("about.htm"));
        }

        // Texte d'aide :
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        _ctrlText.setBorder(BorderFactory.createLoweredBevelBorder());
        getContentPane().add(_scrollPane, gridBagConstraints);
        _ctrlText.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e)
            {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                    onLink(e.getDescription(), e.getURL());
            }
        });

        // Ajouter les boutons :
        // SVO modif du nom du bouton de la page d'accueil, remplace exit par OK
        _ctrlBtnQuit.setText(Messages.getString(Messages.MESSAGES, "btn_ok")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        _ctrlBtnQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        getContentPane().add(_ctrlBtnQuit, gridBagConstraints);

        // Ajuster la taille :
        pack();
    }

    /**
     * Method onLink.
     * 
     * @param string
     * @param uRL
     */
    private void onLink(String description, URL url)
    {
        try
        {
            if (url != null)
            {
                if (url.getProtocol().equals("res"))
                    _ctrlText.setText(getResourcePage(url.getFile()));
                else if (url.getProtocol().equals("file"))
                    _ctrlText.setPage(url);
            }
            else
                _ctrlText.setText(getResourcePage(description));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Affichage de la fen�tre :
     */
    public void showDialog()
    {
        // Ajuster la taille :
        pack();
        // Positionner la fen�tre :
        setBounds(50, 50, 800, 700);

// Rectangle rect = getOwner().getBounds();
// Dimension dim = getSize();
// setBounds(rect.x + ((rect.width - dim.width) / 2), rect.y
// + ((rect.height - dim.height) / 2), dim.width, dim.height);

        // Montrer la dialogue :
        setVisible(true); // appel bloquant parce que la bo�te est modale...
    }
}
