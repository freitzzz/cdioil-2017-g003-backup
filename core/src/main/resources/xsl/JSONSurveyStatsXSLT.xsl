<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : JSONSurveyStatsXSLT.xsl
    Created on : June 12 2018, 10:15
    Author     : <a href="1160936@isep.ipp.pt">Gil Durão</a>
    Description: XSL Document containing the structure of the JSON file containing 
        the statistics related to a survey.
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="3.0">
	<xsl:output method="text" indent="yes"/>

	<xsl:template match="/Survey">
        {
            "surveyid":"<xsl:value-of select="@surveyID"/>",
            "questions":
			{"binaryquestions":[{
		<xsl:for-each select="Questions/BinaryQuestions/BinaryQuestion">
			<!-- Iterates through all Survey BinaryQuestion Labels -->
				"question":{
					"id":"<xsl:value-of select="@questionID"/>",
					"questiontext":"<xsl:value-of select="QuestionText"/>",
					"total":"<xsl:value-of select="Total"/>",
					"average":"<xsl:value-of select="Average"/>",
					"meandeviation":"<xsl:value-of select="MeanDeviation"/>"
                }<xsl:if test = "position() != last()">,</xsl:if>
		</xsl:for-each>
				}]
			,
			"quantitativequestions":[{
		<xsl:for-each select = "Questions/QuantitativesQuestions/QuantitativeQuestion">
				"question":{
					"id":"<xsl:value-of select="@questionID"/>",
					"questiontext":"<xsl:value-of select="QuestionText"/>",
					"total":"<xsl:value-of select="Total"/>",
					"average":"<xsl:value-of select="Average"/>",
					"meandeviation":"<xsl:value-of select="MeanDeviation"/>"
                }<xsl:if test = "position() != last()">,</xsl:if>
		</xsl:for-each>
				}]
			}
        }
	</xsl:template>

</xsl:stylesheet>
