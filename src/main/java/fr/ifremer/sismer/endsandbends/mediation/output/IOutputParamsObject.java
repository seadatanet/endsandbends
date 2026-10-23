/* ============================================================================
 * Nom du fichier	: IInputParamsObject.java
 * Auteur			: Altran
 * Date				: 15 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.mediation.output;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import fr.ifremer.sismer.endsandbends.enums.OutputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public interface IOutputParamsObject
{
    public File getSelectedFile();

    public void setSelectedFile(File selectedFile);

    public OutputFilesTypes getFormat();

    public void setFormat(OutputFilesTypes format);

    public void updateFromResultDialog(ResultsDialog dialog);

    public void convertToXML(File file) throws ParserConfigurationException,
            TransformerConfigurationException, TransformerException,
            IOException;

    public boolean isOutputObjectChanged(IOutputParamsObject inputObject)
            throws EABException;

}
