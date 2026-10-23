/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.ifremer.sismer.endsandbends.mediation.output;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.swing.JSpinner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import fr.ifremer.sismer.endsandbends.enums.OutputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * @author mbettich
 */
public class OutputParamsObject implements IOutputParamsObject
{

    protected OutputFilesTypes format;

    private File selectedFile;

    public OutputParamsObject()
    {
    }

    public OutputParamsObject(OutputFilesTypes format)
    {
        this.format = format;
    }

    public OutputParamsObject(OutputParamsObject outputParamsObject)
    {
        this.format = outputParamsObject.format;
    }

    public OutputFilesTypes getFormat()
    {
        return format;
    }

    public void setFormat(OutputFilesTypes format)
    {
        this.format = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getSelectedFile()
    {
        return selectedFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedFile(File selectedFile)
    {
        this.selectedFile = selectedFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromResultDialog(ResultsDialog dialog)
    {
        // TODO � impl�menter
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     */
    @Override
    public void convertToXML(File file) throws ParserConfigurationException,
            TransformerConfigurationException, TransformerException,
            IOException

    {
        // TODO � impl�menter
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     */
    @Override
    public boolean isOutputObjectChanged(IOutputParamsObject outputObject)
            throws EABException
    {
        if (this.getClass().equals(outputObject.getClass()))
        {
            Field[] currentObjectFields = this.getClass().getDeclaredFields();
            Field[] outputObjectFields =
                outputObject.getClass().getDeclaredFields();
            try
            {
                for (int i = 0; i < currentObjectFields.length; i++)
                {
                    if ((currentObjectFields[i].get(this) != null && outputObjectFields[i]
                        .get(outputObject) == null)
                            || (currentObjectFields[i].get(this) != null && outputObjectFields[i]
                                .get(outputObject) == null))
                    {
                        return true;
                    }
                    if (currentObjectFields[i].get(this) != null
                            && outputObjectFields[i].get(outputObject) != null)
                    {
                        if (currentObjectFields[i].get(this) instanceof JSpinner)
                        {
// System.out.println("spinner");
                        }
                        else if (!currentObjectFields[i].get(this).equals(
                            outputObjectFields[i].get(outputObject)))
                        {
                            return true;
                        }
                    }
                }
            }
            catch (IllegalArgumentException e)
            {
                throw new EABException(ExceptionCode.ARGUMENT_NULL_OR_EMPTY, e);
            }
            catch (IllegalAccessException e)
            {
                throw new EABException(ExceptionCode.UNKOWN_ERROR, e);
            }
        }
        return false;
    }

}
