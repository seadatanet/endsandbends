/* ============================================================================
 * Nom du fichier	: OutputDialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import fr.ifremer.sismer.endsandbends.common.DefaultFolder;
import fr.ifremer.sismer.endsandbends.common.DefaultsHandler;
import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.enums.OutputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.mediation.output.IOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class OutputDialogController implements IOutputDialogController
{
    ResultsDialog dialog;
    OutputParamsObject outputObject;

    /**
     * Liste des erreurs sur le contenu des champs.
     */
    private final List<String> _errorsMessages = new ArrayList<String>();

    /**
     * Getter sur outputObject.
     * 
     * @return the outputObject
     */
    public OutputParamsObject getOutputObject()
    {
        return outputObject;
    }

    /**
     * Setter sur outputObject.
     * 
     * @param outputObject
     *            the outputObject to set
     */
    public void setOutputObject(OutputParamsObject outputObject)
    {
        this.outputObject = outputObject;
    }

    public OutputDialogController(ResultsDialog dialog,
            OutputParamsObject outputObject)
    {
        this.dialog = dialog;
        this.outputObject = outputObject;
        this.dialog.setOutputController(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeDialog()
    {
        initializeDialogDefault();
    }

    /**
     * {@inheritDoc}
     */
    public void initializeDialogDefault()
    {
        dialog.getFileTypeValue().setSelectedItem(
            Messages.getString(Messages.MESSAGES, outputObject.getFormat()
                .getOutputFileType()));
        dialog.getFileTypeValue().setEnabled(false);
    }

    /**
     * Getter sur _errorsMessages.
     * 
     * @return the errorsMessages
     */
    public List<String> getErrorsMessages()
    {
        return _errorsMessages;
    }

    /**
     * Add an errorMessage.
     */
    public void addErrorMessage(final String msg)
    {
        _errorsMessages.add(msg);
    }

    /**
     * Add an errorMessage for a missing mandatory field.
     */
    public void addRequiredFieldMissingError(final String aField)
    {
        addErrorMessage(Messages.getString(Messages.MESSAGES,
            "mandatory.field.missing", new String[] {aField})); //$NON-NLS-1$ // NOI18N
    }

    /**
     * Remove an errorMessage.
     */
    public void removeErrorMessage(final String msg)
    {
        _errorsMessages.remove(msg);
    }

    /**
     * Clear errorsMessages.
     */
    public void clearErrorsMessages()
    {
        _errorsMessages.clear();
    }

    /**
     * Effectue la validation des donn�es du formulaire d'entr�e.
     */
    public boolean validateForm()
    {
        clearErrorsMessages();
        // R�sultat de la validation
        boolean result = true;
        IOutputParamsObject currentOutputObject =
            dialog.getCurrentOutputObject();
        String outputFilePath = dialog.getInputFilePath().getText();
        // Gestion du type de fichier
        currentOutputObject.setFormat(OutputFilesTypes
            .translate((String) dialog.getFileTypeValue().getSelectedItem()));

        // L'emplacement du fichier de g�n�ration est partiellement g�r� par la
        // m�thode chooseInputFile(). On v�rifie ici que l'utilisateur ne l'a
        // pas supprim� ensuite.

        // Il faut �galement v�rifier la saisie du path direct
        if (outputFilePath == null || outputFilePath.isEmpty())
        {
            addRequiredFieldMissingError("output file path"); //$NON-NLS-1$ // NOI18N
            result = false;
        }
        else
        {
            if (null == new File(outputFilePath).getParent())
            {
                addErrorMessage(Globals.WRONG_FILEPATH);
                result = false;
            }
        }

        // V�rification de la correction � apporte au fuseau horaire pour le
        // format ASCII � colonnes.
        if (OutputFilesTypes.COLUMNS_ASCII_FORMAT.equals(OutputFilesTypes
            .translate((String) dialog.getFileTypeValue().getSelectedItem())))
        {
            String timeToApply =
                String.valueOf(dialog.getTimeToApply().getValue());
            String tokenizer = dialog.getColumnsAsciiTokenizer().getText();
            String startIndicator = dialog.getStartIndicatorField().getText();
            String endIndicator = dialog.getEndIndicatorField().getText();
            String startEndIndicatorColumnNUmber =
                dialog.getStartEndIndicatorColumnNumber().getText();
            if (timeToApply == null || timeToApply.isEmpty())
            {
                addRequiredFieldMissingError("Time correction"); //$NON-NLS-1$ // NOI18N
                result = false;
            }

            if (tokenizer == null || tokenizer.isEmpty())
            {
                addRequiredFieldMissingError("Tokenizer"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
            if (startIndicator == null || startIndicator.isEmpty())
            {
                addRequiredFieldMissingError("Start Indicator"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
            if (endIndicator == null || endIndicator.isEmpty())
            {
                addRequiredFieldMissingError("End Indicator"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
            if (startEndIndicatorColumnNUmber == null
                    || startEndIndicatorColumnNUmber.isEmpty()
                    || Integer.valueOf(startEndIndicatorColumnNUmber) <= 0)
            {
                addRequiredFieldMissingError("Start End Indicator Column Number"); //$NON-NLS-1$
                result = false;
            }
            boolean isJoinDateTime =
                dialog.getColumnsAsciiGroupSeparateDateTimeFormatsCheckBox()
                    .isSelected();
            if (isJoinDateTime)
            {
                if (dialog.getColumnsAsciiDatetimeColumnNumber().getText() == null
                        || dialog.getColumnsAsciiDatetimeColumnNumber()
                            .getText().isEmpty()
                        || Integer.valueOf(dialog
                            .getColumnsAsciiDatetimeColumnNumber().getText()) <= 0)
                {
                    addRequiredFieldMissingError("date & time column number"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getColumnsAsciiDatetimeFormats().getSelectedItem() == null)
                {
                    addRequiredFieldMissingError("date & time format"); //$NON-NLS-1$
                    result = false;
                }
            }
            if (!isJoinDateTime)
            {
                if (dialog.getColumnsAsciiDateColumnNumber().getText() == null
                        || dialog.getColumnsAsciiDateColumnNumber().getText()
                            .isEmpty()
                        || Integer.valueOf(dialog
                            .getColumnsAsciiDateColumnNumber().getText()) <= 0)
                {
                    addRequiredFieldMissingError("date column number"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getColumnsAsciiDateFormats().getSelectedItem() == null)
                {
                    addRequiredFieldMissingError("date format"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getColumnsAsciiTimeColumnNumber().getText() == null
                        || dialog.getColumnsAsciiTimeColumnNumber().getText()
                            .isEmpty()
                        || Integer.valueOf(dialog
                            .getColumnsAsciiTimeColumnNumber().getText()) <= 0)
                {
                    addRequiredFieldMissingError("time column number"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getColumnsAsciiTimeFormats().getSelectedItem() == null)
                {
                    addRequiredFieldMissingError("time format"); //$NON-NLS-1$
                    result = false;
                }
            }

            if (dialog.getColumnsAsciiLatitudeColumnNumber().getText() == null
                    || dialog.getColumnsAsciiLatitudeColumnNumber().getText()
                        .isEmpty()
                    || Integer.valueOf(dialog
                        .getColumnsAsciiLatitudeColumnNumber().getText()) <= 0)
            {
                addRequiredFieldMissingError("latitude column number"); //$NON-NLS-1$
                result = false;
            }
            if (dialog.getColumnsAsciiLatitudeFormat().getSelectedItem() == null
                    || ((String) dialog.getColumnsAsciiLatitudeFormat()
                        .getSelectedItem()).isEmpty())
            {
                addRequiredFieldMissingError("latitude format"); //$NON-NLS-1$
                result = false;
            }
            if (dialog.getColumnsAsciiLongitudeColumnNumber().getText() == null
                    || dialog.getColumnsAsciiLongitudeColumnNumber().getText()
                        .isEmpty()
                    || Integer.valueOf(dialog
                        .getColumnsAsciiLongitudeColumnNumber().getText()) <= 0)
            {
                addRequiredFieldMissingError("longitude column number"); //$NON-NLS-1$
                result = false;
            }
            if (dialog.getColumnsAsciiLongitudeFormat().getSelectedItem() == null
                    || ((String) dialog.getColumnsAsciiLongitudeFormat()
                        .getSelectedItem()).isEmpty())
            {
                addRequiredFieldMissingError("longitude format"); //$NON-NLS-1$
                result = false;
            }
        }
        // V�rification de la correction � apporte au fuseau horaire pour le
        // format ASCII � colonnes.
        if (OutputFilesTypes.ASCII_IFREMER_FORMAT.equals(OutputFilesTypes
            .translate((String) dialog.getFileTypeValue().getSelectedItem())))
        {
            if (dialog.getIfremerAsciiBoatNameValue().getText() == null
                    || dialog.getIfremerAsciiBoatNameValue().getText()
                        .isEmpty())
            {
                addRequiredFieldMissingError("Boat name"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
            if (dialog.getIfremerAsciiCampainIdentifierValue().getText() == null
                    || dialog.getIfremerAsciiCampainIdentifierValue().getText()
                        .isEmpty())
            {
                addRequiredFieldMissingError("Campain Id"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
            if (dialog.getIfremerAsciiCampainTitleValue().getText() == null
                    || dialog.getIfremerAsciiCampainTitleValue().getText()
                        .isEmpty())
            {
                addRequiredFieldMissingError("Campain title"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
            if (dialog.getIfremerAsciiCommentValue().getText() == null
                    || dialog.getIfremerAsciiCommentValue().getText().isEmpty())
            {
                addRequiredFieldMissingError("Comments"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
            if (dialog.getIfremerAsciiOperatorNameValue().getText() == null
                    || dialog.getIfremerAsciiOperatorNameValue().getText()
                        .isEmpty())
            {
                addRequiredFieldMissingError("Operator"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
        }
        // V�rification de la correction � apporte au fuseau horaire pour le
        // format ASCII � colonnes.
        if (OutputFilesTypes.WKT_FORMAT.equals(OutputFilesTypes
            .translate((String) dialog.getFileTypeValue().getSelectedItem())))
        {
            String wktResolution =
                String.valueOf(dialog.getWktResolutionValue().getValue());
            if (wktResolution == null || wktResolution.isEmpty())
            {
                addRequiredFieldMissingError("Resolution"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
        }
        // V�rification de la correction � apporte au fuseau horaire pour le
        // format ASCII � colonnes.
        if (OutputFilesTypes.GML_FORMAT.equals(OutputFilesTypes
            .translate((String) dialog.getFileTypeValue().getSelectedItem())))
        {
            String gmlResolution =
                String.valueOf(dialog.getGmlResolutionValue().getValue());
            if (gmlResolution == null || gmlResolution.isEmpty())
            {
                addRequiredFieldMissingError("Resolution"); //$NON-NLS-1$ // NOI18N
                result = false;
            }
        }

        // Conservation du type campagne ou donn�es pour le format ASCII Ifremer
        // P50 effectu� par ifremerAsciiCamapagnButtonActionPerformed et
        // ifremerAsciiDataButtonActionPerformed

        // Conservation de l'information g�n�ration du fichier ou inserttion
        // dans le fichier Mikado pour le format GML effectu� par
        // generateGmlFileRadioChanged et inserIntoMikadoFileRadioChanged
        if (!result)
        {
            showErrorDialog();
        }
        return result;
    }

    public void showErrorDialog()
    {
        List<String> errors = getErrorsMessages();
        StringBuilder concatErrors = new StringBuilder();
        for (String anError : errors)
        {
            concatErrors.append(anError).append("\n"); //$NON-NLS-1$ // NOI18N
        }
        clearErrorsMessages();
        JOptionPane.showMessageDialog(dialog, concatErrors.toString());
    }

    public void saveOutputFile() throws EABException
    {
        int n =
            JOptionPane.showConfirmDialog(dialog,
                Globals.SAVE_PARAMETER_FILE_QUESTION,
                Globals.SAVE_PARAMETER_FILE_TITLE, JOptionPane.YES_NO_OPTION);

        if (JOptionPane.OK_OPTION == n)
        {// INFO CLT Interface de demande de renseignements sur le
            // fichier

            // Create a file chooser
            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(DefaultsHandler
                .getDefaultPath(DefaultFolder.OUTPUT_PARAMETER_FOLDER)));

            // In response to a button click:
            int returnVal = fc.showOpenDialog(dialog);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File chosenFile = fc.getSelectedFile();

                DefaultsHandler.setDefaultPath(
                    DefaultFolder.OUTPUT_PARAMETER_FOLDER, chosenFile
                        .getParent());

                try
                {
                    dialog.getCurrentOutputObject().convertToXML(chosenFile);
                }
                catch (TransformerConfigurationException e)
                {
                    JOptionPane
                        .showMessageDialog(
                            dialog.getParent(),
                            Messages.getString(Messages.MESSAGES,
                                "TRANSFORMER_CONFIGURATION_EXCEPTION"), "Transformer creation Error", //$NON-NLS-1$
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (ParserConfigurationException e)
                {
                    JOptionPane.showMessageDialog(dialog.getParent(), Messages
                        .getString(Messages.MESSAGES,
                            "PARSER_CONFIGURATION_EXCEPTION"), "Parsing Error", //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
                }
                catch (TransformerException e)
                {
                    JOptionPane
                        .showMessageDialog(
                            dialog.getParent(),
                            Messages.getString(Messages.MESSAGES,
                                "TRANSFORMER_EXCEPTION"), "Dom to stream conversion Error", //$NON-NLS-1$
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (IOException e)
                {
                    JOptionPane.showMessageDialog(dialog.getParent(),
                        Messages.getString(Messages.MESSAGES,
                            "IO_EXCEPTION_WRITE"), "File write Error", //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
