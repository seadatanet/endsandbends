/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.ifremer.sismer.endsandbends.mediation.input;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import fr.ifremer.sismer.endsandbends.common.CommonTools;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.ui.MainForm;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * @author mbettich
 */
public class InputParamsObject implements IInputParamsObject
{

    protected InputFilesTypes format;

    protected Date startDate, endDate;

    protected boolean isTimeFilterEnabled;

    private File selectedFile;

    private static final String SIMPLEDATEFORMATMASK =
        Messages.getString(Messages.MESSAGES, "SIMPLEDATEFORMATMASK");
    private static final String SIMPLETIMEFORMATMASK =
        Messages.getString(Messages.MESSAGES, "SIMPLETIMEFORMATMASK");
    private static final String SIMPLEDATE_TIMEFORMATMASK =
        SIMPLEDATEFORMATMASK + " " + SIMPLETIMEFORMATMASK;

    public InputParamsObject()
    {
    }

    public InputParamsObject(InputParamsObject inputObject)
    {
        this.format = inputObject.format;
        this.startDate = inputObject.startDate;
        this.endDate = inputObject.endDate;
        this.isTimeFilterEnabled = inputObject.isTimeFilterEnabled;
    }

    public InputParamsObject(InputFilesTypes format,
            boolean isTimeFilterEnabled, Date startDate, Date endDate)
    {
        this.format = format;
        this.isTimeFilterEnabled = isTimeFilterEnabled;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public InputFilesTypes getFormat()
    {
        return format;
    }

    public void setFormat(InputFilesTypes format)
    {
        this.format = format;
    }

    @Override
    public boolean isTimeFilterEnabled()
    {
        return isTimeFilterEnabled;
    }

    public void setIsTimeFilterEnabled(boolean isTimeFilterEnabled)
    {
        this.isTimeFilterEnabled = isTimeFilterEnabled;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
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

    private Date dateFormatter(String unformattedDate, String formattedTime)
            throws ParseException
    {
        Date formattedDate = null;
        UTCDateFormat dateSDF =
            new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_DATE);
        Date tempFormattedDate = dateSDF.parse(unformattedDate);
        UTCDateFormat dateISOSDF =
            new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_DATEISO);
        String finalDateStr = dateISOSDF.format(tempFormattedDate);
        UTCDateFormat isoSDF = new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_ISO);
        formattedDate = isoSDF.parse(finalDateStr + "T" + formattedTime + ".0");
        return formattedDate;
    }

    @Override
    public void updateFromInputDialog(InputDialog dialog) throws EABException
    {

        if (dialog.getStartDate_date().getText() != null
                && !dialog.getStartDate_date().getText().isEmpty()
                && dialog.getStartDate_time().getText() != null
                && !dialog.getStartDate_time().getText().isEmpty())
        {
            try
            {
                this.startDate =
                    dateFormatter(dialog.getStartDate_date().getText(), dialog
                        .getStartDate_time().getText());
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (dialog.getEndDate_date().getText() != null
                && !dialog.getEndDate_date().getText().isEmpty()
                && dialog.getEndDate_time().getText() != null
                && !dialog.getEndDate_time().getText().isEmpty())
        {
            try
            {
                this.endDate =
                    dateFormatter(dialog.getEndDate_date().getText(), dialog
                        .getEndDate_time().getText());
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        isTimeFilterEnabled = dialog.getIsTimeFilteredEnabled().isSelected();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     * @throws EABException
     */
    @Override
    public void convertToXML(File file) throws ParserConfigurationException,
            TransformerConfigurationException, TransformerException,
            IOException

    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not Implemented yet !");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInputObjectChanged(IInputParamsObject inputObject)
    {
        if (this.getClass().equals(inputObject.getClass()))
        {
            if (!CommonTools.equals(this.getStartDate(), inputObject
                .getStartDate()))
            {
                return true;
            }

            if (!CommonTools
                .equals(this.getEndDate(), inputObject.getEndDate()))
            {
                return true;
            }

            Field[] currentObjectFields = this.getClass().getDeclaredFields();
            Field[] inputObjectFields =
                inputObject.getClass().getDeclaredFields();
            try
            {
                for (int i = 0; i < currentObjectFields.length; i++)
                {
                    if ((currentObjectFields[i].get(this) != null && inputObjectFields[i]
                        .get(inputObject) == null)
                            || (currentObjectFields[i].get(this) != null && inputObjectFields[i]
                                .get(inputObject) == null))
                    {
                        return true;
                    }
                    if (currentObjectFields[i].get(this) != null
                            && inputObjectFields[i].get(inputObject) != null)
                    {
                        if (currentObjectFields[i].get(this) instanceof ArrayList<?>
                                && inputObjectFields[i].get(inputObject) instanceof ArrayList<?>)
                        {
                            ArrayList<?> currentField =
                                (ArrayList<?>) currentObjectFields[i].get(this);
                            ArrayList<?> inputField =
                                (ArrayList<?>) inputObjectFields[i]
                                    .get(inputObject);
                            if (currentField.size() == inputField.size())
                            {
                                for (int j = 0; j < currentField.size(); j++)
                                {
                                    DateSegment currentFieldTab =
                                        (DateSegment) currentField.get(j);
                                    DateSegment inputFieldTab =
                                        (DateSegment) inputField.get(j);

                                    if (!CommonTools.equals(currentFieldTab
                                        .getStartDate(), inputFieldTab
                                        .getStartDate())
                                            || !CommonTools.equals(
                                                currentFieldTab.getEndDate(),
                                                inputFieldTab.getEndDate()))
                                    {
                                        return true;
                                    }
                                }
                            }
                            else
                            {
                                return true;
                            }
                        }
                        else if (!currentObjectFields[i].get(this).equals(
                            inputObjectFields[i].get(inputObject)))
                        {
                            return true;
                        }
                    }
                }
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

}
