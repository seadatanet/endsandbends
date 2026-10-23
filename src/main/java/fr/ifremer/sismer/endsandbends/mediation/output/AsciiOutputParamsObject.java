/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.ifremer.sismer.endsandbends.mediation.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.enums.OutputFilesTypes;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * @author mbettich
 */
public class AsciiOutputParamsObject extends OutputParamsObject
{

    protected String type, operator, comments, campaignId, campaignTitle,
            boatName;

    protected boolean withSeconds;

    public AsciiOutputParamsObject(AsciiOutputParamsObject outputObject)
    {
        super(outputObject);
        this.type = outputObject.type;
        this.operator = outputObject.operator;
        this.comments = outputObject.comments;
        this.campaignId = outputObject.campaignId;
        this.campaignTitle = outputObject.campaignTitle;
        this.boatName = outputObject.boatName;
        this.withSeconds = outputObject.withSeconds;
    }

    /**
     * 
     */
    public AsciiOutputParamsObject()
    {
        super.setFormat(OutputFilesTypes.ASCII_IFREMER_FORMAT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromResultDialog(ResultsDialog dialog)
    {
        if (dialog.getAsciiTypeSurveyRadioButton().isSelected())
        {
            type = "survey";
        }
        else if (!dialog.getAsciiTypeSurveyRadioButton().isSelected())
        {
            type = "data";
        }
        operator = dialog.getIfremerAsciiOperatorNameValue().getText();
        comments = dialog.getIfremerAsciiCommentValue().getText();
        campaignId = dialog.getIfremerAsciiCampainIdentifierValue().getText();
        campaignTitle = dialog.getIfremerAsciiCampainTitleValue().getText();
        boatName = dialog.getIfremerAsciiBoatNameValue().getText();
        withSeconds = dialog.getWriteSeconds().isSelected();
    }

    @Override
    public void convertToXML(File file) throws ParserConfigurationException,
            TransformerConfigurationException, TransformerException,
            IOException
    {

        FileOutputStream fos;

        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element outputModel = doc.createElement("OutputModel");
        doc.appendChild(outputModel);
        outputModel.setAttribute("format", "ASCII_IFREMER_FORMAT");

        Element typeEl = doc.createElement("Type");
        typeEl.setTextContent(String.valueOf(this.getType()));

        Element writeSecondsEl = doc.createElement("WriteSeconds");
        writeSecondsEl.setTextContent(String.valueOf(this.isWithSeconds()));

        Element asciiOperator = doc.createElement("AsciiOperator");
        asciiOperator.setTextContent(this.getOperator().trim());

        Element commentsEl = doc.createElement("Comments");
        commentsEl.setTextContent(this.getComments().trim());

        Element campaignIdEl = doc.createElement("CampaignId");
        campaignIdEl.setTextContent(this.getCampaignId().trim());
        campaignIdEl.getTextContent();
        Element campaignTitleEl = doc.createElement("CampaignTitle");
        campaignTitleEl.setTextContent(this.getCampaignTitle().trim());

        Element boatNameEl = doc.createElement("BoatName");
        boatNameEl.setTextContent(this.getBoatName().trim());

        outputModel.appendChild(typeEl);
        outputModel.appendChild(writeSecondsEl);
        outputModel.appendChild(asciiOperator);
        outputModel.appendChild(commentsEl);
        outputModel.appendChild(campaignIdEl);
        outputModel.appendChild(campaignTitleEl);
        outputModel.appendChild(boatNameEl);

        try
        {
            fos = new FileOutputStream(file.getAbsolutePath());

            // XERCES 1 or 2 additionnal classes.
            OutputFormat of =
                new OutputFormat(Globals.XML, Globals.UTF_8, true);
            of.setIndent(1);
            of.setIndenting(true);
            XMLSerializer serializer = new XMLSerializer(fos, of);
            serializer.serialize(doc);
            serializer.asDOMSerializer();
            fos.close();
        }
        catch (FileNotFoundException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        // As a DOM Serializer

        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /**
     * Getter sur type.
     * 
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter sur type.
     * 
     * @param type
     *            the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Getter sur operator.
     * 
     * @return the operator
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Setter sur operator.
     * 
     * @param operator
     *            the operator to set
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * Getter sur comments.
     * 
     * @return the comments
     */
    public String getComments()
    {
        return comments;
    }

    /**
     * Setter sur comments.
     * 
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments)
    {
        this.comments = comments;
    }

    /**
     * Getter sur campaignId.
     * 
     * @return the campaignId
     */
    public String getCampaignId()
    {
        return campaignId;
    }

    /**
     * Setter sur campaignId.
     * 
     * @param campaignId
     *            the campaignId to set
     */
    public void setCampaignId(String campaignId)
    {
        this.campaignId = campaignId;
    }

    /**
     * Getter sur campaignTitle.
     * 
     * @return the campaignTitle
     */
    public String getCampaignTitle()
    {
        return campaignTitle;
    }

    /**
     * Setter sur campaignTitle.
     * 
     * @param campaignTitle
     *            the campaignTitle to set
     */
    public void setCampaignTitle(String campaignTitle)
    {
        this.campaignTitle = campaignTitle;
    }

    /**
     * Getter sur boatName.
     * 
     * @return the boatName
     */
    public String getBoatName()
    {
        return boatName;
    }

    /**
     * Setter sur boatName.
     * 
     * @param boatName
     *            the boatName to set
     */
    public void setBoatName(String boatName)
    {
        this.boatName = boatName;
    }

    /**
     * Getter sur withSeconds.
     * 
     * @return the withSeconds
     */
    public boolean isWithSeconds()
    {
        return withSeconds;
    }

    /**
     * Setter sur withSeconds.
     * 
     * @param withSeconds
     *            the withSeconds to set
     */
    public void setWithSeconds(boolean withSeconds)
    {
        this.withSeconds = withSeconds;
    }
}