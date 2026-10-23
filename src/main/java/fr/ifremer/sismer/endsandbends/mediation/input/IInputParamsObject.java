/* ============================================================================
 * Nom du fichier	: IInputParamsObject.java
 * Auteur			: Altran
 * Date				: 15 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.mediation.input;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public interface IInputParamsObject
{
    public File getSelectedFile();

    public void setSelectedFile(File selectedFile);

    public InputFilesTypes getFormat();

    public void setFormat(InputFilesTypes format);

    public void updateFromInputDialog(InputDialog dialog) throws EABException;

    public void convertToXML(File file) throws ParserConfigurationException,
            TransformerConfigurationException, TransformerException,
            IOException;

    public Date getStartDate();

    public Date getEndDate();

    public boolean isInputObjectChanged(IInputParamsObject inputObject);

    /**
     * @return
     */
    boolean isTimeFilterEnabled();

}
